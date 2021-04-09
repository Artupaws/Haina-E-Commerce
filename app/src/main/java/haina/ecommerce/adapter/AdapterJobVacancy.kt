package haina.ecommerce.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import haina.ecommerce.databinding.ListItemJobCategoryBinding
import haina.ecommerce.databinding.ListItemJobVacancyBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataItemJob
import haina.ecommerce.view.detailjob.DetailJobActivity

class AdapterJobVacancy(private val context: Context, private val jobList: List<DataItemJob?>?): RecyclerView.Adapter<AdapterJobVacancy.Holder>(){

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val helper:Helper = Helper
        private val binding = ListItemJobVacancyBinding.bind(itemView)
        private var description:String? = ""
        private var jobCategory:String? = ""

        @SuppressLint("SetTextI18n")
        fun bind(itemHaina: DataItemJob){
            with(binding){
                tvTitleJob.text = itemHaina.title?.toUpperCase()
                tvCompanyName.text = itemHaina.company?.name
                tvDatePublish.text = ("Post : ${itemHaina.date}")
                tvLocation.text = itemHaina.location
                tvSalary.text = ("${helper.convertToFormatMoneySalary(itemHaina.salaryFrom.toString())} - ${helper.convertToFormatMoneySalary(itemHaina.salaryTo.toString())}")
                Glide.with(context).load(itemHaina.photoUrl).skipMemoryCache(true).diskCacheStrategy(
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
                    val intent = Intent(context, DetailJobActivity::class.java)
                    intent.putExtra("detailJob", itemHaina)
                    context.startActivity(intent)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterJobVacancy.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemJobVacancyBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterJobVacancy.Holder, position: Int) {
        val job: DataItemJob = jobList?.get(position)!!
        holder.bind(job)
    }

    override fun getItemCount(): Int = jobList?.size!!
}