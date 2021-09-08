package haina.ecommerce.view.applyjob

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterDocumentUserChoose
import haina.ecommerce.databinding.ActivityApplyJobBinding
import haina.ecommerce.model.DataDocumentUser
import haina.ecommerce.model.DataUser
import haina.ecommerce.view.myaccount.detailaccount.DetailAccountActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File

class ApplyJobActivity : AppCompatActivity(), View.OnClickListener,
    ApplyJobContract.View, AdapterDocumentUserChoose.AdapterResumeClick {

    private lateinit var binding: ActivityApplyJobBinding
    private lateinit var presenter: ApplyJobPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var idDocumentResume:Int? = null
    var idJobVacancy:Int = 0
    val refresh:String= "1"
    private var progressDialog: Dialog? = null
    private var uriPdf: Uri? = null
    private var namePdf:String? = null
    var path: String? = null
    private var fileToUpload:MultipartBody.Part? = null
    private var idResume:Int = 0

    companion object {
        //Permission code
        private val PERMISSION_CODE_PDF = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = ApplyJobPresenter(this, this)
        presenter.loadDocumentResume(1)
        presenter.getDataUserProfile()
        refresh()
        binding.toolbarApplyJob.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarApplyJob.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarApplyJob.title = intent.getStringExtra("titleJob")
        idJobVacancy = intent.getIntExtra("idJobVacancy", 0)
        binding.btnSubmit.setOnClickListener(this)
        binding.tvAddResume.setOnClickListener(this)
//        binding.tvResume.setOnClickListener(this)
        binding.tvActionReviewProfile.setOnClickListener(this)
        loadingDialog()
        requestMultiplePermissions()
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            idDocumentResume?.let { presenter.loadDocumentResume(it) }
            presenter.getDataUserProfile()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_action_review_profile -> {
                val intent = Intent(applicationContext, DetailAccountActivity::class.java)
                startActivity(intent)
            }
            R.id.tv_add_resume -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE_PDF)
                    } else {
                        //permission already granted
                        pickPdf()
                    }
                } else {
                    //system OS is < Marshmallow
                    pickPdf()
                }
//                val intent = Intent(applicationContext, AddRequirementActivity::class.java)
//                    .putExtra("title", "Add Resume")
//                    .putExtra("fromApplyJob", true)
//                startActivity(intent)
            }
            R.id.btn_submit -> {
                checkDataApply()
//                if (idDocumentResume == null || idJobVacancy == 0){
//                    Toast.makeText(applicationContext, "Please choose 1 resume for apply this job", Toast.LENGTH_SHORT).show()
//                } else {
//                    presenter.applyJob(idJobVacancy, binding.etDescriptionAds.text.toString())
//                }
            }
//            R.id.tv_resume -> {
//
//            }
        }
    }

    private fun checkUpload(path: String?){
        // Parsing any Media type file
        val file = File(path!!)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("*/*"), file.toString())
        fileToUpload = MultipartBody.Part.createFormData("docs", file.name, requestBody)
        val filename: RequestBody = RequestBody.create(MediaType.parse("text/plain"), namePdf!!)
        val idCategory = RequestBody.create(MediaType.parse("text/plain"), "1")
        presenter.uploadDocument(fileToUpload!!, filename, idCategory)
    }

    private fun pickPdf() {
        //Intent to pick pdf
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "application/pdf"
        intent = Intent.createChooser(intent, "Choose a file")
        startActivityForResult(intent, 1)
    }

    private fun checkDataApply(){
        var isEmptyNotes = true
        var isEmptyResume = true

        var notes = binding.etDescriptionAds.text.toString()
        var resume = idResume

        if (notes.isNullOrEmpty()){
            isEmptyNotes = true
            binding.tvTitleAdditionalInfo.error = getString(R.string.cant_empty)
        } else {
            isEmptyNotes = false
            binding.tvTitleAdditionalInfo.error = null
            notes = binding.etDescriptionAds.text.toString()
        }

        if (resume == 0){
            isEmptyResume = true
            binding.tvTitleResume.error = getString(R.string.cant_empty)
        } else {
            isEmptyResume = false
            binding.tvTitleResume.error = null
            resume = idResume
        }

        if (!isEmptyResume && !isEmptyNotes){
            presenter.applyJob(idJobVacancy, notes, resume)
        } else {
            Toast.makeText(applicationContext, "Please complete form", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            uriPdf = data!!.data
            val uriString = uriPdf.toString()
            val myFile = File(uriString)
            var cursor: Cursor? = null
            path = myFile.absolutePath
//            path = getFilePathFromURI(this, uri)
            if (uriString.startsWith("content://")){
                try {
                    cursor = contentResolver.query(uriPdf!!, null,null,null, null)
                    if (cursor != null && cursor.moveToFirst()){
                        namePdf = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)).toString()
                    }
                } finally {
                    cursor?.close()
                }
            } else if (uriString.startsWith("file://")){
                namePdf = myFile.name
            }
            checkUpload(path)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun messageGetDataPersonal(msg: String) {
        Timber.d(msg)
        if (msg.contains("Success")){
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun messagetGetDocument(msg: String) {
        Timber.d(msg)
        if (msg.contains("Success")){
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun messageUploadDocument(msg: String) {
        Timber.d(msg)
        if (msg.contains("upload file success")){
            presenter.loadDocumentResume(1)
        }
    }

    override fun messageApplyJob(msg: String) {
        if (msg.contains("Success!")){
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            move()
        } else {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun move(){
        onBackPressed()
    }

    private fun requestMultiplePermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(applicationContext, "All permissions are granted, ready for upload!", Toast.LENGTH_SHORT).show()
                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        Toast.makeText(applicationContext, "Please grant permission for storage!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?, token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }

            }).withErrorListener { Toast.makeText(applicationContext, "Some Error! ", Toast.LENGTH_SHORT).show() }
            .onSameThread()
            .check()
    }

    private fun loadingDialog(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private val adapterDocumentResume by lazy {
        AdapterDocumentUserChoose(applicationContext, arrayListOf(), this)
    }

    override fun getDocumentResume(item: List<DataDocumentUser?>?) {
        adapterDocumentResume.clear()
        adapterDocumentResume.addResume(item)
        binding.rvResume.adapter = adapterDocumentResume
//        val adapterResume = AdapterDocumentUserChoose(this, item, this)
//        binding.rvResume.apply {
//            adapter = adapterResume
//            adapterResume.notifyDataSetChanged()
//        }
    }

    override fun getDataUser(data: DataUser?) {
        Glide.with(this).load(data?.photo).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.NONE).listener(object : RequestListener<Drawable>{
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.progressCircular.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                binding.progressCircular.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
                return false
            }

        }).into(binding.ivProfile)

        binding.tvName.text = data?.fullname
        binding.tvEmail.text = data?.email
        binding.tvPhone.text = data?.phone
        binding.tvPosition.text = data?.latestWork?.position
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun listResumeClicked(view: View, data: DataDocumentUser) {
        when(view.id){
            R.id.cardView2 -> {
                idResume = data.id!!
            }
        }
    }
}