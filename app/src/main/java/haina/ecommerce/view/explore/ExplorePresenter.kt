package haina.ecommerce.view.explore

import android.util.Log
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseCodeCurrency
import haina.ecommerce.model.ResponseCovid
import haina.ecommerce.model.ResponseCurrency
import haina.ecommerce.model.ResponseHeadlineNews
import haina.ecommerce.util.Constants
import retrofit2.Call
import retrofit2.Response

class ExplorePresenter(val view: ExploreContract.View) : ExploreContract.Presenter {

    override fun loadDataExplore() {

//        val callHeadlines = NetworkConfig().getHeadlineNews().getDataHeadlines(Constants.API_NEWS)
//        callHeadlines.enqueue(object : retrofit2.Callback<ResponseHeadlineNews> {
//            override fun onResponse(call: Call<ResponseHeadlineNews>, response: Response<ResponseHeadlineNews>) {
//                if (response.isSuccessful) {
//                    val data = response.body()?.articles
//                    view.loadHeadlineNews(data)
//                    view.dismissShimmerHeadlineNews()
//
//                } else {
//                    view.errorMessage("Failed")
//                    view.dismissShimmerHeadlineNews()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseHeadlineNews>, t: Throwable) {
//                Log.d("ONFAILURE", t.toString())
//                view.dismissShimmerHeadlineNews()
//                view.errorMessage(t.toString())
//            }
//
//        })

        fun getCurrency(base: String){
            NetworkConfig().getCurrency().getDataCurrency(base)
            .enqueue(object : retrofit2.Callback<ResponseCurrency>{
                override fun onResponse(call: Call<ResponseCurrency>, response: Response<ResponseCurrency>) {
                    if (response.isSuccessful){
                        val data = response.body()?.data
                        view.loadCurrency(data)
                        Log.d("currency", data.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseCurrency>, t: Throwable) {
                    Log.d("currency", "fail")
                }

            })

        }

//        val callCovidIndo = NetworkConfig().getCovidIndo().getDataCovidIndo()
//        callCovidIndo.enqueue(object : retrofit2.Callback<ResponseCovid>{
//            override fun onResponse(call: Call<ResponseCovid>, response: Response<ResponseCovid>) {
//                if (response.isSuccessful){
//                    val data = response.body()?.data
//                    view.loadCovidIndo(data)
//                    Log.d("covid", data.toString())
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseCovid>, t: Throwable) {
//                Log.d("covid", "fail")
//            }
//
//        })
//
        val callListCodeCurrency = NetworkConfig().getListCodeCurrency().getDataListBaseCurrency()
        callListCodeCurrency.enqueue(object : retrofit2.Callback<ResponseCodeCurrency> {
            override fun onResponse(call: Call<ResponseCodeCurrency>, response: Response<ResponseCodeCurrency>) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    view.loadListCodeCurrency(data)
                    Log.d("baseCurrency", data.toString())
                }
            }

            override fun onFailure(call: Call<ResponseCodeCurrency>, t: Throwable) {
                Log.d("baseCurrency", "failed")
            }

        })
    }

}