package haina.ecommerce.view.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarNotification.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarNotification.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarNotification.title = "Notification"



    }
}