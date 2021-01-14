package haina.ecommerce.view

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityMainBinding
import haina.ecommerce.view.explore.ExploreFragment
import haina.ecommerce.view.history.HistoryFragment
import haina.ecommerce.view.myaccount.MyAccountFragment
import haina.ecommerce.view.posting.PostingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentExplore = ExploreFragment()
    private val fragmentHistory = HistoryFragment()
    private val fragmentPosting = PostingFragment()
    private val fragmentCart = CartFragment()
    private val fragmentMyAccount = MyAccountFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = fragmentExplore
    var doubleTap: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager.beginTransaction().apply {
            add(R.id.view_botnav, fragmentMyAccount).hide(fragmentMyAccount)
            add(R.id.view_botnav, fragmentCart).hide(fragmentCart)
            add(R.id.view_botnav, fragmentPosting).hide(fragmentPosting)
            add(R.id.view_botnav, fragmentHistory).hide(fragmentHistory)
            add(R.id.view_botnav, fragmentExplore).hide(fragmentExplore)
        }.disallowAddToBackStack().commit()

        initListeners()

        when (intent.extras?.getString("loginStatus").toString()) {
            "1" -> {
                loadFragment(fragmentMyAccount)
                hideFragment(fragmentExplore)
                activeFragment = fragmentMyAccount
                binding.bottomNavigationView.menu.findItem(R.id.myAccountFragment).isChecked = true
            }
            "3" -> {
                loadFragment(fragmentPosting)
                hideFragment(fragmentExplore)
                activeFragment = fragmentPosting
                binding.bottomNavigationView.menu.findItem(R.id.postingFragment).isChecked = true
            }
            else -> {
                loadFragment(fragmentExplore)
                activeFragment = fragmentExplore
                binding.bottomNavigationView.menu.findItem(R.id.exploreFragment).isChecked = true
            }
        }

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

                R.id.postingFragment -> {
                    fragmentManager.beginTransaction().hide(activeFragment).show(fragmentPosting).commit()
                    activeFragment = fragmentPosting
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
        if (fragment != null) {
            supportFragmentManager.beginTransaction().hide(fragment).commit()
            return true
        }
        return false
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().show(fragment).commit()
            return true
        }
        return false
    }

    override fun onBackPressed() {
        if (doubleTap) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Please click once again to exit", Toast.LENGTH_SHORT).show()
            doubleTap = true
            val handler: Handler = Handler()
            handler.postDelayed({ doubleTap = false }, 500)
        }
    }

}