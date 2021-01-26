package com.pk.dexloadexemplar

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.pk.dexloadexemplar.apk.ApkFile
import com.pk.dexloadexemplar.downloadHelper.FileDownloadTask
import io.reactivex.schedulers.Schedulers

class ButtonActionFactory internal constructor(var context: Context) {
    fun getAction(
            context: Context,
            apkFile: ApkFile,
            progressBar: ProgressBar,
            button: Button
    ): View.OnClickListener {
        return View.OnClickListener {
            val dexDisposableObserver = DexDisposableObserver(context, apkFile, progressBar, button)
            it?.isEnabled = false
            val copyFileToUrl = FileDownloadTask.copyFileToUrl(
                    context,
                    apkFile)
            if (copyFileToUrl.isPresent) {
                copyFileToUrl.get()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.computation())
                        .subscribe(dexDisposableObserver)
            }
        }
    }
}
