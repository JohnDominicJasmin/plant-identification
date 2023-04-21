package com.example.plantidentification.feature_plant_identification.core.utils.connection

import android.content.Context
import android.content.IntentSender
import android.location.LocationManager


object ConnectionStatus {




    fun Context.hasInternetConnection(): Boolean {
        return NetworkConnectivityUtil(this).hasInternet()
    }


}