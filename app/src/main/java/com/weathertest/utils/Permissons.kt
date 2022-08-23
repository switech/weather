package com.weathertest.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object Permissons {
    const val LOCATION = 105

    // Request permission..
    fun Request_FINE_LOCATION(act: Activity?, code: Int) {
        ActivityCompat.requestPermissions(act!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), code)
    }

    //Check Permisson
    fun Check_FINE_LOCATION(context: Context?): Boolean {
        val result = ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }
}