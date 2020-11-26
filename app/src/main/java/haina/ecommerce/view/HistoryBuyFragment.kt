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

        val listBuy = arrayListOf<HistoryBuy>()

        val buyAdapter = AdapterHistoryBuy(requireContext(),listBuy)
        binding.rvHistoryBuy.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = buyAdapter
            if (listBuy.size == 0){
                binding.rvHistoryBuy.visibility = View.GONE
            } else {
                binding.rvHistoryBuy.visibility = View.VISIBLE
                binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}