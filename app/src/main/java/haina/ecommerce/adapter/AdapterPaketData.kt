package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemPaketDataBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.PaketDataItem
import haina.ecommerce.view.topup.paketdata.PaketDataFragment

class AdapterPaketData(val context: Context, private val listPaketData: List<PaketDataItem?>?): RecyclerView.Adapter<AdapterPaketData.Holder>(){

    private var index:Int = -1
    var onItemClick: (Int, String) -> Unit = { i: Int, s: String -> }
    var indexChoose:(Int)-> Unit ={i:Int->}
    private val helper:Helper = Helper
    private var broadcaster:LocalBroadcastManager? = null

    inner class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemPaketDataBinding.bind(view)
        fun bind(item: PaketDataItem){
            with(binding) {
                tvTitle.text = item.description
                tvDescription.text = item.description
                tvPrice.text = helper.convertToFormatMoneyIDRFilter(item.sellPrice.toString())
                linearClick.setOnClickListener {index = adapterPosition
                    notifyDataSetChanged()
                    val intentPaketData = Intent("paketData")
                            .putExtra("serviceType",item.description)
                            .putExtra("sellPrice", item.sellPrice.toString())
                            .putExtra("idProduct", item.id)
                    broadcaster?.sendBroadcast(intentPaketData)
                }
                if (index == adapterPosition){
                    linearClick.setBackgroundResource(R.drawable.background_internet_enable)
                }else{
                    linearClick.setBackgroundResource(R.drawable.background_internet_disable)
                    val intentResetPrice = Intent("resetPrice")
                            .putExtra("reset","true")
                    broadcaster?.sendBroadcast(intentResetPrice)
                }
                indexChoose(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPaketData.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPaketDataBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPaketData.Holder, position: Int) {
        val paketData: PaketDataItem = listPaketData?.get(position)!!
        holder.bind(paketData)
    }

    override fun getItemCount(): Int = listPaketData?.size!!
}