package haina.ecommerce.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterNews
import haina.ecommerce.databinding.FragmentExploreBinding
import haina.ecommerce.model.News
import haina.ecommerce.network.ConfigNetwork

class ExploreFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private val apiCurrency: String = R.string.api_token_currency.toString()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listNews = arrayListOf(
                News(title = "Trump Continues to Postpone Transition, Biden: Many People Will Die", "CNBC Indonesia", R.drawable.biden),
                News(title = "Whatsapp Change Archive Feature, from Holiday Mode to Read Later", "CNN", R.drawable.whatsapp),
                News(title = "How to Play PS5 Games on PS4", "CNN", R.drawable.ps5),
                News(title = "Gold Price Today November 17th, Down to IDR 980 Thousand per Gram", "CNN", R.drawable.gold),
                News(title = "Pakistan is planning to turn Bollywood legend's house into a museum", "CNN", R.drawable.pakistan),
                News(title = "Erdogan Visits Northern Cyprus, Greece and Cyprus Furious", "CNN", R.drawable.erdogan),
                News(title = "Recognizing the Meaning of the 'Prestigious List' of UNESCO Biosphere Reserves", "CNN", R.drawable.unesco)
        )

        val newsAdapter = AdapterNews(listNews)
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }

//        val listCountry = arrayListOf<CountryCurrency>()
//
//        val currencyAdapter = context?.let { AdapterSpinnerCurrency(it, listCountry) }
//        binding.includeCurrency.spnCountry.adapter = currencyAdapter

        binding.menuServices.linearOther.setOnClickListener(this)
        binding.menuServices.linearNews.setOnClickListener(this)

    }

    companion object{
        fun newInstance(): ExploreFragment{
            val fragment = ExploreFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.linear_other -> {
                Navigation.findNavController(p0).navigate(HomeFragmentDirections.actionHomeFragmentToOtherFragment())
            }
            R.id.linear_news -> {
                binding.nestedScroll.post(Runnable {
                    run() {
                        binding.nestedScroll.smoothScrollTo(0, binding.rvNews.top)
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}