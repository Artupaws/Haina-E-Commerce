package haina.ecommerce.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterHistoryBuy
import haina.ecommerce.databinding.FragmentHistoryBillBinding
import haina.ecommerce.model.HistoryBuy

class HistoryBillFragment : Fragment() {

    private var _binding: FragmentHistoryBillBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBillBinding.inflate(inflater, container, false)

        val listBill = arrayListOf<HistoryBuy>()
        val billAdapter = AdapterHistoryBuy(requireContext(), listBill)
        binding.rvHistoryBill.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = billAdapter
            if (listBill.size == 0) {
                binding.rvHistoryBill.visibility = View.GONE
            } else {
                binding.includeEmpty.linearEmpty.visibility = View.GONE
                binding.rvHistoryBill.visibility = View.VISIBLE
            }

            return binding.root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}