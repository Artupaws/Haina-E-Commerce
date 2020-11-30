package haina.ecommerce.view.other

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityOtherBinding

class OtherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarOther.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarOther.title = "All Categories"
        binding.toolbarOther.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}