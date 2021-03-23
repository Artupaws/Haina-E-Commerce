package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemNamePaketDataBinding
import haina.ecommerce.databinding.ListItemPaketDataBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.PaketDataItem

class AdapterPaketData (val context: Context, private val listPaketData: List<PaketDataItem?>?): RecyclerView.Adapter<AdapterPaketData.Holder>(){

    private var index:Int = -1
    var onItemClick: (Int, String) -> Unit = { i: Int, s: String -> }
    private val helper:Helper = Helper()

    inner class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemPaketDataBinding.bind(view)
        fun bind(item: PaketDataItem){
            with(binding) {
                tvDescription.text = item.description
                tvPrice.text = helper.convertToFormatMoneyIDRFilter(item.sellPrice.toString())
                linearClick.setOnClickListener {index = adapterPosition
                    notifyDataSetChanged()
                    onItemClick(item.sellPrice!!, tvPrice.text.toString())
                }
                if (index == adapterPosition){
                    linearClick.setBackgroundResource(R.drawable.background_internet_enable)
                }else{
                    linearClick.setBackgroundResource(R.drawable.background_internet_disable)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPaketData.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPaketDataBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPaketData.Holder, position: Int) {
        val paketData: PaketDataItem = listPaketData?.get(position)!!
        holder.bind(paketData)
    }

    override fun getItemCount(): Int = listPaketData?.size!!
}