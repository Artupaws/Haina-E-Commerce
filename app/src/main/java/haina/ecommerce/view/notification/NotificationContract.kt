package haina.ecommerce.view.notification

import haina.ecommerce.model.notification.Notificationcategory

interface NotificationContract {

    fun messageGetNotification(msg:String)
    fun getDataNotification(data:List<Notificationcategory?>?)

}