package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemPulsaBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.Pulsa

class AdapterPulsa (val context: Context, private val listPulsa: List<Pulsa>): RecyclerView.Adapter<AdapterPulsa.Holder>(){

    private var index:Int = -1
    var onItemClick: (Int, String) -> Unit = { i: Int, s: String -> }
    private val helper:Helper = Helper()

    inner class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemPulsaBinding.bind(view)
        fun bind(item: Pulsa){
            with(binding){
                tvNominal.text = item.nominal
                tvPrice.text = helper.convertToFormatMoneyIDRFilter(item.price.toString())
                linearClick.setOnClickListener {index = adapterPosition
                    notifyDataSetChanged()
                    onItemClick(item.price, "pulsa")
                }
                if (index == adapterPosition){
                    linearPulsa.setBackgroundResource(R.drawable.background_internet_enable)
                }else{
                    linearPulsa.setBackgroundResource(R.drawable.background_internet_disable)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPulsa.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPulsaBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterPulsa.Holder, position: Int) {
        val pulsa: Pulsa = listPulsa[position]
        holder.bind(pulsa)
    }

    override fun getItemCount(): Int = listPulsa.size
}