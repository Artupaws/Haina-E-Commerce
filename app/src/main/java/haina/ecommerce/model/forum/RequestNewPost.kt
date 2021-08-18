package haina.ecommerce.model.forum


data class RequestNewPost(
    val subforumId:Int,
    val title:String,
    val imagePost:List<ImagePostData?>?,
    val description:String
)
