package haina.ecommerce.view.companycatalog.catalog

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.companycatalog.ResponseGetCompanyItem
import haina.ecommerce.model.companycatalog.ResponseGetCompanyItemCategory
import haina.ecommerce.view.companycatalog.dashboard.CompanyDashboardContract
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class CompanyCatalogItemsPresenter(val view: CompanyCatalogItemsContract.View, val context: Context) {

    fun getCompanyItem(idCategory:Int){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).getCompanyItemByCategory(idCategory)
        showProperty.enqueue(object : retrofit2.Callback<ResponseGetCompanyItem>{
            override fun onResponse(call: Call<ResponseGetCompanyItem>, response: Response<ResponseGetCompanyItem>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getCompanyItemList(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.message(error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseGetCompanyItem>, t: Throwable) {
                view.dismissLoading()
                view.message(t.localizedMessage.toString())
            }
        })
    }

}