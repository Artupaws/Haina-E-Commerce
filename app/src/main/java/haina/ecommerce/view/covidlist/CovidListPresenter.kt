package haina.ecommerce.view.covidlist

import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseCovid
import retrofit2.Call
import retrofit2.Response

class CovidListPresenter(val view: CovidListContract) {

    fun loadCovidList(){
        val callListCovid = NetworkConfig().getConnectionHaina().getDataCovidIndo()
        callListCovid.enqueue(object : retrofit2.Callback<ResponseCovid>{
            override fun onResponse(call: Call<ResponseCovid>, response: Response<ResponseCovid>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.loadListCovid(data)
                    view.errorMessage(response.body()?.message.toString())
                } else {
                    view.errorMessage(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseCovid>, t: Throwable) {
                view.errorMessage(t.localizedMessage)
            }

        })
    }

}