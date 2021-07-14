package haina.ecommerce.view.history.historymyproperty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityMyPropertyBinding

class MyPropertyActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMyPropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}