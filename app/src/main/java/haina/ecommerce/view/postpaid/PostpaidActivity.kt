package haina.ecommerce.view.postpaid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityPostpaidBinding

class PostpaidActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPostpaidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostpaidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarPostpaid.title = "Pascabayar"
        binding.toolbarPostpaid.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarPostpaid.setNavigationOnClickListener { onBackPressed() }
    }
}