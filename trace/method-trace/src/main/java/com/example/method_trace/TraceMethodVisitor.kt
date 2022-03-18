package com.example.method_trace

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

class TraceMethodVisitor(
        private var context: MethodTraceContext,
        private var className: String, api: Int, mv: MethodVisitor?,
        access: Int, var methodName: String?, desc: String?
) : AdviceAdapter(api, mv, access, methodName, desc) {

    override fun onMethodEnter() {
        super.onMethodEnter()
        context.logger.i("TraceMethodVisitor onMethodEnter", "----插桩----className: $className  methodName: ${methodName}------")
        if (methodName != null) {
            mv.visitLdcInsn("$className#$methodName");
////            mv.visitMethodInsn(INVOKESTATIC, "com/ss/android/ugc/bytex/method_trace_lib/MyTrace", "beginSection", "(Ljava/lang/String;)V", false);
//            mv.visitMethodInsn(INVOKESTATIC, "com/example/method_trace_lib/MyTrace", "beginSection", "(Ljava/lang/String;)V", false);

            mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                    "android/os/Trace",
                    "beginSection",
                    "(Ljava/lang/String;)V",
                    false);


        }
    }

    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        context.logger.i("TraceMethodVisitor onMethodExit", "----插桩----className: $className  methodName: ${methodName}------")
//        mv.visitMethodInsn(INVOKESTATIC, "com/ss/android/ugc/bytex/method_trace_lib/MyTrace", "endSection", "()V", false);
        if (methodName!=null){
//            mv.visitMethodInsn(INVOKESTATIC, "com/example/method_trace_lib/MyTrace", "endSection", "()V", false);
            mv.visitMethodInsn(INVOKESTATIC,
                    "android/os/Trace",
                    "endSection",
                    "()V",
                    false)
        }


    }
}