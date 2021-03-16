package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemPaketDataBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.PaketData
import haina.ecommerce.model.Pulsa

class AdapterPaketData (val context: Context, private val listPaketData: List<PaketData>): RecyclerView.Adapter<AdapterPaketData.Holder>(){

    private var index:Int = -1
    var onItemClick: (Int, String) -> Unit = { i: Int, s: String -> }
    private val helper:Helper = Helper()

    inner class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemPaketDataBinding.bind(view)
        fun bind(item: PaketData){
            with(binding){
                tvTitle.text = item.title
                tvDescription.text = item.description
                tvPrice.text = helper.convertToFormatMoneyIDRFilter(item.price.toString())
                linearClick.setOnClickListener {index = adapterPosition
                    notifyDataSetChanged()
                    onItemClick(item.price, "paketData")
                }
                if (index == adapterPosition){
                    linearPulsa.setBackgroundResource(R.drawable.background_internet_enable)
                }else{
                    linearPulsa.setBackgroundResource(R.drawable.background_internet_disable)
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
        val paketData: PaketData = listPaketData[position]
        holder.bind(paketData)
    }

    override fun getItemCount(): Int = listPaketData.size
}