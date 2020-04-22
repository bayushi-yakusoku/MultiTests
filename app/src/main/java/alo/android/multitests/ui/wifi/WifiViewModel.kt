package alo.android.multitests.ui.wifi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WifiViewModel (application: Application) : AndroidViewModel(application) {
    private var _label = MutableLiveData<String>()
    val label : LiveData<String>
        get() = _label

    init {
        _label.value = "Yessah!"
    }

    fun updateLabelByClick() {
        _label.value = "Clicked!"
    }
}
