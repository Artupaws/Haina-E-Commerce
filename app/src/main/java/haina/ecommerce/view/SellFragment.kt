package haina.ecommerce.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterSelling
import haina.ecommerce.databinding.FragmentSellBinding
import haina.ecommerce.model.Selling

class SellFragment : Fragment() {

    private var _binding: FragmentSellBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSellBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listSelling = arrayListOf(
                Selling("Playstation 5 Digital Version 2020","desc: Playstation 5 Digital Version 2020 Storage 1000 Giga Byte","Rp10.000.000", R.drawable.ps5),
                Selling("Playstation 5 Digital Version 2020","desc: Playstation 5 Digital Version 2020 Storage 1000 Giga Byte","Rp10.000.000", R.drawable.ps5),
                Selling("Playstation 5 Digital Version 2020","desc: Playstation 5 Digital Version 2020 Storage 1000 Giga Byte","Rp10.000.000", R.drawable.ps5),
                Selling("Playstation 5 Digital Version 2020","desc: Playstation 5 Digital Version 2020 Storage 1000 Giga Byte","Rp10.000.000", R.drawable.ps5),
                Selling("Playstation 5 Digital Version 2020","desc: Playstation 5 Digital Version 2020 Storage 1000 Giga Byte","Rp10.000.000", R.drawable.ps5),
                Selling("Playstation 5 Digital Version 2020","desc: Playstation 5 Digital Version 2020 Storage 1000 Giga Byte","Rp10.000.000", R.drawable.ps5),
                Selling("Playstation 5 Digital Version 2020","desc: Playstation 5 Digital Version 2020 Storage 1000 Giga Byte","Rp10.000.000", R.drawable.ps5),
                Selling("Playstation 5 Digital Version 2020","desc: Playstation 5 Digital Version 2020 Storage 1000 Giga Byte","Rp10.000.000", R.drawable.ps5)
        )

        val sellingAdapter = AdapterSelling(requireContext(), listSelling)
        binding.rvSell.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = sellingAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}