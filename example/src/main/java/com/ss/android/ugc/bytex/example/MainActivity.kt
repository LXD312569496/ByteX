package com.ss.android.ugc.bytex.example

import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.ss.android.ugc.bytex.example.coverage.CoverageReportTask

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Handle coverage log info, send to the server
        CoverageReportTask.init()

        val imageView=findViewById<ImageView>(R.id.iv)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            imageView.setImageDrawable(getDrawable(R.mipmap.ic_launcher))
        }
        Log.d("MainActivity", "onCreate: ")
    }
}
