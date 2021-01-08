package haina.ecommerce.view.history.historysubmitapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.adapter.AdapterHistorySubmitJob
import haina.ecommerce.databinding.FragmentHistorySubmitApplicationBinding
import haina.ecommerce.model.DataJobApplication

class HistorySubmitJobFragment : Fragment(), HistorySubmitJobContract {

    private var _binding: FragmentHistorySubmitApplicationBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: HistorySubmitJobPresenter
    private var totalSubmit:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistorySubmitApplicationBinding.inflate(inflater, container, false)
        presenter = HistorySubmitJobPresenter(this, requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getJobSubmit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getListSubmitJob(item: List<DataJobApplication?>?) {
        val adapterHistorySubmitJob = AdapterHistorySubmitJob(requireContext(), item)
        binding.rvHistoryJobSubmit.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterHistorySubmitJob
            adapterHistorySubmitJob.notifyDataSetChanged()
        }
        totalSubmit = "Total submit : ${item?.size.toString()} Application"
        binding.tvTotalSubmit.text = totalSubmit
    }

    override fun messageGetSubmitJob(msg: String) {
        Log.d("HistorySubmitJob", msg)
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}