package haina.ecommerce.view.history.historyjobvacancy.historyacceptapplicant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterShortlistApplicant
import haina.ecommerce.databinding.FragmentHistoryAcceptBinding
import haina.ecommerce.model.DataShortlist
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.history.historyjobvacancy.historyinterviewapplicant.HistoryInterviewContract
import haina.ecommerce.view.history.historyjobvacancy.historyinterviewapplicant.HistoryInterviewPresenter
import haina.ecommerce.view.login.LoginActivity

class HistoryAcceptFragment : Fragment(), HistoryInterviewContract, View.OnClickListener, AdapterShortlistApplicant.ItemAdapterCallback {

    private var _binding : FragmentHistoryAcceptBinding? = null
    private val binding get() = _binding
    private lateinit var presenter : HistoryInterviewPresenter
    private lateinit var sharedPref: SharedPreferenceHelper
    private val status:String = "accepted"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryAcceptBinding.inflate(inflater, container, false)
        presenter = HistoryInterviewPresenter(this, requireContext())
        sharedPref = SharedPreferenceHelper(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getInterviewApplicant(status)
        binding?.includeLogin?.btnLoginNotLogin?.setOnClickListener(this)
        if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding?.includeLogin?.linearNotLogin?.visibility = View.INVISIBLE
            binding?.rvAccept?.visibility = View.VISIBLE
        } else {
            binding?.includeLogin?.linearNotLogin?.visibility = View.VISIBLE
            binding?.rvAccept?.visibility = View.GONE
        }
        refresh()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login_not_login -> {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getInterviewApplicant(status)
    }

    private fun refresh(){
        binding?.swipeRefresh?.setOnRefreshListener {
            presenter.getInterviewApplicant(status)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun messageStatusSuccess(msg: String) {
        Log.d("acceptSuccess", msg)
        if (msg.contains("Success")){
            binding?.includeEmpty?.linearEmpty?.visibility = View.INVISIBLE
            binding?.rvAccept?.visibility = View.VISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        } else {
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.rvAccept?.visibility = View.INVISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        }
    }

    override fun messageStatusFailed(msg: String) {
        Log.d("acceptFailed", msg)
        if (msg == "null" && sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)){
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.includeEmpty?.tvEmpty?.text = getString(R.string.empty)
            binding?.rvAccept?.visibility = View.INVISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        } else if (msg.contains("Doesn't Exist")){
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.includeEmpty?.tvEmpty?.text = getString(R.string.empty)
            binding?.rvAccept?.visibility = View.INVISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        } else {
            binding?.includeEmpty?.linearEmpty?.visibility = View.INVISIBLE
            binding?.rvAccept?.visibility = View.VISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        }
    }

    override fun getInterviewApplicant(item: List<DataShortlist?>?) {
        val adapterInterview = AdapterShortlistApplicant(requireContext(), item, this)
        binding?.rvAccept?.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterInterview
            adapterInterview.notifyDataSetChanged()
        }
    }

    override fun onAdapterClick(view: View, data: DataShortlist) {
        TODO("Not yet implemented")
    }
}