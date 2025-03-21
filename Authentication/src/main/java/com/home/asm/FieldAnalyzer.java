package com.home.asm;

import org.objectweb.asm.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.objectweb.asm.Opcodes.ASM9;

public class FieldAnalyzer {

    public static Set<String> getFields(String className) throws IOException {
        Set<String> fields = new HashSet<>();

        ClassReader classReader = new ClassReader(className);
        classReader.accept(new ClassVisitor(ASM9) {
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                fields.add(name);
                return super.visitField(access, name, descriptor, signature, value);
            }
        }, 0);

        return fields;
    }

    public static void main(String[] args) throws IOException {
        Set<String> fields = getFields("com/home/pureFabrication/fourthExample/Command4");
        System.out.println("Felder in Command4: " + fields);
    }
}
