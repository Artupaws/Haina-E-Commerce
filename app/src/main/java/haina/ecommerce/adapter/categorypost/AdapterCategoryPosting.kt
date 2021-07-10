package haina.ecommerce.adapter.categorypost

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemCategoryBinding
import haina.ecommerce.model.categorypost.DataCategory
import haina.ecommerce.model.service.CategoryService
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants


class AdapterCategoryPosting(val context: Context, private val listCategory: List<DataCategory?>?,
                             val itemAdapterCallback: ItemAdapterCallback, val codeLanguage:String):
    RecyclerView.Adapter<AdapterCategoryPosting.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemCategoryBinding.bind(view)
        fun bind(itemHaina: DataCategory, itemAdapterCallback: ItemAdapterCallback){
            with(binding){
                val icon = HtmlCompat.fromHtml("${itemHaina.icon}", HtmlCompat.FROM_HTML_MODE_LEGACY)
//                setLanguage(codeLanguage, binding, itemHaina)
                faIcon.text = icon
                linearDataCategory.setOnClickListener {
                    itemAdapterCallback.onAdapterClick(linearDataCategory, itemHaina)
                }
                when(codeLanguage){
                    "en"-> {
                        tvNameCategory.text = itemHaina.name
                    } "zh" -> {
                        tvNameCategory.text = itemHaina.nameZh
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCategoryPosting.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCategoryBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterCategoryPosting.Holder, position: Int) {
        val photo: DataCategory = listCategory?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listCategory?.size!!

    private fun setLanguage(codeLanguage:String?, binding: ListItemCategoryBinding, data: DataCategory){
        when (codeLanguage){
            "zh"-> {
                binding.tvNameCategory.text = data.nameZh
            }
            "en"-> {
                binding.tvNameCategory.text = data.name
            }
        }
    }

    interface ItemAdapterCallback{
        fun onAdapterClick(view: View, data:DataCategory)
    }

}