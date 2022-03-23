package com.example.large_bitmap_plugin;

import com.ss.android.ugc.bytex.common.Constants;
import com.ss.android.ugc.bytex.common.log.LevelLog;
import com.ss.android.ugc.bytex.transformer.cache.FileData;
import com.ss.android.ugc.bytex.transformer.processor.FileProcessor;
import com.ss.android.ugc.bytex.transformer.processor.Input;
import com.ss.android.ugc.bytex.transformer.processor.Output;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;

/**
 * 第二种实现方式
 */
public class CustomFileProcessor implements FileProcessor {
    @Override
    public Output process(Chain chain) throws IOException {
        Input input=chain.input();
        FileData fileData=input.getFileData();
        //do something with fileData
        LevelLog.sDefaultLogger.i("CustomFileProcessor process:"+fileData.getRelativePath());


        if (fileData.getRelativePath().equals("android/support/v7/widget/AppCompatImageView.class")){
            ClassReader classReader=new ClassReader(fileData.getBytes());
            ClassWriter classWriter=new ClassWriter(0);

            ClassVisitor classVisitor=new ClassVisitor(Constants.ASM_API,classWriter) {
                @Override
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//                    super.visit(version, access, name, signature, superName, interfaces);
                    LevelLog.sDefaultLogger.i("ClassVisitor visit name:"+name+",superName:"+superName);
                    super.visit(version, access, name, signature, "com/ss/android/ugc/bytex/example/MonitorImageView", interfaces);
                }
            };

            classReader.accept(classVisitor,0);

            byte[] bytes=classWriter.toByteArray();
            fileData.setBytes(bytes);
        }



        //todo:遍历处理
//        if (fileData.getRelativePath().equals("android/support/v7/widget/AppCompatImageView.class")){
//            LevelLog.sDefaultLogger.i("CustomFileProcessor 替换逻辑");
//            String path="C:\\ByteX\\example\\build\\intermediates\\javac\\bytexCnDebug\\classes\\com\\ss\\android\\ugc\\bytex\\example\\MonitorImageView.class";
//            File file=new File(path);
//
//            byte[] bytes=Files.toByteArray(file);
//            fileData.setBytes(bytes);
//        }else if (fileData.getRelativ Path().equals("com/ss/android/ugc/bytex/example/MonitorImageView.class")){
//            fileData.delete();
//        }
        return chain.proceed(input);
    }
}
