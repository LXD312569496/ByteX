package com.example.large_bitmap_plugin

import com.ss.android.ugc.bytex.common.visitor.BaseClassVisitor
import org.objectweb.asm.MethodVisitor


class LargeBitmapClassVisitor(private var context: LargeBitmapContext, extension: LargeBitmapExtension?)
    : BaseClassVisitor() {

    private val ImageViewClass = "android/support/v7/widget/AppCompatImageView"
    private val ImageViewClassX = "androidx/appcompat/widget/AppCompatImageView"

//    private val MyImageViewClass = "com/ss/android/ugc/bytex/example/MonitorImageView"
private val MyImageViewClass = "com/example/large_bitmap_lib/MonitorImageView"


    lateinit var mClassName: String


    override fun visitSource(source: String?, debug: String?) {
        super.visitSource(source, debug)
//        context.logger.i("source: ${source},debug: $debug")
    }

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        context.logger.i("name: ${name},superName: $superName")

        if (name.equals(ImageViewClass) || name.equals(ImageViewClassX)) {
            super.visit(version, access, name, signature, MyImageViewClass, interfaces)
        } else {
            super.visit(version, access, name, signature, superName, interfaces)
        }
    }

    override fun visitMethod(access: Int, name: String?, descriptor: String?, signature: String?, exceptions: Array<out String>?): MethodVisitor {
//        context.logger.i("visitMethod name: ${name},descriptor: $descriptor")
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }


}