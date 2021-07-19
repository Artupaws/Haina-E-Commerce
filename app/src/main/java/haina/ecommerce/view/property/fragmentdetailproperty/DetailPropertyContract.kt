package haina.ecommerce.view.property.fragmentdetailproperty

import haina.ecommerce.base.BaseView

interface DetailPropertyContract {

    interface View:BaseView{
        fun messageChangeAvailability(msg:String)
    }

}