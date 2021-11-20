package haina.ecommerce.view.restaurant.register

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantData

interface RegisterRestaurantContract {
    interface View : BaseView {
        fun message(msg:String)
        fun registerRestaurant(data: RestaurantData?)
        fun getRestaurantCuisine(data:List<CuisineAndTypeData?>?)
        fun getRestaurantType(data:List<CuisineAndTypeData?>?)
    }
}