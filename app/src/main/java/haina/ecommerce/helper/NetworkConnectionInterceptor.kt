package haina.ecommerce.helper

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import android.net.NetworkInfo

import android.net.ConnectivityManager
import okhttp3.Request
import java.io.IOException


class NetworkConnectionInterceptor(private val context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isConnected()){
            throw NoConnectivityException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}

class NoConnectivityException : IOException() {
    // You can send any message whatever you want from here.
    override val message: String
        get() = "No Internet Connection"
    // You can send any message whatever you want from here.
}