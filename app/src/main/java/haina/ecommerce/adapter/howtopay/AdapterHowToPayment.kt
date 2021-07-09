package haina.ecommerce.adapter.howtopay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemHowToPayBinding
import haina.ecommerce.model.howtopay.InstructionsItem

class AdapterHowToPayment(val context: Context, private val listHowToPay: List<InstructionsItem?>?):
RecyclerView.Adapter<AdapterHowToPayment.Holder>(){
    inner class Holder(view:View):RecyclerView.ViewHolder(view) {
        private val binding = ListItemHowToPayBinding.bind(view)
        fun bind(data:InstructionsItem){
            with(binding){
                tvTitlePaymentMethod.text = data.paymentMedia
                tvDescriptionPayment.text = data.howTo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHowToPayment.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemHowToPayBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterHowToPayment.Holder, position: Int) {
        val data : InstructionsItem = listHowToPay?.get(position)!!
        holder.bind(data)
    }

    override fun getItemCount(): Int = listHowToPay!!.size
}