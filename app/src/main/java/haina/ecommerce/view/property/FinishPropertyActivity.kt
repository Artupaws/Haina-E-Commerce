package haina.ecommerce.view.property

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityFinishPropertyBinding
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.history.historymyproperty.MyPropertyActivity

class FinishPropertyActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityFinishPropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnToDashboard.setOnClickListener(this)
        binding.btnToMyproperty.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_to_myproperty -> {
                val intentMyProperty = Intent(applicationContext, MyPropertyActivity::class.java)
                intentMyProperty.putExtra("finish", true)
                startActivity(intentMyProperty)
                finishAffinity()
            }
            R.id.btn_to_dashboard -> {
                val intentDashboard = Intent(applicationContext, MainActivity::class.java)
                startActivity(intentDashboard)
                finishAffinity()
            }
        }
    }


}