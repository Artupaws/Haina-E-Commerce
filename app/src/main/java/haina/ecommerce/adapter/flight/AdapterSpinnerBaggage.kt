package haina.ecommerce.adapter.flight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import haina.ecommerce.model.flight.BaggageInfosItem

class AdapterSpinnerBaggage(val context: Context, private val listBaggage: List<BaggageInfosItem?>?): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return listBaggage?.size!!
    }

    override fun getItem(p0: Int): Any {
        return listBaggage?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (p1 == null) {
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, p2, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = p1
            vh = view.tag as ItemHolder
        }
        val descBaggage = "${listBaggage?.get(p0)?.desc} - ${listBaggage?.get(p0)?.fare}"
        vh.label.text = descBaggage

        return view
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(android.R.id.text1) as TextView
    }

}