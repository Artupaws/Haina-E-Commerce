package haina.ecommerce.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentMyAccountBinding
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity

class MyAccountFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPref: SharedPreferenceHelper
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireContext())
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
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
                sharedPref.removeValue(Constants.PREF_IS_LOGIN)
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("loginStatus", "1")
                startActivity(intent)
                activity?.finish()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}