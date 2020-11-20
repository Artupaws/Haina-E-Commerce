package haina.ecommerce.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterCart
import haina.ecommerce.databinding.FragmentCartBinding
import haina.ecommerce.model.Cart

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarCart.title = "Cart"

        val listCart = arrayListOf(
                Cart("Playstation 5 Digital Version 2020 Storage 1000 Giga Byte",R.drawable.ps5,"Rp10.000.000"),
                Cart("Playstation 5 Digital Version 2020 Storage 1000 Giga Byte",R.drawable.ps5,"Rp10.000.000")
        )

        val cartAdapter = AdapterCart(requireContext(), listCart)
        binding.rvCart.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}