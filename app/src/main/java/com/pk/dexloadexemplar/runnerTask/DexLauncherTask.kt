package com.pk.dexloadexemplar.runnerTask

import android.content.Context
import android.content.Intent
import com.pk.dexloadexemplar.DLog.DLog
import com.pk.dexloadexemplar.apk.ApkFile
import dalvik.system.DexClassLoader
import java.util.function.Function

class DexLauncherTask(
        var context: Context
): Function<ApkFile, Boolean> {
    override fun apply(t: ApkFile): Boolean {
        if (t.file == null)return false

        val dexLocation = t.file!!.parent + "/" + t.uuid + "/classes.dex"
        val optimizedAbsPath = context.getDir("outdex", Context.MODE_PRIVATE).absolutePath;
        val dexClassLoader = DexClassLoader(dexLocation, optimizedAbsPath, null, context.classLoader)

        val loadClass = dexClassLoader.loadClass(t.mainActivity)
        val intent = Intent(context, loadClass)
        context.startActivity(intent)
        DLog.d(TAG, "apply", "load classes")
        return true
    }

    companion object {
        val TAG = DexLauncherTask.javaClass.name!!
    }
}