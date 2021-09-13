package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemNamePaketDataBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.DataItem
import haina.ecommerce.model.pulsaanddata.PaketDataItem
import haina.ecommerce.view.topup.paketdata.PaketDataFragment

class AdapterPaketDataName (val context: Context, private val listPaketData: List<DataItem?>?): RecyclerView.Adapter<AdapterPaketDataName.Holder>(){

    private var index:Int = -1
    var onItemClick: (Int, String) -> Unit = { _: Int, _: String -> }
    private val helper:Helper = Helper
    private var broadcaster:LocalBroadcastManager? = null

    inner class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemNamePaketDataBinding.bind(view)
        fun bind(item: DataItem){
            with(binding) {
                tvPaketDataName.text = item.name
                setupListPaketData(binding, item.paketData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPaketDataName.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemNamePaketDataBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPaketDataName.Holder, position: Int) {
        val paketDataName: DataItem = listPaketData?.get(position)!!
        holder.bind(paketDataName)
    }

    override fun getItemCount(): Int {
        return listPaketData?.size ?: 0
    }


    private fun setupListPaketData(binding:ListItemNamePaketDataBinding, list:List<PaketDataItem?>?){
        val adapterPaketData = AdapterPaketData(context, list)
        binding.rvPaketData.apply {
            adapter = AdapterPaketData(context, list)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        adapterPaketData.indexChoose = { i: Int->
            val index:Int = i
            if (index == -1){
//               PaketDataFragment().resetPrice(true)
            }
        }
    }
}