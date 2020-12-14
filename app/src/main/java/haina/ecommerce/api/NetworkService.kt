package haina.ecommerce.api

import haina.ecommerce.model.*
import okhttp3.MultipartBody
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

    //Login
    @FormUrlEncoded
    @POST("api/login")
    fun loginUser(
            @Field("email") email: String,
            @Field("password") password:String,
            @Field("device_token") deviceToken:String,
            @Field("api_key") apiKey: String
    ): Call<ResponseLogin>

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
    fun getDataListBaseCurrency(): Call<ResponseBaseCurrency>

    //Get List Job Category
    @GET ("api/jobs/category")
    fun getDataListJobCategory(): Call<ResponseJobCategory>

    //Get List Job Location
    @GET ("api/location")
    fun getDataListJobLocation(): Call<ResponseListJobLocation>

    //Get Data User
    @FormUrlEncoded
    @POST("api/detail")
    @Headers("No-Authentication: true")
    fun getDataUser(
            @Field("api_key") apiKey: String
    ): Call<ResponseGetDataUser>

    //User Logout
    @FormUrlEncoded
    @POST("api/logout")
    @Headers("No-Authentication: true")
    fun userLogout(
            @Field("api_key") apiKey: String
    ): Call<ResponseLogout>

    //Posting Job Vacancy
    @Multipart
    @POST("api/jobs/vacancy/post")
    @Headers("No-Authentication: true")
    fun postingJobVacancy(
            @Part imageCompany:MultipartBody.Part,
            @Part("title")title:String,
            @Part("id_location")idLocation:String,
            @Part("id_category")idCategory:String,
            @Part("description")description:String,
            @Part("salary_from")salaryFrom:String,
            @Part("salary_to")salaryTo:String,
            @Part("api_key")apiKey:String
    ): Call<ResponsePostingJobVacancy>

}