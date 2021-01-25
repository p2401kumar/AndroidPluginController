package com.pk.dexloadexemplar.DLog;

import android.util.Log;

public class DLog {
    public static void d(String className, String functionName, String message) {
        Log.d("DexLoadExemplar " + className + ":" + functionName, message);
    }

    public static void e(String className, String functionName, String errorMessage) {
        Log.e("DexLoadExemplar " + className + ":" + functionName, errorMessage);
    }

    public static void i(String className, String functionName, String errorMessage) {
        Log.i("DexLoadExemplar " + className + ":" + functionName, errorMessage);
    }
}
