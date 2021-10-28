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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterListAllThreads
import haina.ecommerce.adapter.restaurant.AdapterRestaurantList
import haina.ecommerce.databinding.FragmentRestaurantDetailBinding
import haina.ecommerce.databinding.FragmentRestaurantReviewBinding
import haina.ecommerce.model.property.CityItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.model.restaurant.master.RestaurantPagination
import timber.log.Timber

class RestaurantDetailFragment :
    Fragment()
    ,RestaurantDetailContract.View
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

        presenter.getRestaurantDetail(restaurantData!!.id!!)
    }


    //View Function
    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun setRestaurantData(data:RestaurantData){

    }
    //End View Function

    //Contract Function
    override fun message(msg: String) {
        Timber.d(msg)
    }

    override fun getRestaurantData(data: RestaurantData?) {
        setRestaurantData(data!!)
    }

    override fun getReviewList(data: RestaurantData?) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.hide()
    }
    //End Contract Function


}