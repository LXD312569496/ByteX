package com.example.method_trace_lib;


import android.os.Build;
import android.os.Trace;

public class MyTrace {

    public static void beginSection(String tag) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.beginSection(tag);
        }
    }

    public static void endSection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        }
    }

}
