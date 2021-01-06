package haina.ecommerce.view.myaccount.addskills

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseAddSkills
import haina.ecommerce.model.ResponseDeleteSkills
import haina.ecommerce.model.ResponseGetUserSkills
import retrofit2.Call
import retrofit2.Response

class AddSkillPresenter (val view:AddSkillsContract, val context: Context){

    fun addSkills(skillName:String){
        val addSkills = NetworkConfig().getConnectionHainaBearer(context).addSkillsUser(skillName)
        addSkills.enqueue(object : retrofit2.Callback<ResponseAddSkills>{
            override fun onResponse(call: Call<ResponseAddSkills>, response: Response<ResponseAddSkills>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddSkills("1")
                } else {
                    view.messageAddSkills("0")
                }
            }

            override fun onFailure(call: Call<ResponseAddSkills>, t: Throwable) {
                view.messageAddSkills(t.localizedMessage.toString())
            }

        })
    }

    fun showListSkill(){
        val getListSkill = NetworkConfig().getConnectionHainaBearer(context).getListSkill()
        getListSkill.enqueue(object : retrofit2.Callback<ResponseGetUserSkills>{
            override fun onResponse(call: Call<ResponseGetUserSkills>, response: Response<ResponseGetUserSkills>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListSkills(data)
                    view.messageGetListSkills(response.body()?.value.toString())
                } else {
                    view.messageGetListSkills(response.body()?.value.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetUserSkills>, t: Throwable) {
                view.messageGetListSkills(t.localizedMessage.toString())
            }

        })

    }

    fun deleteSkills(skillName:String){
        val deleteSkills = NetworkConfig().getConnectionHainaBearer(context).deleteSkills(skillName)
        deleteSkills.enqueue(object : retrofit2.Callback<ResponseDeleteSkills>{
            override fun onResponse(call: Call<ResponseDeleteSkills>, response: Response<ResponseDeleteSkills>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageDeleteSkills(response.body()?.message.toString())
                } else {
                    view.messageDeleteSkills(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseDeleteSkills>, t: Throwable) {
                view.messageDeleteSkills(t.localizedMessage.toString())
            }

        })
    }

}