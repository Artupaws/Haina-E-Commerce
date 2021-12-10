package haina.ecommerce.view.companycatalog.item.detail

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.companycatalog.ResponseGetCompanyItem
import haina.ecommerce.model.companycatalog.ResponseGetCompanyItemCategory
import haina.ecommerce.model.companycatalog.ResponseGetItemDetail
import haina.ecommerce.view.companycatalog.dashboard.CompanyDashboardContract
import haina.ecommerce.view.companycatalog.item.detail.CompanyItemDetailContract
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class CompanyItemDetailPresenter(val view: CompanyItemDetailContract.View, val context: Context) {

    fun getItemDetail(idItem:Int){
        view.showLoading()
        val companyItem = NetworkConfig().getConnectionHainaBearer(context).getItemDetail(idItem)
        companyItem.enqueue(object : retrofit2.Callback<ResponseGetItemDetail>{
            override fun onResponse(call: Call<ResponseGetItemDetail>, response: Response<ResponseGetItemDetail>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getItemDetail(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.message(error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseGetItemDetail>, t: Throwable) {
                view.dismissLoading()
                view.message(t.localizedMessage.toString())
            }
        })
    }

}