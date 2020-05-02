package alo.android.multitests.ui.wifi

import alo.android.multitests.R
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
        
        val binding = ItemWifiBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        
        return WifiViewHolder(binding)
    }
    
    override fun getItemCount(): Int {
        Timber.d("Start")
        
        return listWifi.size
    }
    
    override fun onBindViewHolder(holder: WifiViewHolder, position: Int) {
        Timber.d("Start")
        
        val currentWifiItem = listWifi[position]
        
        val currentContext = holder.itemView.context
        
        holder.binding.let {
            it.wifiLabel.apply {
                text = currentWifiItem.ssid
                
                setOnClickListener {
                    context.toast(currentWifiItem.bssid)
                }
            }
            
            it.wifiFrequencyTextView.text = currentContext.getString(R.string.frequency_with_unit, currentWifiItem.frequency)
            
            it.wifiInfoTextView.text = currentWifiItem.capabilities
            it.wifiInfoTextView.isSelected = true
            
            it.wifiActivateImageButton.apply {
                if (currentWifiItem.isConnected) {
                    setColorFilter(currentContext.getColor(R.color.colorAccent))
                    
                    setOnClickListener {
                        currentContext.toast("et Paf!")
                    }
                }
                else {
                    setColorFilter(currentContext.getColor(R.color.colorBlack))
                    
                    isClickable = false
                }
            }
        }
    }
    
    private var listWifi : List<WifiViewModel.WifiItem> = listOf()
    
    fun setList(list : List<WifiViewModel.WifiItem>?) {
        Timber.d("Start")
        
        listWifi = list ?: listOf()
    }
    
    init {
        Timber.d("Start")
    }
}
