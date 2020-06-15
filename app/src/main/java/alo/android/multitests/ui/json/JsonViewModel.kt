package alo.android.multitests.ui.json

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

class JsonViewModel(application: Application) : AndroidViewModel(application) {
    val label = "Label du fragment Json"
    
    private val fileName = "test.json"
    
    private var _content = MutableLiveData<String>()
    val content : LiveData<String>
        get() = _content
    
    fun write(question : String = "bla?", answer : String = "gne?!?!") {
        writeToFile(FlashCard(question, answer, listOf("tag1", "tag2")))
    }
    
    private fun writeToFile(flashCard: FlashCard) {
        val jsonString = Klaxon().toJsonString(flashCard)
        
        getApplication<Application>().openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }
        
    }
    
    fun read() {
        readFromFile()
    }
    
    private fun readFromFile() {
        _content.value = getApplication<Application>().openFileInput(fileName)
                .readBytes()
                .toString(Charsets.UTF_8)
    }
}

data class FlashCard (@Json(index = 1) var question : String,
                      @Json(index = 2) var answer : String,
                      @Json(index = 3) var tags : List<String>? = null,
                      @Json(index = 4) var properties : Map<String, String>? = null)
