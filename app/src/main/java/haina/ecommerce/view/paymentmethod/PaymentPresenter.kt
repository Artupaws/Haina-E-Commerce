package haina.ecommerce.view.paymentmethod

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.bill.ResponseAddBillTransaction
import haina.ecommerce.model.flight.ResponseGetRealTicketPrice
import haina.ecommerce.model.hotels.ResponseBookingHotel
import haina.ecommerce.model.hotels.newHotel.RequestBookingHotelToDarma
import haina.ecommerce.model.hotels.newHotel.ResponseSetBooking
import haina.ecommerce.model.paymentmethod.ResponsePaymentMethod
import haina.ecommerce.model.transaction.ResponseCreateTransactionProductPhone
import haina.ecommerce.model.vacancy.ResponseCreateVacancy
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class PaymentPresenter(val view:PaymentContract.View, val context: Context) {

    fun getPaymentMethod(){
        view.showLoading()
        val getPaymentMethod = NetworkConfig().getConnectionHainaBearer(context).getPaymentMethod()
        getPaymentMethod.enqueue(object :retrofit2.Callback<ResponsePaymentMethod>{
            override fun onResponse(call: Call<ResponsePaymentMethod>, response: Response<ResponsePaymentMethod>) {
                view.dismissLoading()
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
                view.dismissLoading()
                view.messageGetPaymentMethod(t.localizedMessage.toString())
            }

        })
    }

    fun createTransaction(customerNumber:String, productCode:String, idPaymentMethod:Int, idInquiry: Int){
        view.showLoading()
        val createTransaction = NetworkConfig().getConnectionHainaBearer(context).createTransactionProductPhone(customerNumber, productCode, idPaymentMethod, idInquiry)
        createTransaction.enqueue(object : retrofit2.Callback<ResponseCreateTransactionProductPhone>{
            override fun onResponse(call: Call<ResponseCreateTransactionProductPhone>, response: Response<ResponseCreateTransactionProductPhone>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCreateTransaction(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCreateTransaction(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCreateTransactionProductPhone>, t: Throwable) {
                view.dismissLoading()
                view.messageCreateTransaction(t.localizedMessage.toString())
            }

        })
    }

    fun createBillTransaction(productCode:String, amount:String, customerNumber:String, idPaymentMethod:Int, idInquiry:Int?){
        view.showLoading()
        val createTransaction = NetworkConfig().getConnectionHainaBearer(context).addBillTransaction(productCode, amount, customerNumber, idPaymentMethod, idInquiry)
        createTransaction.enqueue(object : retrofit2.Callback<ResponseAddBillTransaction>{
            override fun onResponse(call: Call<ResponseAddBillTransaction>, response: Response<ResponseAddBillTransaction>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCreateBillTransaction(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCreateBillTransaction(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddBillTransaction>, t: Throwable) {
                view.dismissLoading()
                view.messageCreateBillTransaction(t.localizedMessage.toString())
            }

        })
    }


    fun createBookingHotel(hotelId:Int, roomId:Int, checkIn:String, checkOut:String, totalGuest:Int, totalPrice:Int, idPaymentMethod:Int){
        view.showLoading()
        val createBooking = NetworkConfig().getConnectionToDarma(context).createBookingHotel(hotelId, roomId, checkIn, checkOut, totalGuest, totalPrice, idPaymentMethod)
        createBooking.enqueue(object : retrofit2.Callback<ResponseBookingHotel>{
            override fun onResponse(call: Call<ResponseBookingHotel>, response: Response<ResponseBookingHotel>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageBookingHotel(response.body()?.message.toString())
                } else {
                    view.messageBookingHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBookingHotel>, t: Throwable) {
                view.dismissLoading()
                view.messageBookingHotel(t.localizedMessage.toString())
            }

        })
    }

    fun createBookingHotelDarma(dataBooking: RequestBookingHotelToDarma){
        view.showLoading()
        val createBooking = NetworkConfig().getConnectionHainaBearer(context).setBookingHotelDarma(dataBooking)
        createBooking.enqueue(object : retrofit2.Callback<ResponseSetBooking>{
            override fun onResponse(call: Call<ResponseSetBooking>, response: Response<ResponseSetBooking>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageBookingHotel(response.body()?.message.toString())
                } else {
                    view.messageBookingHotel(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseSetBooking>, t: Throwable) {
                view.dismissLoading()
                view.messageBookingHotel(t.localizedMessage.toString())
            }
        })
    }

    fun createVacancyPaid(positionJob:String, idCompany:Int, idSpecialist:Int, level:Int, type:Int,
                          description:String, experience:Int, idEdu:Int, minSalary:Int, maxSalary:Int, salaryDisplay:Int, address:String,
                          idCity:Int, packageId:Int, paymentMethodId:Int, skill:String){
        view.showLoading()
        val createVacancyPaid = NetworkConfig().getConnectionHainaBearer(context).createPostVacancyPaid(positionJob, idCompany, idSpecialist, level, type, description, experience, idEdu, minSalary, maxSalary, salaryDisplay, address, idCity, packageId, paymentMethodId, skill)
        createVacancyPaid.enqueue(object : retrofit2.Callback<ResponseCreateVacancy>{
            override fun onResponse(call: Call<ResponseCreateVacancy>, response: Response<ResponseCreateVacancy>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCreateVacancy(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageCreateVacancy(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCreateVacancy>, t: Throwable) {
                view.dismissLoading()
                view.messageCreateVacancy(t.localizedMessage.toString())
            }

        })

    }
    fun createBookingFlightDarma(paymentMethodId: Int){
        view.showLoading()
        val createBooking = NetworkConfig().getConnectionHainaBearer(context).setBookingFlight(paymentMethodId)
        createBooking.enqueue(object : retrofit2.Callback<ResponseGetRealTicketPrice>{
            override fun onResponse(call: Call<ResponseGetRealTicketPrice>, response: Response<ResponseGetRealTicketPrice>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageBookingFlight(response.body()?.message.toString())
                } else {
                    view.messageBookingFlight(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetRealTicketPrice>, t: Throwable) {
                view.dismissLoading()
                view.messageBookingFlight(t.localizedMessage.toString())
            }
        })
    }

}