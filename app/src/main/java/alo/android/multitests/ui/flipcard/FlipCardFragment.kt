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
import androidx.fragment.app.Fragment

class FlipCardFragment : Fragment() {
    
    lateinit var binding : FragmentFlipcardBinding
    
    var isFront : Boolean = true
    
    lateinit var cardFrontAnimator : Animator
    lateinit var cardBackAnimator : Animator
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        isFront = true
    
        cardFrontAnimator = AnimatorInflater.loadAnimator(context, R.animator.front_card_animation)
        cardBackAnimator = AnimatorInflater.loadAnimator(context, R.animator.back_card_animation)
        
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
//        cardBackAnimator.setTarget(binding.backCardImageView)
        
        cardFrontAnimator.start()
//        cardBackAnimator.start()
        
        binding.frontCardImageView.isClickable = false
        binding.backCardImageView.isClickable = true
        
        isFront = false
    }
    
    private fun backToFrontAnim() {
    
//        cardFrontAnimator.setTarget(binding.backCardImageView)
        cardBackAnimator.setTarget(binding.frontCardLayout)
    
//        cardFrontAnimator.start()
        cardBackAnimator.start()
    
        binding.frontCardImageView.isClickable = true
        binding.backCardImageView.isClickable = false
    
        isFront = true
    }
}
