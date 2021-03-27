package haina.ecommerce.view.detailtransaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.databinding.ActivityDetailTransactionBinding

class DetailTransactionActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}