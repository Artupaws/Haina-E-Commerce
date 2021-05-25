
package haina.ecommerce.adapter.service

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemServiceCategoryBinding
import haina.ecommerce.model.service.DataService
import haina.ecommerce.preference.SharedPreferenceHelper


class AdapterServiceCategory(val context: Context, private val listServiceCategory: List<DataService?>?):
    RecyclerView.Adapter<AdapterServiceCategory.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemServiceCategoryBinding.bind(view)
        fun bind(itemHaina: DataService){
            with(binding){
              tvTitleService.text = itemHaina.name
                setListService(binding,itemHaina)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterServiceCategory.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemServiceCategoryBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterServiceCategory.Holder, position: Int) {
        val data: DataService = listServiceCategory?.get(position)!!
        holder.bind(data)
    }

    override fun getItemCount(): Int = listServiceCategory?.size!!

    private fun setListService(binding: ListItemServiceCategoryBinding, dataService: DataService){
        binding.rvService.apply {
            adapter = AdapterService(context, dataService.category)
            layoutManager = GridLayoutManager(context, 4)
        }
    }

}