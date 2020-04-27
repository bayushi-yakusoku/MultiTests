package alo.android.multitests.ui.wifi;

import alo.android.multitests.databinding.FragmentWifiBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

class WifiFragment : Fragment() {

    private val viewModel: WifiViewModel by viewModels()
    private lateinit var binding : FragmentWifiBinding
    private lateinit var wifiAdapter : WifiAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWifiBinding.inflate(inflater)

        wifiAdapter = WifiAdapter()

        binding.let {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel

            it.wifiRecyclerView.let {
                it.adapter = wifiAdapter

                it.layoutManager = LinearLayoutManager(context)
            }
        }

        viewModel.listWifi.observe(viewLifecycleOwner, Observer {
            wifiAdapter.setList(it)
            wifiAdapter.notifyDataSetChanged()
        })

        return binding.root
    }
}
