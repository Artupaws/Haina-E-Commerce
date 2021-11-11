package haina.ecommerce.view.restaurant.detail.review

import haina.ecommerce.model.restaurant.master.ReviewPagination

interface RestaurantReviewListContract {

    fun message(code:Int,msg:String)
    fun getReviewList(data: ReviewPagination?)
}