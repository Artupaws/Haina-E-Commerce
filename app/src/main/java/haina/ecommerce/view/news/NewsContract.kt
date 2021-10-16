package haina.ecommerce.view.news

import haina.ecommerce.model.news.DataNewsTable
import haina.ecommerce.model.news.NewsCategory

interface NewsContract {

    fun messageGetNews(msg:String)

    fun getNewsCategory(data:List<NewsCategory?>?)
    fun getNews(data:List<DataNewsTable?>?)

}