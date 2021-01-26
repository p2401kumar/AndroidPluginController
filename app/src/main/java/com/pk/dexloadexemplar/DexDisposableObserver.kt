package com.pk.dexloadexemplar

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.pk.dexloadexemplar.DLog.DLog
import com.pk.dexloadexemplar.apk.ApkFile
import com.pk.dexloadexemplar.extract.ExtractTask
import com.pk.dexloadexemplar.runnerTask.DexLauncherTask
import io.reactivex.observers.DisposableObserver

class DexDisposableObserver(
        var context: Context,
        var apkFile: ApkFile,
        var progressBar: ProgressBar,
        var view: View? = null
) : DisposableObserver<Double?>() {
    override fun onNext(aDouble: Double) {
        DLog.d(TAG, "onNext", "Progress: $aDouble")
        progressBar.progress = (aDouble * 100).toInt()
    }

    override fun onError(e: Throwable) {
        DLog.e(TAG, "onError", "Error: $e")
        progressBar.post {
            view?.isEnabled = true
        }
    }

    override fun onComplete() {
        DLog.i(TAG, "onComplete", "completed")
        progressBar.progress = 0
        progressBar.post {
            Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show()
            view?.isEnabled = true
            if (ExtractTask(context).apply(apkFile)) {
                Toast.makeText(context, "Extraction Done", Toast.LENGTH_SHORT).show()
                DexLauncherTask(context).apply(apkFile)
            }
        }

    }

    companion object {
        private val TAG = DexDisposableObserver::class.java.name
    }
}