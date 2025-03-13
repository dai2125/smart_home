package com.home.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.analysis.*;
import org.objectweb.asm.util.Textifier;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

import static org.objectweb.asm.Opcodes.*;

public class CustomClassWriter {

    static String className = "java.lang.Integer";
    static String cloneableInterface = "java/lang/Cloneable";
    ClassReader reader;
    ClassWriter writer;
    AddInterfaceAdapter addInterfaceAdapter;
    PublicizeMethodAdapter pubMethAdapter;
    final static String CLASSNAME = "com/home/pureFabrication/fifthExample/PayByPayPal";
//    private final String CLASSNAME = "com/home/creator/BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA/thirdExample/ClassB";
//    final static String CLASSNAME = "com/home/creator/BContainsOrCompositelyAggregatesA/movieExample/MovieProduction";
//    final static String CLASSNAME = "com/home/creator/BContainsOrCompositelyAggregatesA/badExample/Car";
//    final static String CLASSNAME = "com/home/creator/BCloselyUsesA/goodExample/ReportGenerator";
//    final static String CLASSNAME = "com/home/creator/BCloselyUsesA/badExample/ReportGenerator";


    private final String MODELNAME = "com/home/creator/BHasTheInitializingDataForAThatWillBePassedToAWhenItIsCreatedThusBIsAnExpertWithRespectToCreatingA/thirdExample/ClassA";
//    final static String MODELNAME = "com/home/creator/BContainsOrCompositelyAggregatesA/movieExample/Movie";
//    final static String MODELNAME = "com/home/creator/BContainsOrCompositelyAggregatesA/badExample/Engine/badExample/Engine";
//    final static String MODELNAME = "com/home/creator/BCloselyUsesA/goodExample/Calculator";
//    final static String MODELNAME = "com/home/creator/BCloselyUsesA/badExample/Calculator";
    final static String RETURN = "RETURN\n,";
    final static String IRETURN = "IRETURN\n,";
    final static String ARETURN = "ARETURN\n,";
    final static String LOCALVARIABLE = "LOCALVARIABLE";
    final static String GETFIELD = "GETFIELD";
    final static String PUTFIELD = "PUTFIELD";
    final static String THIS = "this";
    final static String PUBLIC = "public";
    final static String PRIVATE = "private";
    final static String PROTECTED = "protected";
    final static String STATIC = "static";
    final static String NEW = "NEW";

    final static String CLONEABLE = "java/lang/Cloneable";

    public static void main(String[] args) {
        CustomClassWriter ccw = new CustomClassWriter();
        ccw.publicizeMethod();
        //ccw.addInterface();

        //byte[] modifiedClass1 = ccw.addField();

        // Teste das Öffentlichmachen einer Methode
        //byte[] modifiedClass2 = ccw.publicizeMethod();

        // Teste das Hinzufügen eines Interfaces
        //byte[] modifiedClass3 = ccw.addInterface();
    }

    public class PublicizeMethodAdapter extends ClassVisitor {

        final Logger logger = Logger.getLogger("PublicizeMethodAdapter");
        TraceClassVisitor tracer;
        PrintWriter pw = new PrintWriter(System.out);

        public PublicizeMethodAdapter(ClassVisitor cv) {
            super(ASM4, cv);
            this.cv = cv;
            tracer = new TraceClassVisitor(cv, pw);
        }

        @Override
        public MethodVisitor visitMethod(int access,
                                         String name,
                                         String desc,
                                         String signature,
                                         String[] exceptions) {

            if (name.equals("toUnsignedString0")) {
                logger.info("Visiting unsigned method");
                return tracer.visitMethod(ACC_PUBLIC + ACC_STATIC, name, desc, signature, exceptions);
            }
            return tracer.visitMethod(access, name, desc, signature, exceptions);
        }

