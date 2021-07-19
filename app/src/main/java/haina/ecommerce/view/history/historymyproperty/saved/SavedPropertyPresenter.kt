package haina.ecommerce.view.history.historymyproperty.saved

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.property.ResponseShowProperty
import haina.ecommerce.model.property.ResponseViewDetailProperty
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class SavedPropertyPresenter(val view:SavedPropertyContract.View, val context: Context) {

    fun getShowProperty(idProperty:Int){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).addViewProperty(idProperty)
        showProperty.enqueue(object : retrofit2.Callback<ResponseViewDetailProperty>{
            override fun onResponse(call: Call<ResponseViewDetailProperty>, response: Response<ResponseViewDetailProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListProperty(response.body()?.message.toString())
                    val data = response.body()?.dataViewDetailProperty
                    view.getDataProperty(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListProperty(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseViewDetailProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListProperty(t.localizedMessage.toString())
            }

        })
    }

}