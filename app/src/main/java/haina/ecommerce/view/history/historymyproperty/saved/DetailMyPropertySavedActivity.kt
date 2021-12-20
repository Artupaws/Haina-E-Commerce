package haina.ecommerce.view.history.historymyproperty.saved

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.synnapps.carouselview.ImageListener
import haina.ecommerce.R
import haina.ecommerce.adapter.property.AdapterListFacilityShow
import haina.ecommerce.databinding.FragmentDetailPropertyBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.property.DataShowProperty
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.room.roomsavedproperty.DataSavedProperty
import haina.ecommerce.room.roomsavedproperty.RoomDataSavedProperty
import haina.ecommerce.room.roomsavedproperty.SavedPropertyDao
import haina.ecommerce.util.Constants
import haina.ecommerce.view.property.detailmyproperty.DetailMyPropertyContract
import haina.ecommerce.view.property.detailmyproperty.DetailMyPropertyPresenter
import haina.ecommerce.view.property.fragmentdetailproperty.DetailPropertyContract
import haina.ecommerce.view.property.fragmentdetailproperty.DetailPropertyPresenter
import java.net.URLEncoder

class DetailMyPropertySavedActivity : AppCompatActivity(), View.OnClickListener, DetailPropertyContract.View {

    private lateinit var binding:FragmentDetailPropertyBinding
    private val helper:Helper=Helper
    private lateinit var listParams: ArrayList<String>
    private var  imagesListener : ImageListener? = null
    private var progressDialog:Dialog? = null
    private var confirmDialog:Dialog? = null
    private lateinit var presenter: DetailPropertyPresenter
    private var idProperty:Int = 0
    private var phoneNumber:String? = ""
    private var message :String = ""
    private lateinit var dao: SavedPropertyDao
    private lateinit var database: RoomDataSavedProperty
    private var transactionType:String = ""
    private var messageZh :String = ""
    private lateinit var sharedPref : SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DetailPropertyPresenter(this, this)
        sharedPref = SharedPreferenceHelper(this)
        listParams = ArrayList()
        database = RoomDataSavedProperty.getDatabase(this)
        dao = database.getDataPropertyDao()

        binding.btnRent.setOnClickListener(this)
        binding.btnBuy.setOnClickListener(this)

        val dataProperty = intent.getParcelableExtra<DataShowProperty>("dataProperty")
        phoneNumber = dataProperty?.owner?.phone?.substring(0,1)?.replace("0",
            "+62${dataProperty.owner.phone.length.let {
                dataProperty.owner.phone.substring(1, it) }}")
        message = "Hello, I am interested in *${dataProperty?.title}*,\n" +
                "I got this information from *Haina Service Indonesia App*, can we discuss it further?"
        messageZh = "你好，我对你的*${dataProperty?.title}*感兴趣。 \n" +
                "我从*海纳APP*得到了这个信息。我们可以讨论一下吗？"
        if (dataProperty != null) {
            idProperty = dataProperty.id!!
            showData(dataProperty)
        }
        binding.toolbarDetailProperty.setNavigationOnClickListener {
            onBackPressed()
        }
        if (dataProperty?.images != null){
            for (i in dataProperty.images) {
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
        dialogLoading()
        dialogConfirm(dataProperty?.id!!)
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

    private fun deleteProperty(dataProperty: DataSavedProperty){
        dao.delete(dataProperty)
    }

    private fun dialogConfirm(idProperty:Int){
        confirmDialog = Dialog(this)
        confirmDialog?.setContentView(R.layout.popup_logout)
        confirmDialog?.setCancelable(false)
        confirmDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(this,android.R.color.white))
        val window: Window = confirmDialog?.window!!
        window.setGravity(Gravity.CENTER)
        val title = confirmDialog?.findViewById<TextView>(R.id.tv_title)
        val description = confirmDialog?.findViewById<TextView>(R.id.tv_popup)
        val yes = confirmDialog?.findViewById<TextView>(R.id.tv_action_yes)
        val cancel = confirmDialog?.findViewById<TextView>(R.id.tv_action_cancel)
        title?.text = getString(R.string.okay)
        description?.text = getString(R.string.notes_transaction_property)
        yes?.setOnClickListener{
            presenter.changeAvailability(idProperty, transactionType)
            confirmDialog?.dismiss()
        }
        cancel?.setOnClickListener {
            confirmDialog?.dismiss()
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
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showData(data: DataShowProperty){
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
            adapter = AdapterListFacilityShow(applicationContext, data.facilities)
            layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        }
        binding.tvCertificate.text = data.certificateType
        binding.tvAddress.text = data.address
        binding.tvDescription.text = data.description
        binding.tvNameSeller.text = data.owner?.fullname
        val memberSince = "Member since ${data.owner?.createdAt?.substring(0,10)}"
        binding.tvSinceMember.text = memberSince
        Glide.with(applicationContext).load("https://hainaservice.com/storage/${data.owner?.photo}").into(binding.ivSeller)
        binding.relativeSeller.visibility = View.GONE
        binding.ivSaveProperty.visibility = View.GONE
        binding.linearLooks.visibility = View.GONE
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageChangeAvailability(msg: String) {
        Log.d("changeAvailability", msg)
        if (msg.contains("Success!")){
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            if (sharedPref.getValueString(Constants.LANGUAGE_APP) == "en") {
                openWhatsApp(phoneNumber!!, message)
            }
            else{
                openWhatsApp(phoneNumber!!, messageZh)
            }
        }
    }

    override fun messageUpdateBookmarkProperty(msg: String) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}