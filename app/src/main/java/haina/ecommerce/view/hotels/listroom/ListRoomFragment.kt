package haina.ecommerce.view.hotels.listroom

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterListCommonFacilities
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterListRoomDarma
import haina.ecommerce.databinding.FragmentListRoomBinding
import haina.ecommerce.model.hotels.newHotel.DataPricePolicy
import haina.ecommerce.model.hotels.newHotel.DataRoom
import haina.ecommerce.model.hotels.newHotel.RoomsItemDarma
import haina.ecommerce.view.hotels.listfacitieshotel.BottomSheetFacilitiesHotel

class ListRoomFragment : Fragment(), AdapterListRoomDarma.ItemAdapterCallback, View.OnClickListener, ListRoomContract.View {

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
    private lateinit var presenter: ListRoomPresenter
    private var totalNight:Int? = null
    private var imageRoomUrl:String? = null
    private var progressDialog:Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListRoomBinding.inflate(inflater, container, false)
        clicked = !clicked
        presenter = ListRoomPresenter(this, requireActivity())
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
        totalNight = arguments?.getInt("totalNight")
        setupView(dataRoom)
        dialogLoading()

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
                presenter.getPricePolicy(data.iD!!, data.breakfast!!)
                imageRoomUrl = data.image
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

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(requireActivity().getDrawable(android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageGetPricePolicy(msg: String) {
        Log.d("getDataPricePolicy", msg)
        if (!msg.toLowerCase().contains("success")){
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getPricePolicy(data: DataPricePolicy?) {
        if (data != null) {
            val bundle = Bundle()
            bundle.putParcelable("dataPricePolicy", data)
            bundle.putString("imageRoomUrl", imageRoomUrl)
            totalNight?.let { bundle.putInt("totalNight", it) }
            Navigation.findNavController(binding.root).navigate(R.id.action_listRoomFragment_to_fillInDetailFragment, bundle)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}