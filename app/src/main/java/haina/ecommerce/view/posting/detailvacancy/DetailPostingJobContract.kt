package haina.ecommerce.view.posting.detailvacancy

import haina.ecommerce.model.JobapplicantItem

interface DetailPostingJobContract {

    fun getListJobApplicant(item:List<JobapplicantItem?>?)
    fun messageGetListSuccess(msg:String)
    fun messageGetListFailed(msg:String)

}