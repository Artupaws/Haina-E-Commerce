package haina.ecommerce.view.hotels.listroom

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetPricePolicy
import haina.ecommerce.model.hotels.newHotel.SpecialRequestArrayItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ListRoomPresenter(val view:ListRoomContract.View, val context: Context):ListRoomContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getPricePolicy(room_id: String, breakfastStatus: String) {
        view.showLoading()
        val disposable = NetworkConfig().getConnectionHainaBearerNew(context).getPricePolicy(room_id, breakfastStatus)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.dismissLoading()
                if (it.value == 1){
                    it.message?.let { it2 -> view.messageGetPricePolicy(it2) }
                    it.dataPricePolicy?.let { it1 -> view.getPricePolicy(it1) }
                }else {
                    it.message?.let { it1 -> view.messageGetPricePolicy(it1) }
                }
            }, {
                view.dismissLoading()
                view.messageGetPricePolicy(it.message.toString())
            })
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {
        TODO("Not yet implemented")
    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }

//    fun getPricePolicy(room_id:String, breakfastStatus:String) {
//        val getPricePolicy = NetworkConfig().getConnectionHainaBearer(context)
//            .getPricePolicy(room_id, breakfastStatus)
//        getPricePolicy.enqueue(object : retrofit2.Callback<ResponseGetPricePolicy> {
//            override fun onResponse(call: Call<ResponseGetPricePolicy>, response: Response<ResponseGetPricePolicy>) {
//                if (response.isSuccessful && response.body()?.value == 1) {
//                    view.messageGetPricePolicy(response.body()?.message.toString())
//                    val data = response.body()?.dataPricePolicy
//                    view.getPricePolicy(data)
//                } else {
//                    val error = JSONObject(response.errorBody()?.string())
//                    view.messageGetPricePolicy(error.getString("message"))
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseGetPricePolicy>, t: Throwable) {
//                view.messageGetPricePolicy(t.localizedMessage.toString())
//            }
//
//        })
//    }

}