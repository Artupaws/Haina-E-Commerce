package haina.ecommerce.view.restaurant.detail

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.property.CityItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantData

interface RestaurantDetailContract {
    interface View : BaseView {
        fun message(msg:String)
        fun getRestaurantData(data: RestaurantData?)
        fun getReviewList(data: RestaurantData?)
    }

}