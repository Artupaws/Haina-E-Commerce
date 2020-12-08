package haina.ecommerce.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityMainBinding
import haina.ecommerce.view.explore.ExploreFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentExplore = ExploreFragment()
    private val fragmentHistory = HistoryFragment()
    private val fragmentSell = PostingFragment()
    private val fragmentCart = CartFragment()
    private val fragmentMyAccount = MyAccountFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = fragmentExplore
//    var status: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager.beginTransaction().apply {
            add(R.id.view_botnav, fragmentMyAccount).hide(fragmentMyAccount)
            add(R.id.view_botnav, fragmentCart).hide(fragmentCart)
            add(R.id.view_botnav, fragmentSell).hide(fragmentSell)
            add(R.id.view_botnav, fragmentHistory).hide(fragmentHistory)
            add(R.id.view_botnav, fragmentExplore).hide(fragmentExplore)
        }.commit()

        when (intent.extras?.getString("loginStatus").toString()) {
            "1" -> {
                loadFragment(fragmentMyAccount)
                hideFragment(fragmentExplore)
                activeFragment = fragmentMyAccount
                binding.bottomNavigationView.menu.findItem(R.id.myAccountFragment).isChecked = true
            }
            else -> {
                loadFragment(fragmentExplore)
                activeFragment = fragmentExplore
                binding.bottomNavigationView.menu.findItem(R.id.exploreFragment).isChecked = true
            }
        }

        initListeners()
    }

    private fun initListeners() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.exploreFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(fragmentExplore)
                            .commit()
                    activeFragment = fragmentExplore
                    true
                }

                R.id.historyFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(fragmentHistory).commit()
                    activeFragment = fragmentHistory
                    true
                }

                R.id.sellFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(fragmentSell).commit()
                    activeFragment = fragmentSell
                    true
                }

                R.id.cartFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(fragmentCart).commit()
                    activeFragment = fragmentCart
                    true
                }

                R.id.myAccountFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(fragmentMyAccount).commit()
                    activeFragment = fragmentMyAccount
                    true
                }

                else -> false
            }
        }
    }

    private fun hideFragment(fragment: Fragment?): Boolean {
        if (fragment !=null){
            supportFragmentManager.beginTransaction().hide(fragment).commit()
            return true
        }
        return false
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().show(fragment)
                    .commit()
            return true
        }
        return false
    }

    override fun onPause() {
        super.onPause()
//        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}