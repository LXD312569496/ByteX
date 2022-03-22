package com.example.large_bitmap_plugin;

import com.ss.android.ugc.bytex.common.log.LevelLog;
import com.ss.android.ugc.bytex.transformer.cache.FileData;
import com.ss.android.ugc.bytex.transformer.processor.FileProcessor;
import com.ss.android.ugc.bytex.transformer.processor.Input;
import com.ss.android.ugc.bytex.transformer.processor.Output;

import java.io.IOException;

public class CustomFileProcessor implements FileProcessor {
    @Override
    public Output process(Chain chain) throws IOException {
        Input input=chain.input();
        FileData fileData=input.getFileData();
        //do something with fileData
        LevelLog.sDefaultLogger.i("CustomFileProcessor process");

        return chain.proceed(input);
    }
}
