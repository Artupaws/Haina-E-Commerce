package haina.ecommerce.view.history.historyjobvacancy.historyshortlistapplicant

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterShortlistApplicant
import haina.ecommerce.databinding.FragmentHistorySortListBinding
import haina.ecommerce.model.DataShortlist
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity

class HistoryShortListFragment : Fragment(), HistoryShortListContract, View.OnClickListener, AdapterShortlistApplicant.ItemAdapterCallback {

    private var _binding: FragmentHistorySortListBinding? = null
    private val binding get() = _binding
    private lateinit var presenter: HistoryShortListPresenter
    private val status: String = "shortlisted"
    private val statusInterview: String = "interview"
    private val statusAccept: String = "accepted"
    private val statusDecline: String = "declined"
    private var idApplicant: Int = 0
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistorySortListBinding.inflate(inflater, container, false)
        presenter = HistoryShortListPresenter(this, requireContext())
        sharedPreferenceHelper = SharedPreferenceHelper(requireContext())
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getShortlistApplicant(status)
        binding?.includeLogin?.btnLoginNotLogin?.setOnClickListener(this)
        if (sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding?.includeLogin?.linearNotLogin?.visibility = View.INVISIBLE
            binding?.rvShortList?.visibility = View.VISIBLE
        } else {
            binding?.includeLogin?.linearNotLogin?.visibility = View.VISIBLE
            binding?.rvShortList?.visibility = View.GONE
        }
        refresh()
    }

    private fun refresh() {
        binding?.swipeRefresh?.setOnRefreshListener {
            presenter.getShortlistApplicant(status)
        }
    }

//    override fun onStart() {
//        super.onStart()
//        LocalBroadcastManager.getInstance(requireContext())
//            .registerReceiver(mReceiver, IntentFilter("interview"))
//        LocalBroadcastManager.getInstance(requireContext())
//            .registerReceiver(mReceiver, IntentFilter("accept"))
//        LocalBroadcastManager.getInstance(requireContext())
//            .registerReceiver(mReceiver, IntentFilter("decline"))
//    }
//
//    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            when (intent?.action) {
//                "interview" -> {
//                    val id = intent.getIntExtra("invite", 0)
//                    idApplicant = id
//                    Log.d("interview", idApplicant.toString())
//                    presenter.interviewApplicant(idApplicant, statusInterview)
//                }
//                "accept" -> {
//                    val id = intent.getIntExtra("hired", 0)
//                    idApplicant = id
//                    Log.d("accept", idApplicant.toString())
//                    presenter.acceptApplicant(idApplicant, statusAccept)
//                }
//                "decline" -> {
//                    val id = intent.getIntExtra("reject", 0)
//                    idApplicant = id
//                    Log.d("decline", idApplicant.toString())
//                    presenter.declinedApplicant(idApplicant, statusDecline)
//                }
//            }
//        }
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mReceiver)
//    }

    override fun onResume() {
        super.onResume()
        presenter.getShortlistApplicant(status)
    }

    override fun messageGetShortListSuccess(msg: String) {
        Log.d("shortlistSuc", msg)
        if (msg.contains("Success")) {
            binding?.includeEmpty?.linearEmpty?.visibility = View.INVISIBLE
            binding?.rvShortList?.visibility = View.VISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        } else {
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.rvShortList?.visibility = View.INVISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        }
    }

    override fun messageGetShortListError(msg: String) {
        Log.d("shortlistErr", msg)
        if (msg == "null" && sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.includeEmpty?.tvEmpty?.text = "Empty"
            binding?.rvShortList?.visibility = View.INVISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        } else if (msg.contains("Doesn't Exist")){
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
            binding?.includeEmpty?.tvEmpty?.text = "Empty"
            binding?.rvShortList?.visibility = View.INVISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        } else {
            binding?.includeEmpty?.linearEmpty?.visibility = View.INVISIBLE
            binding?.rvShortList?.visibility = View.VISIBLE
            binding?.swipeRefresh?.isRefreshing = false
        }
    }

    override fun messageInterviewApplicant(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        if (msg.contains("Updated")) {
            presenter.getShortlistApplicant(status)
        }
    }

    override fun messageAcceptApplicant(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        if (msg.contains("Updated")) {
            presenter.getShortlistApplicant(status)
        }
    }

    override fun messageDeclineApplicant(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
        if (msg.contains("Updated")) {
            presenter.getShortlistApplicant(status)
        }
    }

    override fun getShortListApplicant(item: List<DataShortlist?>?) {
        val adapterShortList = AdapterShortlistApplicant(requireContext(), item, this)
        binding?.rvShortList?.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterShortList
            adapterShortList.notifyDataSetChanged()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login_not_login -> {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onAdapterClick(view: View, data: DataShortlist) {
        when(view.id){
            R.id.btn_interview -> {
                presenter.interviewApplicant(data.id!!, statusInterview)
            }
            R.id.btn_accept -> {
                presenter.acceptApplicant(data.id!!, statusAccept)
            }
            R.id.btn_decline -> {
                presenter.declinedApplicant(data.id!!, statusDecline)
            }
        }
    }

}