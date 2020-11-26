package haina.ecommerce.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentLogin2Binding

class Login2Fragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLogin2Binding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(p0: View?) {
        when (p0?.id){
            R.id.btn_login -> {
                Navigation.findNavController(p0).navigate(Login2FragmentDirections.actionLogin2FragmentToHomeFragment())
            }
        }
    }

}