package haina.ecommerce.view.companycatalog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityCompanyCatalogBinding

class   CompanyCatalogActivity : AppCompatActivity(){

    private lateinit var binding: ActivityCompanyCatalogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompanyCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}