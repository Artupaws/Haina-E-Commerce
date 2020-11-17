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
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    lateinit var botnav: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.exploreFragment -> {
                        val exploreFragment = ExploreFragment()
                        val fragmentManager: FragmentManager = childFragmentManager
                        fragmentManager.beginTransaction().replace(R.id.view_botnav, exploreFragment).commit()
                        return true
                    }
                    R.id.mainPageFragment -> {
                        val mainPageFragment = MainPageFragment()
                        val fragmentManager: FragmentManager = childFragmentManager
                        fragmentManager.beginTransaction().replace(R.id.view_botnav, mainPageFragment).commit()
                        return true
                    }
                    R.id.loginFragment ->{
                        val loginFragment = LoginFragment()
                        val fragmentManager: FragmentManager = childFragmentManager
                        fragmentManager.beginTransaction().replace(R.id.view_botnav, loginFragment).commit()
                        return true
                    }
                }
                return false
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragmentManager: FragmentManager = childFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.view_botnav, ExploreFragment()).commit()
    }
}
