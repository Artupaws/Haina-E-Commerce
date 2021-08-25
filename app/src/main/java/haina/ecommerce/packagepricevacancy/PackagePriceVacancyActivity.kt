package haina.ecommerce.packagepricevacancy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityPackagePriceVacancyBinding

class PackagePriceVacancyActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityPackagePriceVacancyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackagePriceVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_next -> {

            }
        }
    }
}