package haina.ecommerce.view.internetandtv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityInternetBinding

class InternetActivity : AppCompatActivity() {

    private lateinit var binding:ActivityInternetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarInternet.title = "Internet and TV"
        binding.toolbarInternet.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarInternet.setNavigationOnClickListener { onBackPressed() }
    }
}