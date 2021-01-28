package haina.ecommerce.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.Window
import android.widget.TextView
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
    private var popupFillData: Dialog? = null
    private var loginMethod:Int? = null
    private var loginStatus:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginMethod = intent.getIntExtra("loginMethod", 0)
        loginStatus = intent.getStringExtra("loginStatus")
        fragmentManager.beginTransaction().apply {
            add(R.id.view_botnav, fragmentMyAccount).hide(fragmentMyAccount)
            add(R.id.view_botnav, fragmentCart).hide(fragmentCart)
            add(R.id.view_botnav, fragmentPosting).hide(fragmentPosting)
            add(R.id.view_botnav, fragmentHistory).hide(fragmentHistory)
            add(R.id.view_botnav, fragmentExplore).hide(fragmentExplore)
        }.disallowAddToBackStack().commit()

        initListeners()
        dialogFillData()
        checkLoginMethod()
        loginStatus()

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

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogFillData(){
        popupFillData = Dialog(this)
        popupFillData?.setContentView(R.layout.popup_fill_personal_data)
        popupFillData?.setCancelable(false)
        popupFillData?.window?.setBackgroundDrawable(applicationContext.getDrawable(android.R.color.transparent))
        val window:Window = popupFillData?.window!!
        window.setGravity(Gravity.CENTER)
        window.attributes.windowAnimations = R.style.DialogAnimation
        val title = popupFillData?.findViewById<TextView>(R.id.tv_title)
        val ok = popupFillData?.findViewById<TextView>(R.id.tv_action_yes)
        title?.text  = applicationContext.getString(R.string.title_attention)
        ok?.setOnClickListener {
            popupFillData?.dismiss()
        }
    }

    private fun loginStatus(){
        when (loginStatus) {
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

    private fun checkLoginMethod(){
        if (loginMethod == 0){
            popupFillData?.dismiss()
        } else if (loginMethod == 1){
            popupFillData?.show()
        }
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
