package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemNamePaketDataBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.DataItem
import haina.ecommerce.model.pulsaanddata.PaketDataItem

class AdapterPaketDataName (val context: Context, private val listPaketData: List<DataItem?>?): RecyclerView.Adapter<AdapterPaketDataName.Holder>(){

    private var index:Int = -1
    var onItemClick: (Int, String) -> Unit = { i: Int, s: String -> }
    private val helper:Helper = Helper()

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
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPaketDataName.Holder, position: Int) {
        val paketDataName: DataItem = listPaketData?.get(position)!!
        holder.bind(paketDataName)
    }

    override fun getItemCount(): Int = listPaketData?.size!!

    private fun setupListPaketData(binding:ListItemNamePaketDataBinding, list:List<PaketDataItem?>?){
        val adapterPaketData = AdapterPaketData(context, list)
        binding.rvPaketData.apply {
            adapter = AdapterPaketData(context, list)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        adapterPaketData.onItemClick = { i: Int, s: String ->
           onItemClick(i, s)
        }
    }
}