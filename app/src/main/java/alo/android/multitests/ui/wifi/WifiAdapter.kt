package alo.android.multitests.ui.wifi

import alo.android.multitests.databinding.ItemWifiBinding
import alo.android.multitests.tool.toast
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WifiAdapter() : RecyclerView.Adapter<WifiAdapter.WifiViewHolder>() {
    class WifiViewHolder(val binding: ItemWifiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiViewHolder {
        return WifiViewHolder(ItemWifiBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int = listWifi.size

    override fun onBindViewHolder(holder: WifiViewHolder, position: Int) {
        holder.binding.wifiLabel.apply {
            text = listWifi[position]

            setOnClickListener {
                context.toast(listWifi[position])
            }
        }
    }

    private var listWifi : List<String> = listOf()

    public fun setList(list : List<String>?) {
        listWifi = list ?: listOf()
    }
}
