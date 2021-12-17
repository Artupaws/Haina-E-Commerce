package haina.ecommerce.adapter.vacancy

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemHistoryApplicationBinding
import haina.ecommerce.databinding.ListItemJobVacancyBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.property.DataShowProperty
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import java.util.ArrayList

class AdapterListMyApplication(val context: Context,
                        private var listApplication: ArrayList<MyApplication?>?,
                        private val adapterCallbackApplication:AdapterCallbackApplication) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private var listApplicationResult : List<MyApplication?>? = listApplication

    inner class ViewHolderApplication(val binding: ListItemHistoryApplicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: MyApplication, adapterCallbackApplication: AdapterCallbackApplication) {
            with(binding) {
                tvStatus.text = itemHaina.status
                tvTitleJob.text = itemHaina.vacancy!!.position
                val datePublish = Helper.dateFormat(itemHaina.createdAt)
                tvDatePublish.text = datePublish
                if (itemHaina.vacancy.salaryDisplay == 1){
                    val salary = "${Helper.convertToFormatMoneyIDRFilter(itemHaina.vacancy.minSalary.toString())}-${Helper.convertToFormatMoneyIDRFilter(itemHaina.vacancy.maxSalary.toString())}"
                    tvSalary.text = salary
                } else {
                    tvSalary.text = context.getString(R.string.salary_hidden)
                }
                tvCompanyName.text = itemHaina.vacancy.company!!.name
                Glide.with(context).load(itemHaina.vacancy.company.iconUrl).skipMemoryCache(true).diskCacheStrategy(
                    DiskCacheStrategy.NONE)
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            progressCircular.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            progressCircular.visibility = View.GONE
                            return false
                        }
                    })
                    .into(ivImageCompany)

                linearJobVacancy.setOnClickListener {
                    adapterCallbackApplication.listApplicationClick(linearJobVacancy, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        return ViewHolderApplication(
            ListItemHistoryApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val level: MyApplication? = listApplication?.get(position)
        (holder as ViewHolderApplication).bind(level!!, adapterCallbackApplication)
    }

    override fun getItemCount(): Int {
        return listApplication!!.size

    }

    interface AdapterCallbackApplication {
        fun listApplicationClick(view: View, dataApplication: MyApplication)
    }

    fun add(data: List<MyApplication?>?) {
        data?.let { listApplication?.addAll(it) }
        notifyItemRangeInserted((listApplication?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        listApplication?.clear()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val querySearch = constraint?.toString()?.lowercase()
                val filterResult = FilterResults()
                filterResult.values = if (querySearch == null){
                    listApplicationResult
                } else {
                    listApplicationResult?.filter {
                        it?.vacancy?.position?.lowercase()!!.contains(querySearch, true)
                    }
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listApplication = results?.values as ArrayList<MyApplication?>
                notifyDataSetChanged()
            }

        }
    }

}