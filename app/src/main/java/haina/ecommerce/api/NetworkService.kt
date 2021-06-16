package haina.ecommerce.api

import haina.ecommerce.model.*
import haina.ecommerce.model.bill.ResponseAddBillTransaction
import haina.ecommerce.model.bill.ResponseGetBillAmount
import haina.ecommerce.model.bill.ResponseGetBillDirect
import haina.ecommerce.model.checkout.ResponseCheckout
import haina.ecommerce.model.currency.ResponseGetCurrency
import haina.ecommerce.model.flight.*
import haina.ecommerce.model.hotels.*
import haina.ecommerce.model.hotels.transactionhotel.ResponseGetTransactionHotel
import haina.ecommerce.model.howtopay.ResponseGetHowToPay
import haina.ecommerce.model.news.ResponseGetListNews
import haina.ecommerce.model.notification.ResponseGetNotification
import haina.ecommerce.model.paymentmethod.ResponsePaymentMethod
import haina.ecommerce.model.productservice.ResponseGetProductService
import haina.ecommerce.model.pulsaanddata.ResponseGetProductPhone
import haina.ecommerce.model.service.ResponseGetService
import haina.ecommerce.model.transaction.ResponseCreateTransactionProductPhone
import haina.ecommerce.model.transactionlist.ResponseGetListTransaction
import haina.ecommerce.model.transactionlist.ResponseGetListTransactionPending
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
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

    //LoginWithGoogle
    @FormUrlEncoded
    @POST("api/login/google")
    @Headers("No-Authentication: true")
    fun loginGoogle(
        @Field("firebase_token") firebaseToken: String?,
        @Field("device_token") deviceToken: String
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

    //Get News
    @GET("api/news/get")
    fun getNews(
        @Query("lang")lang:String
    ):Call<ResponseGetListNews>

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
    @POST("api/detail")
    @Headers("No-Authentication: true")
    fun getDataUser(): Call<ResponseGetDataUser>

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
    fun getMyPostJob():Call<ResponseMyJob>

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

    //Add Personal Data User
    @FormUrlEncoded
    @POST("api/profile")
    @Headers("No-Authentication: true")
    fun addPersonalDataUser(
            @Field("fullname") fullname:String,
            @Field("birthdate") birthdate:String,
            @Field("gender") gender:String,
            @Field("address") address:String,
            @Field("about") about:String
    ):Call<ResponseAddPersonalData>

    //Add Skills
    @FormUrlEncoded
    @POST("api/skill/add")
    @Headers("No-Authentication: true")
    fun addSkillsUser(
            @Field("skill_name") skillName:String
    ):Call<ResponseAddSkills>

    //Get List Skill User
    @POST("api/skill")
    @Headers("No-Authentication: true")
    fun getListSkill():Call<ResponseGetUserSkills>

    //Delete Skills User
    @FormUrlEncoded
    @POST("api/skill/delete")
    @Headers("No-Authentication: true")
    fun deleteSkills(
            @Field("skill_name") skillName:String
    ):Call<ResponseDeleteSkills>

    //Apply Job
    @FormUrlEncoded
    @POST("api/jobs/application/post")
    @Headers("No-Authentication: true")
    fun applyJob(
        @Field("id_job_vacancy")idJobVacancy:Int,
        @Field("id_user_docs")idUserDocs:Int
    ):Call<ResponseApplyJob>

    //Check Applied Job Vacancy
    @FormUrlEncoded
    @POST("api/jobs/check")
    @Headers("No-Authentication: true")
    fun checkAppliedJob(
        @Field("id_job")idJob:Int
    ):Call<ResponseCheckAppliedJob>

    //Add Requires Skill Job
    @FormUrlEncoded
    @POST("api/jobs/vacancy/addskill")
    @Headers("No-Authentication: true")
    fun addRequiresSKill(
        @Field("skill_name")skillName:String,
        @Field("id_job_vacancy")idJobVacancy:Int
    ):Call<ResponseAddSkills>

    //Get SkillRequires
    @FormUrlEncoded
    @POST("api/jobs/vacancy/getskill")
    @Headers("No-Authentication: true")
    fun getSkillRequires(
        @Field("id_job_vacancy")idJobVacancy:Int
    ):Call<ResponseGetSkillRequires>

    //Get Job Application Applicant
    @POST("api/jobs/application/my")
    @Headers("No-Authentiaction: true")
    fun getJobApplication():Call<ResponseGetJobApplications>

    //Get List ShortList Applicant
    @FormUrlEncoded
    @POST("api/company/applicant")
    @Headers("No-Authentication: true")
    fun getShortlistApplicant(
            @Field("status")status:String
    ):Call<ResponseGetShortList>

    //Add Applicant to Shortlist
    @FormUrlEncoded
    @POST("api/company/jobs/applicant/status")
    @Headers("No-Authentication: true")
    fun addShortlistApplicant(
            @Field("id_applicant")idApplicante:Int,
            @Field("status")status:String
    ):Call<ResponseAddShortlistApplicant>

    //Get List Job Applicant
    @FormUrlEncoded
    @POST("api/company/jobs/applicant")
    @Headers("No-Authentication: true")
    fun getListJobApplicant(
            @Field("id_job")idJob:Int
    ):Call<ResponseGetListJobApplicant>

    //Get Status Applicant
    @FormUrlEncoded
    @POST("api/company/applicant/status")
    @Headers("No-Authentication: true")
    fun getStatusApplicant(
            @Field("id_applicant")idApplicant:Int
    ):Call<ResponseGetApplicantStatus>

    //Change Password
    @FormUrlEncoded
    @POST("api/password")
    @Headers("No-Authentication: true")
    fun changePassword(
            @Field("current_password")currentPassword:String,
            @Field("new_password")newPassword:String
    ):Call<ResponseChangePassword>

    //Check Provider And Get Product
    @FormUrlEncoded
    @POST("api/providers")
    fun checkProvider(
            @Field("number")phoneNumber:String):Call<ResponseGetProductPhone>

    //Checkout Product Phone
    @FormUrlEncoded
    @POST("api/pulsa/inquiry")
    fun checkout(
            @Field("customer_number") customerNumber:String,
            @Field("product_code") productCode:String
    ):Call<ResponseCheckout>

    //Get Payment Method
    @POST("api/payment/method")
    fun getPaymentMethod():Call<ResponsePaymentMethod>

    //Get List Transaction
    @POST("api/pulsa/list")
    fun getListTransaction():Call<ResponseGetListTransaction>

    //Transaction Product Phone
    @FormUrlEncoded
    @POST("api/pulsa/transaction")
    fun createTransactionProductPhone(
        @Field("customer_number")customerNumber:String,
        @Field("product_code")productCode:String,
        @Field("id_payment_method")idPaymentMethod:Int,
        @Field("id_inquiry")idInquiry: Int
    ):Call<ResponseCreateTransactionProductPhone>

    //Get All Hotel
    @GET("api/hotel")
    fun getAllHotel():Call<ResponseGetListHotel>

    //Currency Exchange Rate
    @FormUrlEncoded
    @GET("v6/{api_key}/latest/{code_country}")
    fun getCurrency(
            @Path(value = "api_key", encoded = true)apiKey:String,
            @Field(value = "code_country", encoded = true)codeCountry:String
    ):Call<ResponseGetCurrency>

    //Get Hotel By City
    @FormUrlEncoded
    @POST("api/hotel/by_city")
    fun getHotelByCity(
        @Field("city_id")cityId:Int
    ):Call<ResponseGetHotelByCity>

    //Get List City
    @GET("api/hotel/cities")
    fun getListCity():Call<ResponseGetListCity>

    //Search Hotel By Name
    @FormUrlEncoded
    @POST("api/hotel/by_name")
    fun getHotelByName(
        @Field("search_query")searchQuery:String
    ):Call<ResponseGetHotelByName>

    //Create Booking Hotel
    @FormUrlEncoded
    @POST("api/hotel/book/new")
    fun createBookingHotel(
        @Field("hotel_id")hotelId:Int,
        @Field("room_id")roomId:Int,
        @Field("check_in")checkIn:String,
        @Field("check_out")checkOut:String,
        @Field("total_guest")totalGuest:Int,
        @Field("total_price")totalPrice:Int,
        @Field("id_payment_method")idPaymentMethod:Int
    ):Call<ResponseBookingHotel>

    //Get List Transaction Hotel
    @POST("api/hotel/book/user_booking")
    fun getListTransactionHotel():Call<ResponseGetTransactionHotel>

    //Input Rating and Review
    @FormUrlEncoded
    @POST("api/hotel/rating")
    fun inputRating(
        @Field("hotel_id")hotelId:Int,
        @Field("rating")rating:Float,
        @Field("review")review:String
    ):Call<ResponseInputRating>

    //Get List Service
    @POST("api/category/service")
    fun getListService():Call<ResponseGetService>

    //Get Product Service
    @FormUrlEncoded
    @POST("api/group")
    fun getProductService(
        @Field("id_product_category")idProductCategory:Int
    ):Call<ResponseGetProductService>

    //Get Bill Amount
    @FormUrlEncoded
    @POST("api/bills/amountbill")
    fun getBillAmount(
        @Field("order_id")orderId:String,
        @Field("product_code")productCode:String
    ):Call<ResponseGetBillAmount>

    //Get Bill Inquiry
    @FormUrlEncoded
    @POST("api/bills/inquiry")
    fun getBillInquiry(
        @Field("order_id")orderId:String,
        @Field("product_code")productCode:String
    ):Call<ResponseGetBillAmount>

    //Get Bill Direct (no inquiry)
    @FormUrlEncoded
    @POST("api/bills/directbill")
    fun getDirectBill(
        @Field("order_id")orderId:String,
        @Field("product_code")productCode:String
    ):Call<ResponseGetBillDirect>

    //Add Bill Transaction
    @FormUrlEncoded
    @POST("api/bills/transaction")
    fun addBillTransaction(
        @Field("product_code")productCode:String,
        @Field("amount")amount:String,
        @Field("customer_number")customerNumber: String,
        @Field("id_payment_method")idPaymentMethod: Int,
        @Field("id_inquiry")idInquiry:Int?
    ):Call<ResponseAddBillTransaction>

    //Get All Transaction Pending List
    @POST("api/pending_transaction")
    fun getAllTransactionPending():Call<ResponseGetListTransactionPending>

    @POST("api/ticket/airport")
    fun getAirport():Call<ResponseGetListAirport>

    //Get How to Payment
    @FormUrlEncoded
    @POST("/api/how_to_pay")
    fun getHowToPay(
        @Field("id_payment_method")idPaymentMethod:Int
    ):Call<ResponseGetHowToPay>

    //Get notification
    @POST("api/notification")
    fun getNotification():Call<ResponseGetNotification>

    //Get List Airlines
    @FormUrlEncoded
    @POST("api/ticket/schedule")
    fun getListAirlines(
        @Field("trip_type")tripType:String,
        @Field("origin")origin:String,
        @Field("destination")destination:String,
        @Field("depart_date")departDate:String,
        @Field("return_date")returnDate:String?,
        @Field("adult")adult:Int,
        @Field("child")child:Int,
        @Field("infant")baby:Int,
        @Field("airline_access_code")aAC:String
    ):Call<ResponseGetListAirline>

    //Get Calculation Ticket Price
    @Headers("Content-Type: application/json")
    @POST("api/ticket/price")
    fun getCalculationTicketPrice(
        @Body body:RequestPrice
    ):Call<ResponseGetRealTicketPrice>
}