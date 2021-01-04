package haina.ecommerce.api

import haina.ecommerce.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {

    //Register
    @FormUrlEncoded
    @POST("api/register")
    @Headers("No-Authentication: true")
    fun createUser(
            @Field("fullname") fullname: String,
            @Field("email") email: String,
            @Field("username") username: String,
            @Field("phone") phone: String,
            @Field("password") password: String,
            @Field("device_token") deviceToken: String,
            @Field("device_name") deviceName: String
    ): Call<ResponseRegister>

    //Login
    @FormUrlEncoded
    @POST("api/login")
    @Headers("No-Authentication: true")
    fun loginUser(
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("device_token") deviceToken: String,
            @Field("device_name") deviceName: String
            ): Call<ResponseLogin>

    //Currency
    @FormUrlEncoded
    @POST("api/currency")
    fun getDataCurrency(
            @Field("base") base: String
    ): Call<ResponseCurrency>

    //Headlines
//    @GET("v2/top-headlines?sources=bbc-news")
//    fun getDataHeadlines(@Query("apiKey")apiKey: String): Call<ResponseHeadlineNews>

    //Covid Indo
    @GET("api/covid")
    fun getDataCovidIndo(): Call<ResponseCovid>

    //Covid Jakarta
    @GET("api/covid/jkt")
    @Headers("No-Authentication: true")
    fun getDataCovidJkt(): Call<ResponseCovidJkt>

    //Get List Base Currency
    @GET("api/currency/list")
    fun getDataListBaseCurrency(): Call<ResponseBaseCurrency>

    //Get List Job Category
    @GET("api/jobs/category")
    fun getDataListJobCategory(): Call<ResponseJobCategory>

    //Get List Job Location
    @GET("api/location")
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
            @Part photo: MultipartBody.Part,
            @Part("title") title: RequestBody,
            @Part("id_address") idLocation: RequestBody,
            @Part("id_category") idCategory: RequestBody,
            @Part("description") description: RequestBody,
            @Part("salary_from") salaryFrom: RequestBody,
            @Part("salary_to") salaryTo: RequestBody
    ): Call<ResponsePostingJobVacancy>

    //Get List Job Vacancy
    @FormUrlEncoded
    @POST("api/jobs/vacancy")
    @Headers("No-Authentication: true")
    fun getListJobVacancy(
            @FieldMap data: Map<String, Int>
    ):Call<ResponseGetJob>

    //Change Image Profile
    @Multipart
    @POST("api/photo")
    @Headers("No-Authentication: true")
    fun changeImageProfile(@Part file: MultipartBody.Part):Call<ResponseChangeImageProfile>

    //Get My Post Job Vacancy
    @POST("api/company/jobs")
    @Headers("No-Authentication: true")
    fun getMyPost():Call<ResponseGetMyPost>

    //Check Register Company
    @POST("/api/company")
    @Headers("No-Authentication: true")
    fun checkRegisterCompany():Call<ResponseCheckRegisterCompany>

    //Register Company
    @Multipart
    @POST("api/company/register")
    @Headers("No-Authentication: true")
    fun registerCompany(
            @Part file: MultipartBody.Part,
            @Part("name") name: RequestBody,
            @Part("description") description: RequestBody
    ):Call<ResponseRegisterCompany>

    //Get Data Company
    @POST("api/company")
    @Headers("No-Authentication: true")
    fun getDataCompany():Call<ResponseCheckRegisterCompany>

    //Add Image Company
    @Multipart
    @POST("api/company/photo/register")
    @Headers("No-Authentication: true")
    fun addPhotoCompany(
            @Part file: MultipartBody.Part,
            @Part("name") name: RequestBody,
            @Part("id_company") idCompany: RequestBody
    ):Call<ResponseAddImageCompany>

    //Add Address Company
    @FormUrlEncoded
    @POST("api/company/address/register")
    @Headers("No-Authentication: true")
    fun addAddressCompany(
            @Field("id_company") idCompany: String,
            @Field("address") address: String,
            @Field("id_city") idCity: String
    ):Call<ResponseAddAddressCompany>

    //Delete Image Company
    @FormUrlEncoded
    @POST("api/company/photo/delete")
    @Headers("No-Authentication: true")
    fun deleteImageCompany(
            @Field("id") id: Int
    ):Call<ResponseDeletedPhotoCompany>

    //Upload Document
    @Multipart
    @POST("api/docs/add")
    @Headers("No-Authentication: true")
    fun uploadDocument(
            @Part file: MultipartBody.Part,
            @Part("name") name: RequestBody,
            @Part("id_docs_category") idDocCategory: RequestBody
    ):Call<ResponseUploadDocument>

    //Load List Document User
    @FormUrlEncoded
    @POST("api/docs")
    @Headers("No-Authentication: true")
    fun loadDocumentUser(
        @Field("id_docs_category") idDocsCategory:Int
    ):Call<ResponseLoadDocumentUser>
}