package haina.ecommerce.view.restaurant.detail.overview

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.restaurant.master.MenuCategory
import haina.ecommerce.model.restaurant.master.RestaurantData

interface RestaurantOverviewContract {

    interface View : BaseView {
        fun message(msg:String)
        fun getMenuList(data: List<MenuCategory?>?)
    }
}