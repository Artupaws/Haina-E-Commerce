package haina.ecommerce.view.posting.newvacancy

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseMyJob
import retrofit2.Call
import retrofit2.Response

class VacancyPresenter(val view: VacancyContract, val context: Context) {

    fun getDataMyPost() {
        NetworkConfig().getConnectionHainaBearer(context).getMyPostJob()
                .enqueue(object : retrofit2.Callback<ResponseMyJob> {
                    override fun onResponse(call: Call<ResponseMyJob>, response: Response<ResponseMyJob>) {
                        if (response.isSuccessful && response.body()?.value == 1) {
                            val data = response.body()!!.data
                            view.getListMyPost(data)
                            view.successLoadMyPost(response.body()?.message.toString())
                        } else {
                            view.errorLoadMyPost(response.body()?.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseMyJob>, t: Throwable) {
                        view.errorLoadMyPost(t.localizedMessage)
                    }

                })
    }

}