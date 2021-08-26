package haina.ecommerce.adapter.flight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemChooseAddOnBinding
import haina.ecommerce.model.flight.MealInfosItem

class AdapterAddOn(val context: Context, private val dataPassenger: List<MealInfosItem?>?,val callback:CallbackInterface) :
        RecyclerView.Adapter<AdapterAddOn.Holder>() {

    private var broadcaster: LocalBroadcastManager? = null
    private var mCheckSum = 0
    var selectedMeal: MutableList<MealInfosItem> = ArrayList()
    var selectedName: MutableList<String> = ArrayList()

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemChooseAddOnBinding.bind(view)
        fun bind(itemHaina: MealInfosItem) {
            with(binding) {
                val descAndPrice = "${itemHaina.desc} - ${itemHaina.fare}"
                cbAddon.text = descAndPrice
                cbAddon.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked){
                        mCheckSum += itemHaina.fare!!
//                        val intentDataPrice = Intent("priceAddOn")
//                        intentDataPrice.putExtra("price", mCheckSum)
//                        broadcaster?.sendBroadcast(intentDataPrice)
                        selectedMeal.add(itemHaina)
                        callback.passTotal(mCheckSum,selectedMeal)

                    } else {
                        mCheckSum -= itemHaina.fare!!
//                        val intentDataPrice = Intent("priceAddOn")
//                        intentDataPrice.putExtra("price", mCheckSum)
//                        broadcaster?.sendBroadcast(intentDataPrice)
                        selectedMeal.remove(itemHaina)


                        callback.passTotal(mCheckSum,selectedMeal)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAddOn.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemChooseAddOnBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        return Holder(binding.root)
    }


    override fun onBindViewHolder(holder: AdapterAddOn.Holder, position: Int) {
        val depart: MealInfosItem = dataPassenger?.get(position)!!
        holder.bind(depart)

    }

    interface CallbackInterface {
        fun passTotal(sum: Int,code: MutableList<MealInfosItem>)
    }

    interface ItemAdapterCallback{
        fun onClickAdapter(view: View, price:Int)
    }

    override fun getItemCount(): Int = dataPassenger!!.size
}