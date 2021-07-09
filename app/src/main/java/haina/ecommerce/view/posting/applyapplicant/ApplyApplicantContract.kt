package haina.ecommerce.view.posting.applyapplicant

import haina.ecommerce.model.DataDocumentUser
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.model.StatusApplicant

interface ApplyApplicantContract {

    fun messageAddShortlistApplicant(msg:String)
    fun messageDeclineApplicant(msg:String)
    fun messageGetApplicantStatus(msg:String)

    fun getApplicantStatus(item:StatusApplicant?)
}