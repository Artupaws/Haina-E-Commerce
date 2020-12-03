package haina.ecommerce.view.explore

import haina.ecommerce.model.*

interface ExploreContract {

    interface Presenter{
        fun loadDataExplore()
    }

    interface View{
//        fun loadHeadlineNews(list: List<ArticlesItem?>?)
        fun errorMessage(msg: String?)
        fun showShimmerHeadlineNews()
        fun dismissShimmerHeadlineNews()
        fun loadCurrency(item: Data?)
//        fun loadCovidIndo(list: List<DataCovid?>?)
        fun loadListCodeCurrency(list: List<DataCodeCurrency?>?)
    }

}