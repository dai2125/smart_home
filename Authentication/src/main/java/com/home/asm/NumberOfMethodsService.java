package com.home.asm;

import org.objectweb.asm.*;

import java.io.IOException;
import java.util.*;

import static org.objectweb.asm.Opcodes.ASM9;

public class NumberOfMethodsService {

    private boolean isInterface;
    private Set<String> classUsed = new HashSet<>();

    public NumberOfMethodsService() {
    }

    class ClassCallVisitor extends ClassVisitor {
        private final String targetClassName;
        private boolean classCalled = false;

        public ClassCallVisitor(String targetClassName) {
            super(ASM9);
            this.targetClassName = targetClassName.replace('/', '.');
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new NumberOfMethodsService.ClassCallVisitor.MethodCallVisitor(targetClassName);
        }

        class MethodCallVisitor extends MethodVisitor {
            public MethodCallVisitor(String targetClassName) {
                super(ASM9);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
                if(owner.equals(targetClassName)) {
                    classCalled = true;
                }
            }
        }
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
}
