package haina.ecommerce.view.explore

import android.util.Log
import haina.ecommerce.api.ApiCurrency
import haina.ecommerce.api.ApiHeadlineNews
import haina.ecommerce.model.Rates
import haina.ecommerce.model.ResponseCurrency
import haina.ecommerce.model.ResponseHeadlineNews
import haina.ecommerce.util.Constants
import retrofit2.Call
import retrofit2.Response

class ExplorePresenter(private val view:ExploreContract.View): ExploreContract.Presenter {

    override fun loadDataExplore() {

        view.showShimmerHeadlineNews()

        val callHeadlines = ApiHeadlineNews().getInstance().getHeadlines(Constants.API_NEWS)
        callHeadlines.enqueue(object : retrofit2.Callback<ResponseHeadlineNews>{
            override fun onResponse(call: Call<ResponseHeadlineNews>, response: Response<ResponseHeadlineNews>) {
                if (response.isSuccessful) {
                    val data = response.body()?.articles
                    view.loadHeadlineNews(data)
                    view.dismissShimmerHeadlineNews()

                } else {
                    view.errorMessage("Failed")
                    view.dismissShimmerHeadlineNews()
                }
            }

            override fun onFailure(call: Call<ResponseHeadlineNews>, t: Throwable) {
                Log.d("ONFAILURE", t.toString())
                view.dismissShimmerHeadlineNews()
                view.errorMessage(t.toString())
            }

        })

        val call = ApiCurrency().getInstance().getCurrency()
        call.enqueue(object : retrofit2.Callback<ResponseCurrency>{
            override fun onResponse(call: Call<ResponseCurrency>, response: Response<ResponseCurrency>) {
                if (response.isSuccessful){
                    val data = response.body()?.rates
                    view.loadCurrency(data)
                    Log.d("currency", data.toString())
                }
            }

            override fun onFailure(call: Call<ResponseCurrency>, t: Throwable) {
                Log.d("currency", "fail")
            }

        })
//        call.enqueue(object : retrofit2.Callback<Rates>{
//            override fun onResponse(call: Call<Rates>, response: Response<Rates>) {
//                if (response.isSuccessful){
//                    val data = response.body()?.iDR.toString()
//                    view.loadCurrency(data)
//                    Log.d("currency", data)
//
//                }
//            }
//
//            override fun onFailure(call: Call<Rates>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

}