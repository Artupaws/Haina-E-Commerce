package haina.ecommerce.view.posting

import haina.ecommerce.model.DataMyPost

interface PostingContract {

    fun successLoadMyPost(msg:String)
    fun errorLoadMyPost(msg:String)
    fun getListMyPost(list: List<DataMyPost?>?)

}