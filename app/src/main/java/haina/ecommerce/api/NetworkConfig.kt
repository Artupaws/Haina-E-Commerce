package haina.ecommerce.api

import haina.ecommerce.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkConfig {

    fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
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

    fun getCovidIndo(): NetworkService {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_COVID_INDO)
                .client(getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(NetworkService::class.java)
    }

}