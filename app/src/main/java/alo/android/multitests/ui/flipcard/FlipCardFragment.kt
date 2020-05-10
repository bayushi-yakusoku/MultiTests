package alo.android.multitests.ui.flipcard

import alo.android.multitests.R
import alo.android.multitests.databinding.FragmentFlipcardBinding
import alo.android.multitests.tool.toast
import android.animation.AnimatorInflater
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
    
        val animator = AnimatorInflater.loadAnimator(context, R.animator.simple_test)
        
        animator.setTarget(binding.cardImageView)
        
        binding.cardImageView.setOnClickListener {
            context?.toast("Click")

//            it.animate().alpha(0f).setDuration(1000)
//            it.animate().rotationYBy(180f).setDuration(1000)
            animator.start()
        }
        
        return binding.root
    }
}
