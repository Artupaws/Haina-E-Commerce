package haina.ecommerce.view.hotels.transactionhotel.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListBooking
import haina.ecommerce.databinding.FragmentFinishHotelBinding
import haina.ecommerce.model.hotels.Bookings
import haina.ecommerce.view.hotels.transactionhotel.DetailBookingsActivity

class FinishHotelFragment : Fragment(), AdapterListBooking.ItemAdapterCallback {

    private lateinit var _binding:FragmentFinishHotelBinding
    private val binding get() = _binding
    private lateinit var listBooking:ArrayList<Bookings>
    private var popupInputReview: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFinishHotelBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDataDummy()
        binding.rvBooking.apply {
            adapter = AdapterListBooking(requireActivity(), listBooking, this@FinishHotelFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        dialogInputReview()
    }

    private fun initDataDummy(){
        listBooking = ArrayList()
        listBooking.add(Bookings("123A","Complete","12 APR","13 APR","2", "Jakarta", "1","VIP Room","Borobudur Hotel", "Bagus, pelayanan ramah", 5F))
        listBooking.add(Bookings("123B","Complete","14 APR","15 APR","2", "Jakarta", "1","VIP Room","Prambanan Hotel", "Bagus, pelayanan ramah", 5f))
        listBooking.add(Bookings("123C","Complete","16 APR","17 APR","2", "Jakarta", "1","VIP Room","Sriwijaya Hotel", "Hotelnya bagus dan nyaman", 4.5f))
        listBooking.add(Bookings("123D","Complete","18 APR","19 APR","2", "Jakarta", "1","VIP Room","Borobudur Hotel", "", 0f))
        listBooking.add(Bookings("123E","Waiting For You","20 APR","21 APR","2", "Jakarta", "1","VIP Room","Borobudur Hotel", "", 0f))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogInputReview(){
        popupInputReview = Dialog(requireActivity())
        popupInputReview?.setContentView(R.layout.popup_review_hotel)
        popupInputReview?.setCancelable(true)
        popupInputReview?.window?.setBackgroundDrawable(requireActivity().getDrawable(android.R.color.white))
        val window:Window = popupInputReview?.window!!
        window.setGravity(Gravity.CENTER)
        val btnInputReview = popupInputReview?.findViewById<Button>(R.id.btn_send_review)
        val etReview = popupInputReview?.findViewById<EditText>(R.id.et_input_review)
        val ratingBar = popupInputReview?.findViewById<RatingBar>(R.id.ratingBar)

        ratingBar?.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { view, rating, boolean ->
                Toast.makeText(requireActivity(), rating.toString(), Toast.LENGTH_SHORT).show()
            }

        btnInputReview?.setOnClickListener {
            popupInputReview?.dismiss()
        }
    }

    override fun onClick(view: View, data: Bookings) {
        when(view.id){
            R.id.btn_input_rating -> {
                popupInputReview?.show()
            }
            R.id.cv_click -> {
                val intent = Intent(context, DetailBookingsActivity::class.java)
                    requireActivity().startActivity(intent)
            }
        }
    }

}