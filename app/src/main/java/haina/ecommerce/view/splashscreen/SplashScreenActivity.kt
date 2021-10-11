package haina.ecommerce.view.splashscreen

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import haina.ecommerce.databinding.ActivitySplashScreenBinding
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.history.historytransaction.HistoryTransactionActivity
import haina.ecommerce.view.hotels.transactionhotel.HistoryTransactionHotelActivity
import haina.ecommerce.view.myaccount.MyAccountPresenter
import haina.ecommerce.view.notification.NotificationActivity

class SplashScreenActivity : AppCompatActivity(), SplashScreenContract.View {

    private lateinit var binding: ActivitySplashScreenBinding
    lateinit var handler: Handler
    private var broadcaster: LocalBroadcastManager? = null
    private var firebaseToken: String = ""
    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var presenter: SplashScreenPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SplashScreenPresenter(this, applicationContext)


        sharedPref = SharedPreferenceHelper(this)
        val dataParams= intent.extras
        Log.d("dataNotification", dataParams?.get("page").toString())
        when(dataParams?.get("page")){
            "Transaction"-> {
                val intent = Intent(applicationContext, HistoryTransactionActivity::class.java)
                intent.putExtra("tabs", dataParams.get("tabs").toString())
                startActivity(intent)
            }
            "Job"->{
                val intent = Intent(applicationContext, NotificationActivity::class.java)
                intent.putExtra("notif", true)
                startActivity(intent)
            }
            "Hotel"->{
                val intent = Intent(applicationContext, HistoryTransactionHotelActivity::class.java)
                intent.putExtra("tabs", dataParams.get("tabs").toString())
                startActivity(intent)
            }
            else -> {
                letMeIn()
        }
        }
    }

    private fun letMeIn(){

        if (isConnect()) {
            presenter.getServerStatus()
        } else {
            binding.viewLoading.visibility = View.INVISIBLE
            val snackbar = Snackbar.make(binding.viewLoading, "No connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Let Me In", View.OnClickListener { goToExplore()
                    binding.viewLoading.visibility = View.VISIBLE
                })
            snackbar.show()
        }

    }

    private fun goToExplore() {
        if (sharedPref.getValueString(Constants.LANGUAGE_APP) == null){
            sharedPref.save(Constants.LANGUAGE_APP, "en")
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        } else{
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    private fun isConnect(): Boolean {
        val connect: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
    }


    override fun serverOnline(msg: String) {
        handler = Handler()
        handler.postDelayed({
            goToExplore()
        }, 2000)
    }

    override fun serverOffline(msg: String) {
    }

    override fun showLoading() {
        binding.viewLoading.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        binding.viewLoading.visibility = View.INVISIBLE
    }
}