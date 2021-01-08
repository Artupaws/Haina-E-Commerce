package haina.ecommerce.view.history.historyshortlistapplicant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterShortlistApplicant
import haina.ecommerce.databinding.FragmentHistorySortListBinding
import haina.ecommerce.model.DataShortlistApplicant

class HistorySortListActivity : Fragment(), HistoryShortListContract {

    private var _binding : FragmentHistorySortListBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: HistoryShortListPresenter
    private val status:String = "shortlisted"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistorySortListBinding.inflate(inflater, container, false)
        presenter = HistoryShortListPresenter(this, requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getShortlistApplicant(status)
        refresh()
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getShortlistApplicant(status)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun messageGetShortList(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        if (msg.isNotEmpty()){
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun getShortListApplicant(item: List<DataShortlistApplicant?>?) {
        val adapterShortList = AdapterShortlistApplicant(requireContext(), item)
        binding.rvShortList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterShortList
            adapterShortList.notifyDataSetChanged()
        }

    }

}