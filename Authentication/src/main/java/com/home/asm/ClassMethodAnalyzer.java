package com.home.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.*;

public class ClassMethodAnalyzer extends ClassVisitor {

    private Map<String, Set<String>> methodToFields = new HashMap<>();
    private String currentMethod;
    private Set<String> allFields = new HashSet<>();
    private Set<String> allMethods = new HashSet<>();

    public ClassMethodAnalyzer(Set<String> allFields, Set<String> allMethods) {
        super(Opcodes.ASM9);
        this.allFields = allFields;
        this.allMethods = allMethods;

//        System.out.println("ClassMethodAnalyzer ALL FIELDS");
//        Iterator<String> itr = allFields.iterator();
//        while(itr.hasNext()) {
//            System.out.println(itr.next());
//        }
//
//        System.out.println("ClassMethodAnalyzer ALL METHODS");
//        itr = allMethods.iterator();
//        while(itr.hasNext()) {
//            System.out.println(itr.next());
//        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        currentMethod = name;
        if(!currentMethod.equals("<init>") && !currentMethod.equals("<clinit>")) {
            methodToFields.put(name, new HashSet<>());
//            System.out.println("currentMethod: " + currentMethod);
        }

        return new MethodVisitor(Opcodes.ASM9) {
            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String descpritor) {
//                System.out.println("|||| opcode: " + " owner: " + owner + " name: " + name + " desc: " + descpritor);
                if(!currentMethod.equals("<init>") && !currentMethod.equals("<clinit>") && allFields.contains(name)) {
                    methodToFields.get(currentMethod).add(name);
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
//                System.out.println("[][] opcode: " + opcode + " owner: " + owner + " name: " + name + " desc: " + desc + " itf: " + itf);
                if(!currentMethod.equals("<init>") && !currentMethod.equals("<clinit>") && allMethods.contains(name)) {
                    methodToFields.get(currentMethod).add(name);
                }
            }
        };

    }

    public Map<String, Set<String>> getMethodToFields() {
//        System.out.println("getMethodToFields(): " + methodToFields.size());
//        Iterator<String> itr = methodToFields.keySet().iterator();
//        while(itr.hasNext()) {
//            String methodName = itr.next();
//            Set<String> fields = methodToFields.get(methodName);
//            System.out.println("methodName: " + methodName + " fields: " + fields);
//        }
        return methodToFields;
    }
}
