package haina.ecommerce.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import haina.ecommerce.R
import haina.ecommerce.adapter.TabAdapter
import haina.ecommerce.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarHistory.title = "History"
        binding.viewPagerHistory.adapter = TabAdapter(requireActivity().supportFragmentManager, 0)
        binding.tabLayout.setupWithViewPager(binding.viewPagerHistory)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}