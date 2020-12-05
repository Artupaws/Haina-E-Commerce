package haina.ecommerce.view.postingjob

import android.Manifest
import android.R
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import haina.ecommerce.adapter.AdapterAutoJobCategory
import haina.ecommerce.databinding.ActivityPostingJobBinding
import haina.ecommerce.model.DataItem

class PostingJobActivity : AppCompatActivity(), PostingJobContract, View.OnClickListener {

    private lateinit var binding: ActivityPostingJobBinding
    private lateinit var presenter: PostingJobPresenter
    var suggestions: List<DataItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostingJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = PostingJobPresenter(this)

        binding.etDescriptionJob.setLines(5)
        binding.toolbar.title = "Posting Job"
        binding.toolbar.setNavigationIcon(haina.ecommerce.R.drawable.ic_back_black)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.cvAddImage.setOnClickListener(this)
        presenter.loadListJobCategory()

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            haina.ecommerce.R.id.cv_add_image -> {
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
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSION_CODE -> {
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
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            binding.ivCompany.setImageURI(data?.data)
        }
    }

    override fun successPostingJob(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun errorPostingJob(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun successLoadJobCategory(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun errorLoadJobCategory(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun loadJobCategory(item: ArrayList<DataItem?>?) {
        val adapterAutoTextJobCategory= AdapterAutoJobCategory(this,haina.ecommerce.R.layout.list_item_autotextcomplete, item)
        binding.etCategoryJob.threshold = 1
        binding.etCategoryJob.setAdapter(adapterAutoTextJobCategory)
    }

}