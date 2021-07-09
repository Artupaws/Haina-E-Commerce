package haina.ecommerce.view.news

import haina.ecommerce.model.news.DataNews

interface NewsContract {

    fun messageGetNews(msg:String)
    fun getNews(data:List<DataNews?>?)

}