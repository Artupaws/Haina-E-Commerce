package haina.ecommerce.view.explore

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterCodeCurrency
import haina.ecommerce.adapter.AdapterHeadlineNews
import haina.ecommerce.adapter.AdapterSpinnerCurrency
import haina.ecommerce.databinding.FragmentExploreBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.*
import haina.ecommerce.util.Constants
import haina.ecommerce.view.covidlist.CovidListActivity
import haina.ecommerce.view.other.OtherActivity

class ExploreFragment : Fragment(), ExploreContract, View.OnClickListener {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding
    private val helper: Helper = Helper()
    @SuppressLint("SetTextI18n")
    private lateinit var presenter: ExplorePresenter
    private var baseCurrency: String = "USD"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        presenter = ExplorePresenter(this)

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
//        presenter.loadListBaseCurrency()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.menuServices?.linearOther?.setOnClickListener(this)
        binding?.menuServices?.linearNews?.setOnClickListener(this)
        binding?.covidNews?.tvDetailCovid?.setOnClickListener(this)
        presenter.loadListBaseCurrency()
        presenter.loadCovidJkt()
//        presenter.loadHeadlinesNews(Constants.API_HEADLINES_NEWS)
        setBaseCurrency()
    }

    private fun setBaseCurrency(){
        binding?.includeCurrency?.spnCountry?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                baseCurrency = binding?.includeCurrency?.spnCountry?.selectedItem.toString()
                when {
                    baseCurrency.contains("USD") -> {
                        baseCurrency = "USD"
                    }
                    baseCurrency.contains("FJD") -> {
                        baseCurrency = "FJD"
                    }
                    baseCurrency.contains("MXN") -> {
                        baseCurrency = "MXN"
                    }
                    baseCurrency.contains("STD") -> {
                        baseCurrency = "STD"
                    }
                    baseCurrency.contains("LVL") -> {
                        baseCurrency = "LVL"
                    }
                    baseCurrency.contains("SCR") -> {
                        baseCurrency = "SCR"
                    }
                    baseCurrency.contains("CDF") -> {
                        baseCurrency = "CDF"
                    }
                    baseCurrency.contains("BBD") -> {
                        baseCurrency = "BBD"
                    }
                    baseCurrency.contains("GTQ") -> {
                        baseCurrency = "GTQ"
                    }
                    baseCurrency.contains("CLP") -> {
                        baseCurrency = "CLP"
                    }
                    baseCurrency.contains("HNL") -> {
                        baseCurrency = "UGX"
                    }
                    baseCurrency.contains("ZAR") -> {
                        baseCurrency = "TND"
                    }
                    baseCurrency.contains("CUC") -> {
                        baseCurrency = "CUC"
                    }
                    baseCurrency.contains("BSD") -> {
                        baseCurrency = "BSD"
                    }
                    baseCurrency.contains("SSL") -> {
                        baseCurrency = "SDG"
                    }
                    baseCurrency.contains("SDG") -> {
                        baseCurrency = "SDG"
                    }
                    baseCurrency.contains("IQD") -> {
                        baseCurrency = "IQD"
                    }
                    baseCurrency.contains("CUP") -> {
                        baseCurrency = "CUP"
                    }
                    baseCurrency.contains("GMD") -> {
                        baseCurrency = "GMD"
                    }
                    baseCurrency.contains("TWD") -> {
                        baseCurrency = "TWD"
                    }
                    baseCurrency.contains("RSD") -> {
                        baseCurrency = "RSD"
                    }
                    baseCurrency.contains("DOP") -> {
                        baseCurrency = "DOP"
                    }
                    baseCurrency.contains("KMF") -> {
                        baseCurrency = "KMF"
                    }
                    baseCurrency.contains("BCH") -> {
                        baseCurrency = "BCH"
                    }
                    baseCurrency.contains("MYR") -> {
                        baseCurrency = "MYR"
                    }
                    baseCurrency.contains("FKP") -> {
                        baseCurrency = "FKP"
                    }
                    baseCurrency.contains("XOF") -> {
                        baseCurrency = "XOF"
                    }
                    baseCurrency.contains("GEL") -> {
                        baseCurrency = "GEL"
                    }
                    baseCurrency.contains("BTC") -> {
                        baseCurrency = "BTC"
                    }
                    baseCurrency.contains("UYU") -> {
                        baseCurrency = "MAD"
                    }
                    baseCurrency.contains("CVE") -> {
                        baseCurrency = "CVE"
                    }
                    baseCurrency.contains("TOP") -> {
                        baseCurrency = "TOP"
                    }
                    baseCurrency.contains("AZN") -> {
                        baseCurrency = "AZN"
                    }
                    baseCurrency.contains("OMR") -> {
                        baseCurrency = "OMR"
                    }
                    baseCurrency.contains("PGK") -> {
                        baseCurrency = "PGK"
                    }
                    baseCurrency.contains("KES") -> {
                        baseCurrency = "KES"
                    }
                    baseCurrency.contains("SEK") -> {
                        baseCurrency = "SEK"
                    }
                    baseCurrency.contains("BTN") -> {
                        baseCurrency = "BTN"
                    }
                    baseCurrency.contains("UAH") -> {
                        baseCurrency = "UAH"
                    }
                    baseCurrency.contains("GNF") -> {
                        baseCurrency = "GNF"
                    }
                    baseCurrency.contains("ERN") -> {
                        baseCurrency = "ERN"
                    }
                    baseCurrency.contains("MZN") -> {
                        baseCurrency = "MZN"
                    }
                    baseCurrency.contains("SVC") -> {
                        baseCurrency = "SVC"
                    }
                    baseCurrency.contains("ARS") -> {
                        baseCurrency = "ARS"
                    }
                    baseCurrency.contains("QAR") -> {
                        baseCurrency = "QAR"
                    }
                    baseCurrency.contains("IRR") -> {
                        baseCurrency = "IRR"
                    }
                    baseCurrency.contains("MRO") -> {
                        baseCurrency = "MRO"
                    }
                    baseCurrency.contains("XPD") -> {
                        baseCurrency = "XPD"
                    }
                    baseCurrency.contains("CNY") -> {
                        baseCurrency = "CNY"
                    }
                    baseCurrency.contains("THB") -> {
                        baseCurrency = "THB"
                    }
                    baseCurrency.contains("EUR") -> {
                        baseCurrency = "EUR"
                    }
                }
                presenter.loadCurrency(baseCurrency)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                baseCurrency = "USD"
            }

        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.linear_other -> {
                val intent = Intent(activity, OtherActivity::class.java)
                startActivity(intent)
            }
            R.id.linear_news -> {
                binding?.nestedScroll?.post(Runnable {
                    run() {
                        binding?.nestedScroll?.smoothScrollTo(0, binding!!.rvNews.top)
                    }
                })
            }
            R.id.tv_detail_covid -> {
                val intent = Intent(activity, CovidListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun errorMessage(msg: String?) {
//        Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
    }

    override fun showShimmerHeadlineNews() {
        Log.i("Success", "gone shimmer")
    }

    override fun dismissShimmerHeadlineNews() {
        Log.i("Failed", "gone shimmer")
    }

    override fun loadListCodeCurrency(list: List<DataCodeCurrency?>?) {
        val adapterCodeCurrency = activity?.let { AdapterSpinnerCurrency(it, list) }
        binding?.includeCurrency?.spnCountry?.adapter = adapterCodeCurrency
        binding?.includeCurrency?.spnCountry?.setSelection(67)
    }

    override fun loadCurrency(item: Data?) {
        binding?.includeCurrency?.tvChnCurrency?.text = helper.convertToFormatMoneyCNY(item?.rates?.cNY.toString())
        binding?.includeCurrency?.tvIdrCurrency?.text = helper.convertToFormatMoneyIDR(item?.rates?.iDR.toString())
        binding?.includeCurrency?.tvEurCurrency?.text = helper.convertToFormatMoneyUSD(item?.rates?.uSD.toString())
    }

    override fun loadCovidJkt(item: DataCovidJkt?) {
        binding?.covidNews?.tvProvince?.text = item?.provinsi.toString()
        binding?.covidNews?.tvTotalCases?.text = item?.kasusPosi.toString()
        binding?.covidNews?.tvTotalRecover?.text = item?.kasusSemb.toString()
        binding?.covidNews?.tvTotalDie?.text = item?.kasusMeni.toString()
    }

//    override fun loadHeadlinesNews(list: List<ArticlesItem?>?) {
//        val newsAdapter = activity?.let { AdapterHeadlineNews(it, list) }
//        binding?.rvNews?.apply {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//            adapter = newsAdapter
//        }
//    }
}