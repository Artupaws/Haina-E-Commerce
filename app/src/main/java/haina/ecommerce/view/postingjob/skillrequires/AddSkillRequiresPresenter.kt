package haina.ecommerce.view.postingjob.skillrequires

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseAddSkills
import haina.ecommerce.model.ResponseGetSkillRequires
import haina.ecommerce.model.ResponseMyJob
import retrofit2.Call
import retrofit2.Response

class AddSkillRequiresPresenter(val view:AddSkillRequiresContract, val context: Context) {

    fun addSkillRequires(skillName:String, idJobVacancy:Int){
        val addSkillRequires = NetworkConfig().getConnectionHainaBearer(context).addRequiresSKill(skillName, idJobVacancy)
        addSkillRequires.enqueue(object : retrofit2.Callback<ResponseAddSkills>{
            override fun onResponse(call: Call<ResponseAddSkills>, response: Response<ResponseAddSkills>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddSkillRequires("1")
                } else {
                    view.messageAddSkillRequires("0")
                }
            }

            override fun onFailure(call: Call<ResponseAddSkills>, t: Throwable) {
                view.messageAddSkillRequires(t.localizedMessage.toString())
            }

        })
    }

    fun getSkillRequires(idJobVacancy: Int){
        val getSkillRequires = NetworkConfig().getConnectionHainaBearer(context).getSkillRequires(idJobVacancy)
        getSkillRequires.enqueue(object : retrofit2.Callback<ResponseGetSkillRequires>{
            override fun onResponse(call: Call<ResponseGetSkillRequires>, response: Response<ResponseGetSkillRequires>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getSkillRequires(data)
                    view.messageAddSkillRequires(response.body()?.message.toString())
                } else {
                    view.messageAddSkillRequires(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetSkillRequires>, t: Throwable) {
                view.messageAddSkillRequires(t.localizedMessage!!)
            }

        })
    }

}