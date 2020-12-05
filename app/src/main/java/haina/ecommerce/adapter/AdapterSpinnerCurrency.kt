package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import haina.ecommerce.R
import haina.ecommerce.databinding.LayoutCurrencyBinding
import haina.ecommerce.databinding.LayoutSpinnerCurrencyBinding
import haina.ecommerce.model.DataCodeCurrency

class AdapterSpinnerCurrency(val context: Context, private val listCountry: List<DataCodeCurrency?>?):
    BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return listCountry?.size!!
    }

    override fun getItem(p0: Int): Any {
        return listCountry?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (p1 == null) {
            view = inflater.inflate(R.layout.layout_spinner_currency, p2, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = p1
            vh = view.tag as ItemHolder
        }
        vh.label.text = listCountry?.get(p0)?.rates

        return view
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.tv_country) as TextView
    }

}