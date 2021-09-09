package haina.ecommerce.view.hotels.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haina.ecommerce.adapter.hotel.AdapterListSearchHotel
import haina.ecommerce.databinding.FragmentHotelSearchBinding
import haina.ecommerce.model.hotels.HotelSearchItem

class BottomSheetSearchHotelFragment : BottomSheetDialogFragment(),BottomSheetSearchHotelContract.View, View.OnClickListener {

    private lateinit var _binding: FragmentHotelSearchBinding
    private lateinit var selectionPresenter: BottomSheetSearchHotelPresenter

    private val binding get() = _binding
    private var broadcaster: LocalBroadcastManager? = null
    private var totalAdults: Int = 0
    private var maxAdults: Int = 4
    private var maxKids: Int = 4
    private var maxBaby: Int = 2
    private var totalKids: Int = 0
    private var totalBaby: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelSearchBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        selectionPresenter = BottomSheetSearchHotelPresenter(this, requireActivity())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.tvClose.setOnClickListener { dismiss() }

        selectionPresenter.getSearchHotel("jakarta","2021-09-19","2021-09-20");

    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BottomSheetSearchHotelFragment {
            val fragment = BottomSheetSearchHotelFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onClick(p0: View?) {
    }

    override fun getSearch(data: List<HotelSearchItem?>) {
        binding.rvCityHotel.apply {

            adapter = AdapterListSearchHotel(requireActivity(), data)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


}