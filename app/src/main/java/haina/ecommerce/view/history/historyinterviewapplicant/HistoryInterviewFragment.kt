package haina.ecommerce.view.history.historyinterviewapplicant

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
import haina.ecommerce.databinding.FragmentHistoryInterviewBinding
import haina.ecommerce.model.DataShortlist
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity

class HistoryInterviewFragment : Fragment(), HistoryInterviewContract, View.OnClickListener {

    private var _binding : FragmentHistoryInterviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: HistoryInterviewPresenter
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private val status:String = "interview"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryInterviewBinding.inflate(inflater, container, false)
        presenter = HistoryInterviewPresenter(this, requireContext())
        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getInterviewApplicant(status)
        binding.includeLogin.btnLoginNotLogin.setOnClickListener(this)
        if (sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding.includeLogin.linearNotLogin.visibility = View.INVISIBLE
            binding.rvInterview.visibility = View.VISIBLE
        } else {
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            binding.rvInterview.visibility = View.GONE
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
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getInterviewApplicant(status)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun messageStatusSuccess(msg: String) {
        Log.d("interviewSuccess", msg)
        if (msg.contains("Success")){
            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.rvInterview.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.rvInterview.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun messageStatusFailed(msg: String) {
        Log.d("interviewFailed", msg)
        if (msg == "null" && sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)){
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.includeEmpty.tvEmpty.text = "Empty"
            binding.rvInterview.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else if (msg.contains("Doesn't Exist")){
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.includeEmpty.tvEmpty.text = "Empty"
            binding.rvInterview.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.rvInterview.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun getInterviewApplicant(item: List<DataShortlist?>?) {
        val adapterInterview = AdapterShortlistApplicant(requireContext(), item)
        binding.rvInterview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterInterview
            adapterInterview.notifyDataSetChanged()
        }
    }



}
