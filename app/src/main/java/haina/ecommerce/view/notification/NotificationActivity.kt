package haina.ecommerce.view.notification

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.notification.AdapterNotification
import haina.ecommerce.adapter.notification.AdapterNotificationCategory
import haina.ecommerce.databinding.ActivityNotificationBinding
import haina.ecommerce.model.notification.DataItemNotification
import haina.ecommerce.model.notification.Notificationcategory
import haina.ecommerce.view.MainActivity
import timber.log.Timber

class NotificationActivity : AppCompatActivity(), NotificationContract, AdapterNotification.ItemAdapterCallback {

    private lateinit var binding : ActivityNotificationBinding
    private var intentParams:Boolean = false
    private lateinit var presenter:NotificationPresenter
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        broadcaster = LocalBroadcastManager.getInstance(this)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentParams = intent?.getBooleanExtra("notif", false) == true
        presenter = NotificationPresenter(this, this)
        presenter.getNotification()

        binding.toolbarNotification.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarNotification.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarNotification.title = getString(R.string.notification_title)
        refresh()
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getNotification()
        }
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "CategoryFilter" -> {
                    var data = intent.getParcelableArrayListExtra<DataItemNotification>("NotificationList" )
                    if(data.isNullOrEmpty()){
                        Timber.d("empty")
                        binding.rvNotification.visibility=View.GONE
                        binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
                    }else{
                        binding.rvNotification.visibility=View.VISIBLE
                        binding.includeEmpty.linearEmpty.visibility = View.GONE

                        binding.rvNotification.apply {
                            adapter = AdapterNotification(applicationContext, data, this@NotificationActivity)
                            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        broadcaster?.registerReceiver(mMessageReceiver, IntentFilter("CategoryFilter"))
    }

    override fun onBackPressed() {
        if (intentParams){
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } else {
            super.onBackPressed()
        }
    }

    override fun messageGetNotification(msg: String) {
        Log.d("messageNotif", msg)
        binding.swipeRefresh.isRefreshing = false
        if (!msg.contains("Success")){
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getDataNotification(data: List<Notificationcategory?>?) {
        binding.rvCategory.apply {
            adapter = AdapterNotificationCategory(applicationContext, data)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onAdapterClick(view: View, data: DataItemNotification) {
        presenter.openNotification(data.id!!)
        presenter.getNotification()
        Toast.makeText(applicationContext, data.title, Toast.LENGTH_SHORT).show()
    }
}