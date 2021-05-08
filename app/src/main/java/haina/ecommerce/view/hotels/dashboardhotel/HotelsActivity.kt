package haina.ecommerce.view.hotels.dashboardhotel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.AdapterListHotel
import haina.ecommerce.adapter.hotel.AdapterListLocationHotel
import haina.ecommerce.databinding.ActivityHotelsBinding
import haina.ecommerce.model.hotels.DataItem
import haina.ecommerce.model.hotels.Hotels
import haina.ecommerce.model.hotels.LocationHotels
import haina.ecommerce.view.hotels.detailhotel.DetailHotelsActivity

class HotelsActivity : AppCompatActivity(), HotelContract, AdapterListHotel.ItemAdapterCallBack,
    AdapterListLocationHotel.ItemAdapterCallback {

    private lateinit var binding:ActivityHotelsBinding
    private lateinit var presenter: HotelPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = HotelPresenter(this)
        presenter.getAllHotel()
        presenter.getListCity()
        binding.toolbarHotels.title = applicationContext.getString(R.string.hotels)
        binding.toolbarHotels.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarHotels.setNavigationOnClickListener { onBackPressed() }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.isNotEmpty()!!){
                    presenter.getHotelByName(p0)
                } else {
                    presenter.getAllHotel()
                }
                return true
            }

        })

    }

    override fun getMessageHotel(msg: String) {
        Log.d("dataHotel", msg)
    }

    override fun getDataAllHotel(data: List<DataItem?>?) {
        binding.rvHotels.apply {
            adapter = AdapterListHotel(applicationContext, data, this@HotelsActivity)
            layoutManager = GridLayoutManager(applicationContext, 2)
        }
    }

    override fun getListCity(data: MutableList<LocationHotels>?) {
        val defaultCity = mutableListOf<LocationHotels>()
        defaultCity.addAll(listOf(LocationHotels(-1, "All Location")))
        defaultCity.addAll(data!!)
        val adapterLocation = AdapterListLocationHotel(applicationContext, defaultCity, this)
        binding.rvLocationHotels.apply {
            adapter = adapterLocation
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapterLocation.notifyDataSetChanged()
        }
    }

    override fun onClick(view: View, dataHotel: DataItem) {
        when(view.id){
            R.id.cv_click -> {
                val intent = Intent(this, DetailHotelsActivity::class.java)
                        .putExtra("dataHotel", dataHotel)
                startActivity(intent)
            }
        }
    }

    override fun onClick(data: LocationHotels) {
        Log.d("idCity", data.toString())
        val dataString = data.idCity.toString()
        if (dataString.contains("-1")){
            presenter.getAllHotel()
        } else {
            presenter.getHotelByCity(data.idCity)
        }
    }
}
