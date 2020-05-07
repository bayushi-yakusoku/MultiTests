package alo.android.multitests.ui.draft

import alo.android.multitests.databinding.FragmentDraftDetailsBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import timber.log.Timber

class DraftDetailsFragment : Fragment(){
    
    private lateinit var binding :  FragmentDraftDetailsBinding
    
    private val viewModel : DraftViewModel by activityViewModels()
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Timber.d("Start")
        
        binding = FragmentDraftDetailsBinding.inflate(inflater, container, false)
        
        binding.labelDetailsTextView.text = viewModel.label.value
        
        return binding.root
    }
}
