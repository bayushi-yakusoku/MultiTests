package alo.android.multitests.ui.json

import alo.android.multitests.databinding.FragmentJsonBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

class JsonFragment : Fragment() {
    
    lateinit var binding : FragmentJsonBinding
    
    val viewModel : JsonViewModel by viewModels()
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJsonBinding.inflate(inflater, container, false)
        
        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        
        binding.writeButton.setOnClickListener {
            viewModel.write(binding.questionEditText.text.toString(), binding.answerEditText.text.toString())
        }
        
        binding.readButton.setOnClickListener {
            viewModel.read()
        }
        
        viewModel.content.observe(viewLifecycleOwner, Observer {
            binding.fileContentTextView.text = it
        })
        
        return binding.root
    }
    
}
