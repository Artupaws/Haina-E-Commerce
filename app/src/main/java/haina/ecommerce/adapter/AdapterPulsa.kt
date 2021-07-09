package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemPulsaBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.pulsaanddata.PulsaItem

class AdapterPulsa (val context: Context, private val listPulsa: List<PulsaItem?>?, private val itemAdapterCallback: ItemAdapterCallback):
    RecyclerView.Adapter<AdapterPulsa.Holder>(){

    private var index:Int = -1
    var indexChoose:(Int)-> Unit ={ _:Int->}
    private val helper:Helper = Helper

    inner class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemPulsaBinding.bind(view)
        fun bind(item: PulsaItem){
            with(binding){
                tvNominal.text = item.description
                tvPrice.text = helper.convertToFormatMoneyIDRFilter(item.sellPrice.toString())
                itemView.setOnClickListener {index = adapterPosition
                    notifyDataSetChanged()
                    itemAdapterCallback.onClickAdapter(itemView, item)
                }
                if (index == adapterPosition){
                    linearPulsa.setBackgroundResource(R.drawable.background_internet_enable)
                }else{
                    linearPulsa.setBackgroundResource(R.drawable.background_internet_disable)
                }
                indexChoose(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPulsa.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPulsaBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPulsa.Holder, position: Int) {
        val pulsa: PulsaItem = listPulsa?.get(position)!!
        holder.bind(pulsa)
    }

    override fun getItemCount(): Int = listPulsa?.size!!

    interface ItemAdapterCallback{
        fun onClickAdapter(view: View, data:PulsaItem)
    }
}