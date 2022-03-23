package com.ss.android.ugc.bytex.method_trace

import com.android.build.gradle.AppExtension
import com.example.method_trace.MethodTraceContext
import com.example.method_trace.MethodTraceExtension
import com.ss.android.ugc.bytex.common.CommonPlugin
import com.ss.android.ugc.bytex.common.TransformConfiguration
import com.ss.android.ugc.bytex.common.flow.main.Process
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain
import com.ss.android.ugc.bytex.pluginconfig.anno.PluginConfig
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import javax.annotation.Nonnull


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
        return super.transform(relativePath, chain)
    }

    override fun flagForClassReader(process: Process?): Int {
        return  ClassReader.EXPAND_FRAMES
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