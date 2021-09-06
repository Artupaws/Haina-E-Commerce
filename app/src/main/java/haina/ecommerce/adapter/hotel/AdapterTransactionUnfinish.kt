package haina.ecommerce.adapter.hotel

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.databinding.ListItemUnpaidHotelBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.hotels.newHotel.PaidItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants


class AdapterTransactionUnfinish(val context: Context, private val listHotel: List<PaidItem?>?, private val itemAdapterCallback: ItemAdapterCallback) :
        RecyclerView.Adapter<AdapterTransactionUnfinish.Holder>() {

    private val helper:Helper = Helper
    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var binding: ListItemUnpaidHotelBinding
    private var broadcaster:LocalBroadcastManager? = null
    private var bookingId:Int = 0
    private val minutePayment:Int = 0
    private val secondPayment:Int = 30

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemUnpaidHotelBinding.bind(view)
        fun bind(itemHaina: PaidItem, itemAdapterCallback: ItemAdapterCallback) {
            with(binding) {
//                val timer: Long = 1.toLong()
                bookingId = itemHaina.id!!
                tvHotelName.text = itemHaina.hotel?.hotelName
                tvAddressHotel.text = itemHaina.hotel?.hotelAddress
                val priceAndNight = "${helper.convertToFormatMoneyIDRFilter(itemHaina.totalPrice.toString())} (${itemHaina.totalNight}) Night"
                tvPriceAndTotalNight.text = priceAndNight
                val checkinCheckout = "${itemHaina.checkIn} to ${itemHaina.checkOut}"
                tvCheckinCheckout.text = checkinCheckout
//                Glide.with(context).load(itemHaina.hotel?.).into(ivImageRoom)
                tvIdBooking.text = itemHaina.id.toString()
//                tvTotalGuest.text = itemHaina.totalGuest.toString()
                tvNumberPayment.text = itemHaina.payment?.vaNumber
                tvPaymentNumber.text = itemHaina.paymentMethod?.name
                tvPaymentCategory.text = itemHaina.paymentMethod?.category?.name
                btnCopyNumber.setOnClickListener {
                    sharedPref.removeValue(Constants.CURRENT_TIME_SESSION_PAYMENT)
                    itemAdapterCallback.onClick(btnCopyNumber, itemHaina)
                }
                cvClick.setOnClickListener {
                    itemAdapterCallback.onClick(cvClick, itemHaina)
                }
                tvOptionMenu.setOnClickListener {
                    itemAdapterCallback.onClick(tvOptionMenu, itemHaina)
//                    val popup = PopupMenu(context, ivActionCancel)
//                    popup.inflate(R.menu.menu_option_address_company)
//                    popup.setOnMenuItemClickListener{
//                        iteem -> when(iteem.itemId){
//                        R.id.action_edit -> {
//                            itemHaina.payment?.bookingId?.let {
//                                (context as HistoryTransactionHotelActivity).cancelBookingHotel(it)
//                            }
//                            true
//                        }
//                        else -> false
//                        }
//                    }
                }
                btnHowPay.setOnClickListener {
                    itemAdapterCallback.onClick(btnHowPay, itemHaina)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTransactionUnfinish.Holder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ListItemUnpaidHotelBinding.inflate(inflater)
        broadcaster = LocalBroadcastManager.getInstance(context)
        sharedPref = SharedPreferenceHelper(context)
//        val currentTime =sharedPref.getValueString(Constants.CURRENT_TIME_SESSION_PAYMENT)
//        if (currentTime != null){
//            when {
//                currentTime == "" -> {
//                    countDownStart(minutePayment, secondPayment)
//                }
//                currentTime.contains("expired") -> {
//                }
//            }
//        }
        return Holder(binding.root)
    }

    override fun onBindViewHolder(holder: AdapterTransactionUnfinish.Holder, position: Int) {
        val photo: PaidItem = listHotel?.get(position)!!
        holder.bind(photo, itemAdapterCallback)
    }

    override fun getItemCount(): Int = listHotel?.size!!

//    override fun onViewDetachedFromWindow(holder: Holder) {
//        super.onViewDetachedFromWindow(holder)
//    }

//    private fun countDownStart(minuteSession:Int, secondSession:Int){
//        if (minuteSession != 0){
//            val countDownTimer = SimpleCountDownTimerKotlin(minuteSession.toLong(), secondSession.toLong())
//            countDownTimer.start()
//        }
//    }

//    private fun setColorTextStatus(status: String, binding: ListItemUnpaidHotelBinding) {
//        binding.tvStatusBooking.text = status
//        if (status.contains("Waiting")) {
//            binding.tvStatusBooking.setTextColor(context.resources.getColor(R.color.yellow))
//        } else {
//            binding.tvStatusBooking.setTextColor(context.resources.getColor(android.R.color.holo_green_dark))
//        }
//    }

//    private fun showRatingOrButtonRating(binding: ListItemUnpaidHotelBinding, data: UnpaidItem) {
////        binding.tvUserRating.text = data.reviews
////        binding.ratingBar.rating = data.rating!!
//        if (binding.tvUserRating.text == "") {
//            binding.linearRatingUser.visibility = View.GONE
//            binding.btnInputRating.visibility = View.VISIBLE
//        }
//        if(binding.tvUserRating.text != ""){
//            binding.linearRatingUser.visibility = View.VISIBLE
//            binding.btnInputRating.visibility = View.GONE
//        }
//        if (binding.tvStatusBooking.text.contains("Waiting")){
//            binding.linearRatingUser.visibility = View.GONE
//            binding.btnInputRating.visibility = View.GONE
//        }
//    }

    interface ItemAdapterCallback{
        fun onClick(view: View, data:PaidItem)
    }

//    override fun onCountDownActive(time: String) {
//        (context as Activity).runOnUiThread {
//            binding.tvNotifCountdown.text = "Finish Payment Before :${time}"
//            countDown = time
//        }
//    }
//
//    override fun onCountDownFinished() {
//        binding.tvNotifCountdown.text = "This transaction has expired"
//        val intentData = Intent("dataTransaction")
//        intentData.putExtra("bookingId", bookingId)
//        broadcaster?.sendBroadcast(intentData)
//        sharedPref.save(Constants.CURRENT_TIME_SESSION_PAYMENT, "expired")
//    }
}