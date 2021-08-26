package haina.ecommerce.adapter.flight

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.databinding.ListItemSetAddonBinding
import haina.ecommerce.model.flight.*

class AdapterCombinePassengerAndFlight(
    val context: Context, private val dataPassenger: ArrayList<DataSetPassenger>,
    private val dataTicket:ArrayList<Ticket>,
    private val dataAddons:List<AddOnsItem>?,
    val callback: AdapterCombinePassengerAndFlight.CallbackInterface
) :
        RecyclerView.Adapter<AdapterCombinePassengerAndFlight.Holder>(),AdapterListFlight.CallbackInterface {


    interface CallbackInterface {
        fun passDataAddonsAll( dataAddonsAll:MutableList<PaxDataAddons>)
    }

    var allDataAddons: MutableList<PaxDataAddons> = mutableListOf()

    private var broadcaster:LocalBroadcastManager? =null

    private val positionCollaps: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_collapse)
    }

    private val positionExpand: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_expand)
    }

    private var clicked = false

    val dataBaggage = arrayListOf<BaggageInfosItem>()

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemSetAddonBinding.bind(view)
        private var passengerId:Int = 0
        fun bind(itemHaina: DataSetPassenger) {
            with(binding) {
                passengerId=itemHaina.id
                tvTitlePassenger.text = itemHaina.title
                val fullname = "${itemHaina.first_name} ${itemHaina.last_name}"
                tvNamePassenger.text = fullname
                tvBirthdate.text = itemHaina.birth_date
                tvIdcardNumber.text = itemHaina.id_number
                ivDropdown.setOnClickListener {
                    onAddPostClicked(binding)
                    clicked = !clicked
                }
                setupListDataFlight()

                allDataAddons.add(PaxDataAddons(passengerId, mutableListOf(),0))
            }
        }

        @Suppress("UNCHECKED_CAST")
        private fun setupListDataFlight(){
            binding.rvFlight.apply {
                adapter = AdapterListFlight(context, passengerId, dataTicket, dataAddons, this@AdapterCombinePassengerAndFlight)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCombinePassengerAndFlight.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemSetAddonBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
//        dataAddOn?.forEach { dataBaggage.add(it?.baggageInfos) }
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterCombinePassengerAndFlight.Holder, position: Int) {
        val depart: DataSetPassenger = dataPassenger[position]
        holder.bind(depart)
    }

    override fun getItemCount(): Int = dataPassenger.size


    private fun onAddPostClicked(binding: ListItemSetAddonBinding) {
        setVisibility(clicked, binding)
        setAnimation(clicked, binding)
    }

    private fun setAnimation(clicked:Boolean, binding:ListItemSetAddonBinding){
        if (!clicked){
            binding.ivDropdown.startAnimation(positionExpand)
        } else {
            binding.ivDropdown.startAnimation(positionCollaps)
        }
    }

    private fun setVisibility(clicked: Boolean, binding: ListItemSetAddonBinding){
        if (!clicked){
            binding.rvFlight.visibility = View.GONE
        } else {
            binding.rvFlight.visibility = View.VISIBLE
        }
    }

    override fun passDataAddons(passengerId:Int, dataAddonsAll: MutableList<TripAddonsData>, totalAddons: Int) {
        dataAddonsAll.forEach{ it ->
            allDataAddons.find { data-> data.id==passengerId }?.trip=dataAddonsAll
            allDataAddons.find { data-> data.id==passengerId }?.total=totalAddons


            var meals = ""
            it.meals?.forEach { meal ->
                meals+= meal
            }
            Log.d("data",passengerId.toString()+" "+it.origin+it.destination+it.baggage+it.seat+meals)
        }
        callback.passDataAddonsAll(allDataAddons)

    }
}