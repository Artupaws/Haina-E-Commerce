package haina.ecommerce.view.paymentmethod

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.bill.ResponseAddBillTransaction
import haina.ecommerce.model.hotels.ResponseBookingHotel
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotelDarma
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotelToDarma
import haina.ecommerce.model.hotels.newHotel.ResponseSetBooking
import haina.ecommerce.model.paymentmethod.ResponsePaymentMethod
import haina.ecommerce.model.transaction.ResponseCreateTransactionProductPhone
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class PaymentPresenter(val view:PaymentContract, val context: Context) {

    fun getPaymentMethod(){
        val getPaymentMethod = NetworkConfig().getConnectionHainaBearer(context).getPaymentMethod()
        getPaymentMethod.enqueue(object :retrofit2.Callback<ResponsePaymentMethod>{
            override fun onResponse(call: Call<ResponsePaymentMethod>, response: Response<ResponsePaymentMethod>) {
                if (response.isSuccessful && response.body()?.value ==1 ){
                    val data = response.body()?.data
                    view.getDataPaymentMethod(data)
                    view.messageGetPaymentMethod(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetPaymentMethod(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponsePaymentMethod>, t: Throwable) {
                view.messageGetPaymentMethod(t.localizedMessage.toString())
            }

        })
    }

    fun createTransaction(customerNumber:String, productCode:String, idPaymentMethod:Int, idInquiry: Int){
        val createTransaction = NetworkConfig().getConnectionHainaBearer(context).createTransactionProductPhone(customerNumber, productCode, idPaymentMethod, idInquiry)
        createTransaction.enqueue(object : retrofit2.Callback<ResponseCreateTransactionProductPhone>{
            override fun onResponse(call: Call<ResponseCreateTransactionProductPhone>, response: Response<ResponseCreateTransactionProductPhone>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCreateTransaction(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCreateTransaction(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCreateTransactionProductPhone>, t: Throwable) {
                view.messageCreateTransaction(t.localizedMessage.toString())
            }

        })
    }

    fun createBillTransaction(productCode:String, amount:String, customerNumber:String, idPaymentMethod:Int, idInquiry:Int?){
        val createTransaction = NetworkConfig().getConnectionHainaBearer(context).addBillTransaction(productCode, amount, customerNumber, idPaymentMethod, idInquiry)
        createTransaction.enqueue(object : retrofit2.Callback<ResponseAddBillTransaction>{
            override fun onResponse(call: Call<ResponseAddBillTransaction>, response: Response<ResponseAddBillTransaction>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCreateBillTransaction(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCreateBillTransaction(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddBillTransaction>, t: Throwable) {
                view.messageCreateBillTransaction(t.localizedMessage.toString())
            }

        })
    }


    fun createBookingHotel(hotelId:Int, roomId:Int, checkIn:String, checkOut:String, totalGuest:Int, totalPrice:Int, idPaymentMethod:Int){
        val createBooking = NetworkConfig().getConnectionHainaBearer(context).createBookingHotel(hotelId, roomId, checkIn, checkOut, totalGuest, totalPrice, idPaymentMethod)
        createBooking.enqueue(object : retrofit2.Callback<ResponseBookingHotel>{
            override fun onResponse(call: Call<ResponseBookingHotel>, response: Response<ResponseBookingHotel>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageBookingHotel(response.body()?.message.toString())
                } else {
                    view.messageBookingHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBookingHotel>, t: Throwable) {
                view.messageBookingHotel(t.localizedMessage.toString())
            }

        })
    }

    fun createBookingHotelDarma(dataBooking: RequestBookingHotelToDarma){
        val createBooking = NetworkConfig().getConnectionHainaBearer(context).setBookingHotelDarma(dataBooking)
        createBooking.enqueue(object : retrofit2.Callback<ResponseSetBooking>{
            override fun onResponse(call: Call<ResponseSetBooking>, response: Response<ResponseSetBooking>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageBookingHotel(response.body()?.message.toString())
                } else {
                    view.messageBookingHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseSetBooking>, t: Throwable) {
                view.messageBookingHotel(t.localizedMessage.toString())
            }
        })
    }

}