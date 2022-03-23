package com.ss.android.ugc.bytex.method_trace

import com.example.method_trace.MethodTraceContext
import com.example.method_trace.MethodTraceExtension
import com.example.method_trace.TraceMethodVisitor
import com.ss.android.ugc.bytex.common.Constants
import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class MethodTraceClassVisitor(private var context: MethodTraceContext, extension: MethodTraceExtension?) : BaseClassVisitor() {

    lateinit var mClassName: String
    var isAbstractClass: Boolean = true //是否是抽象类

    override fun visitSource(source: String?, debug: String?) {
        super.visitSource(source, debug)
    }

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        if (name != null) {
            mClassName = name
        }
        //抽象方法或者接口
        this.isAbstractClass = access and Opcodes.ACC_ABSTRACT > 0 || access and Opcodes.ACC_INTERFACE > 0
    }

    override fun visitMethod(
            access: Int,
            name: String?,
            descriptor: String?,
            signature: String?,
            exceptions: Array<out String>?
    ): MethodVisitor {
        if (!isAbstractClass && context.shouldTrace(mClassName, name)) {
            val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
            return TraceMethodVisitor(context, mClassName, Constants.ASM_API, methodVisitor, access, name, descriptor)
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }
}