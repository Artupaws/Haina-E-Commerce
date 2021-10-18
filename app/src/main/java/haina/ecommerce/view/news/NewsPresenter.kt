package haina.ecommerce.view.news

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.news.ResponseGetListNews
import haina.ecommerce.model.news.ResponseGetListNewsTable
import haina.ecommerce.model.news.ResponseNewsCategory
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class NewsPresenter(val context: Context, val view:NewsContract) {

    fun getNewsCategory(){
        val getNews = NetworkConfig().getConnectionHaina().getNewsCategory()
        getNews.enqueue(object : retrofit2.Callback<ResponseNewsCategory>{
            override fun onResponse(call: Call<ResponseNewsCategory>, response: Response<ResponseNewsCategory>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetNews(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getNewsCategory(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetNews(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseNewsCategory>, t: Throwable) {
                view.messageGetNews(t.localizedMessage.toString())
            }

        })
    }

}