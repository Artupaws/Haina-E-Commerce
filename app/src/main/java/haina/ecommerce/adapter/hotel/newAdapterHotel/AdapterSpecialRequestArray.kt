package haina.ecommerce.adapter.hotel.newAdapterHotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemChooseAddOnBinding
import haina.ecommerce.model.hotels.newHotel.SpecialRequestArrayItem

class AdapterSpecialRequestArray(val context: Context, private val listSpecialRequest: List<SpecialRequestArrayItem?>?,
                                 private val itemAdapterCallback: ItemAdapterCallback, private val statusEdit:Boolean,private val selected:String?) :
        RecyclerView.Adapter<AdapterSpecialRequestArray.Holder>() {

    private var listSpecialRequestArrayItem = ArrayList<SpecialRequestArrayItem>()
    private var arraySelected:Array<String>? = null

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemChooseAddOnBinding.bind(view)
        fun bind(itemHaina: SpecialRequestArrayItem?, itemAdapterCallback:ItemAdapterCallback) {
            with(binding) {
                cbAddon.text = itemHaina?.description
                arraySelected!!.forEach {
                    if(itemHaina!!.iD==it){
                        cbAddon.isChecked=true
                    }
                }

                cbAddon.setOnCheckedChangeListener { _, isChecked ->
                    if(isChecked){
                        listSpecialRequestArrayItem.add(itemHaina!!)
                    }else{
                        listSpecialRequestArrayItem.remove(itemHaina!!)
                    }

                    itemAdapterCallback.onClickSpecialRequest(listSpecialRequestArrayItem)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSpecialRequestArray.Holder {
        if(selected!=null){
            arraySelected = selected.split(",").toTypedArray()
            arraySelected!!.forEach {
                listSpecialRequestArrayItem.add(listSpecialRequest?.find { data ->
                    data?.iD==it
                }!!)
            }
        }

        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemChooseAddOnBinding.inflate(inflater)
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterSpecialRequestArray.Holder, position: Int) {
        val photo: SpecialRequestArrayItem? = listSpecialRequest?.get(position)
        holder.bind(photo, itemAdapterCallback)
    }

//    override fun getItemCount(): Int = listSpecialRequest.size
    override fun getItemCount():Int{
    return listSpecialRequest?.size ?: 0
    }

    interface ItemAdapterCallback{
        fun onClickSpecialRequest(dataRequest:ArrayList<SpecialRequestArrayItem>)
    }

}