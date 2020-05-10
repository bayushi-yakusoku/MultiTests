package alo.android.multitests.ui.flipcard

import alo.android.multitests.databinding.FragmentFlipcardBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FlipCardFragment : Fragment() {
    
    lateinit var binding : FragmentFlipcardBinding
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        
        binding = FragmentFlipcardBinding.inflate(inflater, container, false)
        
        return binding.root
    }
}
