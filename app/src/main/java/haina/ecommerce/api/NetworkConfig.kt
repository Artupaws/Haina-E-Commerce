package haina.ecommerce.api

import android.content.Context
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkConfig {

    lateinit var sharedPrefHelper : SharedPreferenceHelper

    private fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(logging)
                .build()
    }

    fun getConnectionHainaBearer(context: Context): NetworkService{
        sharedPrefHelper = SharedPreferenceHelper(context)
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_API_HAINA)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().connectTimeout(300, TimeUnit.SECONDS).addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${sharedPrefHelper.getValueString(Constants.PREF_TOKEN_USER)}")
                            .addHeader("Accept", "application/json")
                            .addHeader("apikey", Constants.APIKEY)
                            .build()
                    chain.proceed(request)
                }.build())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

    fun getConnectionHaina(): NetworkService{
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_API_HAINA)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().connectTimeout(300, TimeUnit.SECONDS).addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("apikey", Constants.APIKEY)
                            .build()
                    chain.proceed(request)
                }.build())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

    fun getNetworkHotel(): NetworkService{
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_API_HAINA_HOTEL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build()
        return retrofit.create(NetworkService::class.java)
    }

    fun getNetworkCurrency(): NetworkService{
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_CURRENCY)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

//    fun getConnectionHaina(): NetworkService {
//        val retrofit: Retrofit = Retrofit.Builder()
//                .baseUrl(Constants.BASE_API_HAINA)
//                .client(getInterceptor())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        return retrofit.create(NetworkService::class.java)
//    }

    fun getHeadlineNews(): NetworkService {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_HEADLINE)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

}