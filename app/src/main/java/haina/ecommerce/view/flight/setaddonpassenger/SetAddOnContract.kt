package haina.ecommerce.view.flight.setaddonpassenger

import haina.ecommerce.model.flight.AddOnsItem
import haina.ecommerce.model.flight.DataAddOn

interface SetAddOnContract {

    fun messageGetAddOn(msg:String)
    fun getDataAddOn(data: List<AddOnsItem>?)
    fun sendDataAddOnSuccess(msg:String)

}