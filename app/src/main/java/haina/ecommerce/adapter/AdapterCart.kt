package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemCartBinding
import haina.ecommerce.model.Cart

class AdapterCart (val context: Context, private val cartList: List<Cart>): RecyclerView.Adapter<AdapterCart.Holder>(){

    inner class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCartBinding.bind(view)
        fun bind(item: Cart){
            with(binding){
//                tvTitleProduct.text = item.title
                tvPriceProduct.text = item.price
//                ivProduct.setImageResource(item.image)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCart.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCartBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterCart.Holder, position: Int) {
        val cart: Cart = cartList[position]
        holder.bind(cart)
    }

    override fun getItemCount(): Int = cartList.size
}