package alo.android.multitests.ui.wifi;

import alo.android.multitests.databinding.FragmentWifiBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

class WifiFragment : Fragment() {

    private val viewModel: WifiViewModel by viewModels()
    private lateinit var binding : FragmentWifiBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWifiBinding.inflate(inflater)

        binding.let {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
        }

        return binding.root
    }
}
