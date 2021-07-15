package haina.ecommerce.view.explore

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterSpinnerCurrency
import haina.ecommerce.adapter.AdapterUnfinishTransactionExplore
import haina.ecommerce.databinding.FragmentExploreBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataCodeCurrency
import haina.ecommerce.model.DataCovidJkt
import haina.ecommerce.model.DataCurrency
import haina.ecommerce.model.DataUser
import haina.ecommerce.model.transactionlist.DataAllTransactionPending
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.covidlist.CovidListActivity
import haina.ecommerce.view.flight.FlightTicketActivity
import haina.ecommerce.view.history.historytransaction.HistoryTransactionActivity
import haina.ecommerce.view.hotels.HotelBaseActivity
import haina.ecommerce.view.topup.TopupActivity
import haina.ecommerce.view.job.JobActivity
import haina.ecommerce.view.news.NewsActivity
import haina.ecommerce.view.notification.NotificationActivity
import haina.ecommerce.view.other.OtherActivity
import haina.ecommerce.view.property.FinishPropertyActivity
import haina.ecommerce.view.property.ShowPropertyActivity

class ExploreFragment : Fragment(), ExploreContract, View.OnClickListener, AdapterUnfinishTransactionExplore.ItemAdapterCallback {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding
    private val helper: Helper = Helper
    lateinit var sharedPref: SharedPreferenceHelper

