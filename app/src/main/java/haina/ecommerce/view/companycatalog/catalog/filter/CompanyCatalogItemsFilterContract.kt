package haina.ecommerce.view.companycatalog.catalog.filter

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import haina.ecommerce.model.property.CityItem
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.restaurant.master.RestaurantPagination

interface CompanyCatalogItemsFilterContract {
    fun message(msg:String)
    fun getCompanyCategoryList(data:List<CompanyItemCategory?>?)
}
