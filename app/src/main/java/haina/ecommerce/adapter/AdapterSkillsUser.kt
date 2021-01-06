package haina.ecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemListDocumentBinding
import haina.ecommerce.model.DataSkillsUser


class AdapterSkillsUser(val context: Context, private val listSkill: List<DataSkillsUser?>?):
    RecyclerView.Adapter<AdapterSkillsUser.Holder>() {
    private var broadcaster: LocalBroadcastManager? = null

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ListItemListDocumentBinding.bind(view)
        private var nameDocument:String? = null
        fun bind(itemHaina: DataSkillsUser){
            with(binding){
                nameDocument = (itemHaina.name)
                tvTitleDocument.text = nameDocument
                ivActionDelete.setOnClickListener {
                    val deleteSkills = Intent("deleteSkill")
                            .putExtra("nameSkill", itemHaina.name)
                    broadcaster?.sendBroadcast(deleteSkills)
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSkillsUser.Holder {
        broadcaster = LocalBroadcastManager.getInstance(context)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemListDocumentBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterSkillsUser.Holder, position: Int) {
        val skill: DataSkillsUser = listSkill?.get(position)!!
        holder.bind(skill)
    }

    override fun getItemCount(): Int = listSkill?.size!!


}