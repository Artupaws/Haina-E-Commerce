package haina.ecommerce.view.companycatalog.item.detail

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.companycatalog.master.CompanyItem

interface CompanyItemDetailContract {

    interface View : BaseView {
        fun message(msg:String)
        fun getItemDetail(data: CompanyItem?)
    }
}