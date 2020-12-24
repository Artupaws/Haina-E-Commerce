package haina.ecommerce.view.posting.vacancy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterMyPostJob
import haina.ecommerce.databinding.FragmentHistoryBuyBinding
import haina.ecommerce.model.DataMyPost
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity

class JobVacancyPostFragment : Fragment(), VacancyContract, View.OnClickListener {

    private var _binding: FragmentHistoryBuyBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: VacancyPresenter
    lateinit var sharedPref: SharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHistoryBuyBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireContext())
        presenter = VacancyPresenter(this, requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getDataMyPost()
        refresh()
        binding.includeLogin.btnLoginNotLogin.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding.includeLogin.linearNotLogin.visibility = View.GONE
            binding.rvJobVacancy.visibility = View.VISIBLE
        } else {
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            binding.rvJobVacancy.visibility = View.GONE
        }
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.getDataMyPost()
        })
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login_not_login -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun successLoadMyPost(msg: String) {
        Log.d("MyPost", msg)
        if (msg.isEmpty()) {
            binding.rvJobVacancy.visibility = View.INVISIBLE
            binding.includeEmpty.tvEmpty.text = "You haven't posted anything yet"
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
            binding.rvJobVacancy.visibility = View.VISIBLE
            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun errorLoadMyPost(msg: String) {
        Log.d("errorLoadPost", msg)
        if (msg == "null" && sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding.rvJobVacancy.visibility = View.INVISIBLE
            binding.includeEmpty.tvEmpty.text = "You haven't posted anything yet"
            binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        } else {
            binding.swipeRefresh.visibility = View.GONE
            binding.rvJobVacancy.visibility = View.VISIBLE
            binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    override fun getListMyPost(list: List<DataMyPost?>?) {
        val getMyPost = AdapterMyPostJob(requireContext(), list)
        binding.includeEmpty.linearEmpty.visibility = View.INVISIBLE
        binding.rvJobVacancy.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = getMyPost
        }
    }
}