package haina.ecommerce.view.flight.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentFillDataPassengerBinding
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants

class FillDataPassengerFragment : Fragment() {

    private lateinit var _binding:FragmentFillDataPassengerBinding
    private val binding get()= _binding
    private lateinit var sharedPref:SharedPreferenceHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentFillDataPassengerBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbarFillDataPassenger.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setDataOrderer()
    }

    private fun setDataOrderer(){
        binding.tvNameOrderer.text = sharedPref.getValueString(Constants.PREF_FULLNAME)
        binding.tvEmail.text = sharedPref.getValueString(Constants.PREF_EMAIL)
        binding.tvPhone.text = sharedPref.getValueString(Constants.PREF_PHONE_NUMBER)
    }

}