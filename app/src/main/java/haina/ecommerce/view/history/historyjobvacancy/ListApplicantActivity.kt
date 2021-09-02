package haina.ecommerce.view.history.historyjobvacancy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import haina.ecommerce.databinding.ActivityListApplicantBinding

class ListApplicantActivity : AppCompatActivity() {

    private lateinit var binding:ActivityListApplicantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListApplicantBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}