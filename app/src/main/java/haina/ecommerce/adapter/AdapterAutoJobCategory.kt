package haina.ecommerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.annotation.LayoutRes
import haina.ecommerce.R
import haina.ecommerce.model.Data
import haina.ecommerce.model.DataItem
import haina.ecommerce.view.postingjob.PostingJobActivity
import kotlinx.coroutines.flow.asFlow

class AdapterAutoJobCategory(val c: Context, @LayoutRes private val layoutResource: Int, private val jobCategory: ArrayList<DataItem?>?) :
        ArrayAdapter<DataItem?>(c, layoutResource, jobCategory!!) {

    var filteredCategory: ArrayList<DataItem?>? = null!!

    override fun getCount(): Int = jobCategory?.size!!

    override fun getItem(position: Int): DataItem? {
        return jobCategory?.get(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView?: LayoutInflater.from(parent.context).inflate(layoutResource,parent, false)
        val name : TextView = view.findViewById(R.id.tv_name)
        name.text = jobCategory?.get(position)?.name
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val queryString = p0?.toString()?.toLowerCase()
                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    jobCategory?.asFlow()
                else
                    jobCategory?.filter {
                        it?.name?.toLowerCase()?.contains(queryString)!! || it.name.toString().contains(queryString)
                    }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                filteredCategory = p1?.values as ArrayList<DataItem?>?
                notifyDataSetChanged()
            }

        }
    }


}