 package haina.ecommerce.view.postingjob.datajob

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterAddressCompanyPostingJob
import haina.ecommerce.adapter.AdapterJobCategory
import haina.ecommerce.databinding.ActivityPostingJobBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.helper.NumberTextWatcher
import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.DataPostingJob
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.datacompany.DataCompanyActivity
import haina.ecommerce.view.postingjob.skillrequires.AddSkillRequiresActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class PostingJobActivity : AppCompatActivity(), PostingJobContract, View.OnClickListener {

    private lateinit var binding: ActivityPostingJobBinding
    private lateinit var presenter: PostingJobPresenter
    private var popupCategory: AlertDialog? = null
    private var popupCheckDataCompany: AlertDialog? = null
    private var broadcaster: LocalBroadcastManager? = null
    private var isEmptyImage = true
    private var isEmptyTitle = true
    private var isEmptyLocation = true
    private var isEmptyCategory = true
    private var isEmptyDescription = true
    private var isEmptySalaryFrom = true
    private var isEmptySalaryTo = true
    private var uri: Uri = Uri.EMPTY
    var idLocation:Int? = 0
    var idJobVacancy:Int? = 0
    var idCategory:String = ""
    val helper:Helper = Helper

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
//        binding.etLocationCompany.setOnClickListener(this)
        binding.btnPostingJob.setOnClickListener(this)
        val locale = Locale("es", "IDR")
        val numDecs = 2 // Let's use 2 decimals
        val twSalaryFrom: TextWatcher = NumberTextWatcher(binding.etSalaryFrom, locale, numDecs)
        val twSalaryTo: TextWatcher = NumberTextWatcher(binding.etSalaryTo, locale, numDecs)
        binding.etSalaryFrom.addTextChangedListener(twSalaryFrom)
        binding.etSalaryTo.addTextChangedListener(twSalaryTo)
        presenter.loadListJobCategory()
        presenter.getDataCompany()
        showPopup()
        refresh()

    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.loadListJobCategory()
            presenter.getDataCompany()
        }
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

            R.id.btn_posting_job -> {
                binding.btnPostingJob.visibility = View.INVISIBLE
                binding.relativeLoading.visibility = View.VISIBLE
                binding.cvAddImage.isEnabled = false
//                Toast.makeText(applicationContext, "salary : ${helper.changeFormatMoneyToValue(binding.etSalaryFrom.text.toString())} " +
//                        "+ ${helper.changeFormatMoneyToValue(binding.etSalaryTo.text.toString())}", Toast.LENGTH_SHORT).show()
                checkingDataJob()
            }
        }
    }

    private fun checkingDataJob(){
        val title = binding.etTitleJob.text.toString()
        var location = idLocation
        var category = idCategory
        var description = binding.etDescriptionJob.text.toString()
        var salaryFrom = binding.etSalaryFrom.text.toString()
        var salaryTo = binding.etSalaryTo.text.toString()
        val filepath = getRealPathFromURIPath(uri, this)
        val file = File(filepath)
        val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("photo", file.name, mFile)

        isEmptyImage = uri == Uri.EMPTY

        if (title.isEmpty()){
            binding.outlinedTextFieldTitleJob.error = "Title can't empty"
            isEmptyTitle = true
        } else {
            binding.etTitleJob.text.toString()
            isEmptyTitle = false
        }

        if (location == null){
            binding.tvChooseRv.error = "Please choose one your location company"
            isEmptyLocation = true
        } else {
            location = idLocation
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

        when {
            salaryTo.isEmpty() -> {
                binding.outlinedFieldSalaryTo.error = "Salary to can't empty"
                isEmptySalaryTo = true
            }
            salaryTo < salaryFrom -> {
                binding.outlinedFieldSalaryTo.error = "Salary to must bigger than salary from"
                isEmptySalaryTo = false
            }
            else -> {
                binding.etSalaryTo.text.toString()
                isEmptySalaryTo = false
            }
        }

        if (!isEmptyImage && !isEmptyTitle  && !isEmptyDescription && !isEmptySalaryFrom && !isEmptySalaryTo && !isEmptyLocation && !isEmptyCategory){
            val titlePost: RequestBody = RequestBody.create(MultipartBody.FORM, title)
            val descriptionPost: RequestBody = RequestBody.create(MultipartBody.FORM, description)
            val salaryFromPost: RequestBody = RequestBody.create(MultipartBody.FORM, helper.changeFormatMoneyToValue(binding.etSalaryFrom.text.toString()))
            val salaryToPost: RequestBody = RequestBody.create(MultipartBody.FORM, helper.changeFormatMoneyToValue(binding.etSalaryTo.text.toString()))
            val idCategoryPost: RequestBody = RequestBody.create(MultipartBody.FORM, idCategory)
            val idLocationPost: RequestBody = RequestBody.create(MultipartBody.FORM, location.toString())
             presenter.postingJobVacancy(body, titlePost, idLocationPost, idCategoryPost, descriptionPost, salaryFromPost, salaryToPost)
        } else {
            binding.btnPostingJob.visibility = View.VISIBLE
            binding.relativeLoading.visibility = View.INVISIBLE
            binding.cvAddImage.isEnabled = true
            Toast.makeText(applicationContext, "Please complete form", Toast.LENGTH_SHORT).show()
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

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
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
        uri = if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            binding.ivCompany.setImageURI(data?.data)
            data?.data!!
        } else {
            Uri.EMPTY
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver,
                IntentFilter("jobCategory")
        )
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver2,
                IntentFilter("addressCompany")
        )
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action){
                "jobCategory" -> {
                    val idCategoryFill = intent.getStringExtra("idCategory")
                    val nameCategory = intent.getStringExtra("nameCategory")
                    binding.etCategoryJob.setText(nameCategory)
                    idCategory = idCategoryFill!!
                    popupCategory?.dismiss()
                }
            }
        }
    }

    private val mMessageReceiver2: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action){
                "addressCompany" -> {
                    val idLocationFill = intent.getIntExtra("idAddress", 0)
                    idLocation = idLocationFill
                    Log.d("idAddress", idLocation.toString())
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
        binding.etTitleJob.text?.clear()
        binding.etCategoryJob.text?.clear()
        binding.etDescriptionJob.text?.clear()
        binding.etSalaryFrom.text?.clear()
        binding.etSalaryTo.text?.clear()
        binding.ivCompany.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_add))
        binding.btnPostingJob.visibility = View.VISIBLE
        binding.relativeLoading.visibility = View.INVISIBLE
        binding.cvAddImage.isEnabled = true
        binding.outlinedTextFieldTitleJob.error = null
        binding.tvChooseRv.error = null
        binding.outlinedFieldCategory.error = null
        binding.outlinedFieldDescription.error = null
        binding.outlinedFieldSalaryForm.error = null
        binding.outlinedFieldSalaryTo.error = null
    }

    override fun errorPostingJob(msg: String) {
        binding.btnPostingJob.visibility = View.VISIBLE
        binding.relativeLoading.visibility = View.INVISIBLE
        binding.cvAddImage.isEnabled = true
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun getValuePostingJob(item: DataPostingJob?) {
        Log.d("successLoadCategory", item?.id.toString())
        idJobVacancy = item?.id
        if (item?.id != null){
         move(item)
        }
    }

    override fun successLoadJobCategory(msg: String) {
        Log.d("successLoadCategory", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun errorLoadJobCategory(msg: String) {
        Log.d("errorLoadCategory", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    private fun showPopup() {
        val popup = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.popup_check_register_company, null)
        popup.setCancelable(false)
        popup.setView(view)
        val actionCancel = view.findViewById<TextView>(haina.ecommerce.R.id.tv_action_cancel)
        val actionYes = view.findViewById<TextView>(haina.ecommerce.R.id.tv_action_yes)
        val attention = view.findViewById<TextView>(haina.ecommerce.R.id.tv_popup)
        popupCheckDataCompany = popup.create()
        popupCheckDataCompany?.dismiss()
        attention.text = "You have not added your company address, please add it first to continue posting job vacancy"
        actionCancel.setOnClickListener {
            onBackPressed()
        }

        actionYes.setOnClickListener {
            val intent = Intent(applicationContext, DataCompanyActivity::class.java)
            startActivity(intent)
            popupCheckDataCompany?.dismiss()
        }
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

    override fun onResume() {
        super.onResume()
        presenter.getDataCompany()
    }

    override fun getDataCompany(item: DataCompany) {
        val getListAddressCompany = AdapterAddressCompanyPostingJob(this, item.addressCompanies)
        binding.rvAddressCompany.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = getListAddressCompany
        }
        if (item.addressCompanies?.isEmpty()!!){
            popupCheckDataCompany?.show()
        } else {
            popupCheckDataCompany?.dismiss()
        }
    }


    override fun messageGetDataCompany(msg: String) {
        Log.d("messageCompany", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    private fun move(item:DataPostingJob){
        val intent = Intent(applicationContext, AddSkillRequiresActivity::class.java)
        intent.putExtra("idJobVacancy", item.id)
        startActivity(intent)
    }

}
