package com.vickydroid.productshowcase.network

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.android.material.snackbar.Snackbar

object NetworkUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun showNoInternetSnackbar(activity: Activity, retryAction: () -> Unit) {
        Snackbar.make(activity.findViewById(android.R.id.content), "No internet connection", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                retryAction()
            }
            .show()
    }
}