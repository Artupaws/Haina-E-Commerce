package haina.ecommerce.view.property.fragmentdetailproperty

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.adapter.property.AdapterListFacilityShow
import haina.ecommerce.databinding.FragmentDetailPropertyBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.property.DataShowProperty
import haina.ecommerce.room.roomphotoproperty.DataProperty
import haina.ecommerce.room.roomphotoproperty.PropertyDao
import haina.ecommerce.room.roomsavedproperty.DataSavedProperty
import haina.ecommerce.room.roomsavedproperty.RoomDataSavedProperty
import haina.ecommerce.room.roomsavedproperty.SavedPropertyDao
import java.net.URLEncoder


class DetailPropertyFragment : Fragment(), View.OnClickListener, DetailPropertyContract.View {

    private lateinit var _binding:FragmentDetailPropertyBinding
    private val binding get() = _binding
    private var  imagesListener : ImageListener? = null
    private lateinit var listParams: ArrayList<String>
    private val helper: Helper = Helper
    private var progressDialog:Dialog? = null
    private var confirmDialog:Dialog? = null
    private lateinit var dao:SavedPropertyDao
    private lateinit var database:RoomDataSavedProperty
    private var dataProperty:DataShowProperty? = null
    private lateinit var presenter: DetailPropertyPresenter
    private var transactionType:String = ""
    private var phoneNumber:String? = ""
    private var message :String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailPropertyBinding.inflate(inflater, container, false)
        database = RoomDataSavedProperty.getDatabase(requireActivity())
        dao = database.getDataPropertyDao()
        presenter = DetailPropertyPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnBuy.setOnClickListener(this)
        binding.btnRent.setOnClickListener(this)
        listParams = ArrayList()
        dataProperty = arguments?.getParcelable("dataProperty")
        getDataSaved(dataProperty?.id!!)
        phoneNumber = dataProperty?.owner?.phone?.substring(0,1)?.replace("0",
            "+62${dataProperty?.owner?.phone?.length?.let {
                dataProperty?.owner?.phone?.substring(1, it) }}")
        message = "Hello, i'am interested in *${dataProperty?.title}*,\n" +
                "I got this information from *Haina Service Indonesia App*, can we discuss it further?"
        if (dataProperty?.images != null){
            for (i in dataProperty!!.images!!) {
                i?.path?.let { listParams.add(it) }
                Log.d("listImageProperty", listParams.toString())
                binding.vpImageProperty.pageCount = listParams.size
                imagesListener = ImageListener { position, imageView ->
                    Glide.with(this).load(listParams[position]).placeholder(R.drawable.ps5).into(imageView)
                }
                binding.vpImageProperty.setImageListener(imagesListener)
                binding.vpImageProperty.setImageListener(imagesListener)
            }
        }
        val address = "${dataProperty?.city?.province}, ${dataProperty?.city?.nameCity}"
        binding.tvLocation.text = address
        binding.tvNameProperty.text = dataProperty?.title
        binding.toolbarDetailProperty.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        dataProperty?.let { showData(it) }
        dialogConfirm(dataProperty?.id!!)
        toggleSavedProperty()
        dialogLoading()
    }

    override fun onResume() {
        super.onResume()
        progressDialog?.dismiss()
    }

    private fun showData(data:DataShowProperty){
        if (data.rentalPrice.toString() == "0" && data.sellingPrice.toString() != "0"){
            binding.tvPriceSell.text = helper.convertToFormatMoneyIDRFilter(data.sellingPrice.toString())
            binding.linearPriceRent.visibility = View.GONE
            binding.btnRent.visibility = View.GONE
        } else if (data.rentalPrice.toString() != "0" && data.sellingPrice.toString() == "0"){
            binding.tvPriceRent.text = helper.convertToFormatMoneyIDRFilter(data.rentalPrice.toString())
            binding.linearPriceSell.visibility = View.GONE
            binding.btnBuy.visibility = View.GONE
        } else {
            val priceSell = helper.convertToFormatMoneyIDRFilter(data.sellingPrice.toString())
            val priceRent = helper.convertToFormatMoneyIDRFilter(data.rentalPrice.toString())
            binding.tvPriceSell.text =  priceSell
            binding.tvPriceRent.text =  priceRent
            binding.btnBuy.visibility = View.VISIBLE
            binding.btnRent.visibility = View.VISIBLE
        }

        binding.tvPropertyType.text = data.propertyType
        binding.tvBuildingArea.text = data.buildingArea
        binding.tvLandArea.text = data.landArea
        binding.tvBedroom.text = data.bedroom.toString()
        binding.tvBathroom.text= data.bathroom.toString()
        binding.tvFloor.text = data.floorLevel.toString()
        binding.rvFacility.apply {
            adapter = AdapterListFacilityShow(requireActivity(), data.facilities)
            layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.tvCertificate.text = data.certificateType
        binding.tvAddress.text = data.address
        binding.tvDescription.text = data.description
        binding.tvNameSeller.text = data.owner?.fullname
        val memberSince = "Member since ${data.owner?.createdAt?.substring(0,10)}"
        binding.tvSinceMember.text = memberSince
        Glide.with(requireActivity()).load("https://hainaservice.com/storage/${data.owner?.photo}").into(binding.ivSeller)
    }

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun dialogConfirm(idProperty:Int){
        confirmDialog = Dialog(requireActivity())
        confirmDialog?.setContentView(R.layout.popup_logout)
        confirmDialog?.setCancelable(false)
        confirmDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window: Window = confirmDialog?.window!!
        window.setGravity(Gravity.CENTER)
        val title = confirmDialog?.findViewById<TextView>(R.id.tv_title)
        val description = confirmDialog?.findViewById<TextView>(R.id.tv_popup)
        val yes = confirmDialog?.findViewById<TextView>(R.id.tv_action_yes)
        val cancel = confirmDialog?.findViewById<TextView>(R.id.tv_action_cancel)
        title?.text = "Confirmation"
        description?.text = requireActivity().getString(R.string.notes_transaction_property)
        yes?.setOnClickListener{
            presenter.changeAvailability(idProperty, transactionType)
            confirmDialog?.dismiss()
        }
        cancel?.setOnClickListener {
            confirmDialog?.dismiss()
        }
    }

    private fun toggleSavedProperty(){
        binding.ivSaveProperty.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                savePhotoProperty(DataSavedProperty(dataProperty?.id!!,
                    dataProperty?.images?.get(0)?.path.toString(), dataProperty?.sellingPrice, dataProperty?.title,
                dataProperty?.address, dataProperty?.rentalPrice))
                Toast.makeText(requireActivity(), "Property Saved", Toast.LENGTH_SHORT).show()
            }else{
                deleteProperty(DataSavedProperty(dataProperty?.id!!,
                    dataProperty?.images?.get(0)?.path.toString(), dataProperty?.sellingPrice, dataProperty?.title,
                    dataProperty?.address, dataProperty?.rentalPrice))
                Toast.makeText(requireActivity(), "Property Unsaved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun savePhotoProperty(dataProperty: DataSavedProperty){
        if (dao.getById(dataProperty.id).isEmpty()){
            dao.insert(dataProperty)
        }else{
            dao.update(dataProperty)
        }
    }

    private fun getDataSaved(idProperty: Int){
        binding.ivSaveProperty.isChecked = dao.getById(idProperty).isNotEmpty()
    }

    private fun deleteProperty(dataProperty:DataSavedProperty){
        dao.delete(dataProperty)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_buy -> {
                transactionType = "buy"
                confirmDialog?.show()
            }
            R.id.btn_rent -> {
                transactionType = "rental"
                confirmDialog?.show()
            }
        }
    }

    private fun openWhatsApp(number: String, message: String) {
        try {
            val url = "https://api.whatsapp.com/send?phone=$number&text=" + URLEncoder.encode(message, "UTF-8")
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            i.setPackage("com.whatsapp")
            startActivity(i)
        } catch (e: Exception) {
            Log.e("ERROR WHATSAPP", e.toString())
            Toast.makeText(requireActivity(), e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun messageChangeAvailability(msg: String) {
        Log.d("changeAvailability", msg)
        if (msg.contains("Success!")){
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
            openWhatsApp(phoneNumber!!, message)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.show()
    }
}