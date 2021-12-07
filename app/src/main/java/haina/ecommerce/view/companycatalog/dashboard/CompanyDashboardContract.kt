package haina.ecommerce.view.companycatalog.dashboard

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import haina.ecommerce.model.property.CityItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantPagination

interface CompanyDashboardContract {

    interface View : BaseView {
        fun message(msg:String)
        fun getCompanyCategoryList(data:List<CompanyItemCategory?>?)
    }
}