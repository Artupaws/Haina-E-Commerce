package haina.ecommerce.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityLoginBinding
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.register.account.RegisterActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginContract {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var isEmailEmpty = true
    private var isPasswordEmpty = true
    private var isDeviceTokenEmpty = true
    private var isDeviceNameEmpty = true
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private lateinit var presenter: LoginPresenter
    private var manufacturer: String = ""
    private var gsc:GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001
    private var loginMethod:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        presenter = LoginPresenter(this)
        sharedPreferenceHelper = SharedPreferenceHelper(this)

        val gso:GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(applicationContext.getString(R.string.default_web_client_id))
            .build()
        gsc = GoogleSignIn.getClient(this, gso)

        binding.btnLogin.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        binding.linearGoogle.setOnClickListener(this)
        getDeviceName()

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isNotEmpty()) {
                    binding.etPassword.error = null
                    true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                false
            }

        })
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
//                binding.btnLoginGoogle.isEnabled= false
                checkLogin()
            }
            R.id.btn_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.linear_google -> {
                val intentGoogle = Intent(gsc?.signInIntent)
                startActivityForResult(intentGoogle, RC_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("loginGoogle", account.idToken!!)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e : ApiException){
                Log.w("googleFail", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    loginMethod = 1
                    Toast.makeText(applicationContext, "Login", Toast.LENGTH_SHORT).show()
                    move(loginMethod)
                } else {
                    loginMethod = null
                    Snackbar.make(binding.linearGoogle, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    private fun getDeviceName() {
        manufacturer = Build.MANUFACTURER+" "+Build.MODEL
        Log.d("deviceName", manufacturer)
    }

    private fun checkLogin(){
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()
        val deviceToken = sharedPreferenceHelper.getValueString(Constants.PREF_TOKEN_FIREBASE).toString()

        if (email.isEmpty()){
            binding.etEmail.error = "Email can't empty"
            isEmailEmpty = true
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error = "Email not valid"
            isEmailEmpty = true
        } else {
            email = binding.etEmail.text.toString()
            isEmailEmpty = false
        }

        if (password.isEmpty()){
            binding.etPassword.error = "Password can't empty"
            isPasswordEmpty = true
        } else {
            password = binding.etPassword.text.toString()
            isPasswordEmpty = false
        }

        isDeviceTokenEmpty = deviceToken.equals(null)

        isDeviceNameEmpty = manufacturer == ""

        if (!isEmailEmpty && !isPasswordEmpty && !isDeviceTokenEmpty && !isDeviceNameEmpty){
            loginMethod = 0
            presenter.loginUser(email, password, deviceToken, manufacturer)
        } else {
            Toast.makeText(applicationContext, "Please complete the form", Toast.LENGTH_SHORT).show()
            binding.btnLogin.visibility = View.VISIBLE
            binding.relativeLoading.visibility = View.INVISIBLE
            binding.btnRegister.isEnabled = true
//            binding.btnLoginGoogle.isEnabled= true
        }

    }

    private fun move(method:Int?){
        if (method == 0){
            sharedPreferenceHelper.save(Constants.PREF_IS_LOGIN, true)
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else if (method == 1){
            sharedPreferenceHelper.save(Constants.PREF_IS_LOGIN, true)
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("loginMethod", 1)
            startActivity(intent)
            finish()
        }

    }

    override fun successLogin(msg: String) {
        Log.d("successLogin", msg)
        loginMethod = 0
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        move(loginMethod)
    }

    override fun failedLogin(msg: String) {
        Log.d("failedLogin", msg)
        loginMethod = null
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        binding.btnLogin.visibility = View.VISIBLE
        binding.relativeLoading.visibility = View.INVISIBLE
        binding.btnRegister.isEnabled = true
//        binding.btnLoginGoogle.isEnabled= true

    }

    override fun getToken(token: String) {
        sharedPreferenceHelper.save(Constants.PREF_TOKEN_USER, token)
        Log.d("token", token)
    }

}