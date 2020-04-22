package alo.android.multitests.ui.draft

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

class DraftViewModel(application: Application) : AndroidViewModel(application) {
    private var _label = MutableLiveData<String>()
    val label : LiveData<String>
        get() = _label

    init {
        Timber.d("Start")
        _label.value = "Yessah!"
    }

    fun updateLabelByClick() {
        Timber.d("Start")
        _label.value = "Clicked!"
    }
}
