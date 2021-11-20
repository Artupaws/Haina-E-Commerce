package haina.ecommerce.view.restaurant.detail

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterListAllThreads
import haina.ecommerce.adapter.restaurant.AdapterRestaurantList
import haina.ecommerce.databinding.FragmentRestaurantDetailBinding
import haina.ecommerce.databinding.FragmentRestaurantReviewBinding
import haina.ecommerce.model.property.CityItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.model.restaurant.master.RestaurantPagination
import haina.ecommerce.room.roomsavedproperty.DataSavedProperty
import haina.ecommerce.view.restaurant.detail.overview.RestaurantOverviewFragment
import haina.ecommerce.view.restaurant.detail.review.RestaurantReviewListFragment
import timber.log.Timber

class RestaurantDetailFragment :
    Fragment()
    ,RestaurantDetailContract.View
    ,RestaurantOverviewFragment.FragmentCallback
    ,RestaurantReviewListFragment.FragmentCallback
{
    private lateinit var _binding:FragmentRestaurantDetailBinding
    private val binding get() = _binding
    private lateinit var presenter: RestaurantDetailPresenter
    private var progressDialog: Dialog? = null
    private var broadcaster:LocalBroadcastManager? = null
    private lateinit var ctx:Context

    private var page:Int = 1
    private var totalPage:Int= 0

    private var latitude:Double? = null
    private var longitude:Double? = null

    private var cuisineId:Int? = null
    private var typeId:Int? = null

    private var tab:Int = 1

    private lateinit var overviewFragment:RestaurantOverviewFragment
    private lateinit var reviewFragment:RestaurantReviewListFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false)
        presenter = RestaurantDetailPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogLoading()
        val restaurantData = arguments?.getParcelable<RestaurantData>("RestaurantData")


        //api function
        presenter.getRestaurantDetail(restaurantData!!.id!!)

        //binding action
        binding.btnOverview.setOnClickListener { changeTab() }
        binding.btnReview.setOnClickListener { changeTab() }
        binding.ivBack.setOnClickListener{
            findNavController().navigateUp()
        }
        for (fragment in childFragmentManager.fragments) {
            childFragmentManager.beginTransaction().remove(fragment).commit()
        }
        //fragment initiation
        overviewFragment = RestaurantOverviewFragment(restaurantData,this)
        reviewFragment = RestaurantReviewListFragment(restaurantData, this)
        childFragmentManager.beginTransaction()
            .add(R.id.frame_restaurant_detail,overviewFragment)
            .add(R.id.frame_restaurant_detail,reviewFragment)
            .hide(reviewFragment)
            .show(overviewFragment)
            .commit()
    }

    //View Function
    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                .hide(overviewFragment)
                .hide(reviewFragment)
                .show(fragment)
                .commit()
            return true
        }
        return false
    }

    private fun changeTab(){
        if(tab == 1){
            tab = 2
            binding.tvReview.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
            binding.tvReview.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.tvOverview.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.tvOverview.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))

            loadFragment(reviewFragment)
        }else{
            tab = 1
            binding.tvReview.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.tvReview.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.tvOverview.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.black))
            binding.tvOverview.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))

            loadFragment(overviewFragment)
        }
    }
    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun setRestaurantData(data:RestaurantData){
        binding.tvRestaurantName.text = data.name

        var cuisineString = ""
        data.cuisine!!.forEach {
            cuisineString += it!!.name
            if(! it.equals(data.cuisine.lastIndex)){
                cuisineString +=" "
            }
        }
        var typeString = ""
        data.type!!.forEach {
            typeString += it!!.name

            if(! it.equals(data.type.lastIndex)){
                typeString +=" "
            }
        }
        binding.tvTagline.text = "$cuisineString $typeString"
        binding.tvLocation.text = data.address
        binding.tvRating.text = data.rating
        binding.tvPhotoCount.text = data.photo?.count().toString()
        binding.tvReviewCount.text = "${data.reviews} Reviews"

        binding.ivSave.isChecked = data.bookmarked == 1

        binding.ivSave.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                presenter.removeRestaurantSaved(data?.id!!)
            }else{
                presenter.setRestaurantSaved(data?.id!!)
            }
        }
    }
    //End View Function

    //Contract Function
    override fun message(msg: String) {
        Timber.d(msg)
    }

    override fun getRestaurantData(data: RestaurantData?) {
        setRestaurantData(data!!)
    }


    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.hide()
    }

    override fun fullPhoto(data: RestaurantData) {
        val bundle = Bundle()
        bundle.putParcelable("RestaurantData",data)

        findNavController().navigate(R.id.action_restaurantDetail_to_restaurantPhotoGallery,bundle)
    }

    override fun writeReview(data: RestaurantData) {
        val bundle = Bundle()
        bundle.putParcelable("RestaurantData",data)

        findNavController().navigate(R.id.action_restaurantDetail_to_restaurantAddReview,bundle)
    }
    //End Contract Function


}