package haina.ecommerce.view.forum.profileuser

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityProfileUserBinding
import haina.ecommerce.helper.Helper.dateFormat
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.model.forum.SubforumData
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.forum.detailforum.DetailForumContract
import timber.log.Timber

class ProfileUserActivity : AppCompatActivity(), View.OnClickListener, DetailForumContract.ViewProfileSubforum {

    private lateinit var binding:ActivityProfileUserBinding
    private var progressDialog:Dialog? = null
    private lateinit var presenter: ProfileUserPresenter
    private lateinit var subforumData: SubforumData
    private lateinit var sharedPref:SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserBinding.inflate(layoutInflater)
        sharedPref = SharedPreferenceHelper(this)
        setContentView(binding.root)
        presenter = ProfileUserPresenter(this, this)
        binding.toolbarProfileUser.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnFollow.setOnClickListener(this)
        binding.btnUnfollow.setOnClickListener(this)
        dialogLoading()
        subforumData = intent?.getParcelableExtra("dataSubforum")!!
        showDataProfileSubforum(subforumData)
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun showDataProfileSubforum(data: SubforumData){
        binding.tvNameUser.text = data.name
        binding.tvPost.text = data.totalPost.toString()
        binding.tvFollowers.text = data.totalFollowers.toString()
        Glide.with(this).load(data.subforumImage).into(binding.ivImageUser)
        binding.tvCategory.text = if (sharedPref.getValueString(Constants.LANGUAGE_APP) == "en")"${getString(R.string.category)} : ${data.category}" else "${getString(R.string.category)} : ${data.categoryZh}"
        binding.tvCreatedAt.text = "${getString(R.string.created_at)} : ${dateFormat(data.createdAt)}"
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_follow -> {
                binding.btnFollow.visibility = View.GONE
                binding.btnUnfollow.visibility = View.VISIBLE
                subforumData.id?.let { presenter.followSubforum(it) }
            }
            R.id.btn_unfollow -> {
                binding.btnFollow.visibility = View.VISIBLE
                binding.btnUnfollow.visibility = View.GONE
                subforumData.id?.let { presenter.unfollowSubforum(it) }
            }
        }
    }

    override fun messageFollowSubforum(msg: String) {
        Timber.d(msg)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}