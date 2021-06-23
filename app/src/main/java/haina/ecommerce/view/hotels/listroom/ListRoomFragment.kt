package haina.ecommerce.view.hotels.listroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterListCommonFacilities
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterListRoomDarma
import haina.ecommerce.databinding.FragmentListRoomBinding
import haina.ecommerce.model.hotels.newHotel.DataRoom
import haina.ecommerce.model.hotels.newHotel.RoomsItemDarma
import haina.ecommerce.view.hotels.listfacitieshotel.BottomSheetFacilitiesHotel

class ListRoomFragment : Fragment(), AdapterListRoomDarma.ItemAdapterCallback, View.OnClickListener {

    private lateinit var _binding:FragmentListRoomBinding
    private val binding get() = _binding
    private val positionCollaps: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_collapse)
    }
    private val positionExpand: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_expand)
    }
    private var clicked = false
    private var dataRoom:DataRoom? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListRoomBinding.inflate(inflater, container, false)
        clicked = !clicked
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.linearDescriptionHotel.setOnClickListener(this)
        binding.tvSeeAllFacilites.setOnClickListener(this)
        binding.toolbarListRoom.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        dataRoom = arguments?.getParcelable<DataRoom>("dataRoom")
        setupView(dataRoom)

    }

    private fun setupView(data:DataRoom?){
        binding.tvNameHotel.text = data?.hotelInfo?.name
        binding.ratingBarHotel.rating = data.let { it?.hotelInfo?.rating!! }
        binding.tvAddressHotel.text = data?.hotelInfo?.address
        binding.tvDescriptionHotel.text = data?.hotelInfo?.message

        binding.rvRoomDarma.apply {
            adapter = AdapterListRoomDarma(requireActivity(), data?.hotelInfo?.rooms, this@ListRoomFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }

        if (data?.hotelInfo?.commonFacility != null){
            binding.rvCommonFacilities.apply {
                adapter = AdapterListCommonFacilities(requireActivity(), data.hotelInfo.commonFacility)
                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            }
        } else {
            binding.tvTitleCommonFacilities.visibility =View.GONE
            binding.rvCommonFacilities.visibility = View.GONE
        }
    }

    private fun onAddPostClicked(clicked: Boolean) {
        setVisibility(clicked)
        setAnimation(clicked)
    }

    private fun setAnimation(clicked:Boolean){
        if (!clicked){
            binding.ivDropdown.startAnimation(positionCollaps)
        } else {
            binding.ivDropdown.startAnimation(positionExpand)
        }
    }

    private fun setVisibility(clicked: Boolean){
        if (!clicked){
            binding.tvDescriptionHotel.visibility = View.GONE
        } else {
            binding.tvDescriptionHotel.visibility = View.VISIBLE
        }
    }

    override fun onClick(view: View, data: RoomsItemDarma) {
        when(view.id){
            R.id.btn_select -> {
                Toast.makeText(requireActivity(), "${data.breakfast} ${data.iD}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.linear_description_hotel -> {
                onAddPostClicked(clicked)
                clicked = !clicked
            }
            R.id.tv_see_all_facilites -> {
                val bundle = Bundle()
                bundle.putParcelable("data", dataRoom)
                childFragmentManager.let {
                    BottomSheetFacilitiesHotel.instanceBottomSheetFacilitiesHotel(bundle).apply {
                        show(it, tag)
                    }
                }
            }
        }

    }

}