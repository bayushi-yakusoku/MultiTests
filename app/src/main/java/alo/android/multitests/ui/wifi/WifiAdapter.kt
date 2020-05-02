package alo.android.multitests.ui.wifi

import alo.android.multitests.databinding.ItemWifiBinding
import alo.android.multitests.tool.toast
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class WifiAdapter() : RecyclerView.Adapter<WifiAdapter.WifiViewHolder>() {
    class WifiViewHolder(val binding: ItemWifiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiViewHolder {
        Timber.d("Start")

        return WifiViewHolder(ItemWifiBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        Timber.d("Start")

        return listWifi.size
    }

    override fun onBindViewHolder(holder: WifiViewHolder, position: Int) {
        Timber.d("Start")

        holder.binding.let {
            it.wifiLabel.apply {
                text = listWifi[position]

                setOnClickListener {
                    context.toast(listWifi[position])
                }
            }

            it.wifiFrequencyTextView.text = "12 GHz"
            it.wifiInfoTextView.text = "WPA/WPA2 PSK"
        }
    }

    private var listWifi : List<String> = listOf()

    public fun setList(list : List<String>?) {
        Timber.d("Start")

        listWifi = list ?: listOf()
    }

    init {
        Timber.d("Start")
    }
}
