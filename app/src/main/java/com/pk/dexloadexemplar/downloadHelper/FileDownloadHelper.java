package com.pk.dexloadexemplar.downloadHelper;

import android.content.Context;

import androidx.annotation.NonNull;

import com.pk.dexloadexemplar.apk.ApkFile;
import com.pk.dexloadexemplar.apk.ApkFileInfo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class FileDownloadHelper {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 100;

    public static Optional<Observable<Double>> copyFileToUrl(
            @NonNull Context context,
            @NonNull ApkFile apkFile
    ) {
        return copyFileToUrl(context, apkFile.getApkUrl(), apkFile.getApkName());
    }

    public static Optional<Observable<Double>> copyFileToUrl(
            @NonNull Context context,
            @NonNull String url,
            @NonNull String fileName
    ) {
        try {
            File file = new File(context.getExternalFilesDir(null), fileName);
            file.delete();

            return Optional.of(doCopy(
                    new URL(url),
                    file));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static synchronized Observable<Double> doCopy(
            @NonNull URL source,
            @NonNull File destination) {

        return Observable.create(new ObservableOnSubscribe<Double>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Double> emitter) throws Exception {
                FileOutputStream output = null;
                HttpURLConnection urlConnection = (HttpURLConnection) source.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                int totalSize = urlConnection.getContentLength();
                InputStream inputStream = urlConnection.getInputStream();

                try {
                    output = FileUtils.openOutputStream(destination);
                } catch (IOException e) {
                    emitter.onError(e);
                    emitter.onComplete();
                }

                try {
                    if (output != null) {
                        copy(inputStream, output, totalSize, emitter);
                        output.close();
                    }
                } catch (Exception e) {
                    emitter.onError(e);
                    emitter.onComplete();
                } finally {
                    IOUtils.closeQuietly(inputStream);
                    emitter.onComplete();
                }
            }

            private int copy(
                    InputStream input,
                    final OutputStream output,
                    int totalSize,
                    @io.reactivex.annotations.NonNull ObservableEmitter<Double> emitter) throws IOException {

                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                long count = 0;
                int n;
                while (-1 != (n = input.read(buffer))) {
                    output.write(buffer, 0, n);
                    count += n;
                    emitter.onNext((double) count / totalSize);
                }
                if (count > Integer.MAX_VALUE) {
                    return -1;
                }
                return (int) count;
            }
        });
    }
}
