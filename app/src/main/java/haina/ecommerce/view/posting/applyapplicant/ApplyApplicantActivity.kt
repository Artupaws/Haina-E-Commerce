package haina.ecommerce.view.posting.applyapplicant

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityApplyApplicantBinding
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.model.JobapplicantItem
import haina.ecommerce.model.StatusApplicant
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.webview.WebViewActivity

class ApplyApplicantActivity : AppCompatActivity(), View.OnClickListener, ApplyApplicantContract {

    private lateinit var binding: ActivityApplyApplicantBinding
    private val statusShortlist:String = "shortlisted"
    private val statusDeclined:String = "declined"
    private var idApplicant:Int = 0
    private var docsUrl:String? = null
    private var popupDeclined: AlertDialog? = null
    private lateinit var presenter: ApplyApplicantPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplyApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarApplyApplicant.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarApplyApplicant.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarApplyApplicant.title = "Apply Applicant"
        binding.btnShortList.setOnClickListener(this)
        presenter = ApplyApplicantPresenter(this, this)
        popupDeclineApplicant()
        stateButtonLoading()
        val item  = intent.getParcelableExtra<JobapplicantItem>("dataApplicant")
        idApplicant = item?.id!!
        presenter.getStatusApplicant(idApplicant)
        docsUrl = item.userDocument?.docsUrl
        binding.tvFullname.text = item.fullname
        binding.tvEmail.text = item.email
        binding.tvPhone.text = item.phone
        binding.tvGender.text = item.gender
        binding.tvAbout.text = item.about
        binding.tvTitleDocument.text = item.userDocument?.docsName
        Glide.with(this).load(item.photo).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.NONE).listener(object : RequestListener<Drawable>{
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.progressCircular.visibility = View.INVISIBLE
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                binding.progressCircular.visibility = View.INVISIBLE
                return false
            }
        }).into(binding.ivApplicant)
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
        binding.btnDecline.setOnClickListener(this)
        binding.cvDocument.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_short_list -> {
                stateButtonLoading()
                presenter.addShortlistApplicant(idApplicant, statusShortlist)
            }
            R.id.btn_decline ->{
                popupDeclined?.show()
            }
            R.id.cv_document ->{
                val intent = Intent(applicationContext, WebViewActivity::class.java)
                intent.putExtra("url", docsUrl)
                intent.putExtra("intentFrom", "document")
                startActivity(intent)
            }
        }
    }

    override fun messageAddShortlistApplicant(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        if (msg.contains("Update")){
            stateFinishLoading()
            presenter.getStatusApplicant(idApplicant)
        }
    }

    override fun messageDeclineApplicant(msg: String) {
        if (msg.contains("Update")){
            stateFinishLoading()
            presenter.getStatusApplicant(idApplicant)
        }
    }

    override fun messageGetApplicantStatus(msg: String) {
        if (msg.contains("Success")){
            stateFinishLoading()
        } else {
            stateFinishLoading()
        }
    }

    override fun getApplicantStatus(item: StatusApplicant?) {
        binding.tvApplicationStatus.text = item?.status
        if (binding.tvApplicationStatus.text != "pending"){
            binding.btnShortList.isEnabled = false
            binding.btnDecline.isEnabled = false
        }
    }

    private fun popupDeclineApplicant(){
        val popup = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.popup_logout, null)
        popup.setCancelable(false)
        popup.setView(view)
        val actionCancel = view.findViewById<TextView>(R.id.tv_action_cancel)
        val actionYes = view.findViewById<TextView>(R.id.tv_action_yes)
        val title = view.findViewById<TextView>(R.id.tv_title)
        val message = view.findViewById<TextView>(R.id.rv_popup)
        popupDeclined = popup.create()
        title.text = "Decline"
        message.text = "Are you sure want to decline this applicant ?"
        actionCancel.setOnClickListener { popupDeclined?.dismiss() }
        actionYes.setOnClickListener {
            popupDeclined?.dismiss()
            stateButtonLoading()
            presenter.declinedApplicant(idApplicant, statusDeclined)
        }
    }

    private fun stateButtonLoading(){
        binding.btnDecline.visibility = View.INVISIBLE
        binding.btnShortList.visibility = View.INVISIBLE
        binding.ivLoadingShortlist.visibility = View.VISIBLE
        binding.ivLoadingDecline.visibility = View.VISIBLE
    }

    private fun stateFinishLoading(){
        binding.btnDecline.visibility = View.VISIBLE
        binding.btnShortList.visibility = View.VISIBLE
        binding.ivLoadingShortlist.visibility = View.INVISIBLE
        binding.ivLoadingDecline.visibility = View.INVISIBLE
    }

}