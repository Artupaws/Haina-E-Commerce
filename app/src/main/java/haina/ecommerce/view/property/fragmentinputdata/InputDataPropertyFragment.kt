package haina.ecommerce.view.property.fragmentinputdata

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.property.AdapterListCity
import haina.ecommerce.adapter.property.AdapterListFacility
import haina.ecommerce.adapter.property.AdapterListProvince
import haina.ecommerce.databinding.FragmentInputDataPropertyBinding
import haina.ecommerce.model.property.DataCity
import haina.ecommerce.model.property.DataFacilitiesProperty
import haina.ecommerce.model.property.DataProvince
import haina.ecommerce.view.property.PropertyActivity
import java.util.ArrayList

class InputDataPropertyFragment : Fragment(), InputDataPropertyContract.View,
    AdapterListFacility.ItemAdapterCallback, AdapterListProvince.ItemAdapterCallback,
AdapterListCity.ItemAdapterCallback, View.OnClickListener{

    private lateinit var _binding :FragmentInputDataPropertyBinding
    private val binding get() = _binding
    private var progressDialog:Dialog? = null
    private var popupProvince:Dialog? = null
    private var popupCity:Dialog? = null
    private lateinit var presenter: InputDataPropertyPresenter
    private var listFacility= ArrayList<String>()
    private var propertyType:String = ""
    private var typePosting:String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInputDataPropertyBinding.inflate(inflater, container, false)
        presenter = InputDataPropertyPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbar7.setNavigationOnClickListener {
            (requireActivity() as PropertyActivity).onBackPressed()
        }
        binding.includeDataPropertyTop.etProvince.setOnClickListener(this)
        binding.includeDataPropertyTop.etCity.setOnClickListener(this)
        binding.includeDataPropertyTop.btnNext.setOnClickListener(this)
        presenter.getFacilities()
        presenter.getProvince()
        radioGroupPropertyType()
    }

    override fun onResume() {
        super.onResume()
        progressDialog?.dismiss()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(requireActivity().getDrawable(android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogProvince(dataProvince: List<DataProvince?>?) {
        popupProvince = Dialog(requireActivity())
        popupProvince?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupProvince?.setCancelable(true)
        popupProvince?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupProvince?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupProvince?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupProvince?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupProvince?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupProvince?.findViewById<TextView>(R.id.textView10)
        actionClose?.setOnClickListener { popupProvince?.dismiss() }
        title?.text = getString(R.string.province)
        rvDestination?.apply {
            adapter = AdapterListProvince(requireActivity(), dataProvince, this@InputDataPropertyFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        searchView?.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.isNotEmpty()!!){
                    (rvDestination?.adapter as AdapterListProvince).filter.filter(p0)
                    (rvDestination.adapter as AdapterListProvince).notifyDataSetChanged()
                } else {
                    (rvDestination?.adapter as AdapterListProvince).filter.filter("")
                }
                return true
            }
        })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun popupDialogCity(dataCity: List<DataCity?>?) {
        popupCity = Dialog(requireActivity())
        popupCity?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupCity?.setCancelable(true)
        popupCity?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupCity?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupCity?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupCity?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupCity?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupCity?.findViewById<TextView>(R.id.textView10)
        actionClose?.setOnClickListener { popupCity?.dismiss() }
        title?.text = getString(R.string.city_required)
        rvDestination?.apply {
            adapter = AdapterListCity(requireActivity(), dataCity, this@InputDataPropertyFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
        searchView?.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0?.isNotEmpty()!!){
                    (rvDestination?.adapter as AdapterListCity).filter.filter(p0)
                    (rvDestination.adapter as AdapterListCity).notifyDataSetChanged()
                } else {
                    (rvDestination?.adapter as AdapterListCity).filter.filter("")
                }
                return true
            }
        })
    }

    private fun radioGroupPropertyType(){
        binding.includeDataPropertyTop.rdGroupPropertyType.setOnCheckedChangeListener { _, i ->
            val radio: RadioButton = requireActivity().findViewById(i)
            radioButtonTypeProperty(radio)
        }
        binding.includeDataPropertyTop.rdGroupTypePosting.setOnCheckedChangeListener { _, i ->
            val radio: RadioButton = requireActivity().findViewById(i)
            radioButtonTypePosting(radio)
        }
    }

    private fun radioButtonTypePosting(view: View){
        val radio: RadioButton = requireActivity().findViewById(binding.includeDataPropertyTop.rdGroupTypePosting.checkedRadioButtonId)
        typePosting = radio.text.toString()
        when(typePosting){
            "For Sell" -> {
                binding.includeDataPropertyTop.linearPriceRent.visibility = View.GONE
                binding.includeDataPropertyTop.linearPriceSell.visibility = View.VISIBLE
            }
            "For Rent" -> {
                binding.includeDataPropertyTop.linearPriceRent.visibility = View.VISIBLE
                binding.includeDataPropertyTop.linearPriceSell.visibility = View.GONE
            }
            "Both" -> {
                binding.includeDataPropertyTop.linearPriceRent.visibility = View.VISIBLE
                binding.includeDataPropertyTop.linearPriceSell.visibility = View.VISIBLE
            }
        }
    }

    private fun radioButtonTypeProperty(view: View){
        val radio: RadioButton = requireActivity().findViewById(binding.includeDataPropertyTop.rdGroupPropertyType.checkedRadioButtonId)
        propertyType = radio.text.toString()
        when(propertyType){
            "Warehouse" -> {
                binding.includeDataPropertyTop.linearBedroom.visibility = View.GONE
            }
            "Office" -> {
                binding.includeDataPropertyTop.linearBedroom.visibility = View.GONE
            }
            else -> {
                binding.includeDataPropertyTop.linearBedroom.visibility = View.VISIBLE
            }
        }
    }

    override fun messageGetFacilities(msg: String) {
        Log.d("getFacilities", msg)
        progressDialog?.dismiss()
    }

    override fun messageGetProvince(msg: String) {
        Log.d("getProvince", msg)
        progressDialog?.dismiss()
    }

    override fun messageGetCity(msg: String) {
        Log.d("getCity", msg)
        progressDialog?.dismiss()
    }

    override fun getDataFacilites(data: List<DataFacilitiesProperty?>?) {
        binding.includeDataPropertyTop.rvFacility.apply {
            adapter = AdapterListFacility(requireActivity(), data, this@InputDataPropertyFragment)
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun getDataProvince(data: List<DataProvince?>?) {
        popupDialogProvince(data)
    }

    override fun getDataCity(data: List<DataCity?>?) {
        if (data != null){
            binding.includeDataPropertyTop.linearCity.visibility = View.VISIBLE
            popupDialogCity(data)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClickAdapter(view: View, data: DataFacilitiesProperty, status:Boolean) {
        when(view.id){
            R.id.cb_addon -> {
                when(status){
                    true -> {
                        listFacility.add(data.name!!)
                        Toast.makeText(requireActivity(), listFacility.toString(), Toast.LENGTH_SHORT).show()
                    }
                    false -> {
                        listFacility.remove(data.name!!)
                        Toast.makeText(requireActivity(), listFacility.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onClickAdapterProvince(view: View, data: DataProvince) {
        when(view.id){
            R.id.tv_province -> {
                val provinceName = data.name
                if (provinceName != binding.includeDataPropertyTop.etProvince.text.toString() && binding.includeDataPropertyTop.etCity.text.isNotEmpty()){
                    binding.includeDataPropertyTop.etCity.setText("")
                }
                binding.includeDataPropertyTop.etProvince.setText(data.name.toString())
                presenter.getCity(data.id!!)
                popupProvince?.dismiss()
            }
        }
    }

    override fun onClickAdapterCity(view: View, data: DataCity) {
        when(view.id){
            R.id.tv_province -> {
                binding.includeDataPropertyTop.etCity.setText(data.name.toString())
                popupCity?.dismiss()
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.et_province -> {
                popupProvince?.show()
            }
            R.id.et_city -> {
                popupCity?.show()
            }
            R.id.btn_next -> {
                Navigation.findNavController(binding.includeDataPropertyTop.btnNext).navigate(R.id.action_inputDataPropertyFragment_to_addPhotoFragment)
            }
        }
    }

}