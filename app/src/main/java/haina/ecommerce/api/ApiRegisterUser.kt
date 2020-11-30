package haina.ecommerce.api

import android.provider.SyncStateContract
import haina.ecommerce.model.ResponseRegister
import haina.ecommerce.util.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

class ApiRegisterUser {

    private fun registerUser(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_REGISTER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): ApiInterfaceRegister{
        return registerUser().create(ApiInterfaceRegister::class.java)
    }

}

interface ApiInterfaceRegister{
    @FormUrlEncoded
    @POST("api/register")
    fun registerUser(
        @Query("apiKey")apiKey: String,
        @Field("fullname")fullname: String,
        @Field("email")email: String,
        @Field("username")username: String,
        @Field("phone")phone: String,
        @Field("password")password: String
    ): Call<ResponseRegister>
}