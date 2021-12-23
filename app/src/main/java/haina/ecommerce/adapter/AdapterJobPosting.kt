package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemSellingBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.view.posting.detailvacancy.DetailPostingJobActivity

class AdapterJobPosting(val context: Context, private val listJob: List<DataMyJob?>?): RecyclerView.Adapter<AdapterJobPosting.Holder>(){

    private var salary:String? = null
    private var applicant:String? = null
    private var datePublish:String? = null
    private val helper: Helper = Helper

    inner class Holder (view:View): RecyclerView.ViewHolder(view){
        private val binding = ListItemSellingBinding.bind(view)
        fun bin(item: DataMyJob?){
            with(binding){
                tvTitlePost.text = item?.title
                tvStatus.text = item?.status
                tvLocation.text = item?.location
                tvCategory.text = item?.jobcategory
                applicant = "${item?.jobapplicant?.size.toString()} ${context.getString(R.string.applicant_count)}"
                tvApplicant.text = applicant
                datePublish = "${context.getString(R.string.date_publish)} : ${item?.date}"
                tvDatePublish.text = datePublish
                salary = "${helper.convertToFormatMoneySalary(item?.salaryFrom.toString())} - ${helper.convertToFormatMoneySalary(item?.salaryTo.toString())}"
                tvSalary.text = salary
                Glide.with(context).load(item?.photoUrl).skipMemoryCache(false).diskCacheStrategy(
                    DiskCacheStrategy.NONE).into(ivImagePost)
                cvClick.setOnClickListener {
                    val intent = Intent(context, DetailPostingJobActivity::class.java)
                    intent.putExtra("item", item)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSellingBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val selling: DataMyJob? = listJob?.get(position)!!
        holder.bin(selling)
    }

    override fun getItemCount(): Int = listJob?.size!!
}