package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
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

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemSortListBinding.bind(view)
        fun bind(itemHaina: DataShortlist){
            with(binding){
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
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterShortlistApplicant.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSortListBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterShortlistApplicant.Holder, position: Int) {
        val item: DataShortlist = listShortlistApplicant?.get(position)!!
        holder.bind(item)
    }

    override fun getItemCount(): Int = listShortlistApplicant?.size!!


}