package haina.ecommerce.view.myaccount.addskills

import haina.ecommerce.model.DataSkillsUser

interface AddSkillsContract {

    fun messageAddSkills(msg:String)
    fun getListSkills(data:List<DataSkillsUser?>?)
    fun messageGetListSkills(msg:String)
    fun messageDeleteSkills(msg:String)

}