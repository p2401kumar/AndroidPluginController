package com.pk.dexloadexemplar.DLog

import android.util.Log

object DLog {
    fun d(className: String, functionName: String, message: String?) {
        Log.d("DexLoadExemplar $className:$functionName", message!!)
    }

    fun e(className: String, functionName: String, errorMessage: String?) {
        Log.e("DexLoadExemplar $className:$functionName", errorMessage!!)
    }

    fun i(className: String, functionName: String, errorMessage: String?) {
        Log.i("DexLoadExemplar $className:$functionName", errorMessage!!)
    }
}