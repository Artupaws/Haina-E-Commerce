package haina.ecommerce.view.restaurant.detail.review.addreview

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.restaurant.master.ReviewPagination

interface RestaurantAddReviewContract {
    interface View : BaseView {
        fun message(code: Int, msg: String)
        fun addReview(data: ReviewData?)
    }
}