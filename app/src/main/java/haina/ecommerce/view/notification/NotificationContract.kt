package haina.ecommerce.view.notification

import haina.ecommerce.model.notification.DataItemNotification

interface NotificationContract {

    fun messageGetNotification(msg:String)
    fun getDataNotification(data:List<DataItemNotification?>?)

}