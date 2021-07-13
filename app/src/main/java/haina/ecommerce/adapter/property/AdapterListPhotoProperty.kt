package haina.ecommerce.adapter.property

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import haina.ecommerce.databinding.ListItemProvinceBinding
import haina.ecommerce.databinding.ListPhotoPropertyBinding
import haina.ecommerce.room.roomphotoproperty.DataProperty

class AdapterListPhotoProperty(val context: Context, private var uri: List<DataProperty>,
                               private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterListPhotoProperty.Holder>() {

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListPhotoPropertyBinding.bind(view)
        fun bind(itemHaina: DataProperty) {
            with(binding) {
                val uri = Uri.parse(itemHaina.uri)
               Glide.with(context).load(uri).into(ivProperty)
                ivAction.setOnClickListener {
                    itemAdapterCallback.onClickDeleteImage(ivAction, itemHaina)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListPhotoProperty.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListPhotoPropertyBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterListPhotoProperty.Holder, position: Int) {
        val depart: DataProperty = uri[position]
        holder.bind(depart)
    }

    interface ItemAdapterCallback{
        fun onClickDeleteImage(view: View, data:DataProperty)
    }

    override fun getItemCount(): Int = uri.size

}