package com.home.asm;

import org.objectweb.asm.*;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class NumberOfMethodsService {

    private boolean isInterface;
    private Set<String> classUsed = new HashSet<>();
    private Set<String> listOfAllMethods = new HashSet<>();
    private List<MethodModel> listOfAllSubMethods = new ArrayList<>();

    public NumberOfMethodsService() {
    }

    public int analyzePackage2(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

//                System.out.println("visitMethod, name: " + name + ", desc: " + desc + ", signature: " + signature + ", exceptions: " + exceptions + ", access: " + access);
                if(!name.equals("<init>") && !name.equals("<clinit>")) {
                    classUsed.add(name);
                }

//                if(name.equals("<clinit>")) {
//                    System.out.println("CLINIT | name: " + name + ", desc: " + desc + ", signature: " + signature + ", exceptions: " + exceptions);
//                }

                return null;
            }
        }, 0);

//        Iterator<String> iterator = classUsed.iterator();
//
//        while(iterator.hasNext()) {
//            System.out.println("ITERATOR: " + iterator.next());
//        }

        return classUsed.size();
    }

    public Set<String> getAllMethods(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        HashSet<String> interfaceList = new HashSet<>();

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

                if(interfaces != null) {
                    for(int i = 0; i < interfaces.length; i++) {
                        interfaceList.add(interfaces[i]);
                    }
                }
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if(!name.equals("<init>") && !name.equals("<clinit>")) {
                    listOfAllMethods.add(name);
                } else if(name.equals("<init>")) {
                    return new MethodVisitor(ASM9) {
                        @Override
                        public void visitParameter(String name, int access) {
                            System.out.println("VISITPARAMETER: " + name);
                            super.visitParameter(name, access);
                        }

                        @Override
                        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
//                            System.out.println("VISITFIELDINSN: " + name);

                            super.visitFieldInsn(opcode, owner, name, descriptor);
                        }
                    };
                }
                return null;
            }
        }, 0);
//        System.out.println("||||| " + className + " " + listOfAllMethods.size());
        return listOfAllMethods;
    }

    public List<String> getAllInterfaceMethods(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        HashSet<String> interfaceList = new HashSet<>();
        List<String> listOfAllMethodsFromInterfaces = new ArrayList<>();

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                if(interfaces != null) {
                    for(int i = 0; i < interfaces.length; i++) {
                        interfaceList.add(interfaces[i]);
                    }
                }
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

                try {
                    if(methodIsFromInterface(interfaceList, name)) {
                        listOfAllMethodsFromInterfaces.add(name);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }, 0);

//        System.out.println("START " + className);
//        for(int i = 0; i < listOfAllMethodsFromInterfaces.size(); i++) {
//            System.out.println(listOfAllMethodsFromInterfaces.get(i));
//        }
//        System.out.println("END");
        return listOfAllMethodsFromInterfaces;
    }

    public boolean methodIsFromInterface(Set<String> listOfInterfaces, String methodName) throws IOException {
        Iterator<String> iterator = listOfInterfaces.iterator();
        final boolean[] ans = {false};

        while(iterator.hasNext()) {
            ClassReader classReader = new ClassReader(iterator.next());
            classReader.accept(new ClassVisitor(ASM9) {

                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    if(name.equals(methodName)) {
                        ans[0] = true;
                    }
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }, 0);
        }
        return ans[0];
    }

    public int getAllPrivateMethods(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        isInterface = false;
        HashSet<String> interfaceList = new HashSet<>();
        List<String> listOfAllMethodsFromInterfaces = new ArrayList<>();
        final int[] ans = {0};

        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                if(access == 2) {
                    ans[0]++;
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }, 0);
        return ans[0];
    }
}
