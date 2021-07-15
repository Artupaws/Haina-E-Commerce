package haina.ecommerce.view.property

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.databinding.ActivityShowPropertyBinding

class ShowPropertyActivity : AppCompatActivity() {

    private lateinit var binding:ActivityShowPropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}