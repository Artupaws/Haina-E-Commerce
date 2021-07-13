package haina.ecommerce.view.property.fragmentshowproperty

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.property.DataShowProperty

interface ShowPropertyContract {

    interface View : BaseView {
        fun messageGetListProperty(msg:String)
        fun getDataProperty(data:List<DataShowProperty?>?)
    }

}