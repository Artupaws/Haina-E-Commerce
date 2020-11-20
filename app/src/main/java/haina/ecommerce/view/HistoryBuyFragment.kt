package haina.ecommerce.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterHistoryBuy
import haina.ecommerce.databinding.FragmentHistoryBuyBinding
import haina.ecommerce.model.HistoryBuy

class HistoryBuyFragment : Fragment() {

    private var _binding: FragmentHistoryBuyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHistoryBuyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listBuy = arrayListOf(
                HistoryBuy("Playstation 5 Digital Version 2020 Storage 1000 Giga Byte", "Rp12.000.000", R.drawable.ps5, "waiting seller"),
                HistoryBuy("Playstation 5 Digital Version 2020 Storage 1000 Giga Byte", "Rp13.000.000", R.drawable.ps5, "waiting payment"),
                HistoryBuy("Playstation 5 Digital Version 2020 Storage 1000 Giga Byte", "Rp0", R.drawable.ps5, "packing"),
                HistoryBuy("Playstation 5 Digital Version 2020 Storage 1000 Giga Byte", "Rp11.000.000", R.drawable.ps5, "delivery"),
                HistoryBuy("Playstation 5 Digital Version 2020 Storage 1000 Giga Byte", "Rp1", R.drawable.ps5, "received")
        )

        val buyAdapter = AdapterHistoryBuy(requireContext(),listBuy)
        binding.rvHistoryBuy.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = buyAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}