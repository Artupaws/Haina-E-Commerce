package haina.ecommerce.view.restaurant.detail.overview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.adapter.restaurant.AdapterRestaurantFoodCategory
import haina.ecommerce.adapter.restaurant.menu.AdapterRestaurantMenuCategory
import haina.ecommerce.databinding.LayoutRestaurantOverviewBinding
import haina.ecommerce.model.restaurant.master.MenuCategory
import haina.ecommerce.model.restaurant.master.RestaurantData
import timber.log.Timber
import com.ibnux.locationpicker.LocationPickerActivity

import android.content.Intent




class RestaurantOverviewFragment(val data:RestaurantData,val fragmentCallback: FragmentCallback):
    Fragment()
    , RestaurantOverviewContract.View
{

    private lateinit var _binding:LayoutRestaurantOverviewBinding
    private val binding get() = _binding
    private lateinit var presenter: RestaurantOverviewPresenter
    private var broadcaster:LocalBroadcastManager? = null
    private lateinit var ctx: Context


    //Lazy Adapter
    private val adapterRestaurantCategory by lazy {
        AdapterRestaurantFoodCategory(requireActivity(), arrayListOf())
    }
    //End Lazy Adapter

    interface FragmentCallback{
        fun fullPhoto(data:RestaurantData)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = LayoutRestaurantOverviewBinding.inflate(inflater, container, false)
        presenter = RestaurantOverviewPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRestaurantData()

        presenter.getRestaurantMenu(data.id!!)

        binding.rvFoodCategory.adapter = adapterRestaurantCategory
        binding.ivLocation.setOnClickListener {
            startActivityForResult(Intent(requireActivity(), LocationPickerActivity::class.java), 4268)
        }

        binding.btnSeeAllPhoto.setOnClickListener {
            fragmentCallback.fullPhoto(data)
        }
    }

    //view function
    @SuppressLint("SetTextI18n")
    private fun setRestaurantData(){
        binding.tvPhoneNumber.text = data.phone
        if(data.detailAddress!=""){
            binding.tvFullAddress.text ="${data.address} (${data.detailAddress})"
        }else{
            binding.tvFullAddress.text = "${data.address}"
        }
        Glide.with(ctx).load(data.photo?.get(0)?.url).skipMemoryCache(true).diskCacheStrategy(
            DiskCacheStrategy.NONE).into(binding.ivRestaurantImage)

        adapterRestaurantCategory.add(data.cuisine)

        adapterRestaurantCategory.add(data.type)

        val days = data.openDays?.split(", ")
        Timber.d(days.toString())
        days!!.forEach {
            when(it){
                "1"->{
                    binding.tvMonday.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                "2"->{
                    binding.tvTuesday.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                "3"->{
                    binding.tvWednesday.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                "4"->{
                    binding.tvThursday.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                "5"->{
                    binding.tvFriday.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                "6"->{
                    binding.tvSaturday.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
                "7"->{
                    binding.tvSunday.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                }
            }
        }
        if(data.open24hours==1){
            binding.tvWeekendTime.visibility=View.GONE
            binding.tvWeekdayTime.text = "Open 24 Hours"
        }else{
            binding.tvWeekdayTime.text = "Weekdays ${data.weekdaysTimeOpen} - ${data.weekdaysTimeClose}"
            binding.tvWeekendTime.text = "Weekdays ${data.weekendTimeOpen} - ${data.weekendTimeClose}"

        }
    }

    //contract function
    override fun message(msg: String) {
        Timber.d(msg)
    }

    override fun getMenuList(data: List<MenuCategory?>?) {
        binding.rvMenuCategory.adapter = AdapterRestaurantMenuCategory(requireActivity(), data)
        binding.rvMenuCategory.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        if(data!!.size<3){
            binding.btnSeeAllMenu.visibility = View.GONE
        }else{
            binding.btnSeeAllMenu.visibility = View.VISIBLE
        }
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

}