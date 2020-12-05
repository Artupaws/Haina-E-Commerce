package haina.ecommerce.api

import haina.ecommerce.model.*
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    //Register
    @FormUrlEncoded
    @POST("api/register")
    fun createUser(
            @Field("fullname") fullname: String,
            @Field("email") email: String,
            @Field("username") username: String,
            @Field("phone") phone: String,
            @Field("password") password: String,
            @Field("api_key") apiKey: String,
            @Field("device_token") deviceToken: String
    ): Call<ResponseRegister>

    //Currency
    @FormUrlEncoded
    @POST("api/currency")
    fun getDataCurrency(
            @Field("base") base:String
    ): Call<ResponseCurrency>

    //Headlines
    @GET("v2/top-headlines?sources=bbc-news")
    fun getDataHeadlines(@Query("apiKey")apiKey: String): Call<ResponseHeadlineNews>

    //Covid Indo
    @GET("api/covid")
    fun getDataCovidIndo(): Call<ResponseCovid>

    //Covid Jakarta
    @GET("api/covid/jkt")
    fun getDataCovidJkt(): Call<ResponseCovidJkt>

    //Get List Base Currency
    @GET("api/currency/list")
    fun getDataListBaseCurrency(): Call<ResponseCodeCurrency>

    //Get List Job Category
    @GET ("api/jobs/category")
    fun getDataListJobCategory(): Call<ResponseJobCategory>

}