        public void visitEnd() {
            tracer.visitEnd();

            String text = tracer.p.getText().toString();
            String[] words = text.split(" ");

            StringBuilder sb = new StringBuilder();
            StringBuilder name = new StringBuilder();
            StringBuilder object = new StringBuilder();

            boolean first = true;
            Model model = new Model();
            model.setName(MODELNAME);

            int constructorReturn = 0;

            for(int i = 0; i < words.length; i++) {
                if(words[i].contains("RETURN")) {
                    i++;
                    constructorReturn = i;
                    for(int j = i; j < words.length; j++) {
                        // TODO es können mehrere Objekte im Contstructor initialisiert werden
                        // TODO passe den Code hier an, das mehrere Objekte überprüft werden oder
                        // TODO gib den Namen vor/an nach dem Objekt das du überprüfen möchtest
                        if(words[j].equals("access") && words[j + 1].equals("flags")) {
                            i = words.length - 1;
                            break;
                        }

                        if(first) {
                            if(words[j].equals("LOCALVARIABLE") && words[j + 1].equals("this")) {
                                name.append(words[j + 2]);
                                model.setOwner(words[j + 2]);
                                first = false;
                                j += 3;
                            }
                        }

                        if(!first) {
                            if(words[j].equals("LOCALVARIABLE")) {
                                model.setFieldName(model.getOwner()
                                                    .replaceFirst("L", "")
                                                    .replaceAll(";", "") + "." + words[j + 1]);
                                model.setType(words[j + 2]);
// TODO                                model.setName(words[j + 2]);
//                                model.setName(words[j + 2] + " " + words[j] + " " + words[j + 1]  + " i: " + i);
//                                System.out.println("VVVVVV: " + words[j] + " " + words[j + 1] + " " + words[j + 2]);
//                                model.setName(MODELNAME);
                                object.append(words[j + 2]);

                                i = words.length - 1;
                                break;
                            }
                        }
                    }
                }
            }

            /* FELDER VOR DEM CONSTRUCTOR STATEMENT DURCHSUCHEN */
            boolean attributesSet = false;
            if(model.getType() == null) {
                System.out.println("FFFFFFFFFFFFFFFFFFFFFF " + model.getName());
                for(int i = 0; i < words.length; i++) {
                    if(words[i].equals(RETURN)) {
                        break;
                    }

                    if(words[i].equals(NEW) && words[i + 1].equals(model.getName() + "\n,")) {
                        System.out.println(NEW + " " + words[i] + " " + words[i + 1] + " " + words[i + 2]);
                    }

                    if(words[i].equals("INVOKESPECIAL") && words[i + 1].equals(model.getName() + ".<init>")) {
                        System.out.println("INVOKESPECIAL" + " " + words[i] + " " + words[i + 1] + " " + words[i + 2]);
                        model.setInvokeSpecial(words[i + 1]);

                    }

                    if(words[i].equals(PUTFIELD) && words[i + 2].equals(":") && words[i + 3].contains(model.getName())) {
                        System.out.println(PUTFIELD + " " + words[i] + " " + words[i + 1] + " " + words[i + 2]);
                        model.setFieldName(words[i + 1]);
                        model.setType(words[i + 3]);
                        attributesSet = true;
                    }

                }

            }
            System.out.println("\n" + model.toString() + "\n");

            Class<?> clazz;
            Field[] fields;
            Method[] methods;
            Class<?> clazz2;
            Field[] fields2;
            Method[] methods2;

            try {
                clazz = Class.forName(CLASSNAME.replaceAll("/", ".").replaceAll("\n", "").trim());
                fields = clazz.getDeclaredFields();
                methods = clazz.getDeclaredMethods();

                clazz2 = Class.forName(model.getClazzName());
                fields2 = clazz2.getDeclaredFields();
                methods2 = clazz2.getDeclaredMethods();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
//                System.out.println("Couldnt find the class");
            }

            model.setAmountOfFields(fields2.length);
            model.setAmountOfMethods(methods2.length);

            for(int i = 0; i < fields2.length; i++) {
                model.addField(fields2[i].getName());
            }

            for(int i = 0;i < methods2.length; i++) {
                model.addMethod(methods2[i].getName());
            }

            for(int i = 0; i < fields.length; i++) {
                if(fields[i].getType().toString().contains(model.getClazzName())) {
                    model.setField(true);
                }
            }

            /* CONSTRUCTOR && FIELDS */
            for(int i = constructorReturn; i > 0; i--) {

                if(words[i].equals("PUTFIELD")
                        && words[i + 1 ].equals(model.getFieldName())
                        && words[i + 3].contains(model.getType())) {
                    model.setConstructorInitialized(true);
                }

                if(model.getType() != null) {
                    if(words[i].equals("public")
                            && words[i + 1].startsWith("<init>(")
                            && words[i + 1].endsWith(")V\n,")
                            && words[i + 1].contains(model.getType())) {
                        System.out.println("CONSTRUCTOR PARAMETER FOUND " + i);
                        System.out.println(words[i + 1]);
                        model.setConstructorParameter(true);
                    }
                }

                if (words[i].equals("GETFIELD")
                        && words[i + 1].equals(model.getFieldName())) {
                    model.increaseFieldCounter();
                    System.out.println("FIELD NAME FOUND " + words[i + 1] + " i: " + i);
                }
            }

            /* METHODS */
            boolean insideOfAMethod = false;
            boolean methodUsesObject = false;
            String methodName = "";

            for(int i = constructorReturn; i < words.length; i++) {

                if(words[i].matches("public|private|protected")) {
                    if(words[i + 1].matches("static")
                        && words[i + 2].matches("([\\w$]+)\\((.*?)\\)([A-Za-z0-9_/;\\[\\]]+)(?:\n,)?")) {

                        insideOfAMethod = true;
                        methodName = words[i + 2].replaceAll("\n", "").replaceAll(",$", "");

                        System.out.println("ZZZZZZ A METHOD: " + words[i] + " " + words[i + 1] + " " + methodName);
                    } else if(words[i + 1].matches("([\\w$]+)\\((.*?)\\)([A-Za-z0-9_/;\\[\\]]+)(?:\\n,)?")) {

                        insideOfAMethod = true;
                        methodName = words[i + 1].replaceAll("\n", "").replaceAll(",$", "");

                        System.out.println("XXXXXX A METHOD: " + words[i] + " " + words[i + 1] + " " + methodName);
                    }
                }

                if(insideOfAMethod) {
                    if(words[i].equals("GETFIELD") && words[i + 1].equals(model.getFieldName())) {
                        System.out.println("BBBBB: " + words[i] + " " + words[i + 1]);
                        if(!methodUsesObject) {
                            methodUsesObject = true;
                            model.increaseMethodCounter();
                        }
                    }
                }

                if(words[i].equals("RETURN\n,") || words[i].equals("ARETURN\n,") || words[i].equals("IRETURN\n,")) {
                    System.out.println(words[i].replaceAll(" ", "%").replaceAll("\n", "!"));
                    model.addToOwnerMethods(methodName, methodUsesObject);
                    methodName = "";
                    methodUsesObject = false;
                    model.increaseOwnerTotalMethods();
                }
            }

            System.out.println("\n" + model.toString() + "\n");
            model.getAllOwnerMethod();

            bCloselyUsesA(model);
        }
    }

