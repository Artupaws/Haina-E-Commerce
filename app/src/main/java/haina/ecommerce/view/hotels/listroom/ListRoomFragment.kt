package haina.ecommerce.view.hotels.listroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentListRoomBinding
import haina.ecommerce.model.hotels.newHotel.DataRoom

class ListRoomFragment : Fragment() {

    private lateinit var _binding:FragmentListRoomBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.toolbarListRoom.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val dataRoom = arguments?.getParcelable<DataRoom>("dataRoom")
        setupView(dataRoom)

    }

    private fun setupView(data:DataRoom?){
        binding.tvNameHotel.text = data?.hotelInfo?.name
        binding.ratingBarHotel.rating = data.let { it?.hotelInfo?.rating!! }
        binding.tvAddressHotel.text = data?.hotelInfo?.address
        binding.tvDescriptionHotel.text = data?.hotelInfo?.message
    }

}