package alo.android.multitests.ui.speech

import alo.android.multitests.databinding.FragmentSpeechBinding
import alo.android.multitests.tool.toast
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import timber.log.Timber
import java.util.*

const val SPEECH_REQUEST_CODE = 1

class SpeechFragment : Fragment() {
    
    private lateinit var binding : FragmentSpeechBinding
    private val viewModel: SpeechViewModel by viewModels()
    
    private lateinit var tts : TextToSpeech
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Timber.d("Start")
        
        tts = TextToSpeech(context) {status ->
            when (status) {
                TextToSpeech.SUCCESS -> {
                    context?.toast("TTS initialised")
                    tts.setLanguage(Locale.FRANCE)
                    binding.listenImageButton.setOnClickListener { listen() }
                }
                
                TextToSpeech.ERROR -> context?.toast("TTS init failed")
                
                else -> context?.toast("TTS status unknown")
            }
        }
        
        binding = FragmentSpeechBinding.inflate(inflater, container, false)
        
        binding.speakImageButton.setOnClickListener { speak() }
        
        return binding.root
    }
    
    fun listen() {
        Timber.d("Start")
        
        context?.toast("Listen click")
        
        if (binding.speechResultTextView.text.isBlank())
            return
        
        var pitch = binding.pitchSeekBar.progress / 50f
        if (pitch < 0.1f)
            pitch = 0.1f
        
        tts.setPitch(pitch)
        
        var speed = binding.speedSeekBar.progress / 50f
        if (speed < 0.1f)
            speed = 0.1f
        
        tts.setSpeechRate(speed)
        
        val text = binding.speechResultTextView.text.toString()
        
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "maRequest")
    }
    
    fun speak() {
        Timber.d("Start")
        
        displaySpeechRecognizer()
    }
    
    private fun displaySpeechRecognizer() {
        Timber.d("Start")
        
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("Start")
        
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                    data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                        results?.get(0)
                    }
            
            binding.speechResultTextView.text = spokenText
        }
        else {
            binding.speechResultTextView.text = "Rate..."
        }
        
        super.onActivityResult(requestCode, resultCode, data)
    }
    
    override fun onDestroy() {
        Timber.d("Start")
        
        super.onDestroy()
    
        tts.shutdown()
    }
}
