package haina.ecommerce.view.forum.tab.myforum

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
import haina.ecommerce.databinding.ActivityDetailMySubforumBinding


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
        binding.toolbarForumDetail.setNavigationOnClickListener {
            onBackPressed()
        }
        presenter.getSubforumData(dataSubforum.id!!)
        dialogLoading()
        viewType = if (sharedPref.getValueString(Constants.PREF_USERNAME).toString().contains(dataSubforum.creatorName.toString())){
            2
        } else{
            1
        }
        Timber.d(viewType.toString())
        binding.nestedProfile.isFillViewport = true



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
                    binding.collapsingForumDetail.title = dataSubforum.name

                }
            } else {
                if (state !== "Internediate") {
                    binding.collapsingForumDetail.title = ""
                    state = "Internediate"
                }
            }
        })

    }

    @SuppressLint("SetTextI18n")
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

        if(data.following == null ){
            binding.btnFollow.setBackgroundColor(resources.getColor(R.color.yellow))
            binding.btnFollow.text = "Follow"
        }else{
            if(data.role == "mod"||data.role == "submod"){

                binding.btnFollow.setBackgroundColor(Color.WHITE)
                binding.btnFollow.text = "Edit Forum Detail"
                binding.btnFollow.setOnClickListener {

                }
            } else{
                if(data.following!!){
                    binding.btnFollow.setBackgroundColor(Color.WHITE)
                    binding.btnFollow.text = "Unfollow"
                    binding.btnFollow.setOnClickListener {
                        presenter.unfollowSubforum(data.subforumId!!)
                    }
                }else{
                    binding.btnFollow.setBackgroundColor(resources.getColor(R.color.yellow))
                    binding.btnFollow.text = "Follow"

                    binding.btnFollow.setOnClickListener {
                        presenter.followSubforum(data.subforumId!!)
                    }
                }
            }
        }

        binding.tvNameUser.text = data.subforumName
        binding.tvAbout.text = data.description
        binding.tvTotalFollowers.text = "${data.followersCount} Followers"
        Glide.with(applicationContext).load(data.image).into(binding.ivImageUser)


    }

    override fun messageFollowSubforum(msg: String) {
        presenter.getSubforumData(dataSubforum.id!!)
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