package haina.ecommerce.view.myaccount.detailaccount

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataDocumentUser
import haina.ecommerce.model.DataSkillsUser
import haina.ecommerce.model.DataUser

interface DetailAccountContract {



    interface View:BaseView{
        fun getDocumentResume(item: List<DataDocumentUser?>?)
        fun getDocumentPortfolio(item: List<DataDocumentUser?>?)
        fun getDocumentCertificate(item: List<DataDocumentUser?>?)
        fun getDataUser(data : DataUser?)
        fun getSkillsUser(data: List<DataSkillsUser?>?)

        fun messageLoadDataPersonal(msg:String)
        fun messageLoadSkillUser(msg:String)
        fun messageLoadDocumentUser(msg:String)
        fun messageAddDataPersonalUser(msg:String)
        fun messageDeleteSkill(msg:String)
        fun messageAddWorkExperience(msg:String)
    }
}