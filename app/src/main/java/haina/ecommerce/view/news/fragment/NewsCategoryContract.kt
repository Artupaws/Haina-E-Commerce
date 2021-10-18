package haina.ecommerce.view.news.fragment

import haina.ecommerce.model.news.DataNewsTable
import haina.ecommerce.model.news.NewsCategory

interface NewsCategoryContract {

    fun messageGetNews(msg:String)

    fun getNews(data:List<DataNewsTable?>?)

}