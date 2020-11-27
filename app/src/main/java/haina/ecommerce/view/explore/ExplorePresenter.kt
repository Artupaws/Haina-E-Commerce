package haina.ecommerce.view.explore

import android.util.Log
import haina.ecommerce.api.ApiHeadlineNews
import haina.ecommerce.model.ResponseHeadlineNews
import haina.ecommerce.util.Constants
import retrofit2.Call
import retrofit2.Response

class ExplorePresenter(private val view:ExploreContract.View): ExploreContract.Presenter {

    override fun loadExplore() {

        view.showShimmerHeadlineNews()

        val call = ApiHeadlineNews().getInstance().getHeadlines(Constants.API_NEWS)
        call.enqueue(object : retrofit2.Callback<ResponseHeadlineNews>{
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
    }

}