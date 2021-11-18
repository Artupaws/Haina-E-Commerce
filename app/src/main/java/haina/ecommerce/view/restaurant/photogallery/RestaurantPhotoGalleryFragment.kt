package haina.ecommerce.view.restaurant.photogallery

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.adapter.restaurant.photogallery.AdapterRestaurantPhotoDetail
import haina.ecommerce.adapter.restaurant.photogallery.AdapterRestaurantPhotoGallery
import haina.ecommerce.databinding.FragmentRestaurantPhotoGalleryBinding
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.model.restaurant.master.RestaurantPhoto
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

    override fun photoDetail(listPhoto: List<RestaurantPhoto?>?, position: Int) {
        startActivity(
            AdapterRestaurantPhotoDetail.createIntent(
                requireContext(),
                listPhoto!!,
                position
            )
        )
    }


}