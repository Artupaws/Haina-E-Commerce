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
import haina.ecommerce.model.DataSkillRequires
import haina.ecommerce.model.DataSkillsUser
import haina.ecommerce.model.SkillRequires


class AdapterAddSkillRequires(val context: Context, private val listSkill: List<DataSkillRequires?>?):
    RecyclerView.Adapter<AdapterAddSkillRequires.Holder>() {
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemListDocumentBinding.bind(view)
        private var nameDocument:String? = null
        fun bind(itemHaina: DataSkillRequires){
            with(binding){
             tvTitleDocument.text = itemHaina.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAddSkillRequires.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemListDocumentBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterAddSkillRequires.Holder, position: Int) {
        val skill: DataSkillRequires = listSkill?.get(position)!!
        holder.bind(skill)
    }

    override fun getItemCount(): Int = listSkill?.size!!


}