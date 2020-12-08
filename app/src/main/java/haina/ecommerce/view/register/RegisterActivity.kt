package haina.ecommerce.view.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityRegisterBinding
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity

class RegisterActivity : AppCompatActivity(), View.OnClickListener, RegisterContract {

    private lateinit var binding: ActivityRegisterBinding
    private var isEmptyFullname: Boolean = true
    private var isEmptyEmail: Boolean = true
    private var isEmptyUsername: Boolean = true
    private var isEmptyPhone: Boolean = true
    private var isEmptyPassword: Boolean = true
    private var isEmptyDeviceToken: Boolean = true
    private var isEmptyConfirmPassword: Boolean = true
    @SuppressLint("SetTextI18n")
    private lateinit var presenter: RegisterPresenter
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = RegisterPresenter(this)
        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        sharedPreferenceHelper = SharedPreferenceHelper(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_register -> {
                binding.btnRegister.visibility = View.INVISIBLE
                binding.relativeLoading.visibility = View.VISIBLE
                binding.btnLoginGoogle.isEnabled = false
                binding.btnLogin.isEnabled = false
                registerCheck()
            }
        }
    }

    private fun registerCheck() {
        val deviceToken = sharedPreferenceHelper.getValueString(Constants.PREF_TOKEN_FIREBASE).toString()
        var fullname = binding.etFullname.text.toString()
        var email = binding.etEmail.text.toString()
        var username = binding.etUsername.text.toString()
        var phone = binding.etPhone.text.toString()
        var password = binding.etPassword.text.toString()
        var confirmPassword = binding.etConfirmPassword.text.toString()
        if (fullname.isEmpty()) {
            binding.outlinedTextFieldFullname.error = "Fullname can't empty"
            isEmptyFullname = true
        } else {
            fullname = binding.etFullname.text.toString()
            isEmptyFullname = false
        }

        if (email.isEmpty()) {
            binding.outlinedTextFieldEmail.error = "Email can't empty"
            isEmptyEmail = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.outlinedTextFieldEmail.error = "Email not valid"
            isEmptyEmail = true
        } else {
            email = binding.etEmail.text.toString()
            isEmptyEmail = false
        }

        if (username.isEmpty()) {
            binding.outlinedTextFieldUsername.error = "Username can't empty"
            isEmptyUsername = true
        } else {
            username = binding.etUsername.text.toString()
            isEmptyUsername = false
        }

        if (phone.isEmpty()) {
            binding.outlinedTextFieldPhone.error = "Phone can't empty"
            isEmptyPhone = true
        } else {
            phone = binding.etPhone.text.toString()
            isEmptyPhone = false
        }

        if (password.isEmpty()) {
            binding.outlinedTextFieldPassword.error = "Password can't empty"
            isEmptyPassword = true
        } else {
            password = binding.etPassword.text.toString()
            isEmptyPassword = false
        }

        when {
            confirmPassword.isEmpty() -> {
                binding.outlinedTextFieldCPassword.error = "Confrim password can't empty"
                isEmptyConfirmPassword = true
            }
            confirmPassword != password -> {
                binding.outlinedTextFieldCPassword.error = "Confrim password must same with password"
                isEmptyConfirmPassword = true
            }
            else -> {
                confirmPassword = binding.etConfirmPassword.text.toString()
                isEmptyConfirmPassword = false
            }
        }

        isEmptyDeviceToken = deviceToken.equals(null)

        if (!isEmptyFullname && !isEmptyEmail && !isEmptyUsername && !isEmptyPhone && !isEmptyPassword && !isEmptyConfirmPassword && !isEmptyDeviceToken) {
            presenter.createUser(fullname, email, username, phone, password, Constants.APIKEY, deviceToken)
        } else {
            Toast.makeText(applicationContext, "Please Complete Form Register", Toast.LENGTH_SHORT).show()
            binding.btnRegister.visibility = View.VISIBLE
            binding.relativeLoading.visibility = View.INVISIBLE
            binding.btnLoginGoogle.isEnabled = true
            binding.btnLogin.isEnabled = true
        }

    }

    override fun successCreateUser(msg: String) {
        Log.d("successRegister", msg)
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        move()
    }

    override fun errorCreateUser(msg: String) {
        Log.d("failedRegister", msg)
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        binding.btnRegister.visibility = View.VISIBLE
        binding.relativeLoading.visibility = View.INVISIBLE
        binding.btnLoginGoogle.isEnabled = true
        binding.btnLogin.isEnabled = true
    }

    override fun getTokenUser(token: String) {
        sharedPreferenceHelper.save(Constants.PREF_TOKEN_USER, token)
        Log.d("token", token)
    }

    private fun move(){
        sharedPreferenceHelper.save(Constants.PREF_IS_LOGIN, true)
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("loginStatus", "1")
        startActivity(intent)
        finish()
    }

}