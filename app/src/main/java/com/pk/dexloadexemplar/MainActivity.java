package com.pk.dexloadexemplar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.pk.dexloadexemplar.apk.ApkFile;
import com.pk.dexloadexemplar.apk.ApkFileInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    @BindView(value = R.id.instance1)
    Button instance1;

    @BindView(value = R.id.instance2)
    Button instance2;

    @BindView(value = R.id.progressBar)
    ProgressBar progressBar;


    ButtonActionFactory buttonActionFactory = new ButtonActionFactory(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ApkFile apkFile1 = ApkFileInfo.INSTANCE.get("Dex Example 1");
        if (apkFile1 != null) {
            instance1.setOnClickListener(
                    buttonActionFactory.getAction(
                            apkFile1,
                            new DexDisposableObserver(this, apkFile1, progressBar, instance1)
                    ));
        }

        ApkFile apkFile2 = ApkFileInfo.INSTANCE.get("Dex Example 2");
        if (apkFile2 != null) {
            instance2.setOnClickListener(
                    buttonActionFactory.getAction(
                            apkFile2,
                            new DexDisposableObserver(this, apkFile2, progressBar, instance2)
                    ));
        }
    }
}