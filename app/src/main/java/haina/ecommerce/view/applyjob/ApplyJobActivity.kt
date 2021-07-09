package haina.ecommerce.view.applyjob

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

class ApplyJobActivity : AppCompatActivity(), View.OnClickListener, ApplyJobContract {

    private lateinit var binding: ActivityApplyJobBinding
    private lateinit var presenter: ApplyJobPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var idDocumentResume:Int? = null
    var idJobVacancy:Int = 0
    val refresh:String?= "1"

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
        binding.tvTitleJob.text = binding.toolbarApplyJob.title
        idJobVacancy = intent.getIntExtra("idJobVacancy", 0)
        binding.btnSubmit.setOnClickListener(this)
        binding.tvAddResume.setOnClickListener(this)
        binding.tvActionReviewProfile.setOnClickListener(this)

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
                val intent = Intent(applicationContext, AddRequirementActivity::class.java)
                    .putExtra("title", "Add Resume")
                startActivity(intent)
            }
            R.id.btn_submit -> {
                if (idDocumentResume == null || idJobVacancy == null){
                    Toast.makeText(applicationContext, "Please choose 1 resume for apply this job", Toast.LENGTH_SHORT).show()
                } else {
                    presenter.applyJob(idJobVacancy, idDocumentResume!!)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("idDocument"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action){
                "idDocument" -> {
                    val fromIntent = intent.getIntExtra("resume", 0)
                    idDocumentResume = fromIntent
                    Log.d("idDocument", idDocumentResume.toString())
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadDocumentResume(1)
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

    override fun getDocumentResume(item: List<DataDocumentUser?>?) {
        val adapterResume = AdapterDocumentUserChoose(this, item)
        binding.rvResume.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterResume
            adapterResume.notifyDataSetChanged()
        }
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
}