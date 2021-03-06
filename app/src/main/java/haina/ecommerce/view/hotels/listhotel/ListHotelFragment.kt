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
import haina.ecommerce.databinding.FragmentScheduleHotelBinding
import haina.ecommerce.model.hotels.newHotel.DataHotelDarma
import haina.ecommerce.model.hotels.newHotel.DataRoom

class ListHotelFragment : Fragment(), AdapterListHotelDarma.ItemAdapterCallBack, ListHotelContract {

    private lateinit var _binding:FragmentScheduleHotelBinding
    private val binding get() = _binding
    private lateinit var presenter: ListHotelPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentScheduleHotelBinding.inflate(inflater, container, false)
        presenter = ListHotelPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val dataHotelDarma = arguments?.getParcelable<DataHotelDarma>("dataHotel")
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
            Navigation.findNavController(binding.root).navigate(R.id.action_scheduleHotelFragment_to_listRoomFragment, bundle)
        }
    }

}