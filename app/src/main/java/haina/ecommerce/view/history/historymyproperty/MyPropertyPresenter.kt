package haina.ecommerce.view.history.historymyproperty

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.property.ResponseShowProperty
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class MyPropertyPresenter(val view:MyPropertyContract.View, val context: Context) {

    fun getShowProperty(){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).showMyProperty()
        showProperty.enqueue(object : retrofit2.Callback<ResponseShowProperty>{
            override fun onResponse(call: Call<ResponseShowProperty>, response: Response<ResponseShowProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListProperty(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataProperty(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListProperty(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseShowProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListProperty(t.localizedMessage.toString())
            }

        })
    }

}