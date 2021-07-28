package haina.ecommerce.view.history.historymyproperty.saved

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.property.DataDetailProperty
import haina.ecommerce.model.property.DataShowProperty

interface SavedPropertyContract {

    interface View:BaseView{
        fun messageGetListProperty(msg:String)
        fun getDataProperty(data:DataShowProperty?)
    }

}