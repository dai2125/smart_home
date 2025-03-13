package com.home.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM9;

public class NumberOfConstructorsService {

    private int counter = 0;

    public NumberOfConstructorsService() {
    }

    public int countConstructors(String className) throws IOException {
        ClassReader classReader = new ClassReader(className);
        counter = 0;

        classReader.accept(new ClassVisitor(ASM9) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals("<init>")) {
                    counter++;
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }, 0);
        return counter;
    }
}
