package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemCovidBinding
import haina.ecommerce.model.DataCovid

class AdapterCovid(val context: Context, val list: List<DataCovid?>?) :
    RecyclerView.Adapter<AdapterCovid.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemCovidBinding.bind(view)
        fun bind(item: DataCovid) {
            with(binding) {
                tvProvince.text = item.provinsi
                tvTotalCases.text = item.kasusPosi.toString()
                tvTotalRecover.text = item.kasusSemb.toString()
                tvTotalDie.text = item.kasusMeni.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCovid.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCovidBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterCovid.Holder, position: Int) {
        val covid: DataCovid = list?.get(position)!!
        holder.bind(covid)
    }

    override fun getItemCount(): Int = list?.size!!
}