    public CustomClassWriter() {

        try {
            reader = new ClassReader(CLASSNAME);
            writer = new ClassWriter(reader, 0);

        } catch (IOException ex) {
            Logger.getLogger(CustomClassWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CustomClassWriter(byte[] contents) {
        reader = new ClassReader(contents);
        writer = new ClassWriter(reader, 0);
    }

    public byte[] addField() {
        var addFieldAdapter = new AddFieldAdapter("aNewBooleanField", org.objectweb.asm.Opcodes.ACC_PUBLIC, writer);
        reader.accept(addFieldAdapter, 0);
        return writer.toByteArray();
    }

    public byte[] publicizeMethod() {
        pubMethAdapter = new PublicizeMethodAdapter(writer);
        reader.accept(pubMethAdapter, 0);
        return writer.toByteArray();
    }

    public byte[] addInterface() {
        addInterfaceAdapter = new AddInterfaceAdapter(writer);
        reader.accept(addInterfaceAdapter, 0);
        return writer.toByteArray();
    }

    public class AddInterfaceAdapter extends ClassVisitor {

        public AddInterfaceAdapter(ClassVisitor cv) {
            super(ASM4, cv);
        }

        @Override
        public void visit(int version, int access, String name,
                          String signature, String superName, String[] interfaces) {
            String[] holding = new String[interfaces.length + 1];
            holding[holding.length - 1] = CLONEABLE;
            System.arraycopy(interfaces, 0, holding, 0, interfaces.length);

            cv.visit(V1_5, access, name, signature, superName, holding);
        }
    }

    private int getReturnIndexOfConstructor(String[] words) {
        int ans = 0;
        for(int i = 0; i < words.length; i++) {
            if(words[i].contains(RETURN)) {
                return i;
            }
        }
        return ans;
    }

    private Model searchConstructor(String[] words, Model model) {
        boolean first = true;

        for(int i = 0; i < words.length; i++) {
            if(words[i].contains("RETURN")) {
                i++;
                for(int j = i; j < words.length; j++) {

                    if(first) {
                        if(words[j].equals("LOCALVARIABLE") && words[j + 1].equals("this")) {
//                            name.append(words[j + 2]);
                            model.setOwner(words[j + 2]);
                            first = false;
                            j += 3;
                        }
                    }

                    if(!first) {
                        if(words[j].equals("LOCALVARIABLE")) {
                            model.setFieldName(model.getOwner()
                                    .replaceFirst("L", "")
                                    .replaceAll(";", "") + "." + words[j + 1]);
                            model.setType(words[j + 2]);
                            model.setName(words[j + 2]);
                            i = words.length - 1;
                            break;
                        }
                    }
                }
            }
        }
        return model;
    }

    private void bCloselyUsesA(Model model) {
        if(model.getField()) {
            System.out.println(model.getFieldName() + " is a Field in " + model.getOwner());
        } else {
            System.out.println(model.getFieldName() + " is NOT a Field in " + model.getOwner());
        }
        if(model.getConstructorParameter()) {
            System.out.println(model.getFieldName() + " is parameter in the constructor in " + model.getOwner());
        } else {
            System.out.println(model.getFieldName() + " is NOT parameter in the constructor in " + model.getOwner());
        }
        if(model.getConstructorInitialized()) {
            System.out.println(model.getFieldName() + " is initialized in the constructor in " + model.getOwner());
        } else {
            System.out.println(model.getFieldName() + " is NOT initialized in the constructor in " + model.getOwner());
        }

        System.out.println(model.getFieldName() +  " is used "
                + model.getFieldCounter() + " times in the fields");

        System.out.println(model.getFieldName() + " occurs "
                + model.getAllOccurencesInOwnerMethods() + " times in a total of "
                + model.getOwnerTotalMethods() + " methods of "
                + model.getOwner());
    }

    public class AddFieldAdapter extends ClassVisitor {

        String fieldName;
        int access;
        boolean isFieldPresent;

        public AddFieldAdapter(String fieldName, int access, ClassVisitor cv) {
            super(ASM4, cv);
            this.cv = cv;
            this.access = access;
            this.fieldName = fieldName;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc,
                                       String signature, Object value) {
            if (name.equals(fieldName)) {
                isFieldPresent = true;
            }
            return cv.visitField(access, name, desc, signature, value);
        }

        @Override
        public void visitEnd() {
            if (!isFieldPresent) {
                FieldVisitor fv = cv.visitField(access, fieldName, Type.BOOLEAN_TYPE.toString(), null, null);
                if (fv != null) {
                    fv.visitEnd();
                }
            }
            cv.visitEnd();
        }
    }
}
