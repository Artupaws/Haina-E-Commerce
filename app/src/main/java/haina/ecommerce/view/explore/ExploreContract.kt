package haina.ecommerce.view.explore

import haina.ecommerce.model.*

interface ExploreContract {

    fun errorMessage(msg: String?)
    fun showShimmerHeadlineNews()
    fun dismissShimmerHeadlineNews()

//    fun loadListCodeCurrency(list: List<DataCodeCurrency?>?)
//    fun loadCurrency(item: DataCurrency?)
    fun loadCovidJkt(item: DataCovidJkt?)
//    fun loadHeadlinesNews(list: List<ArticlesItem?>?)

}