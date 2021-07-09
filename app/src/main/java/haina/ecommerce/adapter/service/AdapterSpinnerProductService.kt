package haina.ecommerce.adapter.service

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import haina.ecommerce.R
import haina.ecommerce.model.productservice.DataProductService

class AdapterSpinnerProductService(val context: Context, private val listProduct: List<DataProductService?>?): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return listProduct?.size!!
    }

    override fun getItem(p0: Int): Any {
        return listProduct?.get(p0)!!
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
        val nameProduct = "${listProduct?.get(p0)?.name}"
        vh.label.text = nameProduct

        return view
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.tv_country) as TextView
    }

}