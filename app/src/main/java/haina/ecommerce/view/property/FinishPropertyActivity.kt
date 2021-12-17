package haina.ecommerce.view.property

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityFinishPropertyBinding
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.history.historyjobvacancy.MyVacancyActivity
import haina.ecommerce.view.history.historymyproperty.MyPropertyActivity

class FinishPropertyActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityFinishPropertyBinding
    private var endProgress:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnToDashboard.setOnClickListener(this)
        binding.btnActionGoto.setOnClickListener(this)
        endProgress = intent?.getStringExtra("finish")
        if (endProgress == "vacancy"){
            binding.tvTitleSuccess.text = getString(R.string.vacancy_post_success)
            binding.tvTitleDescriptionSuccess.text = getString(R.string.see_new_vacancy)
            binding.btnActionGoto.text = getString(R.string.go_to_my_vacancy)
        } else if (endProgress == "property"){
            binding.tvTitleSuccess.text = getString(R.string.property_post_success)
            binding.tvTitleDescriptionSuccess.text = getString(R.string.see_at_property)
            binding.btnActionGoto.text = getString(R.string.go_to_my_property)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_action_goto -> {
                when(endProgress){
                    "property" -> {
                        startActivity(Intent(applicationContext, MyPropertyActivity::class.java).putExtra("finish", true))
                        finishAffinity()
                    }
                    "vacancy" -> {
                        startActivity(Intent(applicationContext, MyVacancyActivity::class.java))
                    }
                }
            }
            R.id.btn_to_dashboard -> {
                val intentDashboard = Intent(applicationContext, MainActivity::class.java)
                startActivity(intentDashboard)
                finishAffinity()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(applicationContext,  MainActivity::class.java))
        finishAffinity()
    }


}