package haina.ecommerce.view.internetandtv

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.bill.ResponseGetBillAmount
import haina.ecommerce.model.bill.ResponseGetBillDirect
import haina.ecommerce.model.productservice.ResponseGetProductService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class InternetPresenter(val view:InternetContract, val context: Context) {

    fun getProductService(idProductCategory:Int){
        val getProductService = NetworkConfig().getConnectionHaina().getProductService(idProductCategory)
        getProductService.enqueue(object : retrofit2.Callback<ResponseGetProductService>{
            override fun onResponse(call: Call<ResponseGetProductService>, response: Response<ResponseGetProductService>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetProductService(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataProductService(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetProductService(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetProductService>, t: Throwable) {
                view.messageGetProductService(t.localizedMessage.toString())
            }

        })
    }

    fun getBillInquiry(orderId:String, productCode:String){
        val getBillAmount = NetworkConfig().getConnectionHainaBearer(context).getBillInquiry(orderId, productCode)
        getBillAmount.enqueue(object : retrofit2.Callback<ResponseGetBillAmount>{
            override fun onResponse(call: Call<ResponseGetBillAmount>, response: Response<ResponseGetBillAmount>) {
                if (response.isSuccessful){
                    view.messageGetBillAmount(response.body()?.message.toString())
                    val data = response.body()?.dataInquiry
                    view.getDataBillAmount(data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetBillAmount(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetBillAmount>, t: Throwable) {
                view.messageGetBillAmount(t.localizedMessage.toString())
            }

        })
    }

    fun getDirectBill(orderId:String, productCode:String){
        val getBillAmount = NetworkConfig().getConnectionHainaBearer(context).getDirectBill(orderId, productCode)
        getBillAmount.enqueue(object : retrofit2.Callback<ResponseGetBillDirect>{
            override fun onResponse(call: Call<ResponseGetBillDirect>, response: Response<ResponseGetBillDirect>) {
                if (response.isSuccessful){
                    view.messageGetBillAmount(response.body()?.message.toString())
                    val data = response.body()?.dataInquiry
                    view.getDataBillDirect(data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetBillAmount(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetBillDirect>, t: Throwable) {
                view.messageGetBillAmount(t.localizedMessage.toString())
            }

        })
    }
}