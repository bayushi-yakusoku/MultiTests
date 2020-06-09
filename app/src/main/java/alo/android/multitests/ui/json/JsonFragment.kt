package alo.android.multitests.ui.json

import alo.android.multitests.databinding.FragmentJsonBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

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
        
        return binding.root
    }
    
}
