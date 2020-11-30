package haina.ecommerce.view.explore

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterHeadlineNews
import haina.ecommerce.databinding.FragmentExploreBinding
import haina.ecommerce.model.ArticlesItem
import haina.ecommerce.view.HomeFragmentDirections

class ExploreFragment : Fragment(), ExploreContract.View, View.OnClickListener {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding
    private lateinit var presenter: ExploreContract.Presenter

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
        presenter.loadExplore()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.menuServices?.linearOther?.setOnClickListener(this)
        binding?.menuServices?.linearNews?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.linear_other -> {
                Navigation.findNavController(p0).navigate(HomeFragmentDirections.actionHomeFragmentToOtherFragment())
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

    override fun loadHeadlineNews(list: List<ArticlesItem?>?) {
        val newsAdapter = activity?.let { AdapterHeadlineNews(it, list) }
        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
    }

    override fun errorMessage(msg: String?) {
        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
    }

    override fun showShimmerHeadlineNews() {
        Log.i("Success", "gone shimmer")
    }

    override fun dismissShimmerHeadlineNews() {
        Log.i("Failed", "gone shimmer")
    }
}