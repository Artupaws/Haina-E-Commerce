package haina.ecommerce.view.history.historyshortlistapplicant

import android.content.Intent
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
import haina.ecommerce.model.DataShortlist
import haina.ecommerce.model.DataShortlistApplicant
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity

class HistoryShortListFragment : Fragment(), HistoryShortListContract, View.OnClickListener {

    private var _binding : FragmentHistorySortListBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: HistoryShortListPresenter
    private val status:String = "shortlisted"
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistorySortListBinding.inflate(inflater, container, false)
        presenter = HistoryShortListPresenter(this, requireContext())
        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getShortlistApplicant(status)
        binding.includeLogin.btnLoginNotLogin.setOnClickListener(this)
        refresh()
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getShortlistApplicant(status)
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding.includeLogin.linearNotLogin.visibility = View.INVISIBLE
            binding.rvShortList.visibility = View.VISIBLE
        } else {
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            binding.rvShortList.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun messageGetShortListSuccess(msg: String) {
        if (msg.contains("Success")){
            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.rvShortList.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.rvShortList.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun messageGetShortListError(msg: String) {
        if (msg == "null" && sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)){
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.includeEmpty.tvEmpty.text = "Empty"
            binding.rvShortList.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.rvShortList.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun getShortListApplicant(item: List<DataShortlist?>?) {
        val adapterShortList = AdapterShortlistApplicant(requireContext(), item)
        binding.rvShortList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterShortList
            adapterShortList.notifyDataSetChanged()
        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login_not_login -> {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

}