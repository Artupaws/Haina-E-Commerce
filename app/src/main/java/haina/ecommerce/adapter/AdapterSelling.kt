package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemSellingBinding
import haina.ecommerce.model.HistoryBuy
import haina.ecommerce.model.Selling

class AdapterSelling(val context: Context, private val sellingList: List<Selling>): RecyclerView.Adapter<AdapterSelling.Holder>(){

    inner class Holder (view:View): RecyclerView.ViewHolder(view){
        private val binding = ListItemSellingBinding.bind(view)
        fun bin(item: Selling){
            with(binding){
                ivProduct.setImageResource(item.image)
                tvTitleProduct.text = item.title
                tvPriceProduct.text = item.price
                tvDescProduct.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSellingBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val selling: Selling = sellingList[position]
        holder.bin(selling)
    }

    override fun getItemCount(): Int = sellingList.size
}