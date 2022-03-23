package com.example.large_bitmap_plugin

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.commons.AdviceAdapter

class LargeBitmapMethodVisitor(
        private var context: LargeBitmapContext,
        private var className: String, api: Int, mv: MethodVisitor?,
        access: Int, var methodName: String?, desc: String?
) : AdviceAdapter(api, mv, access, methodName, desc) {


    /**
     * com.facebook.imagepipeline.request.ImageRequest#ImageRequest
     * 1.hook 构造方法
     * 2.在方法退出后，拿到mRequestListener
     * 3.往里面添加一个自定义的listener
     */
    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
    }




}
