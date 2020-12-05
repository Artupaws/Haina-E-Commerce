package haina.ecommerce.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentMyAccountBinding
import haina.ecommerce.view.login.LoginActivity

class MyAccountFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.includeLogin.btnLogin.setOnClickListener(this)
        Toast.makeText(activity, "dor", Toast.LENGTH_SHORT).show()
//        binding.includeLogin.linearNotLogin.visibility = View.GONE
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}