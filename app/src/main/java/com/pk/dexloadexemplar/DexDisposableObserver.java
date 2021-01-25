package com.pk.dexloadexemplar;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pk.dexloadexemplar.DLog.DLog;
import com.pk.dexloadexemplar.apk.ApkFile;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

public class DexDisposableObserver extends DisposableObserver<Double> {
    private static final String TAG = DexDisposableObserver.class.getName();
    ApkFile apkFile;
    ProgressBar progressBar;
    Context context;

    public DexDisposableObserver(
            Context context,
            ApkFile apkFile,
            ProgressBar progressBar
    ) {
        this.apkFile = apkFile;
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    public void onNext(@NonNull Double aDouble) {
        DLog.d(TAG, "onNext", "Progress: " + aDouble);
        progressBar.setProgress((int) (aDouble*100));
    }

    @Override
    public void onError(@NonNull Throwable e) {
        DLog.e(TAG, "onError", "Error: " + e);
    }

    @Override
    public void onComplete() {
        DLog.i(TAG, "onComplete", "completed");
        progressBar.setProgress(0);
        progressBar.post(() -> {
            Toast.makeText(context, "Download complete", Toast.LENGTH_SHORT).show();
        });
    }
}
