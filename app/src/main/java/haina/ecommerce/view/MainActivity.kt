package haina.ecommerce.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityMainBinding
import haina.ecommerce.view.explore.ExploreFragment
import haina.ecommerce.view.history.HistoryFragment
import haina.ecommerce.view.myaccount.MyAccountFragment
import haina.ecommerce.view.posting.PostingFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    var doubleTap: Boolean = false
    private var popupFillData: Dialog? = null
    private var loginMethod:Int? = null
    private var loginStatus:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginMethod = intent.getIntExtra("loginMethod", 0)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        dialogFillData()
        checkLoginMethod()
        stateOpenFragment()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        when (item.itemId){
            R.id.exploreFragment -> {
                fragment = ExploreFragment()
            }
            R.id.historyFragment -> {
                fragment = HistoryFragment()
            }
            R.id.postingFragment -> {
                fragment = PostingFragment()
            }
            R.id.cartFragment -> {
                fragment = CartFragment()
            }
            R.id.myAccountFragment -> {
                fragment = MyAccountFragment()
            }
        }
        return loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.view_botnav, fragment)
                    .commit()
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

    private fun stateOpenFragment(){
        when (intent.getStringExtra("loginStatus")){
            "1" -> {
                loadFragment(MyAccountFragment())
                binding.bottomNavigationView.menu.findItem(R.id.myAccountFragment).isChecked = true
            }
            "3" -> {
                loadFragment(PostingFragment())
                binding.bottomNavigationView.menu.findItem(R.id.postingFragment).isChecked = true
            }
            else -> {
                loadFragment(ExploreFragment())
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
