package haina.ecommerce.view.applyjob

import haina.ecommerce.base.BaseView
import haina.ecommerce.model.DataDocumentUser
import haina.ecommerce.model.DataUser

interface ApplyJobContract {

    interface View:BaseView{
        fun messageGetDataPersonal(msg:String)
        fun messagetGetDocument(msg:String)
        fun messageUploadDocument(msg:String)
        fun messageApplyJob(msg:String)

        fun getDocumentResume(item: List<DataDocumentUser?>?)
        fun getDataUser(data : DataUser?)
    }

}