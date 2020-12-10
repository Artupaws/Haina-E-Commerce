package haina.ecommerce.api

import android.content.Context
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkConfig {

    lateinit var sharedPrefHelper : SharedPreferenceHelper

    private fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
    }

    fun getConnectionHainaHeaders(context: Context): NetworkService{
        sharedPrefHelper = SharedPreferenceHelper(context)
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_API_HAINA)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${sharedPrefHelper.getValueString(Constants.PREF_TOKEN_USER)}")
                            .addHeader("Accept","application/json")
                            .build()
                    chain.proceed(request)
                }.build())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

    fun getConnectionHaina(): NetworkService {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_API_HAINA)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

    fun getHeadlineNews(): NetworkService {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_HEADLINE)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

}