package haina.ecommerce.view.property.fragmentaddphoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentAddPhotoBinding

class AddPhotoFragment : Fragment() {

    private lateinit var _binding:FragmentAddPhotoBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarAddPhotoProperty.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }
}