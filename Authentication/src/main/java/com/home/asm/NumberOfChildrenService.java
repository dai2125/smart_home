package com.home.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.objectweb.asm.Opcodes.ASM9;

public class NumberOfChildrenService {

    private ClassLoader classLoader;
    private String targetClassName;
    private Set<String> childClasses = new HashSet<String>();

    public NumberOfChildrenService(String targetClassName) {

        //        this.classLoader = getClass().getClassLoader();
        this.classLoader = getClass().getClassLoader();
        this.targetClassName = targetClassName;
    }

    // TODO wurde neu implementiert, später wieder kontrollieren, vielleicht kannst du über superName überprüfen ob das Elternteil ein Abstract Class ist, ausprobieren
    public int calculateNOC(HashSet<String> test) throws IOException {
        int[] counter = {0};
        Iterator<String> iterator = test.iterator();
        //System.out.println("NoC targetClassName: " + targetClassName);

        while(iterator.hasNext()) {
            String className = iterator.next();
            ClassReader classReader = new ClassReader(className);

            if(!className.equals(targetClassName)) {

                classReader.accept(new ClassVisitor(ASM9) {
                    @Override
                    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                        //System.out.println("NoC visit(): " + name + ", superName: " + superName + ", interfaces: " + Arrays.toString(interfaces));
                        if(superName.equals(targetClassName)) {
                            counter[0]++;
                        }
                        for(int i = 0; i < interfaces.length; i++) {
                            //System.out.println("NoC interface1: " + targetClassName);
                            //System.out.println("NoC interface1: " + interfaces[i]);
                            if(interfaces[i].equalsIgnoreCase(targetClassName)) {
                                //System.out.println("Noc interface2: " + interfaces[i]);
                                counter[0]++;
                            }
                        }
                        super.visit(version, access, name, signature, superName, interfaces);
                    }
                }, 0);
            }
        }
        //System.out.println("NoC counter: " + counter[0]);
        return counter[0];
    }

    public int calculateNOC(String basePackage) {
        try {
            //System.out.println("NoC: basePackage: " + basePackage);
//            System.out.println("NoC: " + basePackage.replace('.', '/'));
//            String baseDir = basePackage.replace('.', '/');
            String baseDir = basePackage.replaceAll("\\\\", "/");

//            baseDir = baseDir.replaceAll("\\\\", "/");
            //System.out.println("Noc: baseDir: "+ baseDir);
            baseDir = basePackage.replaceAll(".*/", "");
//            baseDir = basePackage.replaceAll("C:/Users/Lenovo/IdeaProjects/Authentication/Authentication/src/main/java/", "");
            //System.out.println("Noc: basePackage: " + basePackage);
//            String resourcePath = classLoader.getResource(baseDir).getPath();
            File packageDir = new File(basePackage);
            //System.out.println("NoC: packageDir: "+ packageDir + " " + packageDir.getName() + " " + packageDir.getAbsolutePath());

            scanPackageForChildren(packageDir, basePackage);

            for(String child : childClasses) {
                System.out.println(" - " + child);
            }

            return childClasses.size();
        } catch(Exception e) {
            System.err.println("Fehler bei der NOC-Berechnung: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    private void scanPackageForChildren(File packageDir, String packageName) throws IOException {
        File[] files = packageDir.listFiles();
        if(files == null) {
            //System.out.println("NoC scanPackageForChildren: files == null: " + packageName);
            return;
        }

        for(File file : files) {
            if(file.isDirectory()) {
                scanPackageForChildren(file, packageName + "." + file.getName());
            } else if(file.getName().endsWith(".class")) {
//                System.out.println("file.getName(): " + file.getName());
                //System.out.println("NoC scanPackageForChildren(): " + file.getName());
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                checkIfChildClass(className);
            } else if(file.getName().endsWith(".java")) {
                System.out.println("NoC scanPackageForChildren() file.getName(): " + file.getName());
                String className = file.getName().replace(".java", "");
                String path = file.getAbsolutePath();
                String baseDir = System.getProperty("user.dir");
                path = path.replace(baseDir + "\\", "");
                path = path.replace("\\", "/");
                path = path.replace(".java", "");

                System.out.println("NoC packageName: "  +   packageName);

                System.out.println("NoC scanPackageForChildren() path: " + path);

                ClassReader reader = new ClassReader(className);

            }
        }
    }

    private void checkIfChildClass(String className) {
        try {
            String internalClassName = className.replace('.', '/');
            System.out.println("NoC checkIfChildClass(): " + internalClassName);
            FileInputStream fis = new FileInputStream(classLoader.getResource(internalClassName + ".class").getPath());
            ClassReader reader = new ClassReader(fis);

            NumberOfChildrenService.SuperClassChecker checker = new NumberOfChildrenService.SuperClassChecker();
            reader.accept(checker, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

//            System.out.println("reader: " + reader.getClassName());
//            System.out.println("reader: " + reader.getSuperName());
            for(int i = 0; i < reader.getInterfaces().length; i++) {
//                System.out.println(reader.getInterfaces()[i]);
                if(reader.getInterfaces()[i].equals(targetClassName)) {
                    childClasses.add(className);
                }
            }

//            System.out.println("targetClassName: " + targetClassName);
//            System.out.println("checker.getSuperClassName(): " + checker.getSuperClassName());
//            System.out.println("checker: " + checker.superClassName);
            if(targetClassName.equals(checker.getSuperClassName())) {
                childClasses.add(className);
            }

            fis.close();
        } catch(IOException e) {
            System.err.println("Fehler beim Prüfen von " + className + ": " + e.getMessage());
        }
    }

    private static class SuperClassChecker extends ClassVisitor {
        private String superClassName;

        public SuperClassChecker() {
            super(Opcodes.ASM9);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.superClassName = superName;
        }

        public String getSuperClassName() {
            return superClassName;
        }
    }
}
