package haina.ecommerce.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import haina.ecommerce.adapter.TabAdapterHistory
import haina.ecommerce.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbarHistory?.title = "History"
        binding?.viewPagerHistory?.adapter = TabAdapterHistory(requireActivity().supportFragmentManager, 0)
//        binding?.viewPagerHistory?.offscreenPageLimit = 4
        binding?.tabLayoutHistory?.setupWithViewPager(binding?.viewPagerHistory)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}