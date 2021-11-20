package haina.ecommerce.view.restaurant.register

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterInputImages
import haina.ecommerce.databinding.FragmentRegisterRestaurantBinding
import haina.ecommerce.model.forum.ImagePostData
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.room.roomimagepost.ImagePostClient
import haina.ecommerce.room.roomimagepost.ImagePostDao
import okhttp3.MultipartBody
import timber.log.Timber
import java.util.ArrayList

class RegisterRestaurantFragment :
    Fragment()
    ,RegisterRestaurantContract.View
    , AdapterInputImages.InputImageClick
{
    private lateinit var _binding: FragmentRegisterRestaurantBinding
    private val binding get() = _binding
    private lateinit var presenter: RegisterRestaurantPresenter
    private var progressDialog: Dialog? = null
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var ctx: Context

    private lateinit var daoImage: ImagePostDao
    private lateinit var databaseImage: ImagePostClient
    private var typePick:Int = 1
    private var uri: Uri = Uri.EMPTY

    private var listPhotoArray = ArrayList<MultipartBody.Part>()
    private lateinit var listImages:MutableList<ImagePostData>

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    private val imagesAdapter by lazy {
        AdapterInputImages(ctx, mutableListOf(), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterRestaurantBinding.inflate(inflater, container, false)
        presenter = RegisterRestaurantPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context

        dialogLoading()

        binding.ivBack.setOnClickListener {
//            deleteAll()
            findNavController().navigateUp()
        }

        binding.btnRegisterRestaurant.setOnClickListener {
//            submitRestaurant()
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

    //End View Function

    override fun message(msg: String) {
        Timber.d(msg)
    }

    override fun registerRestaurant(data: RestaurantData?) {
        TODO("Not yet implemented")
    }

    override fun getRestaurantCuisine(data: List<CuisineAndTypeData?>?) {
        TODO("Not yet implemented")
    }

    override fun getRestaurantType(data: List<CuisineAndTypeData?>?) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun dismissLoading() {
        TODO("Not yet implemented")
    }

    override fun onClickAddImage(view: View, data: ImagePostData, position: Int) {
        TODO("Not yet implemented")
    }
}