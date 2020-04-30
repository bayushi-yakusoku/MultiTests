package alo.android.multitests.ui.wifi;

import alo.android.multitests.databinding.FragmentWifiBinding
import android.content.pm.PackageManager
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
        Timber.d("Start")

        binding = FragmentWifiBinding.inflate(inflater)

        checkPermissions()

        initialisation()

        createObservers()

        return binding.root
    }

    private val MY_PERMISSIONS_REQUEST_FOR_WIFI = 1

    private fun checkPermissions() {
        Timber.d("Start")

        val requiredPermissions = viewModel.missingWifiPermissions()

        if (requiredPermissions.isNotEmpty()) {
            binding.wifiRefreshButton.isEnabled = false

            askWifiPermissions(requiredPermissions)
        }
    }

    private fun askWifiPermissions(requestedPermissions : List<String>) {
        Timber.d("Start")

        requestPermissions(requestedPermissions.toTypedArray(),
            MY_PERMISSIONS_REQUEST_FOR_WIFI)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Timber.d("Start")

        when (requestCode) {
            MY_PERMISSIONS_REQUEST_FOR_WIFI -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    binding.wifiRefreshButton.isEnabled = true
                }
            }
        }
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