    @SuppressLint("SetTextI18n")
    private lateinit var presenter: ExplorePresenter
    private var baseCurrency: String = "USD"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        presenter = ExplorePresenter(this, requireContext())
        sharedPref = SharedPreferenceHelper(requireContext())

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getListPendingTransaction()
        presenter.getDataUserProfile()
        binding?.menuServices?.linearOther?.setOnClickListener(this)
        binding?.menuServices?.linearNews?.setOnClickListener(this)
        binding?.menuServices?.linearJob?.setOnClickListener(this)
        binding?.covidNews?.tvDetailCovid?.setOnClickListener(this)
        binding?.ivNotification?.setOnClickListener(this)
        binding?.menuServices?.linearTopup?.setOnClickListener(this)
        binding?.menuServices?.linearHotel?.setOnClickListener(this)
        binding?.menuServices?.linearFlightTicket?.setOnClickListener(this)
        binding?.menuServices?.linearDeal?.setOnClickListener(this)
        binding?.menuServices?.linearProperty?.setOnClickListener(this)
//        presenter.loadListBaseCurrency()
//        presenter.loadCovidJkt()
//        presenter.loadHeadlinesNews(Constants.API_HEADLINES_NEWS)
        refresh()
        hideOrShowPendingTransaction(sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN))
    }

    private fun setBaseCurrency(data:List<DataCodeCurrency?>?) {
        if (!data.isNullOrEmpty()){
            val adapterSpinner =AdapterSpinnerCurrency(requireActivity(), data)
            binding?.includeCurrency?.spnCountry?.adapter = adapterSpinner
            binding?.includeCurrency?.spnCountry?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    baseCurrency = data[p2]?.rates.toString()
                    Log.d("currency", baseCurrency)
                    presenter.loadCurrency(baseCurrency)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    binding?.includeCurrency?.spnCountry?.setSelection(90)
                    baseCurrency = "USD"
                    presenter.loadCurrency(baseCurrency)
                }

            }
        }
    }

    private fun hideOrShowPendingTransaction(statusLogin:Boolean){
        if (!statusLogin){
            binding?.shimmerTransactionPending?.visibility = View.GONE
            binding?.includeTransactionPending?.layoutTransactionPending?.visibility = View.GONE
            binding?.includeTransactionPending?.layoutTransactionPending?.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.linear_other -> {
                val intent = Intent(activity, OtherActivity::class.java)
                startActivity(intent)
//                throw RuntimeException("Test Crash")
            }
            R.id.linear_news -> {
                val intent = Intent(activity, NewsActivity::class.java)
                startActivity(intent)
//                binding?.nestedScroll?.post(Runnable {
//                    run() {
//                        binding?.nestedScroll?.smoothScrollTo(0, binding!!.rvNews.top)
//                    }
//                })
            }
            R.id.tv_detail_covid -> {
                val intent = Intent(activity, CovidListActivity::class.java)
                startActivity(intent)
            }
            R.id.linear_job -> {
                val intent = Intent(activity, JobActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_notification -> {
                if(sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)){
                    val intent = Intent(activity, NotificationActivity::class.java)
                    startActivity(intent)
                } else {
                    val snackbar = Snackbar.make(binding?.ivNotification!!, "Please login for access notification", Snackbar.LENGTH_SHORT)
                        .setAction("Close", null)
                    snackbar.show()
                }
            }
            R.id.linear_topup -> {
                val intent = Intent(requireContext(), TopupActivity::class.java)
                startActivity(intent)
            }
            R.id.linear_hotel -> {
                val intent = Intent(requireContext(), HotelBaseActivity::class.java)
                startActivity(intent)
            }

            R.id.linear_flight_ticket -> {
                val flight = Intent(requireContext(), FlightTicketActivity::class.java)
                startActivity(flight)
            }
            R.id.linear_deal -> {
                val intentProperty = Intent(requireContext(), FinishPropertyActivity::class.java)
                startActivity(intentProperty)
            }
            R.id.linear_property -> {
                val intentShowProperty = Intent(requireActivity(), ShowPropertyActivity::class.java)
                startActivity(intentShowProperty)
            }
        }
    }

    private fun refresh() {
        binding?.swipeRefresh?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
//            presenter.loadCovidJkt()
//            presenter.loadListBaseCurrency()
//            presenter.loadHeadlinesNews(Constants.API_HEADLINES_NEWS)
            presenter.getListPendingTransaction()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun errorMessage(msg: String) {
        Log.d("errorExplore", msg)
        binding?.swipeRefresh?.isRefreshing = false
    }

    override fun successMessage(msg: String) {
        Log.d("successExplore", msg)
        binding?.swipeRefresh?.isRefreshing = false
    }

    override fun messageGetTransactionList(msg: String) {
        Log.d("getListTransaction", msg!!)
        binding?.swipeRefresh?.isRefreshing = false
//        if (!msg.contains("Success")){
//            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
//        }
    }

    override fun getTransactionPending(data: List<DataAllTransactionPending?>?) {
        Log.d("dataTransactionPending", data?.size.toString())
        if (data?.isEmpty()!! && sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)){
            binding?.shimmerTransactionPending?.visibility = View.GONE
            binding?.includeTransactionPending?.layoutTransactionPending?.visibility = View.GONE
        } else {
            binding?.shimmerTransactionPending?.visibility = View.GONE
            binding?.includeTransactionPending?.layoutTransactionPending?.visibility = View.VISIBLE
            binding?.includeTransactionPending?.rvTransactionPending?.apply {
                adapter = AdapterUnfinishTransactionExplore(requireContext(), data, this@ExploreFragment)
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun dismissShimmerHeadlineNews() {
        Log.i("Failed", "gone shimmer")
    }

    override fun messageGetDataUSer(msg: String) {
        Log.d("getDataError", msg)
    }

    override fun getDataUser(data: DataUser?) {
        sharedPref.save(Constants.PREF_FULLNAME, data?.fullname.toString())
        sharedPref.save(Constants.PREF_PHONE_NUMBER, data?.phone.toString())
        sharedPref.save(Constants.PREF_EMAIL, data?.email.toString())
        sharedPref.save(Constants.PREF_GENDER, data?.gender.toString())
    }

    private fun showPendingTransaction(total:Int){
        if (total == 0){
            binding?.shimmerTransactionPending?.visibility = View.VISIBLE
            binding?.includeTransactionPending?.layoutTransactionPending?.visibility = View.GONE
        } else {
            binding?.shimmerTransactionPending?.visibility = View.GONE
            binding?.includeTransactionPending?.layoutTransactionPending?.visibility = View.VISIBLE
        }
    }

    override fun loadListCodeCurrency(list: List<DataCodeCurrency?>?) {
        if (!list.isNullOrEmpty()){
            binding?.shimmerCurrency?.visibility = View.GONE
            binding?.includeCurrency?.constraintCurrency?.visibility = View.VISIBLE
            val adapterCodeCurrency = activity?.let { AdapterSpinnerCurrency(it, list) }
            binding?.includeCurrency?.spnCountry?.adapter = adapterCodeCurrency
            binding?.includeCurrency?.spnCountry?.setSelection(100)
            setBaseCurrency(list)
        }

    }

    override fun loadCurrency(item: DataCurrency?) {
        if (item != null){
            binding?.includeCurrency?.tvChnCurrency?.text = helper.convertToFormatMoneyCNY(item.currency?.cNY.toString())
            binding?.includeCurrency?.tvIdrCurrency?.text = helper.convertToFormatMoneyIDR(item.currency?.iDR.toString())
            binding?.includeCurrency?.tvEurCurrency?.text = helper.convertToFormatMoneyUSD(item.currency?.uSD.toString())
        }
    }


    override fun onClickAdapterPending(view: View, data: DataAllTransactionPending) {
        val intent = Intent(context, HistoryTransactionActivity::class.java)
            .putExtra("id", data.orderId)
        requireActivity().startActivity(intent)
    }

    override fun loadCovidJkt(item: DataCovidJkt?) {
        if (item != null){
            binding?.shimmerCovid?.visibility = View.GONE
            binding?.covidNews?.constraintCovid?.visibility = View.VISIBLE
            binding?.covidNews?.tvProvince?.text = item.provinsi.toString()
            binding?.covidNews?.tvTotalCases?.text = item.kasusPosi.toString()
            binding?.covidNews?.tvTotalRecover?.text = item.kasusSemb.toString()
            binding?.covidNews?.tvTotalDie?.text = item.kasusMeni.toString()
        }
    }
//
//    override fun loadHeadlinesNews(list: List<ArticlesItem?>?) {
//        val newsAdapter = activity?.let { AdapterHeadlineNews(it, list) }
//        binding?.rvNews?.apply {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//            adapter = newsAdapter
//        }
//    }
}