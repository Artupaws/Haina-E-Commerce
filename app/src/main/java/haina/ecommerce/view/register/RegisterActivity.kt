package haina.ecommerce.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityRegisterBinding
import haina.ecommerce.view.login.LoginActivity
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding
    private var isEmptyFullname: Boolean = true
    private var isEmptyEmail: Boolean = true
    private var isEmptyPhone: Boolean = true
    private var isEmptyPassword: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_register -> {
                registerCheck()
            }
        }
    }

    private fun registerCheck() {
        var fullname = binding.etFullname.text.toString()
        var email = binding.etEmail.text.toString()
        var phone = binding.etPhone.text.toString()
        var password = binding.etPassword.text.toString()
        if (fullname.isEmpty()) {
            binding.outlinedTextFieldFullname.error = "Fullname can't empty"
            isEmptyFullname
        } else {
            fullname = binding.etFullname.text.toString()
            !isEmptyFullname
        }

        if (email.isEmpty()) {
            binding.outlinedTextFieldEmail.error = "Email can't empty"
            isEmptyEmail
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.outlinedTextFieldEmail.error = "Email not valid"
            isEmptyEmail
        } else {
            email = binding.etFullname.text.toString()
            !isEmptyEmail
        }

        if (phone.isEmpty()) {
            binding.outlinedTextFieldPhone.error = "Phone can't empty"
            isEmptyPhone
        } else {
            phone = binding.etFullname.text.toString()
            !isEmptyPhone
        }

        if (password.isEmpty()) {
            binding.outlinedTextFieldPassword.error = "Password can't empty"
            isEmptyPassword
        } else {
            password = binding.etFullname.text.toString()
            !isEmptyPassword
        }

        if ((fullname.isNotEmpty() && !isEmptyFullname) && (email.isNotEmpty() && !isEmptyEmail) && (phone.isNotEmpty() && !isEmptyPhone) && (password.isNotEmpty() && !isEmptyPassword)) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
        }

    }

}