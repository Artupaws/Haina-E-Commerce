package haina.ecommerce.view.register.company

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.databinding.ActivityRegisterCompanyBinding

class RegisterCompany : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterCompanyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}