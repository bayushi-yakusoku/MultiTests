package alo.android.multitests.ui.wifi

import android.Manifest
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

class WifiViewModel (application: Application) : AndroidViewModel(application) {
    private var _label = MutableLiveData<String>()
    val label : LiveData<String>
        get() = _label

    private var _listWifi = MutableLiveData<List<WifiItem>>()
    val listWifi : LiveData<List<WifiItem>>
        get() = _listWifi

    fun updateLabelByClick() {
        Timber.d("Start")

        _label.value = "Clicked!"

        refresh()
    }

    private fun refresh() {
        Timber.d("Start")

        _listWifi.value = listOf()

        requestScan()
    }

    private lateinit var wifiManager : WifiManager

    private lateinit var wifiScanReceiver: BroadcastReceiver

    private fun requestScan() {
        Timber.d("Start")

        if ( missingWifiPermissions().isNotEmpty() ) return

        startScan()
    }

    val requiredPermissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION)

    fun missingWifiPermissions() : List<String> {
        Timber.d("Start")

        val missingPermissions = mutableListOf<String>()

        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(getApplication(), permission)
                == PackageManager.PERMISSION_DENIED) {
                // Permission is denied
                missingPermissions.add(permission)
            }
        }

        return missingPermissions
    }

    private fun startScan() {
        Timber.d("Start")

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)

        getApplication<Application>().applicationContext.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        if (!success) {
            // scan failure handling
            scanFailure()
        }
    }

    private fun scanSuccess() {
        Timber.d("Start")

        _label.value = "Scan success!"

        val tmp = wifiManager.connectionInfo
        
        val results = wifiManager.scanResults
        // ... use new scan results ...
        
        _listWifi.value = results.map {
            val status = it.BSSID == tmp.bssid
            it.toWifiItem(status)
        }
    }

    private fun scanFailure() {
        Timber.d("Start")

        _label.value = "Scan failed!"
    
        val tmp = wifiManager.connectionInfo
        
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        // ... potentially use older scan results ...
        
        _listWifi.value = results.map {
            val status = it.BSSID == tmp.bssid
            it.toWifiItem(status)
        }
    }

    init {
        Timber.d("Start")

        _label.value = "Yessah!"

        _listWifi.value = listOf()

        wifiManager = getApplication<Application>().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success) {
                    scanSuccess()
                } else {
                    scanFailure()
                }
            }
        }
    }
    
    data class WifiItem(
            val ssid : String,
            val bssid : String,
            val frequency : Int,
            val capabilities : String = "",
            var isConnected : Boolean = false
    )
}

fun ScanResult.toWifiItem(isConnected: Boolean = false) : WifiViewModel.WifiItem {
    val wifiItem = WifiViewModel.WifiItem(SSID, BSSID, frequency, capabilities, isConnected)
    
    return wifiItem
}
