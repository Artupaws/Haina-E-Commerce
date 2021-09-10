package haina.ecommerce.view.myaccount.detailaccount

import android.content.Context
import android.util.Log
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.*
import haina.ecommerce.model.forum.ResponseGiveUpvote
import haina.ecommerce.model.vacancy.ResponseGetDataCreateVacancy
import haina.ecommerce.util.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class DetailAccountPresenter(val view:DetailAccountContract.View, val context: Context) {

    fun loadDocumentResume(idDocsCategory:Int){
        val loadDocumentUser = NetworkConfig().getConnectionHainaBearer(context).loadDocumentUser(idDocsCategory)
        loadDocumentUser.enqueue(object : retrofit2.Callback<ResponseLoadDocumentUser>{
            override fun onResponse(call: Call<ResponseLoadDocumentUser>, response: Response<ResponseLoadDocumentUser>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    Log.d("document", response.body()?.data.toString())
                    view.getDocumentResume(data)
                    view.messageLoadDocumentUser(response.body()?.message.toString())
                } else {
                    view.messageLoadDocumentUser(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseLoadDocumentUser>, t: Throwable) {
                view.messageLoadDocumentUser(t.localizedMessage.toString())
            }

        })
    }

    fun loadDocumentPortfolio(idDocsCategory:Int){
        val loadDocumentUser = NetworkConfig().getConnectionHainaBearer(context).loadDocumentUser(idDocsCategory)
        loadDocumentUser.enqueue(object : retrofit2.Callback<ResponseLoadDocumentUser>{
            override fun onResponse(call: Call<ResponseLoadDocumentUser>, response: Response<ResponseLoadDocumentUser>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getDocumentPortfolio(data)
                    view.messageLoadDocumentUser(response.body()?.message.toString())
                } else {
                    view.messageLoadDocumentUser(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseLoadDocumentUser>, t: Throwable) {
                view.messageLoadDocumentUser(t.localizedMessage.toString())
            }

        })
    }

    fun loadDocumentCertificate(idDocsCategory:Int){
        val loadDocumentUser = NetworkConfig().getConnectionHainaBearer(context).loadDocumentUser(idDocsCategory)
        loadDocumentUser.enqueue(object : retrofit2.Callback<ResponseLoadDocumentUser>{
            override fun onResponse(call: Call<ResponseLoadDocumentUser>, response: Response<ResponseLoadDocumentUser>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getDocumentCertificate(data)
                    view.messageLoadDocumentUser(response.body()?.message.toString())
                } else {
                    view.messageLoadDocumentUser(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseLoadDocumentUser>, t: Throwable) {
                view.messageLoadDocumentUser(t.localizedMessage.toString())
            }

        })
    }

    fun addDataPersonalUser(fullname:String, birthdate:String, gender:String, address:String, about:String){
        val addDataPersonalUser = NetworkConfig().getConnectionHainaBearer(context).addPersonalDataUser(fullname, birthdate, gender, address, about)
        addDataPersonalUser.enqueue(object : retrofit2.Callback<ResponseAddPersonalData>{
            override fun onResponse(call: Call<ResponseAddPersonalData>, response: Response<ResponseAddPersonalData>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddDataPersonalUser(response.body()?.value.toString())
                } else {
                    view.messageAddDataPersonalUser(response.body()?.value.toString())
                }
            }

            override fun onFailure(call: Call<ResponseAddPersonalData>, t: Throwable) {
                view.messageAddDataPersonalUser(t.localizedMessage.toString())
            }

        })
    }

    fun getDataUserProfile(){
        NetworkConfig().getConnectionHainaBearer(context).getDataUser()
                .enqueue(object : retrofit2.Callback<ResponseGetDataUser>{
                    override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {
                        if (response.isSuccessful && response.body()?.value == 1){
                            val data = response.body()?.data
                            view.getDataUser(data)
                            view.messageLoadDataPersonal(response.body()?.message.toString())
                        } else {
                            view.messageLoadDataPersonal(response.body()?.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                        view.messageLoadDataPersonal(t.localizedMessage)
                    }

                })
    }

    fun getSkillsUser(){
        val getListSkill = NetworkConfig().getConnectionHainaBearer(context).getListSkill()
        getListSkill.enqueue(object : retrofit2.Callback<ResponseGetUserSkills>{
            override fun onResponse(call: Call<ResponseGetUserSkills>, response: Response<ResponseGetUserSkills>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getSkillsUser(data)
                    view.messageLoadSkillUser(response.body()?.value.toString())
                } else {
                    view.messageLoadSkillUser(response.body()?.value.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetUserSkills>, t: Throwable) {
                view.messageLoadSkillUser(t.localizedMessage.toString())
            }

        })

    }

    fun deleteSkills(skillName:String){
        val deleteSkills = NetworkConfig().getConnectionHainaBearer(context).deleteSkills(skillName)
        deleteSkills.enqueue(object : retrofit2.Callback<ResponseDeleteSkills>{
            override fun onResponse(call: Call<ResponseDeleteSkills>, response: Response<ResponseDeleteSkills>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageDeleteSkill(response.body()?.message.toString())
                } else {
                    view.messageDeleteSkill(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseDeleteSkills>, t: Throwable) {
                view.messageDeleteSkill(t.localizedMessage.toString())
            }

        })
    }

    fun addWorkExperience(company:String, city:String, dateStart:String, dateEnd:String, position:String, description:String, salary:Int){
        view.showLoading()
        val addDataPersonalUser = NetworkConfig().getConnectionHainaBearer(context).addWorkExperience(company, city, dateStart, dateEnd, position, description, salary)
        addDataPersonalUser.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddWorkExperience(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageAddWorkExperience(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageAddWorkExperience(t.localizedMessage.toString())
            }

        })
    }

    fun updateWorkExperience(company:String, city:String, dateStart:String, dateEnd:String, position:String, description:String, salary:Int){
        view.showLoading()
        val addDataPersonalUser = NetworkConfig().getConnectionHainaBearer(context).updateWorkExperience(company, city, dateStart, dateEnd, position, description, salary)
        addDataPersonalUser.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddWorkExperience(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageAddWorkExperience(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageAddWorkExperience(t.localizedMessage.toString())
            }

        })
    }

    fun addLastEducation(institution:String, yearStart:String, yearEnd:String, gpa:Double?, major:String, idEdu:Int, city:String){
        view.showLoading()
        val addDataPersonalUser = NetworkConfig().getConnectionHainaBearer(context).addLastEducation(institution, yearStart, yearEnd, gpa, major, idEdu, city)
        addDataPersonalUser.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddLastEducation(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageAddLastEducation(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageAddLastEducation(t.localizedMessage.toString())
            }

        })
    }

    fun updateLastEducation(institution:String, yearStart:String, yearEnd:String, gpa:Double?, major:String, idEdu:Int, city:String){
        view.showLoading()
        val addDataPersonalUser = NetworkConfig().getConnectionHainaBearer(context).updateLastEducation(institution, yearStart, yearEnd, gpa, major, idEdu, city)
        addDataPersonalUser.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddLastEducation(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageAddLastEducation(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageAddLastEducation(t.localizedMessage.toString())
            }

        })
    }

    fun getDataCreateVacancy(){
        view.showLoading()
        val dataCreateVacancy = NetworkConfig().getConnectionHainaBearer(context).getDataCreateVacancy()
        dataCreateVacancy.enqueue(object : retrofit2.Callback<ResponseGetDataCreateVacancy>{
            override fun onResponse(call: Call<ResponseGetDataCreateVacancy>, response: Response<ResponseGetDataCreateVacancy>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetDataCreateVacancy(response.body()?.message.toString())
                    val data = response.body()?.dataCreateVacancy
                    view.getDataCreateVacancy(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetDataCreateVacancy(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetDataCreateVacancy>, t: Throwable) {
                view.messageGetDataCreateVacancy(t.localizedMessage.toString())
            }

        })
    }


}