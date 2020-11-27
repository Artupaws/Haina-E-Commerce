package haina.ecommerce.view.explore

import haina.ecommerce.model.ArticlesItem

interface ExploreContract {

    interface Presenter{
        fun loadExplore()
    }

    interface View{
        fun loadHeadlineNews(list: List<ArticlesItem?>?)
        fun errorMessage(msg: String?)
        fun showShimmerHeadlineNews()
        fun dismissShimmerHeadlineNews()
    }

}