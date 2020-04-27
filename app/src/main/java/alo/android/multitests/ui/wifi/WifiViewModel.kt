package alo.android.multitests.ui.wifi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WifiViewModel (application: Application) : AndroidViewModel(application) {
    private var _label = MutableLiveData<String>()
    val label : LiveData<String>
        get() = _label

    private var _listWifi = MutableLiveData<List<String>>()
    val listWifi : LiveData<List<String>>
        get() = _listWifi

    init {
        _label.value = "Yessah!"

        _listWifi.value = listOf("pouf", "paf")
    }

    fun updateLabelByClick() {
        _label.value = "Clicked!"

        refresh()
    }

    fun refresh() {
        _listWifi.value = listOf("Home_tonio", "Devolo_tonio", "EDBC3-Orange")
    }
}
