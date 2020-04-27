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
import timber.log.Timber

class WifiFragment : Fragment() {

    private val viewModel: WifiViewModel by viewModels()
    private val wifiAdapter = WifiAdapter()

    private lateinit var binding : FragmentWifiBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWifiBinding.inflate(inflater)

        initialisation()

        createObservers()

        return binding.root
    }

    private fun createObservers() {
        Timber.d("Start")

        viewModel.listWifi.observe(viewLifecycleOwner, Observer {
            wifiAdapter.setList(it)
            wifiAdapter.notifyDataSetChanged()
        })
    }

    private fun initialisation() {
        Timber.d("Start")

        binding.let {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel

            it.wifiRecyclerView.let {
                it.adapter = wifiAdapter

                it.layoutManager = LinearLayoutManager(context)
            }
        }
    }

    init {
        Timber.d("Start")
    }
}
