package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemHistoryBuyBinding
import haina.ecommerce.model.HistoryBuy

class AdapterHistoryBuy(val context: Context, private val historyBuyList: List<HistoryBuy>): RecyclerView.Adapter<AdapterHistoryBuy.Holder>(){

    inner class Holder (view:View): RecyclerView.ViewHolder(view){
        private val binding = ListItemHistoryBuyBinding.bind(view)
        fun bind(item: HistoryBuy){
            with(binding){
                tvTitle.text = item.title
//                ivProduct.setImageResource(item.image)
                tvPrice.text = item.price
                when (item.status) {
                    "waiting seller" -> {
                        ivWaitingConfirm.setColorFilter(context.resources.getColor(R.color.green))
                    }
                    "waiting payment" -> {
                        ivWaitingConfirm.setColorFilter(context.resources.getColor(R.color.green))
                        ivWaitingPayment.setColorFilter(context.resources.getColor(R.color.green))
                    }
                    "packing" -> {
                        ivWaitingConfirm.setColorFilter(context.resources.getColor(R.color.green))
                        ivWaitingPayment.setColorFilter(context.resources.getColor(R.color.green))
                        ivPacking.setColorFilter(context.resources.getColor(R.color.green))
                    }
                    "delivery" -> {
                        ivWaitingConfirm.setColorFilter(context.resources.getColor(R.color.green))
                        ivWaitingPayment.setColorFilter(context.resources.getColor(R.color.green))
                        ivPacking.setColorFilter(context.resources.getColor(R.color.green))
                        ivDeliver.setColorFilter(context.resources.getColor(R.color.green))
                    }
                    "received" -> {
                        ivWaitingConfirm.setColorFilter(context.resources.getColor(R.color.green))
                        ivWaitingPayment.setColorFilter(context.resources.getColor(R.color.green))
                        ivPacking.setColorFilter(context.resources.getColor(R.color.green))
                        ivDeliver.setColorFilter(context.resources.getColor(R.color.green))
                        ivReceived.setColorFilter(context.resources.getColor(R.color.green))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemHistoryBuyBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val historyBuy: HistoryBuy = historyBuyList[position]
        holder.bind(historyBuy)
    }

    override fun getItemCount(): Int = historyBuyList.size
}