package com.example.large_bitmap_plugin

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.CommonPlugin
import com.ss.android.ugc.bytex.common.flow.main.Process
import com.ss.android.ugc.bytex.common.flow.main.Processor
import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain
import com.ss.android.ugc.bytex.pluginconfig.anno.PluginConfig
import org.gradle.api.Project

@PluginConfig("bytex.large-bitmap")
@Processor(implement = CustomFileProcessor::class, process = Process.TRAVERSE)
class LargeBitmapPlugin : CommonPlugin<LargeBitmapExtension, LargeBitmapContext>() {

    override fun getContext(project: Project, android: AppExtension, extension: LargeBitmapExtension?): LargeBitmapContext {
        return LargeBitmapContext(project, android, extension)
    }


    override fun traverse(relativePath: String, chain: ClassVisitorChain) {
        context.logger.i("traverse relativePath:$relativePath")

//        super.traverse(relativePath, chain)
//        chain.connect(MonitorImageViewClassVisitor())
//        chain.connect(LargeBitmapClassVisitor(context, extension))
//        chain.connect(TestClassVisitor(context))
    }

    override fun transform(relativePath: String, chain: ClassVisitorChain): Boolean {
        context.logger.i("transform relativePath:$relativePath")
//        chain.connect(LargeBitmapClassVisitor(context, extension))
//        chain.connect(MonitorImageViewClassVisitor(context))
        return super.transform(relativePath, chain)
    }

    //
    class TestClassVisitor(val context: LargeBitmapContext) : BaseClassVisitor() {
        override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
            if (superName=="android/widget/ImageView"&& name!="com/ss/android/ugc/bytex/example/MonitorImageView"){
                super.visit(version, access, name, signature, "com/ss/android/ugc/bytex/example/MonitorImageView", interfaces)
            }else{
                super.visit(version, access, name, signature, superName, interfaces)
            }
            context.logger.i("visit name:$name,superName:$superName")
        }

        override fun visitSource(source: String?, debug: String?) {
            super.visitSource(source, debug)
            context.logger.i("visitSource source:$source,debug:$debug")
        }
    }

//    override fun transform(relativePath: String, node: ClassNode): Boolean {
//
////        try {
//////            val clazzFilePath=getClassFilePath("");
//////            val classReader = ClassReader("com/ss/android/ugc/bytex/example/MonitorImageView")
////            val file=File("C:\\ByteX\\example\\build\\intermediates\\javac\\bytexCnDebug\\classes\\com\\ss\\android\\ugc\\bytex\\example\\MonitorImageView.class")
////            context.logger.e("file:${file.exists()}")
////            val fileInputStream=FileInputStream(file)
////            context.logger.e("fileInputStream:$fileInputStream")
////            val classReader=ClassReader(fileInputStream)
////            context.logger.e("classReader:$classReader")
////        }catch (e: Exception){
////            context.logger.e("MonitorImageView找不到这个类:${(e as IllegalArgumentException).cause}")
////        }
////        context.logger.i("relativePath: ${relativePath},node: $node,name:${node.name},superName:${node.superName}")
////        if (node.superName=="android/widget/ImageView"||node.superName=="android/support/v7/widget/AppCompatImageView"
////                && node.name!="com/ss/android/ugc/bytex/example/MonitorImageView"){
//        if (node.name=="android/support/v7/widget/AppCompatImageView"
//                && node.name!="com/ss/android/ugc/bytex/example/MonitorImageView"){
//            context.logger.i("relativePath: ${relativePath},node: $node,name:${node.name},superName:${node.superName}")
//            val file=File("C:\\ByteX\\example\\build\\intermediates\\javac\\bytexCnDebug\\classes\\com\\ss\\android\\ugc\\bytex\\example\\MonitorImageView.class")
//            val fileInputStream=FileInputStream(file)
//            val classReader=ClassReader(fileInputStream)
//            val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
//            val classVisitor = object : ClassVisitor(Opcodes.ASM6, classWriter) {
//                override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
//                    super.visit(version, access, name, signature, superName, interfaces)
//                }
//            }
//            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
////
////            val newNode=ClassNode()
//            node.accept(classVisitor)
//            classReader.accept(node,0)
//
////            fileInputStream.close()
////            return super.transform("C:\\ByteX\\example\\build\\intermediates\\javac\\bytexCnDebug\\classes\\com\\ss\\android\\ugc\\bytex\\example\\MonitorImageView.class", newNode)
//        }
//        return super.transform(relativePath, node)
//    }
//
//
//    fun getClassFilePath(clazz: Class<*>): String? {
//        // file:/Users/zhy/hongyang/repo/BlogDemo/app/build/intermediates/javac/debug/classes/
//        val buildDir = clazz.protectionDomain.codeSource.location.file
//        val fileName = clazz.simpleName + ".class"
//        val file = File(buildDir + clazz.getPackage().name.replace("[.]".toRegex(), "/") + "/", fileName)
//        return file.getAbsolutePath()
//    }
}