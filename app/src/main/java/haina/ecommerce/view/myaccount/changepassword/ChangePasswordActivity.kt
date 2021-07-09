package haina.ecommerce.view.myaccount.changepassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.snackbar.Snackbar
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityChangePasswordBinding
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity

class ChangePasswordActivity : AppCompatActivity(), View.OnClickListener, ChangePasswordContract {

    private lateinit var binding : ActivityChangePasswordBinding
    private lateinit var presenter: ChangePasswordPresenter
    private lateinit var sharedPref:SharedPreferenceHelper
    private var isEmptyOldPassword = true
    private var isEmptyNewPassword = true
    private var isEmptyConfirmPassword = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = ChangePasswordPresenter(this, this)
        sharedPref = SharedPreferenceHelper(this)
        binding.toolbarChangePassword.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarChangePassword.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarChangePassword.title = applicationContext.getString(R.string.change_password)
        binding.btnChangePassword.setOnClickListener(this)
        clearErrorText()

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_change_password -> {
                binding.relativeLoading.visibility = View.VISIBLE
                binding.btnChangePassword.visibility = View.INVISIBLE
                checkPassword()
            }
        }
    }

    private fun checkPassword(){
        var oldPassword = binding.etOldPassword.text.toString()
        var newPassword = binding.etNewPassword.text.toString()
        var confirmPassword = binding.etConfirmPassword.text.toString()

        if (oldPassword.isEmpty()){
            binding.outlinedOldPassword.error = "old password must be filled"
            isEmptyOldPassword = true
        } else {
            oldPassword = binding.etOldPassword.text.toString()
            isEmptyOldPassword = false
        }

        if (newPassword.isEmpty()){
            binding.outlinedNewPassword.error = "new password must be filled"
            isEmptyNewPassword = true
        } else {
            newPassword = binding.etNewPassword.text.toString()
            isEmptyNewPassword = false
        }

        when {
            confirmPassword.isEmpty() -> {
                binding.outlinedConfirmPassword.error = "confirm password must be filled"
                isEmptyConfirmPassword = true
            }
            confirmPassword!=newPassword -> {
                binding.outlinedConfirmPassword.error = "confirm password doesn't match"
                isEmptyOldPassword = false
            }
            else -> {
                confirmPassword = binding.etConfirmPassword.text.toString()
                isEmptyConfirmPassword = false
            }
        }

        if (!isEmptyOldPassword && !isEmptyNewPassword && !isEmptyConfirmPassword){
            presenter.changePasswordUser(oldPassword, newPassword)
        } else {
            Toast.makeText(applicationContext, "Please complete the form", Toast.LENGTH_SHORT).show()
            binding.relativeLoading.visibility = View.INVISIBLE
            binding.btnChangePassword.visibility = View.VISIBLE
        }

    }

    override fun messageChangePassword(msg: String) {
        when {
            msg.contains("Wrong") -> {
                binding.outlinedOldPassword.error = msg
                binding.relativeLoading.visibility = View.INVISIBLE
                binding.btnChangePassword.visibility = View.VISIBLE
            }
            msg.contains("Success") -> {
                sharedPref.removeValue(Constants.PREF_IS_LOGIN)
                presenter.resetTokenUser()
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                binding.relativeLoading.visibility = View.INVISIBLE
                binding.btnChangePassword.visibility = View.VISIBLE
            }
            else -> {
                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                binding.relativeLoading.visibility = View.INVISIBLE
                binding.btnChangePassword.visibility = View.VISIBLE
            }
        }
    }

    override fun messageLogout(msg: String) {
        Log.d("changePassword", msg)
        if (msg.contains("Success")){
            move()
            val snackbar = Snackbar.make(binding.btnChangePassword, "Please login for access notification", Snackbar.LENGTH_SHORT)
                    .setAction("Close", null)
            snackbar.show()
        }
    }

    private fun move(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("loginStatus", "1")
        startActivity(intent)
        finish()
    }

    private fun clearErrorText(){
        binding.etOldPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()){
                    binding.outlinedOldPassword.error = null
                    true
                }
            }

            override fun afterTextChanged(s: Editable?) {
                false
            }
        })
        binding.etNewPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()){
                    binding.outlinedNewPassword.error = null
                    true
                }
            }

            override fun afterTextChanged(s: Editable?) {
                false
            }
        })
        binding.etConfirmPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()){
                    binding.outlinedConfirmPassword.error = null
                    true
                }
            }

            override fun afterTextChanged(s: Editable?) {
                false
            }
        })
    }

}