package haina.ecommerce.view.news

import haina.ecommerce.model.news.DataNewsTable

interface NewsContract {

    fun messageGetNews(msg:String)
    fun getNews(data:List<DataNewsTable?>?)

}