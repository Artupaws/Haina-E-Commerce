package haina.ecommerce.api

import haina.ecommerce.model.Rates
import haina.ecommerce.model.ResponseCurrency
import haina.ecommerce.model.ResponseHeadlineNews
import haina.ecommerce.util.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ApiCurrency {

    private fun getCurrency(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_CURRENCY)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getInstance(): ApiInterfaceCurrency{
        return getCurrency().create(ApiInterfaceCurrency::class.java)
    }

}

interface ApiInterfaceCurrency{
    @GET("latest?apikey=960e475d7c3340c8bb54f12fd3fe9fbb")
    fun getCurrency(): Call<ResponseCurrency>

}