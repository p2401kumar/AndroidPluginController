package com.pk.dexloadexemplar.apk

object ApkFileInfo: HashMap<String, ApkFile>() {
    init {
        put("Dex Example 1", ApkFile(
                "com.pk.dexexample1",
                "com.pk.dexexample1.MainActivity",
                "DexExample1.apk",
                "https://github.com/p2401kumar/AndroidPluginController/blob/main/ResourceApk/DexExample1.apk?raw=true"));

        put("Dex Example 2", ApkFile(
                "com.pk.dexexample2",
                "com.pk.dexexample2.MainActivity",
                "DexExample2.apk",
                "https://github.com/p2401kumar/AndroidPluginController/blob/main/ResourceApk/DexExample2.apk?raw=true"));
    }
}