package haina.ecommerce.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentHomeBinding
import haina.ecommerce.view.explore.ExploreFragment


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val fragmentExplore: Fragment = ExploreFragment()
    private val fragmentHistory: Fragment = HistoryFragment()
    private val fragmentSell: Fragment = SellFragment()
    private val fragmentCart: Fragment = CartFragment()
    private val fragmentMyAccount: Fragment = MyAccountFragment()
    private var activeFragment: Fragment = fragmentExplore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.exploreFragment -> {
                        val exploreFragment = ExploreFragment()
                        val fragmentManager: FragmentManager = childFragmentManager
                        fragmentManager.beginTransaction()
                            .replace(R.id.view_botnav, exploreFragment).commit()
                        item.isChecked = true
                        return true
                    }
                    R.id.historyFragment -> {
                        val historyFragment = HistoryFragment()
                        val fragmentManager: FragmentManager = childFragmentManager
                        fragmentManager.beginTransaction()
                            .add(R.id.view_botnav, historyFragment).commit()
                        return true
                    }
                    R.id.sellFragment -> {
                        val sellFragment = SellFragment()
                        val fragmentManager: FragmentManager = childFragmentManager
                        fragmentManager.beginTransaction()
                            .replace(R.id.view_botnav, sellFragment).commit()
                        return true
                    }
                    R.id.cartFragment -> {
                        val cartFragment = CartFragment()
                        val fragmentManager: FragmentManager = childFragmentManager
                        fragmentManager.beginTransaction()
                            .replace(R.id.view_botnav, cartFragment).commit()
                        return true
                    }
                    R.id.myAccountFragment -> {
                        val myAccountFragment = MyAccountFragment()
                        val fragmentManager: FragmentManager = childFragmentManager
                        fragmentManager.beginTransaction()
                            .replace(R.id.view_botnav, myAccountFragment).commit()
                        return true
                    }
                }
                return false
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {

        }

        val fragmentManager: FragmentManager = childFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.view_botnav, ExploreFragment()).commit()
        binding.bottomNavigationView.selectedItemId = R.id.exploreFragment

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
