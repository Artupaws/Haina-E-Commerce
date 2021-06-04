package haina.ecommerce.adapter.service

import android.content.Context
import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.caverock.androidsvg.SVG
import haina.ecommerce.databinding.ListItemServiceBinding
import haina.ecommerce.databinding.ListItemServiceCategoryBinding
import haina.ecommerce.model.service.CategoryService
import haina.ecommerce.model.service.DataService
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.electricity.ElectricityActivity
import haina.ecommerce.view.housephone.HousePhoneActivity
import haina.ecommerce.view.internetandtv.InternetActivity
import haina.ecommerce.view.topup.TopupActivity
import haina.ecommerce.view.water.WaterActivity
import java.net.URL


class AdapterService(val context: Context, private val listService: List<CategoryService?>?):
    RecyclerView.Adapter<AdapterService.Holder>() {

    private lateinit var sharedPref: SharedPreferenceHelper

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemServiceBinding.bind(view)
        fun bind(itemHaina: CategoryService){
            with(binding){
                tvTitleService.text = itemHaina.name
                val icon = HtmlCompat.fromHtml("${itemHaina.iconCode}",HtmlCompat.FROM_HTML_MODE_LEGACY)
                ivIconService.text = icon
                val codeLanguage = sharedPref.getValueString(Constants.LANGUAGE_APP)
                setLanguage(codeLanguage, binding, itemHaina)
                itemView.setOnClickListener {
                    when(itemHaina.name){
                        "Internet Service Provider" -> {
                            val intent = Intent(context, InternetActivity::class.java)
                                .putExtra("idProductCategory", itemHaina.id)
                                .putExtra("category", itemHaina.name)
                            context.startActivity(intent)
                        }
                        "Electricity" -> {
                            val intent = Intent(context, InternetActivity::class.java)
                                .putExtra("idProductCategory", itemHaina.id)
                                .putExtra("category", itemHaina.name)
                            context.startActivity(intent)
                        }
                        "Water" -> {
                            val intent = Intent(context, InternetActivity::class.java)
                                .putExtra("idProductCategory", itemHaina.id)
                                .putExtra("category", itemHaina.name)
                            context.startActivity(intent)
                        }
                        "Telephone" -> {
                            val intent = Intent(context, InternetActivity::class.java)
                                .putExtra("idProductCategory", itemHaina.id)
                                .putExtra("category", itemHaina.name)
                            context.startActivity(intent)
                        }
                        "Mobile Credits" -> {
                            val intent = Intent(context, TopupActivity::class.java)
                            context.startActivity(intent)
                        }
                        "Data Plan" -> {
                            val intent = Intent(context, TopupActivity::class.java)
                            context.startActivity(intent)
                        }
                        "TV Cable" -> {
                            val intent = Intent(context, InternetActivity::class.java)
                                .putExtra("idProductCategory", itemHaina.id)
                                .putExtra("category", itemHaina.name)
                            context.startActivity(intent)
                        }
                        "Insurance" -> {
                            val intent = Intent(context, InternetActivity::class.java)
                                .putExtra("idProductCategory", itemHaina.id)
                                .putExtra("category", itemHaina.name)
                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterService.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemServiceBinding.inflate(inflater)
        sharedPref = SharedPreferenceHelper(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterService.Holder, position: Int) {
        val data: CategoryService = listService?.get(position)!!
        holder.bind(data)
    }

    override fun getItemCount(): Int = listService?.size!!

    private fun setLanguage(codeLanguage:String?, binding: ListItemServiceBinding, data: CategoryService){
        when (codeLanguage){
            "zh" -> {
                binding.tvTitleService.text = data.nameZh
            }
            "en" -> {
                binding.tvTitleService.text = data.name
            }
        }
    }

}