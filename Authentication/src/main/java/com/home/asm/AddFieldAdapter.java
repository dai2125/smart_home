package com.home.asm;

import org.objectweb.asm.ClassVisitor;

import static com.home.asm.CustomClassWriter.cloneableInterface;
import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.V1_8;

public class AddFieldAdapter extends ClassVisitor {

    public AddFieldAdapter(ClassVisitor cv) {
        super(ASM4, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        String[] holding = new String[interfaces.length + 1];
        holding[holding.length - 1] = cloneableInterface;
        System.arraycopy(interfaces, 0, holding, 0, interfaces.length);
        cv.visit(V1_8, access, name, signature, superName, holding);
    }
}
