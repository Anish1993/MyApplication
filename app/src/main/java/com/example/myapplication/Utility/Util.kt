package com.example.myapplication.Utility

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

class Util {


    companion object Util {


        @SuppressLint("MissingPermission")
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var isConnected = false
            val activeNetwork = connectivityManager.activeNetworkInfo
            isConnected = activeNetwork != null && activeNetwork.isConnected

            return isConnected
        }


    }
}