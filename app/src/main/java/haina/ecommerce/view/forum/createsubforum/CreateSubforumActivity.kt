package haina.ecommerce.view.forum.createsubforum

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
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterCategoryForum
import haina.ecommerce.databinding.ActivityCreateSubforumBinding
import haina.ecommerce.model.forum.DataCategoryForum
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CreateSubforumActivity : AppCompatActivity(),
    AdapterCategoryForum.ItemAdapterCallback, CreateSubforumContract.View, View.OnClickListener {

    private lateinit var binding:ActivityCreateSubforumBinding
    private var categoryId:Int = 1
    private lateinit var uri: Uri
    private var progressDialog:Dialog? = null
    private lateinit var presenter:CreateSubforumPresenter
    private var imageMultipart:MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateSubforumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = CreateSubforumPresenter(this, this)
        binding.ivImageSubforum.setOnClickListener(this)
        binding.btnCreateSubforum.setOnClickListener(this)

        binding.toolbarCreateSubforum.setNavigationOnClickListener {
            onBackPressed()
        }

        val dataCategory = intent?.getParcelableArrayListExtra<DataCategoryForum>("listCategory")
        setupListCategory(dataCategory)
        dialogLoading()

    }

    private fun setupListCategory(data:List<DataCategoryForum?>?){
        binding.rvCategory.apply {
            adapter = AdapterCategoryForum(applicationContext, data, this@CreateSubforumActivity)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(applicationContext, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.ivImageSubforum.setImageURI(data?.data)
            uri = data?.data!!
            val filepath = getRealPathFromURIPath(uri, this)
            val file = File(filepath)
            val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            imageMultipart = MultipartBody.Part.createFormData("image", file.name, mFile)
//            val body = MultipartBody.Part.createFormData("photo", file.name, mFile)
        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: CreateSubforumActivity): String? {
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    
    private fun checkDataSubforum(){
        var isEmptyName = true
        var isEmptyImage = true
        var isEmptyDescription = true

        var name = binding.etTitle.text.toString()
        var description = binding.etDescription.text.toString()
        var imageParams = imageMultipart

        if (name.isNullOrEmpty()){
            isEmptyName = true
            binding.tvTitleName.error = getString(R.string.cant_empty)
        } else {
            isEmptyName = false
            binding.tvTitleName.error = null
            name = binding.etTitle.text.toString()
        }

        if (imageParams == null){
            isEmptyImage = true
            binding.tvTitleImage.error = getString(R.string.cant_empty)
        } else{
            isEmptyImage = false
            imageParams = imageMultipart
            binding.tvTitleImage.error = null
        }

        if (description.isNullOrEmpty()){
            isEmptyDescription = true
            binding.tvTitleDescription.error = getString(R.string.cant_empty)
        } else {
            isEmptyDescription = false
            description = binding.etDescription.text.toString()
            binding.tvTitleDescription.error = null
        }

        if (!isEmptyName && !isEmptyImage && !isEmptyDescription){
            presenter.createSubforum(name, description, categoryId, imageParams!!)
        }
    }
    
    override fun categoryForumClick(view: View, data: DataCategoryForum) {
        when(view.id){
            R.id.cv_click -> {
                if (data.id != 1){
                    categoryId = data.id!!
                }
            }
        }
    }

    override fun messageCreateSubforum(msg: String) {
        Log.d("createSubforum", msg)
        if (msg.contains("Subforum successfully created!")){
            onBackPressed()
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_image_subforum -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    } else {
                        //permission already granted
                        pickImageFromGallery()
                    }
                } else {
                    //system OS is < Marshmallow
                    pickImageFromGallery()
                }
            }

            R.id.btn_create_subforum -> {
                checkDataSubforum()
            }
        }
    }
}