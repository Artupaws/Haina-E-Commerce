package haina.ecommerce.view.hotels.listcity

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterListCity
import haina.ecommerce.databinding.FragmentListCityHotelBinding
import haina.ecommerce.model.hotels.newHotel.CitiesItem
import haina.ecommerce.model.hotels.newHotel.DataCities

class ListCityHotelFragment : Fragment(), ListCityHotelContract, AdapterListCity.ItemAdapterCallBack {

    private lateinit var _binding:FragmentListCityHotelBinding
    private val binding get() = _binding
    private lateinit var presenter: ListCityHotelPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListCityHotelBinding.inflate(inflater, container, false)
        presenter = ListCityHotelPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getListCity()
    }

    override fun messageGetListCity(msg: String) {
        Log.d("getListCitYHotel", msg)
    }

    override fun getListCity(data: List<DataCities?>?) {
        for(i in data!!){
            binding.rvCityHotel.apply {
                adapter = AdapterListCity(requireActivity(), i?.cities, this@ListCityHotelFragment)
                layoutManager = GridLayoutManager(requireActivity(), 3)
            }
        }
    }

    override fun onClick(view: View, dataCity: CitiesItem) {
        when(view.id){
            R.id.tv_name_city -> {
                Toast.makeText(requireActivity(), dataCity.countryID.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}