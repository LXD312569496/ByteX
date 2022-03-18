package com.ss.android.ugc.bytex.method_trace

import com.example.method_trace.MethodTraceContext
import com.example.method_trace.MethodTraceExtension
import com.example.method_trace.TraceMethodVisitor
import com.ss.android.ugc.bytex.common.Constants
import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import org.objectweb.asm.MethodVisitor

class MethodTraceClassVisitor(private var context: MethodTraceContext, extension: MethodTraceExtension?) : BaseClassVisitor() {

    lateinit var mClassName: String

    override fun visitSource(source: String?, debug: String?) {
        super.visitSource(source, debug)
    }

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        if (name != null) {
            mClassName = name
        }
    }

    override fun visitMethod(
            access: Int,
            name: String?,
            descriptor: String?,
            signature: String?,
            exceptions: Array<out String>?
    ): MethodVisitor {
        if (context.shouldTrace(mClassName, name)) {
            val methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
            return TraceMethodVisitor(context, mClassName, Constants.ASM_API, methodVisitor, access, name, descriptor)
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }
}