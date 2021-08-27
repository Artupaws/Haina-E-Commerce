package haina.ecommerce.view.posting.newvacancy

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseMyJob
import haina.ecommerce.model.vacancy.ResponseCreateVacancy
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class VacancyPresenter(val view: VacancyContract.ViewCreateVacancyFree, val context: Context) {

    fun createVacancyFree(positionJob:String, idCompany:Int, idSpecialist:Int, level:Int, type:Int,
    description:String, experience:Int, idEdu:Int, minSalary:Int, maxSalary:Int, salaryDisplay:Int, address:String,
    idCity:Int, packageId:Int, skill:String){
        view.showLoading()
        val createVacancyFree = NetworkConfig().getConnectionHainaBearer(context).createPostVacancy(positionJob, idCompany, idSpecialist, level, type, description, experience, idEdu, minSalary, maxSalary, salaryDisplay, address, idCity, packageId, skill)
        createVacancyFree.enqueue(object : retrofit2.Callback<ResponseCreateVacancy>{
            override fun onResponse(call: Call<ResponseCreateVacancy>, response: Response<ResponseCreateVacancy>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCreateVacancy(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageCreateVacancy(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCreateVacancy>, t: Throwable) {
                view.dismissLoading()
                view.messageCreateVacancy(t.localizedMessage.toString())
            }

        })

    }

}