package haina.ecommerce.view.hotels.listhotel

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetRoomHotel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ListHotelPresenter(val view:ListHotelContract.View, val context: Context):ListHotelContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable?
    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

//    fun getRoomHotel(idHotel:String){
//        val getRoom = NetworkConfig().getConnectionHainaBearer(context).getDataRoom(idHotel)
//        getRoom.enqueue(object : retrofit2.Callback<ResponseGetRoomHotel>{
//            override fun onResponse(call: Call<ResponseGetRoomHotel>, response: Response<ResponseGetRoomHotel>) {
//                if (response.isSuccessful && response.body()?.value == 1){
//                    view.messageGetRoomHotel(response.body()?.message.toString())
//                    val data = response.body()?.dataRoom
//                    view.getDataRoom(data)
//                } else {
//                    val error = JSONObject(response.errorBody()?.string())
//                    view.messageGetRoomHotel(error.getString("message"))
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseGetRoomHotel>, t: Throwable) {
//                view.messageGetRoomHotel(t.localizedMessage.toString())
//            }
//
//        })
//    }

    override fun getDataRoom(idHotel: String) {
        view.showLoading()
        val disposable = NetworkConfig().getConnectionHainaBearerNew(context).getDataRoom(idHotel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.dismissLoading()
                if (it.value == 1){
                    it.message?.let { it2 -> view.messageGetRoomHotel(it2) }
                    it.dataRoom?.let { it1 -> view.getDataRoom(it1) }
                }else {
                    it.message?.let { it1 -> view.messageGetRoomHotel(it1) }
                }
            }, {
                view.dismissLoading()
                view.messageGetRoomHotel(it.message.toString())
            })
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {
    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }

}