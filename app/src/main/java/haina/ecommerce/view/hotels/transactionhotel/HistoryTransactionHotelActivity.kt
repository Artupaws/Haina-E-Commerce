package haina.ecommerce.view.hotels.transactionhotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListBooking
import haina.ecommerce.adapter.hotel.TabAdapterHistoryHotelTransaction
import haina.ecommerce.databinding.ActivityHistoryTransactionBinding
import haina.ecommerce.model.hotels.Bookings
import haina.ecommerce.model.hotels.DataTransaction

class HistoryTransactionHotelActivity : AppCompatActivity(), HistoryTransactionHotelContract {

    private lateinit var binding:ActivityHistoryTransactionBinding
    private lateinit var presenter:HistoryTransactionHotelPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HistoryTransactionHotelPresenter(this, this)
        presenter.getListTransactionHotel()
//        initDataDummy()

        binding.toolbarTransaction.title = "Bookings"
        binding.toolbarTransaction.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarTransaction.setNavigationOnClickListener { onBackPressed() }
        binding.vpTransaction.adapter = TabAdapterHistoryHotelTransaction(supportFragmentManager, 0)
        binding.vpTransaction.offscreenPageLimit = 3
        binding.tabTransaction.setupWithViewPager(binding.vpTransaction)

    }

    override fun messageGetListTransactionHotel(msg: String) {
        Log.d("messageGetList", msg)
    }

    override fun getListTransactionHotel(data: List<DataTransaction?>?) {
        TODO("Not yet implemented")
    }

//    private fun initDataDummy(){
//        listBooking = ArrayList()
//        listBooking.add(Bookings("123A","Complete","12 APR","13 APR","2", "Jakarta", "1","VIP Room","Borobudur Hotel", "Bagus, pelayanan ramah", 5F))
//        listBooking.add(Bookings("123B","Complete","14 APR","15 APR","2", "Jakarta", "1","VIP Room","Prambanan Hotel", "Bagus, pelayanan ramah", 5f))
//        listBooking.add(Bookings("123C","Complete","16 APR","17 APR","2", "Jakarta", "1","VIP Room","Sriwijaya Hotel", "Hotelnya bagus dan nyaman", 4.5f))
//        listBooking.add(Bookings("123D","Complete","18 APR","19 APR","2", "Jakarta", "1","VIP Room","Borobudur Hotel", "", 0f))
//        listBooking.add(Bookings("123E","Waiting For You","20 APR","21 APR","2", "Jakarta", "1","VIP Room","Borobudur Hotel", "", 0f))
//    }
}