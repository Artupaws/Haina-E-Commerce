package haina.ecommerce.view.forum.detailforum

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import haina.ecommerce.helper.Helper
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterDetailImage
import haina.ecommerce.adapter.forum.AdapterListComment
import haina.ecommerce.databinding.*
import haina.ecommerce.model.forum.DataComment
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.model.forum.ImagesItem
import haina.ecommerce.model.forum.SubforumData
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.forum.profilepage.ProfilePageActivity
import haina.ecommerce.view.forum.profileuser.ProfileUserActivity
import haina.ecommerce.view.forum.tab.myforum.DetailMySubforum
import timber.log.Timber

class DetailForumActivity : AppCompatActivity(), DetailForumContract.View,
    AdapterListComment.ItemAdapterCallback, View.OnClickListener {

    private lateinit var binding: ActivityDetailForumBinding
    private lateinit var listParams: ArrayList<String>
    private lateinit var imagesListener: ImageListener
    private lateinit var presenter: DetailForumPresenter
    private lateinit var sharedPref: SharedPreferenceHelper
    private var postId: Int = 0
    private var comment: String = ""
    private var idUser:Int = 0
    private var subforumId:Int = 0
    private lateinit var dataForum: DataItemHotPost
    private var progressDialog:Dialog? = null
    private var broadcaster:LocalBroadcastManager? = null
    private var helper:Helper = Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPreferenceHelper(this)
        listParams = ArrayList()
        presenter = DetailForumPresenter(this, this)
        binding.ivActionSend.setOnClickListener(this)
        binding.ivImageSubforum.setOnClickListener(this)
        binding.btnFollow.setOnClickListener(this)
        binding.btnUnfollow.setOnClickListener(this)
        broadcaster = LocalBroadcastManager.getInstance(this)

        dataForum = intent?.getParcelableExtra("dataDetail")!!
        idUser = dataForum.userId!!
        postId = dataForum.id!!
        subforumId = dataForum.subforumData?.id!!
        showDataForum(dataForum.subforumData!!, dataForum)
        binding.toolbar9.setNavigationOnClickListener {
            onBackPressed()
        }
        dialogLoading()
        binding.etComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    if (s.isNotEmpty()) {
                        binding.ivActionSend.isClickable = true
                        binding.ivActionSend.isEnabled = true
                        comment = s.toString()
                    }
                } else {
                    binding.ivActionSend.isClickable = false
                    binding.ivActionSend.isEnabled = false
                }
            }

        })
        binding.rvComment.adapter = adapterComment
    }

    private fun showDataForum(subforumData: SubforumData, data:DataItemHotPost) {
//        Glide.with(applicationContext).load(data.authorPhoto).into(binding.ivImageAuthor)
        Timber.d(data.toString())
        binding.tvTitleForum.text = data.title
        val memberSince = "Member since : "+data.memberSince
        binding.tvMemberSince.text = memberSince

        binding.tvDate.text = helper.dateTimeFormat(data.createdAt)
        binding.tvNameUser.text = data.author
        binding.tvContent.text = data.content?.replace("\\n", "\n")

        Glide.with(applicationContext).load(data.authorPhoto).into(binding.ivImageProfile)

        binding.tvNameSubforum.text = subforumData.name
        Glide.with(applicationContext).load(subforumData.subforumImage).into(binding.ivImageSubforum)
        val categorySubforum = if (sharedPref.getValueString(Constants.LANGUAGE_APP) == "en")"${getString(R.string.category)} : ${subforumData.category}"
        else "${getString(R.string.category)} : ${subforumData.categoryZh}"
        binding.tvCategory.text = categorySubforum
        if (data.subforumFollow == true) binding.btnUnfollow.visibility = View.VISIBLE else binding.btnUnfollow.visibility = View.GONE
        data.id?.let {
            presenter.getPostDetail(it)
            presenter.getListComment(it)
        }

        binding.ivImageProfile.setOnClickListener {
            startActivity(
                Intent(applicationContext, ProfilePageActivity::class.java)
                    .putExtra("idUser", data.userId)
            )
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageGetComment(msg: String) {
        Timber.d(msg)
    }

    override fun messageNewComment(msg: String) {
        Timber.d(msg)
        if (!msg.contains("Post Comment Success!")) {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        } else {
            presenter.getListComment(postId)
        }
    }

    override fun messageFollowSubforum(msg: String) {
        presenter.getListComment(postId)
        Timber.d(msg)
    }

    override fun messageDeleteComment(msg: String) {
        if (msg.contains("Comment deleted successfully!")) presenter.getListComment(postId)
        Timber.d(msg)
    }

    override fun messageAssignSubMod(msg: String) {
        if (msg.contains("Assign New Mod Success!")) presenter.getListComment(postId)
        Timber.d(msg)
    }

    override fun getListComment(data: List<DataComment?>?) {
        adapterComment.clear()
        adapterComment.add(data)
        binding.tvCommentCount.text = data?.size.toString()
    }

    fun detailPhoto(listImage: List<ImagesItem?>?, position: Int){
        startActivity(
            AdapterDetailImage.createIntent(
                applicationContext,
                listImage!!,
                position
            )
        )
    }
    override fun getPostDetail(data: DataItemHotPost) {

        binding.tvTitleForum.text = data.title
        if(!data.images.isNullOrEmpty()){
            binding.llImageForum.removeAllViewsInLayout()
            val inflater = LayoutInflater.from(applicationContext)

            when(data.images.size){
                1 -> {
                    val imageview = LayoutOneImageForumBinding.inflate(inflater)
                    Glide.with(applicationContext).load(data.images?.get(0)!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                    imageview.imageView1.setOnClickListener {
                        detailPhoto(data.images,0)
                    }
                    binding.llImageForum.addView(imageview.root)
                }
                2 -> {
                    val imageview = LayoutTwoImageForumBinding.inflate(inflater)
                    Glide.with(applicationContext).load(data.images?.get(0)!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                    Glide.with(applicationContext).load(data.images[1]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView2)
                    imageview.imageView1.setOnClickListener {
                        detailPhoto(data.images,0)
                    }
                    imageview.imageView2.setOnClickListener {
                        detailPhoto(data.images,1)
                    }
                    binding.llImageForum.addView(imageview.root)
                }

                3 -> {
                    val imageview = LayoutThreeImageForumBinding.inflate(inflater)
                    Glide.with(applicationContext).load(data.images?.get(0)!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                    Glide.with(applicationContext).load(data.images[1]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView2)
                    Glide.with(applicationContext).load(data.images[2]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView3)
                    imageview.imageView1.setOnClickListener {
                        detailPhoto(data.images,0)
                    }
                    imageview.imageView2.setOnClickListener {
                        detailPhoto(data.images,1)
                    }
                    imageview.imageView3.setOnClickListener {
                        detailPhoto(data.images,2)
                    }
                    binding.llImageForum.addView(imageview.root)
                }

                4 -> {
                    val imageview = LayoutFourImageForumBinding.inflate(inflater)
                    Glide.with(applicationContext).load(data.images?.get(0)!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView1)
                    Glide.with(applicationContext).load(data.images[1]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView2)
                    Glide.with(applicationContext).load(data.images[2]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView3)
                    Glide.with(applicationContext).load(data.images[3]!!.path).placeholder(R.drawable.skeleton_image).into(imageview.imageView4)
                    imageview.imageView1.setOnClickListener {
                        detailPhoto(data.images,0)
                    }
                    imageview.imageView2.setOnClickListener {
                        detailPhoto(data.images,1)
                    }
                    imageview.imageView3.setOnClickListener {
                        detailPhoto(data.images,2)
                    }
                    imageview.imageView4.setOnClickListener {
                        detailPhoto(data.images,3)
                    }
                    binding.llImageForum.addView(imageview.root)
                }
            }
        }
    }

    override fun showLoading() {
        progressDialog?.show()
        binding.includeLoading.clLoading.visibility = View.VISIBLE
        binding.rvComment.visibility = View.GONE
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
        binding.includeLoading.clLoading.visibility = View.GONE
        binding.rvComment.visibility = View.VISIBLE
    }

    override fun listCommentClick(view: View, listComment: java.util.ArrayList<DataComment?>?, adapterPosition: Int, data: DataComment) {
        when(view.id){
            R.id.tv_option_menu -> {
                val popup = PopupMenu(this, view)
                if (!data.mod?.contains("none")!!){
                    if(data.mod?.equals("banned")){
                        popup.inflate(R.menu.menu_option_comment)
                    }else{
                        popup.inflate(R.menu.menu_option_comment)
                    }
                } else{
                    popup.inflate(R.menu.menu_option_comment_nonmod)
                }

                popup.setOnMenuItemClickListener { item ->
                    when(item.itemId){
                        R.id.add_as_submod -> {
                            Timber.d("$subforumId ${data.userId} submod")
                            data.userId?.let { presenter.assignSubMod(subforumId, it, "submod") }
                        }
                        R.id.delete_comment -> {
                            Timber.d("delete comment")
                            data.id?.let { presenter.deleteComment(it) }
                            listComment?.removeAt(adapterPosition)
                            adapterComment.notifyItemRemoved(adapterPosition)
                        }
                    }
                    true
                }
                popup.show()
            }

            R.id.iv_image_user -> {
                startActivity(
                    Intent(applicationContext, ProfilePageActivity::class.java)
                        .putExtra("idUser", data.userId)
                )
            }
        }
    }

    private val adapterComment by lazy {
        AdapterListComment(applicationContext, arrayListOf(), this)
    }

    override fun onStart() {
        super.onStart()
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("statusFollow"))
    }

    override fun onStop() {
        super.onStop()
        broadcaster?.unregisterReceiver(mMessageReceiver)
    }

    private val mMessageReceiver : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "statusFollow" -> {
                    val status = intent.getBooleanExtra("status", false)
                    Timber.d(status.toString())
                    if (status) binding.btnUnfollow.visibility = View.VISIBLE else binding.btnUnfollow.visibility = View.GONE
                }
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_action_send -> {
                when (comment) {
                    "" -> {
                        Toast.makeText(applicationContext, "Input your comment first!", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        presenter.newComment(postId, comment)
                        binding.etComment.text.clear()
                    }
                }
            }
            R.id.iv_image_subforum -> {
                val intent = Intent(applicationContext, DetailMySubforum::class.java)
                intent.putExtra("dataDetail", dataForum.subforumData)
                startActivity(intent)
            }
            R.id.btn_follow -> {
                binding.btnFollow.visibility = View.GONE
                binding.btnUnfollow.visibility = View.VISIBLE
                presenter.followSubforum(subforumId)
                dataForum.subforumFollow = true
            }
            R.id.btn_unfollow -> {
                binding.btnFollow.visibility = View.VISIBLE
                binding.btnUnfollow.visibility = View.GONE
                presenter.unfollowSubforum(subforumId)
                dataForum.subforumFollow = false
            }
        }
    }
}