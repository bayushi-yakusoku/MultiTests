package alo.android.multitests.ui.draft

import alo.android.multitests.databinding.FragmentDraftBinding
import alo.android.multitests.tool.toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import timber.log.Timber

class DraftFragment : Fragment() {

    private lateinit var binding : FragmentDraftBinding
    private val viewModel: DraftViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("Start")

        binding = FragmentDraftBinding.inflate(inflater, container, false)

        binding.let {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
            
            it.navigateButton.setOnClickListener {
                context?.toast("Navigate click!")
                val action = DraftFragmentDirections.actionNavDraftToDraftDetailsFragment()
                findNavController().navigate(action)
            }
            
            it.testClickImageButton.setOnClickListener {
                context?.toast("Et Zut! " + it.isClickable + " !")
            }
            
            it.testClickImageButton.isClickable = false
        }

        return binding.root
    }
    
    override fun onDestroyView() {
        Timber.d("Start")
        
        super.onDestroyView()
    }
    
    override fun onDestroy() {
        Timber.d("Start")
        
        viewModel.clean()
        
        super.onDestroy()
    }
}
