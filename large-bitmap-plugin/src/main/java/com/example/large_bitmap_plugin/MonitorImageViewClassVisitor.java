package com.example.large_bitmap_plugin;

import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MonitorImageViewClassVisitor extends BaseClassVisitor {

    private static final String ImageViewClass = "android/widget/ImageView";

    private static final String AppCompatImageViewClass = "androidx/appcompat/widget/AppCompatImageView";
    private static final String MyImageViewClass = "com/ss/android/ugc/bytex/example/MonitorImageView";

    LargeBitmapContext context;
    public String className;

    public MonitorImageViewClassVisitor(LargeBitmapContext context) {
        super();
        this.context = context;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        if (name != null) {
            className = name;
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        MethodVisitor newMethodVisitor = new MethodVisitor(api, methodVisitor) {

            private boolean find = false;

            @Override
            public void visitTypeInsn(int opcode, String type) {
                context.getLogger().i("visitTypeInsn type:" + type);
                if (opcode == Opcodes.NEW && "android/widget/ImageView".equals(type)) {
                    find = true;
                    methodVisitor.visitTypeInsn(Opcodes.NEW, MyImageViewClass);
                    return;
                }
                super.visitTypeInsn(opcode, type);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                context.getLogger().i("visitMethodInsn owner:" + owner+",name:"+name);
                if ("android/widget/ImageView".equals(owner) && !className.equals(MyImageViewClass)
                        && opcode == Opcodes.INVOKESPECIAL && find) {
                    find = false;
                    methodVisitor.visitMethodInsn(opcode, MyImageViewClass, name, descriptor, isInterface);
                    return;
                }

                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }
        };
        return newMethodVisitor;
    }
}
