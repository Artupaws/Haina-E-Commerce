package haina.ecommerce.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityLoginBinding
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.register.RegisterActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginContract {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var isEmailEmpty = true
    private var isPasswordEmpty = true
    private var isDeviceTokenEmpty = true
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        presenter = LoginPresenter(this)

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        sharedPreferenceHelper = SharedPreferenceHelper(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_login -> {
                binding.btnLogin.visibility = View.INVISIBLE
                binding.relativeLoading.visibility = View.VISIBLE
                binding.btnRegister.isEnabled = false
                binding.btnLoginGoogle.isEnabled= false
                checkLogin()
            }
            R.id.btn_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun checkLogin(){
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()
        val deviceToken = sharedPreferenceHelper.getValueString(Constants.PREF_TOKEN_FIREBASE).toString()

        if (email.isEmpty()){
            binding.outlinedTextFieldEmail.error = "Email can't empty"
            isEmailEmpty = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.outlinedTextFieldEmail.error = "Email not valid"
            isEmailEmpty = true
        } else {
            email = binding.etEmail.text.toString()
            isEmailEmpty = false
        }

        if (password.isEmpty()){
            binding.outlinedTextFieldPassword.error = "Password can't empty"
            isPasswordEmpty = true
        } else {
            password = binding.etPassword.text.toString()
            isPasswordEmpty = false
        }

        isDeviceTokenEmpty = deviceToken.equals(null)

        if (!isEmailEmpty && !isPasswordEmpty && !isDeviceTokenEmpty){
            presenter.loginUser(email, password, deviceToken, Constants.APIKEY)
        } else {
            Toast.makeText(applicationContext, "Please Complete Form Login", Toast.LENGTH_SHORT).show()
            binding.btnLogin.visibility = View.VISIBLE
            binding.relativeLoading.visibility = View.INVISIBLE
            binding.btnRegister.isEnabled = true
            binding.btnLoginGoogle.isEnabled= true
        }

    }

    private fun move(){
        sharedPreferenceHelper.save(Constants.PREF_IS_LOGIN, true)
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun successLogin(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        move()
    }

    override fun failedLogin(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        binding.btnLogin.visibility = View.VISIBLE
        binding.relativeLoading.visibility = View.INVISIBLE
        binding.btnRegister.isEnabled = true
        binding.btnLoginGoogle.isEnabled= true

    }

    override fun getToken(token: String) {
        sharedPreferenceHelper.save(Constants.PREF_TOKEN_USER, token)
        Log.d("token",token)
    }

}