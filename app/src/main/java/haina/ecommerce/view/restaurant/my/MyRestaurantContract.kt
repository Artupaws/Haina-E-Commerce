package haina.ecommerce.view.restaurant.my

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.property.CityItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantPagination

interface MyRestaurantContract {

    interface View : BaseView {
        fun message(msg:String)
        fun getRestaurantList(data: RestaurantPagination?)
        fun getRestaurantCuisine(data:List<CuisineAndTypeData?>?)
        fun getRestaurantType(data:List<CuisineAndTypeData?>?)
        fun getCity(data:List<CityItem?>?)
    }
}