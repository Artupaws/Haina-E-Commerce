package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemCodeCurrencyBinding
import haina.ecommerce.model.DataCodeCurrency

class AdapterCodeCurrency (val context: Context, private val codeCurrencyList: List<DataCodeCurrency?>?): RecyclerView.Adapter<AdapterCodeCurrency.Holder>() {
    class Holder (view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCodeCurrencyBinding.bind(view)
        fun bind(item: DataCodeCurrency){
            with(binding){
                tvCodeCurrency.text = item.rates
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCodeCurrency.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCodeCurrencyBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterCodeCurrency.Holder, position: Int) {
        val codeCurrency: DataCodeCurrency = codeCurrencyList?.get(position)!!
        holder.bind(codeCurrency)
    }

    override fun getItemCount(): Int = codeCurrencyList?.size!!
}