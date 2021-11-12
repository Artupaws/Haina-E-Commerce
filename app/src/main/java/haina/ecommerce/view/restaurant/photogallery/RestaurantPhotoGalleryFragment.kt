package haina.ecommerce.view.restaurant.photogallery

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
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.restaurant.AdapterRestaurantList
import haina.ecommerce.adapter.restaurant.photogallery.AdapterRestaurantPhotoGallery
import haina.ecommerce.databinding.FragmentRestaurantPhotoGalleryBinding
import haina.ecommerce.databinding.FragmentRestaurantReviewBinding
import haina.ecommerce.model.property.CityItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.model.restaurant.master.RestaurantPagination
import haina.ecommerce.model.restaurant.master.RestaurantPhoto
import haina.ecommerce.view.restaurant.dashboard.RestaurantDashboardPresenter
import timber.log.Timber

class RestaurantPhotoGalleryFragment :
    Fragment()
    ,AdapterRestaurantPhotoGallery.ItemAdapterCallback
{
    private lateinit var _binding: FragmentRestaurantPhotoGalleryBinding
    private val binding get() = _binding
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
        AdapterRestaurantPhotoGallery(requireActivity(), arrayListOf(), this)
    }
    //End Lazy Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRestaurantPhotoGalleryBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context

        val restaurantData = arguments?.getParcelable<RestaurantData>("RestaurantData")


        binding.rvPhotoGallery.adapter = AdapterRestaurantPhotoGallery(requireActivity(),restaurantData!!.photo!!,this)
        binding.rvPhotoGallery.layoutManager = GridLayoutManager(context, 3)
        binding.tvTitle.text = "${restaurantData.photo!!.size} Photos"
        binding.tvRestaurantName.text = restaurantData.name
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun photoDetail(data: RestaurantPhoto) {

    }


}