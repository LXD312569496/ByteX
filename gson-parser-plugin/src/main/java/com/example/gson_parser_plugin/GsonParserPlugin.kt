package com.example.gson_parser_plugin

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.CommonPlugin
import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain
import com.ss.android.ugc.bytex.pluginconfig.anno.PluginConfig
import org.gradle.api.Project
import org.objectweb.asm.MethodVisitor

@PluginConfig("bytex.gson-parser")
class GsonParserPlugin(): CommonPlugin<GsonParserExtension, GsonParserContext>() {
    override fun getContext(
        project: Project,
        android: AppExtension,
        extension: GsonParserExtension
    ): GsonParserContext {
        return GsonParserContext(
            project,android,extension
        )
    }

    override fun transform(relativePath: String, chain: ClassVisitorChain): Boolean {
        chain.connect(GsonClassVisitor(context))
        return super.transform(relativePath, chain)
    }
}


class GsonClassVisitor(val context: GsonParserContext): BaseClassVisitor() {

    var findGsonClass=false

    override fun visitSource(source: String?, debug: String?) {
        super.visitSource(source, debug)
    }

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        context.logger.d("visit class:$name")
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {

        context.logger.d("visitMethod name:$name")

        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }

}


