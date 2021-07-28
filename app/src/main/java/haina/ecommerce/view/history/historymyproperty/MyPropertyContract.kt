package haina.ecommerce.view.history.historymyproperty

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.property.DataShowProperty

interface MyPropertyContract {

    interface View:BaseView{
        fun messageGetListProperty(msg:String)
        fun getDataProperty(data:List<DataShowProperty?>?)
    }

}