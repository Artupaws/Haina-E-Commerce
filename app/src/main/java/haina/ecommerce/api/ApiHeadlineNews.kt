package haina.ecommerce.api

import haina.ecommerce.model.ResponseHeadlineNews
import haina.ecommerce.util.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class ApiHeadlineNews {

    private fun getHeadlineNews(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_NEWS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getInstance(): ApiInterface{
        return getHeadlineNews().create(ApiInterface::class.java)
    }

}

interface ApiInterface{
    @GET("v2/top-headlines?sources=bbc-news")
    fun getHeadlines(@Query("apiKey")apiKey: String): Call<ResponseHeadlineNews>

}