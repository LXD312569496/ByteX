package com.ss.android.ugc.bytex.method_trace

import com.android.build.gradle.AppExtension
import com.example.method_trace.MethodTraceContext
import com.example.method_trace.MethodTraceExtension
import com.ss.android.ugc.bytex.common.CommonPlugin
import com.ss.android.ugc.bytex.common.Constants
import com.ss.android.ugc.bytex.common.flow.main.Process
import com.ss.android.ugc.bytex.common.log.LevelLog
import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain
import com.ss.android.ugc.bytex.pluginconfig.anno.PluginConfig
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


@PluginConfig("bytex.method-trace")
class MethodTracePlugin : CommonPlugin<MethodTraceExtension, MethodTraceContext>() {
    override fun getContext(
            project: Project,
            android: AppExtension,
            extension: MethodTraceExtension
    ): MethodTraceContext {
        return MethodTraceContext(project, android, extension)
    }

    override fun transform(relativePath: String, chain: ClassVisitorChain): Boolean {
        chain.connect(MethodTraceClassVisitor(context, extension))
//        chain.connect(ThreadClassVisitor())
        return super.transform(relativePath, chain)
    }

    override fun flagForClassReader(process: Process?): Int {
        return ClassReader.EXPAND_FRAMES
//        return ClassReader.EXPAND_FRAMES
    }

//    @Nonnull
//    override fun transformConfiguration(): TransformConfiguration {
//        return object : TransformConfiguration {
//            override fun isIncremental(): Boolean {
//                return false
//            }
//        }
//    }

}

class ThreadClassVisitor() : BaseClassVisitor() {
    var className: String = ""

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {

        val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)


        return object : MethodVisitor(Constants.ASM_API, methodVisitor) {
            var find = false
            override fun visitTypeInsn(opcode: Int, type: String?) {
                if (opcode == Opcodes.NEW && "java/lang/Thread".equals(type)) {
                    mv.visitTypeInsn(Opcodes.NEW, "com/ss/android/ugc/bytex/example/CustomThread")
                    find = true
                    LevelLog.sDefaultLogger.i("visitTypeInsn" )
                    return
                }
                super.visitTypeInsn(opcode, type)
            }

            override fun visitMethodInsn(opcode: Int, owner: String?, name: String?, descriptor: String?, isInterface: Boolean) {

                if ("java/lang/Thread".equals(owner) && !className.equals("com/ss/android/ugc/bytex/example/CustomThread") && opcode == Opcodes.INVOKESPECIAL && find) {
                    find = false
                    mv.visitMethodInsn(opcode, "com/ss/android/ugc/bytex/example/CustomThread", name, descriptor, isInterface)
                    LevelLog.sDefaultLogger.i("visitMethodInsn" )

                    return;
                }

                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface)

            }
        }
    }
}