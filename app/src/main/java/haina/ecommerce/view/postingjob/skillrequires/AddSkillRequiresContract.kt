package haina.ecommerce.view.postingjob.skillrequires

import haina.ecommerce.model.DataMyJob
import haina.ecommerce.model.DataSkillRequires

interface AddSkillRequiresContract {

    fun messageAddSkillRequires(msg:String)

    fun getSkillRequires(item:List<DataSkillRequires?>?)
    fun messageGetSkillRequires(msg:String)

}