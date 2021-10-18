package haina.ecommerce.view.news.fragment

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.news.ResponseGetListNewsTable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class NewsCategoryPresenter (val context: Context, val view: NewsCategoryContract) {

    fun getNewsTableWithCategory(page:Int,lang:String,idCategory:Int){
        val getNews = NetworkConfig().getConnectionHaina().getNewsTableWithCategory(lang,idCategory)
        getNews.enqueue(object : retrofit2.Callback<ResponseGetListNewsTable>{
            override fun onResponse(call: Call<ResponseGetListNewsTable>, response: Response<ResponseGetListNewsTable>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetNews(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getNews(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetNews(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListNewsTable>, t: Throwable) {
                view.messageGetNews(t.localizedMessage.toString())
            }

        })
    }
}