package haina.ecommerce.adapter.flight

import android.content.Context
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
    private val dataAddOn: List<BaggageInfosItem?>?,
    private val dataMeals: List<MealInfosItem?>?,
    private val dataSeat: List<SeatInfosItem?>?
) :
        RecyclerView.Adapter<AdapterCombinePassengerAndFlight.Holder>() {

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
        fun bind(itemHaina: DataSetPassenger) {
            with(binding) {
               tvTitlePassenger.text = itemHaina.title
                val fullname = "${itemHaina.first_name} ${itemHaina.last_name}"
                tvNamePassenger.text = fullname
                tvBirthdate.text = itemHaina.birth_date
                tvIdcardNumber.text = itemHaina.id_number
                ivDropdown.setOnClickListener {
                    onAddPostClicked(binding)
                    clicked = !clicked
                }
                setupListDataFlight(binding)
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

    @Suppress("UNCHECKED_CAST")
    private fun setupListDataFlight(binding:ListItemSetAddonBinding){
        if(dataSeat?.isEmpty() == true){
            binding.rvFlight.apply {
                adapter = AdapterListFlight(context, dataTicket, dataAddOn, dataMeals, dataSeat, false)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        } else {
            binding.rvFlight.apply {
                adapter = AdapterListFlight(context, dataTicket, dataAddOn, dataMeals, dataSeat, true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

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
}