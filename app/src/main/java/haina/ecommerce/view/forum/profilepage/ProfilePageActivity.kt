package haina.ecommerce.view.forum.profilepage

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
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
import com.google.android.material.appbar.AppBarLayout

import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import haina.ecommerce.adapter.forum.TabAdapterProfilePage
import kotlin.properties.Delegates


class ProfilePageActivity : AppCompatActivity(), AdapterListMyPost.ItemAdapterCallback,
    ProfilePageContract, View.OnClickListener {

    private lateinit var binding: ActivityDetailMySubforumBinding
    private var progressDialog: Dialog? = null
    private lateinit var presenter: ProfilePagePresenter
    private var UserId by Delegates.notNull<Int>()
    private lateinit var sharedPref: SharedPreferenceHelper
    private var viewType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMySubforumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPreferenceHelper(this)
        presenter = ProfilePagePresenter(this, this)
        binding.ivImageUser.setOnClickListener(this)
        UserId = intent?.getIntExtra("idUser",0)!!

        binding.rvPost.adapter = postAdapter
        binding.toolbarForumDetail.setNavigationOnClickListener {
            onBackPressed()
        }

        presenter.getProfileData(UserId)
        dialogLoading()
//
//        viewType = if (sharedPref.getValueString(Constants.PREF_USERNAME).toString().contains(dataSubforum.creatorName.toString())){
//            2
//        } else{
//            1
//        }

        Timber.d(viewType.toString())
        binding.nestedProfile.isFillViewport = true

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

    override fun getProfileData(data: DataProfile) {

        binding.vpTransaction.adapter = TabAdapterProfilePage(supportFragmentManager, 0,UserId,applicationContext)

        binding.vpTransaction.offscreenPageLimit = 3
        binding.tabTransaction.setupWithViewPager(binding.vpTransaction)

        binding.btnFollow.visibility = View.GONE

        binding.tvNameUser.text = data.username
        binding.tvAbout.visibility = View.GONE
        binding.splitterPost.visibility = View.GONE
        binding.tvTotalFollowers.visibility = View.GONE
        binding.tvCategory.visibility = View.GONE
        Glide.with(applicationContext).load(data.photo).into(binding.ivImageUser)
        binding.tvCreated.text = "${getString(R.string.member_since)} ${data.memberSince}"
        binding.backgroundPage.setBackgroundColor(ContextCompat.getColor(this, R.color.chuck_colorAccent));


        var state:String = "Expanded"
        binding.appbarForumDetail.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                if (state !== "Expanded") {
                    state = "Expanded"
                    binding.collapsingForumDetail.title = ""

                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                if (state !== "Collapsed") {
                    state = "Collapsed"
                    binding.collapsingForumDetail.title =data.username

                }
            } else {
                if (state !== "Internediate") {
                    binding.collapsingForumDetail.title = ""
                    state = "Internediate"
                }
            }
        })
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
            }
        }
    }

}