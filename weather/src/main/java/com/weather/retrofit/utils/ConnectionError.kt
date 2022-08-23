package com.weather.retrofit.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.google.gson.JsonSyntaxException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ConnectionError(context: Context, t: Throwable) {
    var mListener: Listener? = null
    private fun showPopup(context: Context, error: String) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setCancelable(false)
        alertDialog.setTitle("Connection Error")
        alertDialog.setMessage(error)
        alertDialog.setPositiveButton("Retry") { dialogInterface: DialogInterface, i: Int ->
            mListener!!.returnData()
            dialogInterface.dismiss()
        }
        alertDialog.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int -> dialogInterface.dismiss() }
        alertDialog.show()
    }

    /**
     * Method is used to set the listener...
     * @param listener
     */
    fun setListener(listener: Listener?) {
        mListener = listener
    }

    /**
     * INTERFACE is used to get the listener when dialog is closed...
     */
    interface Listener {
        fun returnData()
    }

    init {
        t.printStackTrace()
        if (t is SocketTimeoutException) showPopup(
            context,
            "Request time out, please retry your request."
        ) else if (t is UnknownHostException) showPopup(
            context,
            "The connection to the server was unsuccessful. Please retry your request."
        ) else if (t is JsonSyntaxException) showPopup(
            context,
            "Could not complete your request because of a problem parsing the data."
        ) else showPopup(
            context,
            "Oops!! It looks like you are not connected with internet, please check your internet connection and try again."
        )
    }
}