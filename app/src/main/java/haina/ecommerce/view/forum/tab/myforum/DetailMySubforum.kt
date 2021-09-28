package haina.ecommerce.view.forum.tab.myforum

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterListMyPost
import haina.ecommerce.adapter.forum.TabAdapterForum
import haina.ecommerce.adapter.forum.TabAdapterForumDetail
import haina.ecommerce.databinding.ActivityDetailMySubforumBinding
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.forum.*
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.forum.detailforum.DetailForumActivity
import haina.ecommerce.view.forum.profileuser.ProfileUserActivity
import haina.ecommerce.view.forum.tab.showforum.ShowForumContract
import timber.log.Timber
import java.util.ArrayList

class DetailMySubforum : AppCompatActivity(), AdapterListMyPost.ItemAdapterCallback,
    ShowForumContract.ViewDetailMySubforum, View.OnClickListener {

    private lateinit var binding: ActivityDetailMySubforumBinding
    private var progressDialog: Dialog? = null
    private lateinit var presenter: DetailMySubforumPresenter
    private lateinit var dataSubforum: SubforumData
    private lateinit var sharedPref: SharedPreferenceHelper
    private var viewType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMySubforumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPreferenceHelper(this)
        presenter = DetailMySubforumPresenter(this, this)
        binding.ivImageUser.setOnClickListener(this)
        dataSubforum = intent?.getParcelableExtra("dataDetail")!!
        showData(dataSubforum)
        binding.rvPost.adapter = postAdapter
//        binding.btnBack.setOnClickListener {
//            onBackPressed()
//        }
        presenter.getSubforumData(dataSubforum.id!!)
        dialogLoading()
        viewType = if (sharedPref.getValueString(Constants.PREF_USERNAME).toString().contains(dataSubforum.creatorName.toString())){
            2
        } else{
            1
        }
        Timber.d(viewType.toString())
        binding.nestedProfile.isFillViewport = true

    }

    private fun showData(data: SubforumData) {
        binding.tvNameUser.text = data.name
        binding.tvCategory.text = data.category
        binding.tvCreated.text = "Created At : ${dateFormat(data.createdAt)}"
        binding.tvAbout.text = data.description
        binding.tvTotalFollowers.text = "${data.totalFollowers} Followers"
        Glide.with(applicationContext).load(data.subforumImage).into(binding.ivImageUser)
//        presenter.getListForumPost(data.id!!,page)
    }

    private fun dialogLoading() {
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                android.R.color.white
            )
        )
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private val postAdapter by lazy {
        AdapterListMyPost(applicationContext, arrayListOf(), this, viewType)
    }

    override fun listMyPostClick(view: View, isChecked: Boolean, data: ThreadsItem) {
        when (view.id) {
            R.id.iv_upvote -> {
                if (isChecked) {
                    data.id?.let { presenter.giveUpvote(it) }
                } else {
                    data.id?.let { presenter.removeUpvote(it) }
                }
            }
            R.id.relative_click -> {
                val intentDetail = Intent(applicationContext, DetailForumActivity::class.java)
                intentDetail.putExtra("dataDetail", data)
                startActivity(intentDetail)
            }
        }
    }

    override fun deleteListMyPost(view: View, data: ArrayList<ThreadsItem?>?, adapterPosition: Int, postId: Int) {
    }

    override fun messageGiveUpvote(msg: String) {
        Timber.d(msg)
    }

    override fun getListPost(data: DataAllThreads) {
        postAdapter.clear()
        postAdapter.add(data.threads)
    }

    override fun messageListPost(msg: String) {
        Timber.d(msg)
    }

    override fun getSubforumData(data: SubforumEngagement) {

        binding.vpTransaction.adapter = TabAdapterForumDetail(supportFragmentManager, 0,dataSubforum.id!!, data)

        binding.vpTransaction.offscreenPageLimit = 3
        binding.tabTransaction.setupWithViewPager(binding.vpTransaction)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_image_user -> {
                startActivity(
                    Intent(applicationContext, ProfileUserActivity::class.java)
                        .putExtra("dataSubforum", dataSubforum)
                )
            }
        }
    }

}