package haina.ecommerce.view.restaurant.detail.review

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.restaurant.master.RestaurantData

interface RestaurantReviewListContract {

    interface View : BaseView {
        fun message(msg:String)
        fun getReviewList(data: RestaurantData?)
    }
}