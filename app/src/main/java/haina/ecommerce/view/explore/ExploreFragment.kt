package haina.ecommerce.view.explore

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterCodeCurrency
import haina.ecommerce.adapter.AdapterHeadlineNews
import haina.ecommerce.adapter.AdapterSpinnerCurrency
import haina.ecommerce.databinding.FragmentExploreBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.*
import haina.ecommerce.view.other.OtherActivity

class ExploreFragment : Fragment(), ExploreContract.View, View.OnClickListener {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding
    private lateinit var presenter: ExplorePresenter
    private val helper: Helper = Helper()
    @SuppressLint("SetTextI18n")

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
        presenter.loadDataExplore()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.menuServices?.linearOther?.setOnClickListener(this)
        binding?.menuServices?.linearNews?.setOnClickListener(this)
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    override fun loadHeadlineNews(list: List<ArticlesItem?>?) {
//        val newsAdapter = activity?.let { AdapterHeadlineNews(it, list) }
//        binding?.rvNews?.apply {
//            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//            adapter = newsAdapter
//        }
//    }

    override fun errorMessage(msg: String?) {
        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
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
//        presenter.view.loadListCodeCurrency()
    }

    override fun loadCurrency(item: Data?) {
        binding?.includeCurrency?.tvChnCurrency?.text = helper.convertToFormatMoneyCNY(item?.rates?.cNY.toString())
        binding?.includeCurrency?.tvIdrCurrency?.text = helper.convertToFormatMoneyIDR(item?.rates?.iDR.toString())
        binding?.includeCurrency?.tvEurCurrency?.text = helper.convertToFormatMoneyUSD(item?.rates?.uSD.toString())
    }

//    override fun loadCovidIndo(list: List<DataCovid?>?) {
//        val province: List<DataCovid?>? = list?.filter { it?.provinsi == "DKI Jakarta" }.forEach {  }
//        binding?.covidNews?.tvTotalCases?.text = province.toString()
//        binding?.covidNews?.tvTotalCases?.text = list?.filter { it?.provinsi == "DKI Jakarta" }?.single().toString()
//        binding?.covidNews?.tvTotalCases?.text = list?.groupBy { it?.provinsi}?.forEach { "DKI Jakarta", list }
//    }
}