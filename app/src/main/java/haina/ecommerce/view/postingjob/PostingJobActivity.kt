package haina.ecommerce.view.postingjob

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterJobCategory
import haina.ecommerce.adapter.AdapterJobLocation
import haina.ecommerce.databinding.ActivityPostingJobBinding
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataPostingJob
import haina.ecommerce.util.Constants

class PostingJobActivity : AppCompatActivity(), PostingJobContract, View.OnClickListener {

    private lateinit var binding: ActivityPostingJobBinding
    private lateinit var presenter: PostingJobPresenter
    private var popupCategory: AlertDialog? = null
    private var popupLocation: AlertDialog? = null
    private var broadcaster: LocalBroadcastManager? = null
    private var isEmptyTitle = true
    private var isEmptyLocation = true
    private var isEmptyCategory = true
    private var isEmptyDescription = true
    private var isEmptySalaryFrom = true
    private var isEmptySalaryTo = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostingJobBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = PostingJobPresenter(this, this)
        broadcaster = LocalBroadcastManager.getInstance(this)

        binding.etDescriptionJob.setLines(5)
        binding.toolbar.title = "Posting Job"
        binding.toolbar.setNavigationIcon(haina.ecommerce.R.drawable.ic_back_black)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.cvAddImage.setOnClickListener(this)
        binding.etCategoryJob.setOnClickListener(this)
        binding.etLocationCompany.setOnClickListener(this)
        binding.btnPostingJob.setOnClickListener(this)
        presenter.loadListJobCategory()
        presenter.loadListJobLocation()

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
           R.id.cv_add_image -> {
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

            R.id.et_category_job -> {
                popupCategory?.show()
            }

            R.id.et_location_company -> {
                popupLocation?.show()
                Toast.makeText(this, "location", Toast.LENGTH_SHORT).show()
            }

            R.id.btn_posting_job ->{
                binding.btnPostingJob.visibility = View.INVISIBLE
                binding.relativeLoading.visibility = View.VISIBLE
                binding.cvAddImage.isEnabled = false
            }
        }
    }

    private fun checkingDataJob(){
        var title = binding.etTitleJob.text.toString()
        var location = binding.etLocationCompany.text.toString()
        var category = binding.etCategoryJob.text.toString()
        var description = binding.etDescriptionJob.text.toString()
        var salaryFrom = binding.etSalaryFrom.text.toString()
        var salaryTo = binding.etSalaryTo.text.toString()

        if (title.isEmpty()){
            binding.outlinedTextFieldTitleJob.error = "Title can't empty"
            isEmptyTitle = true
        } else {
            binding.etTitleJob.text.toString()
            isEmptyTitle = false
        }

        if (location.isEmpty()){
            binding.outlinedFieldLocation.error = "Location can't empty"
            isEmptyLocation = true
        } else {
            binding.etLocationCompany.text.toString()
            isEmptyLocation = false
        }

        if (category.isEmpty()){
            binding.outlinedFieldCategory.error = "Category can't empty"
            isEmptyCategory = true
        } else {
            binding.etCategoryJob.text.toString()
            isEmptyCategory = false
        }

        if (description.isEmpty()){
            binding.outlinedFieldDescription.error = "Description can't empty"
            isEmptyDescription = true
        } else {
            binding.etDescriptionJob.text.toString()
            isEmptyDescription = false
        }

        if (salaryFrom.isEmpty()){
            binding.outlinedFieldSalaryForm.error = "Salary from can't empty"
            isEmptySalaryFrom = true
        } else {
            binding.etSalaryFrom.text.toString()
            isEmptySalaryFrom = false
        }

        if (salaryTo.isEmpty()){
            binding.outlinedFieldSalaryTo.error = "Salary to can't empty"
            isEmptySalaryTo = true
        } else {
            binding.etSalaryTo.text.toString()
            isEmptySalaryTo = false
        }

        if (!isEmptyTitle && !isEmptyLocation && !isEmptyCategory && !isEmptyDescription && !isEmptySalaryFrom && !isEmptySalaryTo){
//            presenter.postingJobVacancy(imageCompany = binding.ivCompany.drawable, title,location,category,description,salaryFrom,salaryTo, Constants.APIKEY)
        } else {
            Toast.makeText(applicationContext, "Please Complete Form Login", Toast.LENGTH_SHORT).show()
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

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("jobCategory"))
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver2, IntentFilter("jobLocation"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action){
                "jobCategory" -> {
                    val data = intent.getStringExtra("Category")
                    binding.etCategoryJob.setText(data)
                    popupCategory?.dismiss()
                }
            }
        }
    }

    private val mMessageReceiver2: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action){
                "jobLocation" -> {
                    val data = intent.getStringExtra("Location")
                    binding.etLocationCompany.setText(data)
                    popupLocation?.dismiss()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver2)
    }

    override fun successPostingJob(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun errorPostingJob(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun getValuePostingJob(item: DataPostingJob?) {
        TODO("Not yet implemented")
    }

    override fun successLoadJobCategory(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun errorLoadJobCategory(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    override fun loadJobCategory(itemHaina: List<DataItemHaina?>?) {
        //POP UP Job Category
        val popup = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.layout_pop_up_list, null)
        popup.setCancelable(true)
        popup.setView(view)
        val action = view.findViewById<TextView>(haina.ecommerce.R.id.tv_action)
        val title = view.findViewById<TextView>(haina.ecommerce.R.id.tv_title)
        val rvJob = view.findViewById<RecyclerView>(haina.ecommerce.R.id.rv_popup)
        val jobCategoryAdapter = AdapterJobCategory(this, itemHaina)
        popupCategory = popup.create()
        popupCategory?.dismiss()
        action.setOnClickListener{popupCategory?.dismiss()}
        title.text = "Job Category"
        rvJob.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = jobCategoryAdapter
            jobCategoryAdapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun loadJobLocation(itemHaina: List<DataItemHaina?>?) {
        //POP UP Job Location
        val popup = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.layout_pop_up_list, null)
        popup.setCancelable(true)
        popup.setView(view)
        val action = view.findViewById<TextView>(haina.ecommerce.R.id.tv_action)
        val title = view.findViewById<TextView>(haina.ecommerce.R.id.tv_title)
        val rvJob = view.findViewById<RecyclerView>(haina.ecommerce.R.id.rv_popup)
        val jobLocationAdapter = AdapterJobLocation(this, itemHaina)
        popupLocation = popup.create()
        popupLocation?.dismiss()
        action.setOnClickListener{popupLocation?.dismiss()}
        title.text = "Job Location"
        rvJob.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = jobLocationAdapter
            jobLocationAdapter.notifyDataSetChanged()
        }
    }

}
