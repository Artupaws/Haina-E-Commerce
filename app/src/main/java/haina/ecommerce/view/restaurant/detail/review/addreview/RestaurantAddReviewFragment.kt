package haina.ecommerce.view.restaurant.detail.review.addreview

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterInputImages
import haina.ecommerce.databinding.FragmentRestaurantAddReviewBinding
import haina.ecommerce.model.forum.ImagePostData
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.model.restaurant.master.ReviewData
import haina.ecommerce.room.roomimagepost.ImagePostClient
import haina.ecommerce.room.roomimagepost.ImagePostDao
import haina.ecommerce.room.roomvideopost.VideoPostClient
import haina.ecommerce.room.roomvideopost.VideoPostDao
import haina.ecommerce.room.roomvideopost.VideoPostData
import haina.ecommerce.view.forum.createnewpost.NewPostActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File
import java.util.ArrayList

class RestaurantAddReviewFragment :
    Fragment()
    ,RestaurantAddReviewContract.View
    ,AdapterInputImages.InputImageClick
{
    private lateinit var _binding: FragmentRestaurantAddReviewBinding
    private val binding get() = _binding
    private lateinit var presenter: RestaurantAddReviewPresenter
    private var progressDialog: Dialog? = null
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var ctx: Context

    private lateinit var daoImage: ImagePostDao
    private lateinit var databaseImage: ImagePostClient
    private var typePick:Int = 1
    private var uri: Uri = Uri.EMPTY

    private var restaurantId:Int? = null

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
        _binding = FragmentRestaurantAddReviewBinding.inflate(inflater, container, false)
        presenter = RestaurantAddReviewPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context

        val restaurantData = arguments?.getParcelable<RestaurantData>("RestaurantData")

        restaurantId = restaurantData!!.id

        dialogLoading()

        setRestaurantData(restaurantData)

        binding.btnSubmitReview.setOnClickListener {
            submitReview()
        }

        binding.ivBack.setOnClickListener {
            deleteAll()
            findNavController().navigateUp()
        }

        binding.rbRating.setOnRatingBarChangeListener { ratingBar, _, _ ->
            if(ratingBar.numStars!=0){
                binding.linearReview.visibility = View.VISIBLE
            }
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

    private fun setRestaurantData(data:RestaurantData){
        binding.tvRestaurantName.text = data.name
        Glide.with(ctx).load(data.photo!![0]!!.url).placeholder(R.drawable.skeleton_image).into(binding.ivRestaurant)

        binding.rvImages.adapter = imagesAdapter

        databaseImage = ImagePostClient.getDatabase(ctx)
        daoImage = databaseImage.getImagePostDao()
        getListImagePost(databaseImage, daoImage)
    }

    private fun getListImagePost(database: ImagePostClient, dao: ImagePostDao) {
        listImages = mutableListOf()
        listImages.addAll(dao.getAll())
        listImages.addAll(listOf(ImagePostData(-1, "&#xf067;")))
        if (listImages.size < 1){
            binding.rvImages.visibility = View.GONE
        } else {
            binding.rvImages.visibility = View.VISIBLE
            imagesAdapter.add(listImages)
        }
    }
    //End Of View Function


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE-> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickMediaFromGalery(typePick)
                } else {
                    Toast.makeText(ctx, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && typePick == 1){
            imagesAdapter.clear()
            uri = data?.data!!
            val filepath = getRealPathFromURIPath(uri)
            val file = File(filepath!!)
            val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("review_image[]", file.name, mFile)
            saveImagePost(ImagePostData(0, uri.toString()))
            getListImagePost(databaseImage, daoImage)
            listPhotoArray.add(body)
        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri): String? {
        val cursor: Cursor? = activity?.contentResolver?.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
        }
    }
    private fun pickMediaFromGalery(typePick:Int) {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        if (typePick == 1) intent.type = "image/*" else if (typePick == 2) intent.type = "pdf/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun saveImagePost(imagePost: ImagePostData){
        if (daoImage.getById(imagePost.id).isEmpty()){
            daoImage.insert(imagePost)
        }else{
            daoImage.update(imagePost)
        }
    }
    private fun deleteImagePost(imagePost: ImagePostData){
        daoImage.delete(imagePost)
    }

    private fun deleteAll(){
        daoImage.deleteAll()
    }


    //Contract Function
    override fun message(code: Int, msg: String) {
        Timber.d(msg)
    }

    override fun addReview(data: ReviewData?) {
        deleteAll()
        findNavController().navigateUp()
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.hide()

    }

    override fun onClickAddImage(view: View, data: ImagePostData, position: Int) {

        when(view.id){
            R.id.cv_click -> {
                typePick = 1
                when(data.id) {
                    -1 -> {
                        pickMediaFromGalery(typePick)
                    }
                }
            }
            R.id.iv_action -> {
                deleteImagePost(data)
                imagesAdapter.notifyItemRangeRemoved(position,position)
                imagesAdapter.clear()
                getListImagePost(databaseImage, daoImage)
            }
        }
    }
    //End of Contract Function

    private fun submitReview(){
        val content:RequestBody = RequestBody.create(MultipartBody.FORM,  binding.etReview.text.toString())


        presenter.createReview(restaurantId!!, binding.rbRating.numStars, content, listPhotoArray)
    }
}