package com.pk.dexloadexemplar.apk

import java.io.File

class ApkFile(
    var packageName: String,
    var mainActivity: String,
    var apkName: String,
    var apkUrl: String,
    var uuid: String? = null,
    var file: File? = null
)