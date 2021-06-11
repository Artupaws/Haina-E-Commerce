package haina.ecommerce.view.notification

import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.notification.AdapterNotification
import haina.ecommerce.databinding.ActivityNotificationBinding
import haina.ecommerce.model.notification.DataItemNotification
import haina.ecommerce.view.MainActivity

class NotificationActivity : AppCompatActivity(), NotificationContract, AdapterNotification.ItemAdapterCallback {

    private lateinit var binding : ActivityNotificationBinding
    private var intentParams:Boolean = false
    private lateinit var presenter:NotificationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentParams = intent?.getBooleanExtra("notif", false) == true
        presenter = NotificationPresenter(this, this)
        presenter.getNotification()

        binding.toolbarNotification.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarNotification.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarNotification.title = "Notification"
        refresh()
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getNotification()
        }
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

    override fun getDataNotification(data: List<DataItemNotification?>?) {
        binding.rvNotification.apply {
            adapter = AdapterNotification(applicationContext, data, this@NotificationActivity)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onAdapterClick(view: View, data: DataItemNotification) {
        Toast.makeText(applicationContext, data.title, Toast.LENGTH_SHORT).show()
    }
}