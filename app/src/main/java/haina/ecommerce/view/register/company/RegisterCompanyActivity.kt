package haina.ecommerce.view.register.company

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityRegisterCompanyBinding
import haina.ecommerce.view.MainActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RegisterCompanyActivity : AppCompatActivity(), View.OnClickListener, RegisterCompanyContract {

    private lateinit var binding: ActivityRegisterCompanyBinding
    private var uri: Uri = Uri.EMPTY
    private var isEmptyName = true
    private var isEmptyDescription = true
    private var isEmptyImageCompany = true
    private lateinit var presenter: RegisterCompanyPresenter
    var isRunningRegister = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = RegisterCompanyPresenter(this, this)
        binding.toolbar.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbar.setNavigationOnClickListener { onBackPressed()}
        binding.toolbar.title = "Register Company"
        binding.cvAddImage.setOnClickListener(this)
        binding.btnRegisterCompany.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.cv_add_image -> {
                pickImageFromGallery()
            }
            R.id.btn_register_company -> {
                checkRegisterCompany()
            }
        }
    }

    private fun checkRegisterCompany(){
        var name = binding.etCompanyName.text.toString()
        var description = binding.etDescriptionCompany.text.toString()
        val filepath = getRealPathFromURIPath(uri, this)
        val file = File(filepath)
        val imageCompany: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        val body = MultipartBody.Part.createFormData("icon", file.name, imageCompany)

        isEmptyImageCompany = uri == Uri.EMPTY

        if (name.isEmpty()){
            binding.outlinedTextFieldNameCompany.error = "Name Company can't empty"
            isEmptyName = true
        } else {
            binding.etCompanyName.text.toString()
            isEmptyName = false
        }

        if (description.isEmpty()){
            binding.outlinedFieldDescriptionCompany.error = "Description Company can't empty"
            isEmptyDescription = true
        } else {
            binding.etDescriptionCompany.text.toString()
            isEmptyDescription = false
        }

        if (!isEmptyName && !isEmptyDescription && !isEmptyImageCompany){
            val namePost: RequestBody = RequestBody.create(MultipartBody.FORM, name)
            val descriptionPost: RequestBody = RequestBody.create(MultipartBody.FORM, description)
            presenter.registerCompany(body, namePost, descriptionPost)
            binding.btnRegisterCompany.visibility = View.INVISIBLE
            binding.relativeLoading.visibility = View.VISIBLE
            binding.cvAddImage.isEnabled = false
            isRunningRegister = true
        } else {
            Toast.makeText(applicationContext, "Please completed form", Toast.LENGTH_SHORT).show()
            binding.ivLoading.visibility = View.INVISIBLE
            binding.btnRegisterCompany.visibility = View.VISIBLE
            binding.cvAddImage.isEnabled = true
        }
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, RegisterCompanyActivity.IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            RegisterCompanyActivity.PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        uri = if (resultCode == Activity.RESULT_OK && requestCode == RegisterCompanyActivity.IMAGE_PICK_CODE){
            binding.ivCompany.setImageURI(data?.data)
            data?.data!!
        } else {
            Uri.EMPTY
        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: Activity): String? {
        val cursor: Cursor? = activity.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
        }
    }

    override fun messageRegisterCompany(msg: String) {
        isRunningRegister = if (msg == "1") {
            Toast.makeText(applicationContext, "Register Company Success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("loginStatus", "1")
            startActivity(intent)
            binding.btnRegisterCompany.visibility = View.VISIBLE
            binding.relativeLoading.visibility = View.INVISIBLE
            binding.cvAddImage.isEnabled = true
            false
        } else{
            Toast.makeText(applicationContext, "Register Company Failed", Toast.LENGTH_SHORT).show()
            binding.btnRegisterCompany.visibility = View.VISIBLE
            binding.relativeLoading.visibility = View.INVISIBLE
            binding.cvAddImage.isEnabled = true
            false
        }
    }

    override fun onBackPressed() {
        if (isRunningRegister){
            Toast.makeText(applicationContext, "Please wait, register on progress", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }
}