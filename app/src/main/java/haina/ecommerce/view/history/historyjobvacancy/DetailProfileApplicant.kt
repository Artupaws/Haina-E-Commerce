package haina.ecommerce.view.history.historyjobvacancy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.databinding.ActivityDetailProfileApplicantBinding

class DetailProfileApplicant : AppCompatActivity() {

    private lateinit var binding:ActivityDetailProfileApplicantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}