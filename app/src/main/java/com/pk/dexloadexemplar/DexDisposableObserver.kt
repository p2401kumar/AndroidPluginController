package com.pk.dexloadexemplar

import android.content.Context
import android.widget.ProgressBar
import android.widget.Toast
import com.pk.dexloadexemplar.DLog.DLog
import com.pk.dexloadexemplar.apk.ApkFile
import io.reactivex.observers.DisposableObserver

class DexDisposableObserver(
        var context: Context,
        var apkFile: ApkFile,
        var progressBar: ProgressBar
) : DisposableObserver<Double?>() {
    override fun onNext(aDouble: Double) {
        DLog.d(TAG, "onNext", "Progress: $aDouble")
        progressBar.progress = (aDouble * 100).toInt()
    }

    override fun onError(e: Throwable) {
        DLog.e(TAG, "onError", "Error: $e")
    }

    override fun onComplete() {
        DLog.i(TAG, "onComplete", "completed")
        progressBar.progress = 0
        progressBar.post { Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show() }
    }

    companion object {
        private val TAG = DexDisposableObserver::class.java.name
    }
}