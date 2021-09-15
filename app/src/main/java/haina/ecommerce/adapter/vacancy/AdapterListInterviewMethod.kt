package haina.ecommerce.adapter.vacancy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import haina.ecommerce.model.flight.BaggageInfosItem

class AdapterListInterviewMethod(val context: Context, private val list: Array<String>?): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return list?.size!!
    }

    override fun getItem(p0: Int): Any {
        return list?.get(p0)!!
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
        val descBaggage = "${list?.get(p0)} - ${list?.get(p0)}"
        vh.label.text = descBaggage

        return view
    }

    private class ItemHolder(row: View?) {
        val label: TextView = row?.findViewById(android.R.id.text1) as TextView
    }

}