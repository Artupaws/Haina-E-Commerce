package haina.ecommerce.view.forum.createnewpost

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterInputImages
import haina.ecommerce.adapter.forum.AdapterListSubforum
import haina.ecommerce.databinding.ActivityNewPostBinding
import haina.ecommerce.model.forum.DataSubforum
import haina.ecommerce.model.forum.DataSubforumHotPost
import haina.ecommerce.model.forum.ImagePostData
import haina.ecommerce.model.forum.SubforumData
import haina.ecommerce.room.roomimagepost.ImagePostClient
import haina.ecommerce.room.roomimagepost.ImagePostDao
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File
import java.util.ArrayList

 class NewPostActivity : AppCompatActivity(), AdapterInputImages.ItemAdapterCallback,
    CreateNewPostContract.View, AdapterListSubforum.ItemAdapterCallback, View.OnClickListener {

    private lateinit var binding:ActivityNewPostBinding
    private lateinit var listImages:MutableList<ImagePostData>
    private var uri: Uri = Uri.EMPTY
    private lateinit var dao: ImagePostDao
    private lateinit var database: ImagePostClient
    private var progressDialog:Dialog? = null
    private var listPhotoArray = ArrayList<MultipartBody.Part>()
    private lateinit var presenter: CreateNewPostPresenter
    private var subforumId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = ImagePostClient.getDatabase(this)
        dao = database.getImagePostDao()
        presenter = CreateNewPostPresenter(this, this)
        presenter.getListSubForum()
        binding.btnNewPost.setOnClickListener(this)
        binding.rvImages.adapter = imagesAdapter
        getListImagePost(database, dao)
        binding.toolbarNewPost.setNavigationOnClickListener {
            deleteAll()
            onBackPressed()
        }
        dialogLoading()
        binding.rvSubforum.adapter = subforumAdapter
        AdapterListSubforum.VIEW_TYPE = 2
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
                pickImageFromGallery()

            } else {
                //permission already granted
                pickImageFromGallery()
            }
        } else {
            //system OS is < Marshmallow
            pickImageFromGallery()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imagesAdapter.clear()
            uri = data?.data!!
            val filepath = getRealPathFromURIPath(uri)
            val file = File(filepath!!)
            val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("images[]", file.name, mFile)
            saveImagePost(ImagePostData(0, uri.toString()))
            getListImagePost(database, dao)
            listPhotoArray.add(body)

        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri): String? {
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
        }
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

    private val imagesAdapter by lazy {
        AdapterInputImages(applicationContext, mutableListOf(), this)
    }

    private fun checkDataPost(){
        var isEmptyTitle = true
        var isEmptyContent = true
        var isEmptyImages = true
        var isEmptySubforumId = true

        var title = binding.etTitle.text.toString()
        var content = binding.etDescription.text.toString()
        var images = listPhotoArray
        var subforumIdParams = subforumId

        if (title.isEmpty()){
            isEmptyTitle = true
            binding.tvTitle.error = getString(R.string.cant_empty)
        } else {
            isEmptyTitle = false
            binding.tvTitle.error = null
            title = binding.etTitle.text.toString()
        }

        if (content.isEmpty()){
            isEmptyContent = true
            binding.tvTitleDescription.error = getString(R.string.cant_empty)
        } else {
            isEmptyContent = false
            binding.tvTitleDescription.error = null
            content = binding.etDescription.text.toString()
        }

        if (images.isNullOrEmpty()){
            isEmptyImages = true
            binding.tvTitleImage.error = getString(R.string.cant_empty)
        } else {
            isEmptyImages = false
            binding.tvTitleImage.error = null
            images = listPhotoArray
        }

        if (subforumIdParams == 0){
            isEmptySubforumId = true
            binding.tvTitleSubforum.error = getString(R.string.cant_empty)
        } else {
            isEmptySubforumId = false
            binding.tvTitleSubforum.error = null
            subforumIdParams = subforumId
        }

        if (!isEmptyTitle && !isEmptyContent && !isEmptyImages && !isEmptySubforumId){
            presenter.createNewPost(subforumIdParams, title, content, images, null)
        }
    }

    private fun saveImagePost(imagePost: ImagePostData){
        if (dao.getById(imagePost.id).isEmpty()){
            dao.insert(imagePost)
        }else{
            dao.update(imagePost)
        }
    }

    private fun deleteImagePost(imagePost: ImagePostData){
        dao.delete(imagePost)
    }

    private fun deleteAll(){
        dao.deleteAll()
    }

    override fun onClickAddImage(view: View, data: ImagePostData, position:Int) {
        when(view.id){
            R.id.cv_click -> {
                when(data.id) {
                    -1 -> {
                        checkPermission()
                    } else -> {
                        Toast.makeText(applicationContext, position.toString() , Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.iv_action -> {
                deleteImagePost(data)
                imagesAdapter.notifyItemRangeRemoved(position,position)
                imagesAdapter.clear()
                getListImagePost(database, dao)
            }
        }
    }

    override fun onBackPressed() {
        deleteAll()
        super.onBackPressed()
    }

    override fun messageNewPost(msg: String) {
        Timber.d(msg)
        if (msg.contains("New Post Successfully Posted!")){
            onBackPressed()
        }
    }

     override fun getListSubforum(data: List<SubforumData?>?) {
         subforumAdapter.clear()
         subforumAdapter.add(data)
     }

    private val subforumAdapter by lazy {
        AdapterListSubforum(applicationContext, arrayListOf(), this)
    }

    override fun messageGetListSubforum(msg: String) {
        Timber.d(msg)
        if (msg.contains("New Post Successfully Posted!")){
            onBackPressed()
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun listSubforumClick(view: View, data: SubforumData) {
        when(view.id){
            R.id.cv_click -> {
                subforumId = data.id!!
                Toast.makeText(applicationContext, data.id.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_new_post -> {
                checkDataPost()
            }
        }
    }

}