package haina.ecommerce.view.hotels.listhotel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.hotel.newAdapterHotel.AdapterListHotelDarma
import haina.ecommerce.databinding.FragmentListHotelBinding
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma
import haina.ecommerce.model.hotels.newHotel.DataRoom

class ListHotelFragment : Fragment(), AdapterListHotelDarma.ItemAdapterCallBack, ListHotelContract {

    private lateinit var _binding:FragmentListHotelBinding
    private val binding get() = _binding
    private lateinit var presenter: ListHotelPresenter
    private var totalNight:Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListHotelBinding.inflate(inflater, container, false)
        presenter = ListHotelPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dataHotelDarma = arguments?.getParcelable<DataHotelDarma>("dataHotel")
        totalNight = arguments?.getInt("totalNight", 0)
        val adapterHotel = AdapterListHotelDarma(requireActivity(), dataHotelDarma?.hotels, this)

        binding.toolbar4.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.rvHotels.apply {
            adapter = adapterHotel
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onClick(view: View, idHotel: String) {
        when(view.id){
            R.id.cv_click -> {
                presenter.getRoomHotel(idHotel)
            }
        }
    }

    override fun messageGetRoomHotel(msg: String) {
        Log.d("getDataRoom", msg)
    }

    override fun getDataRoom(data: DataRoom?) {
        if (data != null){
            val bundle = Bundle()
            bundle.putParcelable("dataRoom", data)
            totalNight?.let { bundle.putInt("totalNight", it) }
            Navigation.findNavController(binding.root).navigate(R.id.action_scheduleHotelFragment_to_listRoomFragment, bundle)
        }
    }

}