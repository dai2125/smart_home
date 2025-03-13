package com.home.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClassMethodAnalyzer extends ClassVisitor {

    private Map<String, Set<String>> methodToFields = new HashMap<>();
    private String currentMethod;

    public ClassMethodAnalyzer() {
        super(Opcodes.ASM9);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        currentMethod = name;
        methodToFields.put(name, new HashSet<>());

        return new MethodVisitor(Opcodes.ASM9) {
            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String descpritor) {
                methodToFields.get(currentMethod).add(name);
            }
        };
    }

    public Map<String, Set<String>> getMethodToFields() {
        System.out.println("getMethodToFields(): " + methodToFields.size());
        return methodToFields;
    }
}
