package haina.ecommerce.view.history.historysubmitapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterHistorySubmitJob
import haina.ecommerce.databinding.FragmentHistorySubmitApplicationBinding
import haina.ecommerce.model.DataJobApplication
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity

class HistorySubmitJobFragment : Fragment(), HistorySubmitJobContract, View.OnClickListener {

    private var _binding: FragmentHistorySubmitApplicationBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: HistorySubmitJobPresenter
    private var totalSubmit:String = ""
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistorySubmitApplicationBinding.inflate(inflater, container, false)
        presenter = HistorySubmitJobPresenter(this, requireContext())
        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())
        refresh()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getJobSubmit()
        binding.includeLogin.btnLoginNotLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btn_login_not_login -> {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getJobSubmit()
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding.includeLogin.linearNotLogin.visibility = View.INVISIBLE
            binding.rvHistoryJobSubmit.visibility = View.VISIBLE
        } else {
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            binding.rvHistoryJobSubmit.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
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

    override fun messageGetSubmitJobSuccess(msg: String) {
        Log.d("historyJobSubmitSuccess", msg)
        if (msg.contains("Success")){
            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.rvHistoryJobSubmit.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.rvHistoryJobSubmit.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun messageGetSubmitJobError(msg: String) {
        Log.d("historyJobSubmitError", msg)
        if (msg == "null" && sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)){
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.includeEmpty.tvEmpty.text = "You haven't applied"
            binding.rvHistoryJobSubmit.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.rvHistoryJobSubmit.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }



}