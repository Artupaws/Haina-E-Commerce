package haina.ecommerce.view.property.detailmyproperty

import haina.ecommerce.base.BaseView

interface DetailMyPropertyContract {

    interface View:BaseView{
        fun messageDeleteProperty(msg:String)
    }
}