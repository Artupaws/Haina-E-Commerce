package haina.ecommerce.view.notification

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.notification.ResponseGetNotification
import haina.ecommerce.model.notification.ResponseOpenNotification
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class NotificationPresenter(val view:NotificationContract, val context: Context) {

    fun getNotification(){
        val getNotif = NetworkConfig().getConnectionHainaBearer(context).getNotification()
        getNotif.enqueue(object : retrofit2.Callback<ResponseGetNotification>{
            override fun onResponse(call: Call<ResponseGetNotification>, response: Response<ResponseGetNotification>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetNotification(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataNotification(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetNotification(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetNotification>, t: Throwable) {
                view.messageGetNotification(t.localizedMessage.toString())
            }

        })
    }
    fun openNotification(Id:Int){
        val getNotif = NetworkConfig().getConnectionHainaBearer(context).openNotification(Id)
        getNotif.enqueue(object : retrofit2.Callback<ResponseOpenNotification>{
            override fun onResponse(call: Call<ResponseOpenNotification>, response: Response<ResponseOpenNotification>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetNotification(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetNotification(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseOpenNotification>, t: Throwable) {
                view.messageGetNotification(t.localizedMessage.toString())
            }

        })
    }

}