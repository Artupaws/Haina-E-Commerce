package haina.ecommerce.view.posting.newvacancy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivitySkillAndEducationBinding
import haina.ecommerce.packagepricevacancy.PackagePriceVacancyActivity

class SkillAndEducationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivitySkillAndEducationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkillAndEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener(this)

        binding.toolbarSkillEducation.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_next -> {
                startActivity(Intent(applicationContext, PackagePriceVacancyActivity::class.java))
            }
        }
    }
}