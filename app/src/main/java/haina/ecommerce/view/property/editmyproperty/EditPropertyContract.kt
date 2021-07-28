package haina.ecommerce.view.property.editmyproperty

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.property.DataCity
import haina.ecommerce.model.property.DataFacilitiesProperty
import haina.ecommerce.model.property.DataProvince

interface EditPropertyContract {

    interface View: BaseView {
        fun messageGetFacilities(msg:String)
        fun messageGetProvince(msg:String)
        fun messageGetCity(msg:String)
        fun messageUpdateProperty(msg:String)
        fun getDataFacilites(data:List<DataFacilitiesProperty?>?)
        fun getDataProvince(data:List<DataProvince?>?)
        fun getDataCity(data:List<DataCity?>?)
    }

}