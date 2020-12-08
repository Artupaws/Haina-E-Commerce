package haina.ecommerce.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityLoginBinding
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.register.RegisterActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_login -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("loginStatus", "1")
                startActivity(intent)
                finishAffinity()
            }
            R.id.btn_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

}