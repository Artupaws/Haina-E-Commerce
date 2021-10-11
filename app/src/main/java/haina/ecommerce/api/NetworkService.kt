package haina.ecommerce.api

import haina.ecommerce.adapter.forum.ResponseGetPostDetail
import haina.ecommerce.model.*
import haina.ecommerce.model.bill.ResponseAddBillTransaction
import haina.ecommerce.model.bill.ResponseGetBillAmount
import haina.ecommerce.model.bill.ResponseGetBillDirect
import haina.ecommerce.model.categorypost.ResponseGetCategoryPost
import haina.ecommerce.model.checkout.ResponseCheckout
import haina.ecommerce.model.currency.ResponseGetCurrency
import haina.ecommerce.model.flight.*
import haina.ecommerce.model.forum.*
import haina.ecommerce.model.hotels.*
import haina.ecommerce.model.hotels.newHotel.*
import haina.ecommerce.model.hotels.transactionhotel.ResponseGetTransactionHotel
import haina.ecommerce.model.howtopay.ResponseGetHowToPay
import haina.ecommerce.model.news.ResponseGetListNews
import haina.ecommerce.model.news.ResponseGetListNewsTable
import haina.ecommerce.model.notification.ResponseGetNotification
import haina.ecommerce.model.notification.ResponseOpenNotification
import haina.ecommerce.model.paymentmethod.ResponsePaymentMethod
import haina.ecommerce.model.productservice.ResponseGetProductService
import haina.ecommerce.model.property.*
import haina.ecommerce.model.property.FacilitiesItem
import haina.ecommerce.model.pulsaanddata.ResponseGetProductPhone
import haina.ecommerce.model.service.ResponseGetService
import haina.ecommerce.model.transaction.ResponseCreateTransactionProductPhone
import haina.ecommerce.model.transactionlist.ResponseGetListTransaction
import haina.ecommerce.model.transactionlist.ResponseGetListTransactionPending
import haina.ecommerce.model.vacancy.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface NetworkService {


    @GET("api/news/get")
    fun getServerStatus():Call<ResponseLogout>

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

    @FormUrlEncoded
    @POST("api/news/get-article")
    fun getNewsTable(
        @Field("lang")lang:String
    ):Call<ResponseGetListNewsTable>

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

    @FormUrlEncoded
    @POST("api/job/vacancy/add_bookmark")
    fun jobAddBookmark(
        @Field("id_vacancy") idVacancy: Int
    ): Call<ResponseJobBookmark>

    @FormUrlEncoded
    @POST("api/job/vacancy/remove_bookmark")
    fun jobRemoveBookmark(
        @Field("id_vacancy") idVacancy: Int
    ): Call<ResponseJobBookmark>

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

    //Get List Job Bookmark
    @GET("api/job/vacancy/my_bookmark")
    fun getListJobBookmark(
    ):Call<ResponseGetAllVacancy>

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
            @Part("description") description: RequestBody,
            @Part("siup") siup: RequestBody,
            @Part("id_province") idProvince: RequestBody

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

    @FormUrlEncoded
    @POST("api/open-notification")
    fun openNotification(
        @Field("id")id:Int,
    ):Call<ResponseOpenNotification>

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

    //Get List Nationality
    @GET("api/ticket/nationality")
    fun getListNationality():Call<ResponseGetListNationality>

    //Get Data Add On
    @GET("api/ticket/addons")
    fun getDataAddOn():Call<ResponseGetAddOn>

    //Get Data Add On
    @Headers("Content-Type: application/json")
    @POST("api/ticket/setaddons")
    fun sendDataAddOn(
        @Body body:RequestSetPassengerAddOn
    ):Call<ResponseSetAddOn>


    @Headers("Content-Type: application/json")
    @POST("api/ticket/booking")
    fun setBookingFlight(
        @Field("id_payment_method")paymentMethodId: Int?,

        ):Call<ResponseGetRealTicketPrice>


    //Get List City Hotel
    @POST("api/hotel_darma/all_cities")
    fun getListAllCityHotel():Call<ResponseGetCityHotel>


    @FormUrlEncoded
    @POST("api/hotel_darma/search")
    fun getHotelSearch(
        @Field("search")searchQuery:String,
        @Field("check_in_date")checkIn:String,
        @Field("check_out_date")checkOut:String
    ):Call<ResponseHotelSearch>

    //Get Hotel Darma
    @FormUrlEncoded
    @POST("api/hotel_darma/search_hotel")
    fun getHotelDarma(
        @Field("country_id")countryId:String,
        @Field("city_id")cityId:Int,
        @Field("pax_passport")paxPassport:String,
        @Field("check_in_date")checkIn:String,
        @Field("check_out_date")checkOut:String
        ):Call<ResponseGetHotelDarma>

    //Get Data Add On
    @GET("api/ticket/seat")
    fun getDataSeat():Call<ResponseGetAddOn>

    //Get Room Hotel
    @FormUrlEncoded
    @POST("api/hotel_darma/search_room")
    fun getDataRoom(
        @Field("hotel_id")hotelId:String
    ):Call<ResponseGetRoomHotel>

    //Get Room Hotel
    @FormUrlEncoded
    @POST("api/hotel_darma/search_room")
    fun getDataRoomFromSearch(
        @Field("hotel_id")hotelId:String,
        @Field("city_id")cityId:String
    ):Call<ResponseGetRoomHotel>

    //Get Price Policy
    @FormUrlEncoded
    @POST("api/hotel_darma/price_policy")
    fun getPricePolicy(
        @Field("room_id")roomId:String,
        @Field("breakfast")breakfast:String
    ):Call<ResponseGetPricePolicy>

    //Set Booking Hotel Darma
    @Headers("Content-Type: application/json")
    @POST("api/hotel_darma/create_booking")
    fun setBookingHotelDarma(
        @Body body:RequestBookingHotelToDarma
    ):Call<ResponseSetBooking>

    //Set Data Passenger
    @Headers("Content-Type: application/json")
    @POST("api/ticket/passenger")
    fun setDataPassenger(
        @Body body:RequestSetPassenger
    ):Call<ResponseSetDataPassenger>

    //Get List Booking Hotel Darma
    @POST("api/hotel_darma/booking_list")
    fun getListBookingHotelDarma():Call<ResponseGetListBooking>

    //Cancel List
    @FormUrlEncoded
    @POST("api/hotel_darma/cancel")
    fun cancelBookingHotelDarma(
        @Field("booking_id")bookingId:Int
    ):Call<ResponseCancelBookingHotel>

    //CategoryPost
    @GET("api/post_category")
    fun getCategoryPost():Call<ResponseGetCategoryPost>

    //Get Facilites Properties
    @GET("api/property/facility")
    fun getFacilities():Call<ResponseGetFacilitiesProperty>

    //Get Province
    @POST("api/provinceList")
    fun getProvince():Call<ResponseGetProvince>

    //Get City
    @FormUrlEncoded
    @POST("api/cityList")
    fun getCity(
        @Field("id_province") idProvince:Int
    ):Call<ResponseGetCity>

    //Create Post Property
    @Multipart
    @Headers("No-Authentication: true")
    @POST("api/property/new_property")
    fun createPostProperty(
        @Part("property_type") propertyType:RequestBody,
        @Part("condition") condition:RequestBody,
        @Part("title") title:RequestBody,
        @Part("year") year:RequestBody,
        @Part("id_city") idCity:RequestBody,
        @Part("floor_level") floorLevel:RequestBody,
        @Part("bedroom") bedRoom:RequestBody?,
        @Part("bathroom") bathRoom:RequestBody?,
        @Part("building_area") buildingArea:Int,
        @Part("land_area") landArea:Int,
        @Part("certificate_type") certificateType:String?,
        @Part("address") address:RequestBody,
        @Part("latitude") latitude:RequestBody?,
        @Part("longitude") longitude:RequestBody?,
        @Part("selling_price") sellingPrice:RequestBody?,
        @Part("rental_price") rentalPrice:RequestBody?,
        @Part("facilities") facilities:RequestBody?,
        @Part("description") description:RequestBody?,
        @Part images:ArrayList<MultipartBody.Part>
    ):Call<ResponseCreatePostProperty>

    //Show Property
    @Headers("No-Authentication: true")
    @POST("api/property/show_property")
    fun showProperty():Call<ResponseShowProperty>

    //Show Myproperty
    @Headers("No-Authentication: true")
    @POST("api/property/my_property")
    fun showMyProperty():Call<ResponseShowProperty>

    //Delete Property
    @FormUrlEncoded
    @Headers("No-Atuhentication: true")
    @POST("api/property/delete")
    fun deleteProperty(
        @Field("id_property")idProperty:Int
    ):Call<ResponseDeleteProperty>

    //View Detail Property
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/property/view_property")
    fun addViewProperty(
        @Field("id_property")idProperty:Int
    ):Call<ResponseViewDetailProperty>

    //Change Availability Property
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/property/new_transaction")
    fun changeAvailability(
        @Field("id_property")idProperty: Int,
        @Field("transaction_type")transactionType:String
    ):Call<ResponseChangeAvailability>

    //Update MyProperty
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/property/update_property")
    fun updateMyProperty(
        @Field("id_property") idProperty:Int,
        @Field("property_type") propertyType:String,
        @Field("condition") condition:String,
        @Field("title") title:String,
        @Field("year") year:String,
        @Field("id_city") idCity:Int,
        @Field("floor_level") floorLevel:Int,
        @Field("bedroom") bedRoom:Int?,
        @Field("bathroom") bathRoom:Int?,
        @Field("building_area") buildingArea:Int,
        @Field("land_area") landArea:Int,
        @Field("certificate_type") certificateType:String?,
        @Field("address") address:String,
        @Field("latitude") latitude:String?,
        @Field("longitude") longitude:String?,
        @Field("selling_price") sellingPrice:String?,
        @Field("rental_price") rentalPrice:String?,
        @Field("facilities") facilities:String,
        @Field("description") description:String?
    ):Call<ResponseCreatePostProperty>

    //Update Bookmark Property
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/property/bookmark")
    fun updateBookmarkProperty(
        @Field("id_property")idProperty:Int,
        @Field("bookmark")bookmark:String
    ):Call<ResponseUpdateBookmarkProperty>

    //Update Status Property
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/property/update_transaction")
    fun updateStatusProperty(
        @Field("id_transaction")idTransaction:Int,
        @Field("status")status:String
    ):Call<ResponseUpdateStatusProperty>

    //Get Category Forum
    @Headers("No-Authentication: true")
    @GET("api/forum/category")
    fun getCategoryForum():Call<ResponseGetCategoryForum>

    //Get List Hot Threads
    @Headers("No-Authentication: true")
    @GET("api/forum/hot_post")
    fun getListHotThreads():Call<ResponseGetHotpost>

    //Get List Forum Post
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/post_list")
    fun getListForumPost(
        @Field("subforum_id")IdForum:Int,
        @Field("sort_by")sort:String,
        @Field("page")page:Int
    ):Call<ResponseGetAllThreads>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/subforum_data")
    fun getSubforumData(
        @Field("subforum_id")IdForum:Int
    ):Call<ResponseSubforumData>


    //Get List Forum Post
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/mod_list")
    fun getModList(
        @Field("subforum_id")IdForum:Int
    ):Call<ResponseModList>

    @FormUrlEncoded
    @POST("api/forum/remove_mod")
    fun removeMod(
        @Field("subforum_id")IdForum:Int,
        @Field("user_id")IdUser:Int
    ):Call<ResponseRemoveModerator>

    @FormUrlEncoded
    @POST("api/forum/assign_mod")
    fun addMod(
        @Field("subforum_id")IdForum:Int,
        @Field("user_id")IdUser:Int,
        @Field("role")Role:String
    ):Call<ResponseRemoveModerator>

    //Get List Ban
    @FormUrlEncoded
    @POST("api/forum/banlist")
    fun getForumBannedList(
        @Field("subforum_id")IdForum:Int
    ):Call<ResponseForumBannedList>

    @FormUrlEncoded
    @POST("api/forum/ban_remove")
    fun removeUserBanned(
        @Field("subforum_id")IdForum:Int,
        @Field("user_id")IdUser:Int
    ):Call<ResponseRemoveUserBan>

    @FormUrlEncoded
    @POST("api/forum/home_post")
    fun getListHomePost(
        @Field("page")page:Int
    ):Call<ResponseGetAllThreads>

    //Give Upvote
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/upvote")
    fun upvote(@Field("post_id")postId:Int):Call<ResponseGiveUpvote>

    //Remove Upvote
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/cancel_upvote")
    fun removeUpvote(@Field("post_id")postId:Int):Call<ResponseGiveUpvote>

    //Get Subforum
    @Headers("No-Authentication: true")
    @POST("api/forum/subforum")
    fun getSubforum():Call<ResponseGetListSubforum>

    //Get List Comment
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/comment")
    fun getListComment(@Field("post_id")postId:Int):Call<ResponseGetListComment>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/post_detail")
    fun getPostDetail(@Field("post_id")postId:Int):Call<ResponseGetPostDetail>

    //Get List Comment
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/new_comment")
    fun newComment(@Field("post_id")postId:Int,
                   @Field("content")comment:String
    ):Call<ResponseNewComment>

    //Create Subforum
    @Multipart
    @Headers("No-Authentication: true")
    @POST("api/forum/new_subforum")
    fun createSubForum(
        @Part("name")name:String,
        @Part("description")description:String,
        @Part("category_id")categoryId:Int,
        @Part image: MultipartBody.Part,
        ):Call<ResponseGiveUpvote>

    //Get Data Profile User Forum
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/user_profile")
    fun getUserProfileForum(
        @Field("user_id")userId:Int
    ):Call<ResponseGetProfileUserForum>

    @FormUrlEncoded
    @POST("api/forum/user_profile_post")
    fun getUserPost(
        @Field("user_id")userId:Int,
        @Field("sort_by")sort:String,
        @Field("page")page:Int
    ):Call<ResponseGetAllThreads>

    @FormUrlEncoded
    @POST("api/forum/user_profile_comment")
    fun getUserComment(
        @Field("user_id")userId:Int,
        @Field("sort_by")sort:String,
        @Field("page")page:Int
    ):Call<ResponseUserCommentList>

    //New Post
    @Multipart
    @Headers("No-Authentication: true")
    @POST("api/forum/new_post")
    fun createNewPost(
        @Part("subforum_id")subforumId:Int,
        @Part("title")title:String,
        @Part("content")content:String,
        @Part images :List<MultipartBody.Part>,
        @Part video :List<MultipartBody.Part>?
    ):Call<ResponseGiveUpvote>

    //Get My Subforum
    @Headers("No-Authentication: true")
    @GET("api/forum/my_subforum")
    fun getMySubforum():Call<ResponseGetMySubforum>

    //Get My Post
    @Headers("No-Authentication: true")
    @GET("api/forum/my_post")
    fun getMypost():Call<ResponseGetMypost>

    //Follow Subforum
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/follow_subforum")
    fun followSubforum(@Field("subforum_id")subforumId:Int
    ):Call<ResponseFollowSubforum>

    //Unfollow Subforum
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/unfollow_subforum")
    fun unfollowSubforum(@Field("subforum_id")subforumId:Int
    ):Call<ResponseUnfollowSubforum>

    //Get My Follow subforum
    @Headers("No-Authentication: true")
    @GET("api/forum/following_subforum")
    fun getFollowSubforum():Call<ResponseGetListSubforum>

    //Get All Threads
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/all_post")
    fun getAllThreads(@Field("page")page:Int):Call<ResponseGetAllThreads>

    //Delete MyPost
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/delete_post")
    fun deleteMyPost(@Field("post_id")postId:Int):Call<ResponseDeleteMyPost>

    //Get List Role Subforum
    @Headers("No-Authentication: true")
    @GET("api/forum/my_role")
    fun getListRoleSubforum():Call<ResponseGetListRoleMod>

    //Get List Ban
    @Headers("No-Authentication: true")
    @GET("api/forum/my_ban")
    fun getListBan():Call<ResponseGetListBan>


    //Search Forum
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/search")
    fun searchForum(@Field("keyword")keyword:String):Call<ResponseSearchForum>

    //Add Mod/SubMod
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/assign_mod")
    fun assignMod(
        @Field("subforum_id") subForumId:Int,
        @Field("user_id") userId:Int,
        @Field("role")role:String
    ):Call<ResponseAddMod>

    //Delete Comment
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/forum/delete_comment")
    fun deleteComment(
        @Field("comment_id") commentId:Int
    ):Call<ResponseGiveUpvote>

    //Data Create Vacancy
    @Headers("No-Authentication: true")
    @GET("api/job/vacancy/data")
    fun getDataCreateVacancy():Call<ResponseGetDataCreateVacancy>

    //Create Post Vacancy Free
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/job/vacancy/post")
    fun createPostVacancy(
        @Field("position")positionJob:String,
        @Field("id_company")idCompany:Int,
        @Field("id_specialist")idSpecialist:Int,
        @Field("level")level:Int,
        @Field("type")type:Int,
        @Field("description")description:String,
        @Field("experience")experience:Int,
        @Field("id_edu")idEdu:Int,
        @Field("min_salary")minSalary:Int,
        @Field("max_salary")maxSalary:Int,
        @Field("salary_display")salaryDisplay:Int,
        @Field("address")address:String,
        @Field("id_city")idCity:Int,
        @Field("package")packageId:Int,
        @Field("skill")skill:String
    ):Call<ResponseCreateVacancy>

    //Create Post Vacancy Paid
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/job/vacancy/post")
    fun createPostVacancyPaid(
        @Field("position")positionJob:String,
        @Field("id_company")idCompany:Int,
        @Field("id_specialist")idSpecialist:Int,
        @Field("level")level:Int,
        @Field("type")type:Int,
        @Field("description")description:String,
        @Field("experience")experience:Int,
        @Field("id_edu")idEdu:Int,
        @Field("min_salary")minSalary:Int,
        @Field("max_salary")maxSalary:Int,
        @Field("salary_display")salaryDisplay:Int,
        @Field("address")address:String,
        @Field("id_city")idCity:Int,
        @Field("package")packageId:Int,
        @Field("payment_method_id")paymentMethodId:Int?,
        @Field("skill")skill:String,
    ):Call<ResponseCreateVacancy>

    //Get List My Vacancy
    @Headers("No-Authentication: true")
    @GET("api/job/vacancy")
    fun getListMyVacancy():Call<ResponseGetListMyVacancy>

    //Update Data My Vacancy
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/job/vacancy/update")
    fun updateDataMyVacancy(
        @Field("position")positionJob:String,
        @Field("id_vacancy")idVacancy:Int,
        @Field("id_specialist")idSpecialist:Int,
        @Field("level")level:Int,
        @Field("type")type:Int,
        @Field("description")description:String,
        @Field("experience")experience:Int,
        @Field("id_edu")idEdu:Int,
        @Field("min_salary")minSalary:Int,
        @Field("max_salary")maxSalary:Int,
        @Field("salary_display")salaryDisplay:Int,
        @Field("address")address:String,
        @Field("id_city")idCity:Int,
        @Field("skill")skill:String
    ):Call<ResponseUpdateDataMyVacancy>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/job/vacancy/search")
    fun getListAllVacancy(
        @Field("min_salary")minSalary:Int?,
        @Field("id_edu")idEdu: Int?,
        @Field("id_specialist")idSpecialist: Int?,
        @Field("id_city")idCity: Int?,
        @Field("type")type: Int?,
        @Field("level")level: Int?,
        @Field("experience")experience: Int?
        ):Call<ResponseGetAllVacancy>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/job/vacancy/apply")
    fun applyJobVacancy(
        @Field("id_vacancy")idVacancy:Int,
        @Field("applicant_notes")applicantNotes:String,
        @Field("id_resume")idResume:Int
    ):Call<ResponseGiveUpvote>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/pulsa/cancel")
    fun cancelTransaction(
        @Field("id_transaction")idTransaction:Int?
    ):Call<ResponseGiveUpvote>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/pulsa/cancel")
    fun cancelTransactionJob(
        @Field("id_vacancy")idJob:Int?
    ):Call<ResponseGiveUpvote>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/work_exp/add")
    fun addWorkExperience(
        @Field("company")company:String,
        @Field("city")city:String,
        @Field("date_start")dateStart:String,
        @Field("date_end")dateEnd:String,
        @Field("position")position:String,
        @Field("description")description:String,
        @Field("salary")salary:Int
    ):Call<ResponseGiveUpvote>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/work_exp/update")
    fun updateWorkExperience(
        @Field("company")company:String,
        @Field("city")city:String,
        @Field("date_start")dateStart:String,
        @Field("date_end")dateEnd:String,
        @Field("position")position:String,
        @Field("description")description:String,
        @Field("salary")salary:Int
    ):Call<ResponseGiveUpvote>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/education/add")
    fun addLastEducation(
        @Field("institution")institution:String,
        @Field("year_start")yearStart:String,
        @Field("year_end")yearEnd:String,
        @Field("gpa")gpa:Double?,
        @Field("major")major:String,
        @Field("id_edu")idEdu:Int,
        @Field("city")city:String
    ):Call<ResponseGiveUpvote>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("api/education/update")
    fun updateLastEducation(
        @Field("institution")institution:String,
        @Field("year_start")yearStart:String,
        @Field("year_end")yearEnd:String,
        @Field("gpa")gpa:Double?,
        @Field("major")major:String,
        @Field("id_edu")idEdu:Int,
        @Field("city")city:String
    ):Call<ResponseGiveUpvote>

    //List Applicant Unprocess
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("/api/job/applicant")
    fun getListApplicant(
        @Field("id_vacancy")idVacancy:Int
    ):Call<ResponseGetListApplicant>

    //List Applicant ShortListed
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("/api/job/applicant/shortlisted")
    fun getListApplicantShortListed(
        @Field("id_vacancy")idVacancy:Int
    ):Call<ResponseGetListApplicant>

    //List Applicant Interview
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("/api/job/applicant/interview")
    fun getListApplicantInterview(
        @Field("id_vacancy")idVacancy:Int
    ):Call<ResponseGetListApplicant>

    //List Applicant Accepted
    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("/api/job/applicant/accepted")
    fun getListApplicantAccepted(
        @Field("id_vacancy")idVacancy:Int
    ):Call<ResponseGetListApplicant>

    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("/api/job/applicant/update")
    fun updateApplicantStatus(
        @Field("id_applicant")idVacancy:Int,
        @Field("status")status:String
    ):Call<ResponseGiveUpvote>


    @FormUrlEncoded
    @Headers("No-Authentication: true")
    @POST("/api/job/applicant/invite_interview")
    fun inviteInterview(
        @Field("id_applicant")idVacancy:Int,
        @Field("time")datetime:String,
        @Field("method")method:String,
        @Field("duration")duration:Int,
        @Field("location")location:String,
        @Field("cp_name")contactPerson:String,
        @Field("cp_phone")contactNumber:String,
        ):Call<ResponseInviteInterview>


    @GET("api/job/vacancy/my_applications")
    fun getMyApplication():Call<ResponseMyApplication>

}