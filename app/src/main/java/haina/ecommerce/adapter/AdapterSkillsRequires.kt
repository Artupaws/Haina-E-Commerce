package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemListDocumentBinding
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.model.DataSkillsUser
import haina.ecommerce.model.SkillRequires


class AdapterSkillsRequires(val context: Context, private val listSkill: List<SkillRequires?>?):
    RecyclerView.Adapter<AdapterSkillsRequires.Holder>() {
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemListDocumentBinding.bind(view)
        private var nameDocument:String? = null
        fun bind(itemHaina: SkillRequires){
            with(binding){
             tvTitleDocument.text = itemHaina.name
                ivActionDelete.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSkillsRequires.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemListDocumentBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterSkillsRequires.Holder, position: Int) {
        val skill: SkillRequires = listSkill?.get(position)!!
        holder.bind(skill)
    }

    override fun getItemCount(): Int = listSkill?.size!!


}