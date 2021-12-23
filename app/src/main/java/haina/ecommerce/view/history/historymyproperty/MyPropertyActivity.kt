package haina.ecommerce.view.history.historymyproperty

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.readystatesoftware.chuck.internal.ui.MainActivity
import haina.ecommerce.R
import haina.ecommerce.adapter.property.TabAdapterMyProperty
import haina.ecommerce.databinding.ActivityMyPropertyBinding
import haina.ecommerce.model.property.DataShowProperty
import java.util.ArrayList

class MyPropertyActivity : AppCompatActivity(), MyPropertyContract.View {

    private lateinit var binding:ActivityMyPropertyBinding
    private lateinit var presenter: MyPropertyPresenter
    private var progressDialog:Dialog? = null
    private var broadcaster: LocalBroadcastManager? = null
    private var intentFromFinish:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MyPropertyPresenter(this, this)
        broadcaster = LocalBroadcastManager.getInstance(this)
        intentFromFinish = intent.getBooleanExtra("finish", false)
        binding.toolbarMyproperty.setNavigationOnClickListener { onBackPressed() }
        binding.vpPmyproperty.apply {
            adapter = TabAdapterMyProperty(supportFragmentManager, 0, context)
            offscreenPageLimit = 2
        }
        binding.tabMyproperty.setupWithViewPager(binding.vpPmyproperty)
        presenter.getShowProperty()
        dialogLoading()
        refresh()
    }

    override fun onResume() {
        super.onResume()
        presenter.getShowProperty()
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getShowProperty()
        }
    }

    override fun messageGetListProperty(msg: String) {
        Log.d("getMyProperty", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getDataProperty(data: List<DataShowProperty?>?) {
        val intentTransactionUnfinish = Intent("MyProperty")
            .putParcelableArrayListExtra("Ads", data as ArrayList<DataShowProperty>)
        broadcaster?.sendBroadcast(intentTransactionUnfinish)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onBackPressed() {
        if (!intentFromFinish){
            super.onBackPressed()
        } else if(intentFromFinish){
            val intentDashboard = Intent(applicationContext, haina.ecommerce.view.MainActivity::class.java)
            startActivity(intentDashboard)
            finishAffinity()
        }
    }
}