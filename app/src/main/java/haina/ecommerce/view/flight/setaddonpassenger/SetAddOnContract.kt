package haina.ecommerce.view.flight.setaddonpassenger

import haina.ecommerce.model.flight.DataAddOn

interface SetAddOnContract {

    fun messageGetAddOn(msg:String)
    fun getDataAddOn(data: DataAddOn?)

}