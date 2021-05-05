package haina.ecommerce.view.explore

import android.content.Context
import android.util.Log
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.*
import haina.ecommerce.model.currency.ResponseGetCurrency
import haina.ecommerce.model.transactionlist.ResponseGetListTransaction
import haina.ecommerce.util.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ExplorePresenter(val view: ExploreContract, val context: Context) {

    fun loadListBaseCurrency(){
        val callListCodeCurrency = NetworkConfig().getConnectionHaina().getDataListBaseCurrency()
        callListCodeCurrency.enqueue(object : retrofit2.Callback<ResponseBaseCurrency> {
            override fun onResponse(call: Call<ResponseBaseCurrency>, response: Response<ResponseBaseCurrency>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    view.loadListCodeCurrency(data)
                    Log.d("baseCurrency", data.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBaseCurrency>, t: Throwable) {
                Log.d("baseCurrency", "failed")
            }

        })
    }

//
    fun loadCurrency(base: String){
        val callCurrency = NetworkConfig().getConnectionHaina().getDataCurrency(base)
        callCurrency.enqueue(object : retrofit2.Callback<ResponseCurrency>{
            override fun onResponse(call: Call<ResponseCurrency>, response: Response<ResponseCurrency>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataCurrency
                    view.loadCurrency(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.errorMessage(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCurrency>, t: Throwable) {
                view.errorMessage(t.localizedMessage.toString())
            }

        })
    }

//    fun loadCovidJkt(){
//        val callCovidJkt = NetworkConfig().getConnectionHaina().getDataCovidJkt()
//        callCovidJkt.enqueue(object : retrofit2.Callback<ResponseCovidJkt>{
//            override fun onResponse(call: Call<ResponseCovidJkt>, response: Response<ResponseCovidJkt>) {
//                if (response.isSuccessful && response.body()?.value == 1){
//                    val data = response.body()?.dataJkt
//                    view.loadCovidJkt(data)
//                    view.successMessage(response.body()?.message.toString())
//                } else {
//                    view.errorMessage(response.body()?.message)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseCovidJkt>, t: Throwable) {
//                view.errorMessage(t.localizedMessage)
//            }
//
//        })
//    }
//
//    fun loadHeadlinesNews(apiKey:String){
//        val callHeadlinesNews = NetworkConfig().getHeadlineNews().getDataHeadlines(apiKey)
//        callHeadlinesNews.enqueue(object : retrofit2.Callback<ResponseHeadlineNews>{
//            override fun onResponse(call: Call<ResponseHeadlineNews>, response: Response<ResponseHeadlineNews>) {
//                if (response.isSuccessful && response.body()?.status == "ok"){
//                    val data = response.body()?.articles
//                    view.loadHeadlinesNews(data)
//                    view.successMessage(response.body()?.status.toString())
//                } else {
//                    view.errorMessage(response.body()?.status.toString())
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseHeadlineNews>, t: Throwable) {
//                view.errorMessage(t.localizedMessage)
//            }
//
//        })
//    }

    fun getDataUserProfile(){
        NetworkConfig().getConnectionHainaBearer(context).getDataUser(Constants.APIKEY)
            .enqueue(object : retrofit2.Callback<ResponseGetDataUser>{
                override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {
                    if (response.isSuccessful && response.body()?.value == 1){
                        val data = response.body()?.data
                        view.getDataUser(data)
                        view.messageGetDataUSer(response.body()?.message.toString())
                    } else {
                        val error = JSONObject(response.errorBody()?.string()!!)
                        view.messageGetDataUSer(error.getString("message"))
                    }
                }

                override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                    view.messageGetDataUSer(t.localizedMessage.toString())
                }

            })
    }

    fun getListUnfinishTransaction(){
        val getListunfinishTransaction = NetworkConfig().getConnectionHainaBearer(context).getListTransaction()
        getListunfinishTransaction.enqueue(object : retrofit2.Callback<ResponseGetListTransaction>{
            override fun onResponse(call: Call<ResponseGetListTransaction>, response: Response<ResponseGetListTransaction>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataTransaction
                    view.getTransactionList(data)
                    view.messageGetTransactionList(response.body()?.message)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetTransactionList(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListTransaction>, t: Throwable) {
                view.messageGetTransactionList(t.localizedMessage.toString())
            }

        })
    }

}