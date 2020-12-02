package haina.ecommerce.view.explore

import haina.ecommerce.model.ArticlesItem
import haina.ecommerce.model.Rates

interface ExploreContract {

    interface Presenter{
        fun loadDataExplore()
    }

    interface View{
        fun loadHeadlineNews(list: List<ArticlesItem?>?)
        fun errorMessage(msg: String?)
        fun showShimmerHeadlineNews()
        fun dismissShimmerHeadlineNews()
        fun loadCurrency(item: Rates?)
    }

}