package haina.ecommerce.view.companycatalog.catalog

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.companycatalog.master.CompanyItem
import haina.ecommerce.model.companycatalog.master.PaginationCompanyItem

interface CompanyCatalogItemsContract {
    interface View : BaseView {
        fun message(msg:String)
        fun getCompanyItemList(data:PaginationCompanyItem?)
    }
}