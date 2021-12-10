package haina.ecommerce.view.companycatalog.catalog.filter

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.companycatalog.ResponseGetCompanyItemCategory
import haina.ecommerce.model.restaurant.response.ResponseCuisineAndTypeList
import haina.ecommerce.model.restaurant.response.ResponseRestaurantList
import haina.ecommerce.view.restaurant.dashboard.RestaurantDashboardContract
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class CompanyCatalogItemsFilterPresenter(val view: CompanyCatalogItemsFilterContract, val context: Context) {

    fun getCompanyItemCategory(){
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).getCompanyItemCategory()
        showProperty.enqueue(object : retrofit2.Callback<ResponseGetCompanyItemCategory>{
            override fun onResponse(call: Call<ResponseGetCompanyItemCategory>, response: Response<ResponseGetCompanyItemCategory>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.message(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getCompanyCategoryList(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.message(error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseGetCompanyItemCategory>, t: Throwable) {
                view.message(t.localizedMessage.toString())
            }
        })
    }

}