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
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.history.historytransaction.HistoryTransactionActivity
import haina.ecommerce.view.hotels.transactionhotel.HistoryTransactionHotelActivity
import haina.ecommerce.view.notification.NotificationActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    lateinit var handler: Handler
    private var broadcaster: LocalBroadcastManager? = null
    private var firebaseToken: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
        handler = Handler()
        handler.postDelayed({
            if (isConnect()) {
                goToExplore()
            } else {
                goToExplore()
//                binding.viewLoading.visibility = View.INVISIBLE
//                val snackbar = Snackbar.make(binding.viewLoading, "No connection!", Snackbar.LENGTH_INDEFINITE)
//                        .setAction("Let Me In", View.OnClickListener { goToExplore()
//                            binding.viewLoading.visibility = View.VISIBLE
//                        })
//                snackbar.show()
            }
        }, 2000)
    }

    private fun goToExplore() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isConnect(): Boolean {
        val connect: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo!!.isConnected
    }
}