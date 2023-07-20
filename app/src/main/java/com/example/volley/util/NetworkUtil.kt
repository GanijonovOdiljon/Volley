package com.example.volley.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkUtil (private val context: Context){

    fun isNetworkConnected(): Boolean{
        var result = false
        val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        val netWorkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNEtwork =
            connectivityManager.getNetworkCapabilities(netWorkCapabilities)?: return false
        result  = when{
            activeNEtwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNEtwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNEtwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

           else -> false
        }
    }else{
        connectivityManager.run {
            this.activeNetworkInfo?.run {
                result = when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
        return result
    }
}