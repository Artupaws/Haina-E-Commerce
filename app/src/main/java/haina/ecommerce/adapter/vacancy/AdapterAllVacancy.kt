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
import haina.ecommerce.databinding.ListItemJobVacancyBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.property.DataShowProperty
import haina.ecommerce.model.vacancy.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import java.util.ArrayList

class AdapterAllVacancy(val context: Context,
                        private var listAllVacancy: ArrayList<DataAllVacancy?>?,
                        private val adapterCallbackAllVacancy:AdapterCallbackAllVacancy) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    Filterable {

    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private var listAllVacancyResult : List<DataAllVacancy?>? = listAllVacancy

    inner class ViewHolderAllVacancy(val binding: ListItemJobVacancyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemHaina: DataAllVacancy, adapterCallbackAllVacancy: AdapterCallbackAllVacancy) {
            with(binding) {
                tvTitleJob.text = itemHaina.position
                tvLocation.text = itemHaina.cityName
                val datePublish = Helper.dateFormat(itemHaina.createdAt)
                tvDatePublish.text = datePublish
                if (itemHaina.salaryDisplay == 1){
                    val salary = "${Helper.convertToFormatMoneyIDRFilter(itemHaina.minSalary.toString())}-${Helper.convertToFormatMoneyIDRFilter(itemHaina.maxSalary.toString())}"
                    tvSalary.text = salary
                } else {
                    tvSalary.text = context.getString(R.string.salary_hidden)
                }
                tvCompanyName.text = itemHaina.companyName
                Glide.with(context).load(itemHaina.photoCompany).skipMemoryCache(true).diskCacheStrategy(
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

                if(itemHaina.bookmarked==1){
                    ivSaveJob.isChecked=true
                }

                ivSaveJob.setOnCheckedChangeListener { _, isChecked ->
                    if(isChecked){
                        adapterCallbackAllVacancy.addBookmarkJob(itemHaina.id!!)
                    }else{
                        adapterCallbackAllVacancy.removeBookmarkJob(itemHaina.id!!)
                    }
                }
                linearJobVacancy.setOnClickListener {
                    adapterCallbackAllVacancy.listAllVacancyClick(linearJobVacancy, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        sharedPreferenceHelper = SharedPreferenceHelper(context)
        return ViewHolderAllVacancy(
            ListItemJobVacancyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val level: DataAllVacancy? = listAllVacancy?.get(position)
        (holder as ViewHolderAllVacancy).bind(level!!, adapterCallbackAllVacancy)
    }

    override fun getItemCount(): Int {
        return listAllVacancy!!.size

    }

    interface AdapterCallbackAllVacancy {
        fun listAllVacancyClick(view: View, dataMyVacancy: DataAllVacancy)
        fun addBookmarkJob(idVacancy: Int)
        fun removeBookmarkJob(idVacancy: Int)
    }

    fun addAllVacancy(data: List<DataAllVacancy?>?) {
        data?.let { listAllVacancy?.addAll(it) }
        notifyItemRangeInserted((listAllVacancy?.size?.minus(data?.size!!)!!), data?.size!!)
    }

    fun clear() {
        listAllVacancy?.clear()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val querySearch = constraint?.toString()?.lowercase()
                val filterResult = FilterResults()
                filterResult.values = if (querySearch == null){
                    listAllVacancyResult
                } else {
                    listAllVacancyResult?.filter {
                        it?.position?.lowercase()!!.contains(querySearch, true)
                    }
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listAllVacancy = results?.values as ArrayList<DataAllVacancy?>
                notifyDataSetChanged()
            }

        }
    }

}