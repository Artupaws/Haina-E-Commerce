package haina.ecommerce.view.applyjob

import android.Manifest
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterDocumentUserChoose
import haina.ecommerce.databinding.ActivityApplyJobBinding
import haina.ecommerce.model.DataDocumentUser
import haina.ecommerce.model.DataUser
import haina.ecommerce.view.myaccount.addrequirement.AddRequirementActivity
import haina.ecommerce.view.myaccount.detailaccount.DetailAccountActivity
import timber.log.Timber

class ApplyJobActivity : AppCompatActivity(), View.OnClickListener, ApplyJobContract.View {

    private lateinit var binding: ActivityApplyJobBinding
    private lateinit var presenter: ApplyJobPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var idDocumentResume:Int? = null
    var idJobVacancy:Int = 0
    val refresh:String= "1"
    private var progressDialog: Dialog? = null
    private var uriPdf: Uri? = null
    private var namePdf:String? = null

    companion object {
        //pick Pdf
        private val PICK_PDF = 21

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
        binding.tvActionReviewProfile.setOnClickListener(this)
//        uriPdf = Uri.parse(intent.getStringExtra("uriPdf"))
//        namePdf = intent.getStringExtra("namePdf")
//        Timber.d(uriPdf.toString(), namePdf)
        loadingDialog()
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
                        requestPermissions(permissions, ApplyJobActivity.PERMISSION_CODE_PDF)
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
                if (idDocumentResume == null || idJobVacancy == 0){
                    Toast.makeText(applicationContext, "Please choose 1 resume for apply this job", Toast.LENGTH_SHORT).show()
                } else {
                    presenter.applyJob(idJobVacancy, idDocumentResume!!)
                }
            }
        }
    }

    private fun pickPdf() {
        //Intent to pick pdf
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "application/pdf"
        intent = Intent.createChooser(intent, "Choose a file")
        startActivityForResult(intent, 1)
    }

    override fun onResume() {
        presenter.loadDocumentResume(1)
        Timber.d("comeback")
        super.onResume()
    }

    override fun messageGetDataPersonal(msg: String) {
        Log.d("getDataPersonal", msg)
        if (msg.contains("Success")){
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun messagetGetDocument(msg: String) {
        Log.d("getDocument", msg)
        if (msg.contains("Success")){
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun messageApplyJob(msg: String) {
        Log.d("applyJob", msg)
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

    private fun loadingDialog(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun getDocumentResume(item: List<DataDocumentUser?>?) {
        val adapterResume = AdapterDocumentUserChoose(this, item)
//        binding.rvResume.apply {
//            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
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
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}