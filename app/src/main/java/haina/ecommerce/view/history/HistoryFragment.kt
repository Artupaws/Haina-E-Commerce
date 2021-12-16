package haina.ecommerce.view.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentHistoryBinding
import haina.ecommerce.view.history.historyjobvacancy.MyVacancyActivity
import haina.ecommerce.view.history.historymyproperty.MyPropertyActivity
import haina.ecommerce.view.history.historysubmitapplication.HistorySubmitJobActivity
import haina.ecommerce.view.history.historytransaction.HistoryTransactionActivity
import haina.ecommerce.view.hotels.transactionhotel.HistoryTransactionHotelActivity


class HistoryFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbarHistory?.title = getString(R.string.history)
        binding?.cvJobVacancy?.setOnClickListener(this)
        binding?.cvTransaction?.setOnClickListener(this)
        binding?.cvHistoryHotel?.setOnClickListener(this)
        binding?.cvMyproperty?.setOnClickListener(this)
        binding?.cvApplyingJob?.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cv_job_vacancy -> {
                val intent = Intent(requireContext(), MyVacancyActivity::class.java)
                startActivity(intent)
            }
            R.id.cv_transaction -> {
                val intent = Intent(requireContext(), HistoryTransactionActivity::class.java)
                startActivity(intent)
            }
            R.id.cv_history_hotel -> {
                val intent = Intent(requireContext(), HistoryTransactionHotelActivity::class.java)
                startActivity(intent)
            }
            R.id.cv_myproperty -> {
                val intent = Intent(requireContext(), MyPropertyActivity::class.java)
                startActivity(intent)
            }
            R.id.cv_applying_job -> {
                val intent = Intent(requireContext(), HistorySubmitJobActivity::class.java)
                startActivity(intent)
            }
        }
    }

}