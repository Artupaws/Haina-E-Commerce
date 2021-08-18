package haina.ecommerce.view.forum.forumactivity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterListBan
import haina.ecommerce.adapter.forum.AdapterListRoleMod
import haina.ecommerce.databinding.ActivityForumBinding
import haina.ecommerce.model.forum.DataItemBan
import haina.ecommerce.model.forum.DataModList
import haina.ecommerce.model.forum.ModListItem
import haina.ecommerce.model.forum.SubforumData
import haina.ecommerce.view.forum.tab.myforum.DetailMySubforum
import timber.log.Timber

class ActivityForumActivity : AppCompatActivity(), ActivityForumContract.View,
    AdapterListRoleMod.ItemAdapterCallback, View.OnClickListener, AdapterListBan.ItemAdapterCallback {

    private lateinit var binding:ActivityForumBinding
    private var progressDialog:Dialog? = null
    private lateinit var presenter: ActivityForumPresenter

    private val positionCollaps: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_collapse)
    }

    private val positionExpand: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.anim_expand)
    }
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.relativeMyBan.setOnClickListener(this)
        binding.relativeModRole.setOnClickListener(this)
        presenter = ActivityForumPresenter(this,applicationContext)
        binding.rvModRoles.adapter = adapterListRoleMod
        binding.rvMyBans.adapter = adapterListBan
        binding.toolbar11.setNavigationOnClickListener {
            onBackPressed()
        }
        presenter.getListRoleMod()
        presenter.getListBan()
        refresh()
        dialogLoading()
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun onAddPostClicked(typeClicked: Int) {
        Timber.d(typeClicked.toString())
        setVisibility(clicked, typeClicked)
        setAnimation(clicked, typeClicked)
    }

    private fun setAnimation(clicked:Boolean, typeClicked:Int){
        if (!clicked && typeClicked == 1){
            binding.ivArrowAllThreads.startAnimation(positionExpand)
        } else if (clicked && typeClicked == 1){
            binding.ivArrowAllThreads.startAnimation(positionCollaps)
        }

        if (!clicked && typeClicked == 2){
            binding.ivArrowMyBans.startAnimation(positionExpand)
        } else if (clicked && typeClicked == 2){
            binding.ivArrowMyBans.startAnimation(positionCollaps)
        }
    }

    private fun setVisibility(clicked: Boolean, typeClicked: Int){
        if (clicked && typeClicked == 1){
            binding.rvModRoles.visibility = View.GONE
        } else if (!clicked && typeClicked == 1){
            binding.rvModRoles.visibility = View.VISIBLE
        }

        if (clicked && typeClicked == 2){
            binding.rvMyBans.visibility = View.GONE
        } else if (!clicked && typeClicked == 2){
            binding.rvMyBans.visibility = View.VISIBLE
        }
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getListRoleMod()
            presenter.getListBan()
        }
    }

    override fun messageGetListMod(msg: String) {
        Timber.d(msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun messageGetListBan(msg: String) {
        Timber.d(msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getDataListMod(data:List<ModListItem?>?) {
        adapterListRoleMod.clear()
        adapterListRoleMod.add(data)
        binding.tvTotalModRole.text = data?.size.toString()
    }

    override fun getDataListBan(data: List<DataItemBan?>?) {
        adapterListBan.clear()
        adapterListBan.add(data)
        binding.tvTotalBan.text = data?.size.toString()
    }

    private val adapterListBan by lazy {
        AdapterListBan(applicationContext, arrayListOf(), this)
    }

    private val adapterListRoleMod by lazy {
        AdapterListRoleMod(applicationContext, arrayListOf(), this)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun listModRoleClick(view: View, data: ModListItem) {
//        val dataDetail = SubforumData(data.subforumImage, data.categoryId, data.updatedAt,
//            data.categoryZh, data.name, data.creatorId, data.description!!, data.createdAt, data.id,
//            null, data.totalPoster, null, data.category!!, data.posts!!
//        )
//        when(view.id){
//            R.id.cv_click -> {
//                startActivity(Intent(applicationContext, DetailMySubforum::class.java)
//                    .putExtra("dataDetail", dataDetail))
//            }
//        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.relative_mod_role -> {
                onAddPostClicked(1)
                clicked = !clicked
            }
            R.id.relative_my_ban -> {
                onAddPostClicked(2)
                clicked = !clicked
            }
        }
    }

    override fun listBanClick(view: View, data: DataItemBan) {
//        val dataDetail = SubforumData(data.subforumImage, data.categoryId, data.updatedAt,
//            data.categoryZh, data.name, data.creatorId, data.description!!, data.createdAt, data.id!!,
//            null, data.totalPoster, null, data.category!!, data.posts!!
//        )
//        when(view.id){
//            R.id.cv_click -> {
//                startActivity(Intent(applicationContext, DetailMySubforum::class.java)
//                    .putExtra("dataDetail", dataDetail))
//            }
//        }
    }
}