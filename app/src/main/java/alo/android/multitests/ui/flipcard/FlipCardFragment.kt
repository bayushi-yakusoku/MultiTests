package alo.android.multitests.ui.flipcard

import alo.android.multitests.R
import alo.android.multitests.databinding.FragmentFlipcardBinding
import alo.android.multitests.tool.toast
import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment

class FlipCardFragment : Fragment() {
    
    lateinit var binding : FragmentFlipcardBinding
    
    lateinit var cardFrontAnimator : Animator
    lateinit var cardBackAnimator : Animator
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cardFrontAnimator = AnimatorInflater.loadAnimator(context, R.animator.front_card_animation)
        cardBackAnimator = AnimatorInflater.loadAnimator(context, R.animator.back_card_animation)
    
        cardFrontAnimator.addListener(onEnd = {
            cardBackAnimator.start()
        })
    
    
        binding = FragmentFlipcardBinding.inflate(inflater, container, false)
        
        binding.frontCardImageView.setOnClickListener {
            context?.toast("Click on Front!")
    
            frontToBackAnim()
        }
        
        binding.backCardImageView.setOnClickListener {
            context?.toast("Click on Back!!")
            
            backToFrontAnim()
        }
        
        return binding.root
    }
    
    private fun frontToBackAnim() {
        cardFrontAnimator.setTarget(binding.frontCardLayout)
        cardBackAnimator.setTarget(binding.backCardLayout)
        
        cardFrontAnimator.start()
        
        binding.frontCardImageView.isClickable = false
        binding.backCardImageView.isClickable = true
    }
    
    private fun backToFrontAnim() {
    
        cardFrontAnimator.setTarget(binding.backCardLayout)
        cardBackAnimator.setTarget(binding.frontCardLayout)
        
        cardFrontAnimator.start()
    
        binding.frontCardImageView.isClickable = true
        binding.backCardImageView.isClickable = false
    }
}
