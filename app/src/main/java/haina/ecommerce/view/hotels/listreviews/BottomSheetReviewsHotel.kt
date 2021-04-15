package haina.ecommerce.view.hotels.listreviews

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListReviews
import haina.ecommerce.databinding.FragmentBottomSheetHowToPaymentBinding
import haina.ecommerce.databinding.FragmentBottomSheetReviewsHotelBinding
import haina.ecommerce.model.hotels.Reviews

class BottomSheetReviewsHotel : BottomSheetDialogFragment() {

    private var _binding:FragmentBottomSheetReviewsHotelBinding? = null
    private val  binding get() = _binding
    private var reviewList : ArrayList<Reviews> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBottomSheetReviewsHotelBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbarReviewsHotel?.title = requireContext().getString(R.string.review_hotel)
        binding?.toolbarReviewsHotel?.setNavigationIcon(R.drawable.ic_close_black)
        binding?.toolbarReviewsHotel?.setNavigationOnClickListener { dismiss() }

        initDataReviews()
        binding?.rvReviewsUser?.apply {
            adapter = AdapterListReviews(requireContext(), reviewList)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private var mListener: ItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            mListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface ItemClickListener {
        fun onItemClick(item: String)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BottomSheetReviewsHotel {
            val fragment = BottomSheetReviewsHotel()
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun initDataReviews(){
        reviewList = ArrayList()
        reviewList.add(Reviews("Ricard",4.5F, "Good place to staycation, nice views and good service, overall it's a worth it to try", "21 APR 2021"))
        reviewList.add(Reviews("Rico",5F, "Good place to staycation, nice views and good service, overall it's a worth it to try", "21 APR 2021"))
        reviewList.add(Reviews("Rooney",4.5F, "Good place to staycation, nice views and good service, overall it's a worth it to try", "21 APR 2021"))
        reviewList.add(Reviews("Roosman",5F, "Good place to staycation, nice views and good service, overall it's a worth it to try", "21 APR 2021"))
    }

}