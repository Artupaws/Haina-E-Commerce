package haina.ecommerce.view.hotels.transactionhotel.finish

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterTransactionFinish
import haina.ecommerce.databinding.FragmentFinishHotelBinding
import haina.ecommerce.model.hotels.Bookings
import haina.ecommerce.model.hotels.newHotel.DataBooking
import haina.ecommerce.model.hotels.transactionhotel.DataTransactionHotel
import haina.ecommerce.model.hotels.transactionhotel.PaidItem
import haina.ecommerce.view.hotels.transactionhotel.DetailBookingsActivity

class FinishHotelFragment : Fragment(), AdapterTransactionFinish.ItemAdapterCallback, FinishHotelContract {

    private lateinit var _binding:FragmentFinishHotelBinding
    private val binding get() = _binding
    private lateinit var listBooking:ArrayList<Bookings>
    private var popupInputReview: Dialog? = null
    private lateinit var presenter:FinishHotelpresenter
    private var broadcaster:LocalBroadcastManager?=null
    private var idHotel:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFinishHotelBinding.inflate(inflater, container,false)
        presenter = FinishHotelpresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(mMessageReceiver, IntentFilter("dataBooking"))
    }

    private val mMessageReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            when(intent?.action){
                "dataBooking" -> {
                    val dataTransactionFinish = intent.getParcelableExtra<DataBooking>("bookingHotel")
                    setListTransaction(dataTransactionFinish.paid)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(mMessageReceiver)
    }

    private fun setListTransaction(data:List<haina.ecommerce.model.hotels.newHotel.PaidItem?>?){
        showIsEmpty(data?.size)
        binding.rvBooking.apply {
            adapter = AdapterTransactionFinish(requireActivity(), data, this@FinishHotelFragment, false)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showIsEmpty(listItem:Int?){
        Log.d("item", listItem.toString())
        if (listItem == 0){
            binding.rvBooking.visibility = View.GONE
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
        } else {
            binding.rvBooking.visibility = View.VISIBLE
            binding.includeEmpty.linearEmpty.visibility = View.GONE
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogInputReview(statusInputReview:String){
        popupInputReview = Dialog(requireActivity())
        popupInputReview?.setContentView(R.layout.popup_review_hotel)
        popupInputReview?.setCancelable(true)
        popupInputReview?.window?.setBackgroundDrawable(requireActivity().getDrawable(android.R.color.white))
        val window:Window = popupInputReview?.window!!
        window.setGravity(Gravity.CENTER)
        val btnInputReview = popupInputReview?.findViewById<Button>(R.id.btn_send_review)
        val etReview = popupInputReview?.findViewById<EditText>(R.id.et_input_review)
        val ratingBar = popupInputReview?.findViewById<RatingBar>(R.id.ratingBar)
        val relativeLoading = popupInputReview?.findViewById<RelativeLayout>(R.id.relative_loading)

        ratingBar?.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { view, rating, boolean ->
                Toast.makeText(requireActivity(), rating.toString(), Toast.LENGTH_SHORT).show()
            }

        btnInputReview?.setOnClickListener {
            val review = etReview?.text.toString()
            val rating = ratingBar?.rating

            if (review.isNullOrEmpty() && rating == 0F){
                etReview?.error = "Please input rating"
            } else {
                presenter.inputRatingHotel(idHotel, rating!!, review)
                relativeLoading?.visibility = View.VISIBLE
                btnInputReview.visibility = View.GONE
            }
        }
        if (statusInputReview.contains("Success")){
            popupInputReview?.dismiss()
        } else if (statusInputReview.contains("Failed")) {
            Toast.makeText(requireActivity(), "Input Review Failed", Toast.LENGTH_SHORT).show()
            relativeLoading?.visibility = View.INVISIBLE
            btnInputReview?.visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View, data: haina.ecommerce.model.hotels.newHotel.PaidItem) {
        when(view.id){
            R.id.btn_input_rating -> {
                idHotel = data.hotelId!!
                dialogInputReview("")
                popupInputReview?.show()
            }
            R.id.cv_click -> {
                val intent = Intent(context, DetailBookingsActivity::class.java)
                    requireActivity().startActivity(intent)
            }
        }
    }

    override fun messageInputReview(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        dialogInputReview(msg)
    }
}