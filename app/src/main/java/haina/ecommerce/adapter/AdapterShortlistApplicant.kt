package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import haina.ecommerce.databinding.ListItemSortListBinding
import haina.ecommerce.model.DataShortlist

class AdapterShortlistApplicant(val context: Context, private val listShortlistApplicant: List<DataShortlist?>?):
    RecyclerView.Adapter<AdapterShortlistApplicant.Holder>() {
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemSortListBinding.bind(view)
        private var statusApplicant:String = ""
        fun bind(itemHaina: DataShortlist){
            with(binding){
                statusApplicant = itemHaina.status.toString()
                if (statusApplicant == "interview"){
                    btnDecline.visibility = View.INVISIBLE
                    btnAccept.visibility = View.INVISIBLE
                    btnInterview.visibility = View.VISIBLE
                    btnInterview.isEnabled = false
                } else if (statusApplicant == "shortlisted"){
                    btnDecline.visibility = View.VISIBLE
                    btnAccept.visibility = View.VISIBLE
                    btnInterview.visibility = View.VISIBLE
                } else if (statusApplicant == "accepted") {
                    btnDecline.visibility = View.INVISIBLE
                    btnAccept.visibility = View.VISIBLE
                    btnInterview.visibility = View.INVISIBLE
                    btnAccept.isEnabled = false
                }
                tvName.text = itemHaina.fullname
                tvEmail.text = itemHaina.email
                tvGender.text = itemHaina.gender
                tvLocation.text = itemHaina.address
                tvNameJobVacancy.text = itemHaina.jobVacancyTitle
                Glide.with(context).load(itemHaina.photo).skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
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
                        .into(ivImageApplicant)
                btnInterview.setOnClickListener {
                    val intentInterview = Intent("interview")
                    intentInterview.putExtra("invite", itemHaina.id)
                    broadcaster?.sendBroadcast(intentInterview)
                }
                btnAccept.setOnClickListener {
                    val intentAccept = Intent("accept")
                    intentAccept.putExtra("hired", itemHaina.id)
                    broadcaster?.sendBroadcast(intentAccept)
                }
                btnDecline.setOnClickListener {
                    val intentDecline = Intent("decline")
                    intentDecline.putExtra("reject", itemHaina.id)
                    broadcaster?.sendBroadcast(intentDecline)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSortListBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item: DataShortlist = listShortlistApplicant?.get(position)!!
        holder.bind(item)
    }

    override fun getItemCount(): Int = listShortlistApplicant?.size!!

}