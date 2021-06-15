package haina.ecommerce.adapter.flight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import haina.ecommerce.databinding.LayoutAutotextcompleteBinding
import haina.ecommerce.model.flight.CountriesItem

class AutoTextCompleteViewAdapter(
    val c: Context, private val resourceId:Int, val listNationality: List<CountriesItem?>?,
    private val itemAdapterCallback: ItemAdapterCallback): ArrayAdapter<CountriesItem>(c, resourceId, listNationality!!){

    private lateinit var binding:LayoutAutotextcompleteBinding

    override fun getCount(): Int = listNationality!!.size

    override fun getItem(position: Int): CountriesItem = listNationality?.get(position)!!

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(c).inflate(resourceId, parent, false)
//        view.tv_country.text = listNationality?.get(position)?.countryName
//        view.tv_country.setOnClickListener {
//            itemAdapterCallback.onAdapterClick(view.tv_country, listNationality?.get(position)?.countryID)
//        }
        return view
    }

    interface ItemAdapterCallback{
        fun onAdapterClick(view:View, data: String?)
    }

}