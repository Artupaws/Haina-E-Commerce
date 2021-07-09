package haina.ecommerce.view.howtopayment

import haina.ecommerce.model.howtopay.Data

interface HowToPayContract {

    fun messageGetHowToPay(msg:String)
    fun getDataHowToPay(data: Data?)

}