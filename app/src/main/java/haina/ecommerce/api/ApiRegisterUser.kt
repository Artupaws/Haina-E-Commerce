package haina.ecommerce.api

import haina.ecommerce.model.ResponseRegister
import haina.ecommerce.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

class ApiRegisterUser {

    fun getInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun registerUser(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_REGISTER)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance() = registerUser().create(ApiInterfaceRegister::class.java)

}

interface ApiInterfaceRegister {
    @FormUrlEncoded
    @POST("api/register")
    fun registerUser(
        @Field("fullname") fullname: String,
        @Field("email") email: String,
        @Field("username") username: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("api_key") apiKey: String,
        @Field("device_token") deviceToken: String
    ): Call<ResponseRegister>
}