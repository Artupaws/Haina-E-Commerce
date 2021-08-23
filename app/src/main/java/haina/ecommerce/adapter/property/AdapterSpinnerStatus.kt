package haina.ecommerce.adapter.property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import haina.ecommerce.R
import java.security.AccessController.getContext

class AdapterSpinnerStatus(val context: Context, private val listStatus: List<String?>?): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val initParams = true

    override fun getCount(): Int {
        return listStatus?.size!!
    }

    override fun getItem(p0: Int): Any {
        return listStatus?.get(p0)!!
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
        val baseCodeCurrency = listStatus?.get(p0)
        vh.label.text = baseCodeCurrency
        return view
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.tv_country) as TextView
    }

}