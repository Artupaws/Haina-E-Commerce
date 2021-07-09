package haina.ecommerce.adapter.flight

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import haina.ecommerce.model.flight.CountriesItem


class AutoCompleteAdapter(context: Context?, val textViewResourceId: Int, customers: List<CountriesItem>, val itemAdapterCallback: ItemAdapterCallback) :
    ArrayAdapter<CountriesItem?>(context!!, textViewResourceId, customers) {
    private val layoutInflater: LayoutInflater
    var mCustomers: MutableList<CountriesItem> = ArrayList(customers.size)
    private val mFilter: Filter = object : Filter() {
        override fun convertResultToString(resultValue: Any): String {
            return (resultValue as CountriesItem).countryName!!
        }

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            if (constraint != null) {
                val suggestions: ArrayList<CountriesItem> = ArrayList()
                for (customer in mCustomers) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.countryName!!.toLowerCase()
                            .contains(constraint.toString().toLowerCase())
                    ) {
                        suggestions.add(customer)
                    }
                }
                results.values = suggestions
                results.count = suggestions.size
            }
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            clear()
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll(results.values as ArrayList<CountriesItem?>)
            } else {
                // no filter, add entire original list back in
                addAll(mCustomers)
            }
            notifyDataSetChanged()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        if (view == null) {
            view = layoutInflater.inflate(textViewResourceId, null)
        }
        val customer: CountriesItem? = getItem(position)
        val name = view?.findViewById(R.id.text1) as TextView
        name.text = customer?.countryName
        name.setOnClickListener {
            itemAdapterCallback.onAdapterClick(name, customer!!)
        }
        return view
    }

    override fun getFilter(): Filter {
        return mFilter
    }

    init {
        // copy all the customers into a master list
        mCustomers.addAll(customers)
        layoutInflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    interface ItemAdapterCallback{
        fun onAdapterClick(view: View, data:CountriesItem)
    }
}