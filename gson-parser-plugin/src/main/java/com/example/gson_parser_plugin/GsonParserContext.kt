package com.example.gson_parser_plugin

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.BaseContext
import org.gradle.api.Project

class GsonParserContext(
    project: Project,
    android: AppExtension,
    extension: GsonParserExtension
) : BaseContext<GsonParserExtension>(project, android, extension) {


    override fun init() {
        super.init()
    }
}