package com.pk.dexloadexemplar.extract;

import android.content.Context;

import com.pk.dexloadexemplar.DLog.DLog;
import com.pk.dexloadexemplar.apk.ApkFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ExtractTask implements Function<ApkFile, Boolean> {
    private static final String TAG = ExtractTask.class.getName();
    private final Context context;

    public ExtractTask(Context context) {
        this.context = context;
    }

    @Override
    public Boolean apply(ApkFile apkFile) {
        String path = apkFile.getFile().getPath();
        String extractionFolder = apkFile.getFile().getParent() + "/" + apkFile.getUuid();
        DLog.INSTANCE.d(TAG, "apply", "started extraction: " + path + ": " + extractionFolder);
        try {
            unzip(apkFile.getFile(), new File(extractionFolder));
        } catch (IOException e) {
            DLog.INSTANCE.e(TAG, "apply", "error");
            e.printStackTrace();
            return false;
        }
        DLog.INSTANCE.d(TAG, "apply", "extraction done");
        return true;
    }

    public static void unzip(File zipFile, File targetDirectory) throws IOException {
        targetDirectory.delete();

        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }
}
