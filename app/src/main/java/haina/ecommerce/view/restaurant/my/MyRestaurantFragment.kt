package haina.ecommerce.view.restaurant.my

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
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.restaurant.AdapterRestaurantList
import haina.ecommerce.databinding.FragmentRestaurantMyBinding
import haina.ecommerce.databinding.FragmentRestaurantReviewBinding
import haina.ecommerce.model.property.CityItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.model.restaurant.master.RestaurantPagination
import haina.ecommerce.view.restaurant.dashboard.RestaurantDashboardPresenter
import timber.log.Timber

class MyRestaurantFragment :
    Fragment()
    ,MyRestaurantContract.View
    ,AdapterRestaurantList.ItemAdapterCallback
{
    private lateinit var _binding: FragmentRestaurantMyBinding
    private val binding get() = _binding
    private lateinit var presenter: MyRestaurantPresenter
    private var progressDialog: Dialog? = null
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var ctx: Context

    private var page:Int = 1
    private var totalPage:Int= 0
    private var halal:Int? = null

    private var latitude:Double? = null
    private var longitude:Double? = null



    private var cuisineId:Int? = null
    private var typeId:Int? = null

    //Lazy Adapter
    private val adapterRestaurantList by lazy {
        AdapterRestaurantList(requireActivity(), arrayListOf(), this)
    }
    //End Lazy Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRestaurantMyBinding.inflate(inflater, container, false)
        presenter = MyRestaurantPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context

        dialogLoading()
        getLocation()
        refresh()

        binding.rvMyRestaurant.adapter = adapterRestaurantList

        binding.scrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener {
                v, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY){
                //scrolldown
            }
            if (scrollY < oldScrollY){
                //scrollup
            }
            if(!v.canScrollVertically(1)){
                Timber.d("last")
                if(page!=totalPage){
                    page++
                    presenter.getMyRestaurant(cuisineId,typeId,halal,latitude!!,longitude!!,page)
                }

            }
        })
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.fabRegisterRestaurant.setOnClickListener {
            findNavController().navigate(R.id.action_myRestaurant_to_register_restaurant)
        }

        return binding.root
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

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            adapterRestaurantList.clear()
            page = 1
            presenter.getMyRestaurant(cuisineId,typeId,halal,latitude!!,longitude!!,page)
        }
    }
    //End View Function

    //test function
    private fun getLocation(){

        showLoading()

        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000)
            return
        }


        var l: Location? = null

        l = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if (l != null) {
            latitude = l.latitude
            longitude = l.longitude
            geo()
        }
    }

    private fun geo(){
        dismissLoading()
        val geocoder = Geocoder(ctx)
        val result = geocoder.getFromLocation(latitude!!,longitude!!,1)
        result.forEach {
            Timber.d(it.toString())
        }
        if(adapterRestaurantList.itemCount==0){
            presenter.getMyRestaurant(cuisineId,typeId,halal,latitude!!,longitude!!,page)
        }
    }
    //End test function

    //Contract Function
    override fun message(msg: String) {
        Timber.d(msg)
    }

    override fun getRestaurantList(data: RestaurantPagination?) {
        Timber.d(data.toString())
        binding.swipeRefresh.isRefreshing=false
        totalPage = data!!.totalPage!!
        adapterRestaurantList.add(data.restaurants)
    }

    override fun getRestaurantCuisine(data: List<CuisineAndTypeData?>?) {
        TODO("Not yet implemented")
    }

    override fun getRestaurantType(data: List<CuisineAndTypeData?>?) {
        TODO("Not yet implemented")
    }

    override fun getCity(data: List<CityItem?>?) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.hide()
    }
    //End Contract Function

    //Adapter Callback
    override fun restaurantDataOnClick(data: RestaurantData) {
        val bundle = Bundle()
        bundle.putParcelable("RestaurantData",data)

        findNavController().navigate(R.id.action_myRestaurant_to_restaurantDetail,bundle)
    }
    //End Adapter Callback

}