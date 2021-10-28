package haina.ecommerce.view.restaurant.dashboard

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
import androidx.navigation.Navigation
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterListAllThreads
import haina.ecommerce.adapter.restaurant.AdapterRestaurantList
import haina.ecommerce.databinding.FragmentRestaurantReviewBinding
import haina.ecommerce.model.property.CityItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.model.restaurant.master.RestaurantPagination
import timber.log.Timber

class RestaurantDashboardFragment :
    Fragment()
    ,RestaurantDashboardContract.View
    ,AdapterRestaurantList.ItemAdapterCallback
{
    private lateinit var _binding:FragmentRestaurantReviewBinding
    private val binding get() = _binding
    private lateinit var presenter: RestaurantDashboardPresenter
    private var progressDialog: Dialog? = null
    private var broadcaster:LocalBroadcastManager? = null
    private lateinit var ctx:Context

    private var page:Int = 1
    private var totalPage:Int= 0

    private var latitude:Double? = null
    private var longitude:Double? = null

    private var cuisineId:Int? = null
    private var typeId:Int? = null

    //Lazy Adapter
    private val adapterRestaurantList by lazy {
        AdapterRestaurantList(requireActivity(), arrayListOf(), this)
    }
    //End Lazy Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRestaurantReviewBinding.inflate(inflater, container, false)
        presenter = RestaurantDashboardPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogLoading()
        refresh()
        getLocation()

        binding.rvRestaurantList.adapter = adapterRestaurantList

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
                    presenter.getRestaurantList(cuisineId,typeId,latitude!!,longitude!!,page)
                }

            }
        })
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

//        val locationListener = object : LocationListener{
//            override fun onLocationChanged(location: Location) {
//                latitude = location!!.latitude
//                longitude = location!!.longitude
//
//                geo()
//            }
//
//            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//            }
//        }

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
        presenter.getRestaurantList(cuisineId,typeId,latitude!!,longitude!!,page)
    }
    //End test function

    //Contract Function
    override fun message(msg: String) {
        Timber.d(msg)
    }

    override fun getRestaurantList(data: RestaurantPagination?) {
        Timber.d(data.toString())
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
        Navigation.findNavController(requireView()).navigate(R.id.action_restaurantDashboard_to_restaurantDetail, bundle)
    }
    //End Adapter Callback

}