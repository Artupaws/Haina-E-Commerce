package haina.ecommerce.view.myaccount

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentMyAccountBinding
import haina.ecommerce.model.DataUser
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.login.LoginActivity

class MyAccountFragment : Fragment(), View.OnClickListener, MyAccountContract {

    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPref: SharedPreferenceHelper
    private var broadcaster: LocalBroadcastManager? = null
    private var popupLogout: AlertDialog? = null
    private lateinit var presenter: MyAccountPresenter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireContext())
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        presenter = MyAccountPresenter(this, requireContext())
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding.includeLogin.linearNotLogin.visibility = View.GONE
            binding.linearMenu.visibility = View.VISIBLE
        } else {
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            binding.linearMenu.visibility = View.INVISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getDataUserProfile()
        binding.includeLogin.btnLogin.setOnClickListener(this)
        binding.ivNotification.setOnClickListener(this)
        binding.linearLogout.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }

            R.id.iv_notification -> {
                if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)){
                    val snackbar = Snackbar.make(binding.ivNotification, "You are logged", Snackbar.LENGTH_SHORT)
                        .setAction("Close", null)
                    snackbar.show()
                } else {
                    val snackbar = Snackbar.make(binding.ivNotification, "Please login for access notification", Snackbar.LENGTH_SHORT)
                        .setAction("Close", null)
                    snackbar.show()
                }
            }

            R.id.linear_logout ->{
                showPopupLogout()
                popupLogout?.show()
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun showPopupLogout(){
        val popup = AlertDialog.Builder(activity)
        val view: View = layoutInflater.inflate(R.layout.popup_logout, null)
        popup.setCancelable(false)
        popup.setView(view)
        val actionCancel = view.findViewById<TextView>(R.id.tv_action_cancel)
        val actionYes = view.findViewById<TextView>(R.id.tv_action_yes)
        val title = view.findViewById<TextView>(R.id.tv_title)
        popupLogout = popup.create()
        title.text = "Logout"
        actionCancel.setOnClickListener { popupLogout?.dismiss() }
        actionYes.setOnClickListener {
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            presenter.resetTokenUser()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("loginStatus", "1")
            startActivity(intent)
        activity?.finish()}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        popupLogout?.dismiss()
    }

    override fun successGetDataUser(msg: String) {
        Log.d("getDataSuccess", msg)
    }

    override fun errorGetDataUSer(msg: String) {
        Log.d("getDataError", msg)
    }

    override fun getDataUser(data: DataUser?) {
        sharedPref.save(Constants.PREF_USERNAME, data?.username.toString())
        sharedPref.save(Constants.PREF_EMAIL, data?.email.toString())
        sharedPref.save(Constants.PREF_FULLNAME, data?.fullname.toString())
        binding.tvNameUser.text = data?.fullname.toString()
        activity?.let { Glide.with(it).load(data?.photo).into(binding.ivProfile) }
    }

    override fun successLogout(msg: String) {
        Log.d("successLogout", msg)
    }

    override fun errorLogout(msg: String) {
        Log.d("errorLogout", msg)
    }

    override fun resetTokenUser(data: String?) {
        sharedPref.save(Constants.PREF_TOKEN_LOGIN, data.toString())
    }

}