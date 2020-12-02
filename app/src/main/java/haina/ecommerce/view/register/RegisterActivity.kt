package haina.ecommerce.view.register

import android.annotation.SuppressLint
import android.content.Context
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
import haina.ecommerce.preference.SharedPreference
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.login.LoginActivity
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity(), View.OnClickListener, RegisterContract {

    private lateinit var binding: ActivityRegisterBinding
    private var isEmptyFullname: Boolean = true
    private var isEmptyEmail: Boolean = true
    private var isEmptyUsername: Boolean = true
    private var isEmptyPhone: Boolean = true
    private var isEmptyPassword: Boolean = true
    @SuppressLint("SetTextI18n")
    private lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = RegisterPresenter(this)
        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        val itemData = intent.getSerializableExtra("dataItem")
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_register -> {
                registerCheck()
            }
        }
    }

    private fun registerCheck() {
        val sharedPreference: SharedPreference = SharedPreference(this)
        val deviceToken = sharedPreference.getValueString("preferences").toString()
        var fullname = binding.etFullname.text.toString()
        var email = binding.etEmail.text.toString()
        var username = binding.etUsername.text.toString()
        var phone = binding.etPhone.text.toString()
        var password = binding.etPassword.text.toString()
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

        if (!isEmptyFullname && !isEmptyEmail && !isEmptyUsername && !isEmptyPhone && !isEmptyPassword) {
            presenter.createUser(fullname, email, username, phone, password, Constants.APIKEY, deviceToken)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(applicationContext, "failed $deviceToken", Toast.LENGTH_SHORT).show()
        }

    }

    override fun successCreateUser(msg: String) {
        Log.d("success", msg)
        val snackbar =Snackbar.make(binding.btnRegister, "Register Success", Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    override fun errorCreateUser(msg: String) {
        Log.d("failed", msg)
    }

}