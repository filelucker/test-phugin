package com.shurjopay.lib.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
object NetworkManager {

  @RequiresApi(Build.VERSION_CODES.M)
  fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
      if (capabilities != null) {
        when {
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
           Log.i(TAG, "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
          }
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
//            Log.i(TAG, "NetworkCapabilities.TRANSPORT_WIFI")
            return true
          }
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
//            Log.i(TAG, "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
          }
        }
      }
    return false
  }

  private const val TAG = "NetworkManager"
}
