package haina.ecommerce.view.property.detailmyproperty

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
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

class DetailMyPropertyActivity : AppCompatActivity(), View.OnClickListener, DetailMyPropertyContract.View {

    private lateinit var binding:FragmentDetailPropertyBinding
    private val helper:Helper=Helper
    private lateinit var listParams: ArrayList<String>
    private var  imagesListener : ImageListener? = null
    private var progressDialog:Dialog? = null
    private lateinit var presenter: DetailMyPropertyPresenter
    private var idProperty:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDetailPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DetailMyPropertyPresenter(this, this)
        listParams = ArrayList()

        binding.btnRent.setOnClickListener(this)
        binding.btnBuy.setOnClickListener(this)

        val dataProperty = intent.getParcelableExtra<DataShowProperty>("dataProperty")
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
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_rent -> {
                presenter.deleteProperty(idProperty)
            }
        }
    }

    private fun showData(data: DataShowProperty){
        if (data.rentalPrice.toString() == "0" && data.sellingPrice.toString() != "0"){
            binding.tvPriceSell.text = helper.convertToFormatMoneyIDRFilter(data.sellingPrice.toString())
            binding.linearPriceRent.visibility = View.GONE
        } else if (data.rentalPrice.toString() != "0" && data.sellingPrice.toString() == "0"){
            binding.tvPriceRent.text = helper.convertToFormatMoneyIDRFilter(data.rentalPrice.toString())
            binding.linearPriceSell.visibility = View.GONE
        } else {
            val priceSell = helper.convertToFormatMoneyIDRFilter(data.sellingPrice.toString())
            val priceRent = helper.convertToFormatMoneyIDRFilter(data.rentalPrice.toString())
            binding.tvPriceSell.text =  priceSell
            binding.tvPriceRent.text =  priceRent
        }
        binding.tvLooks.text = data.views.toString()
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
        binding.linearLooks.visibility = View.VISIBLE
        binding.relativeSeller.visibility = View.GONE
        binding.ivSaveProperty.visibility = View.GONE
        binding.btnRent.setBackgroundColor(Color.RED)
        binding.btnRent.setText(R.string.delete)
        binding.btnBuy.setText(R.string.mark_as_sold)
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageDeleteProperty(msg: String) {
        Log.d("deleteProperty", msg)
        if (msg.contains("Property Deleted!")){
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}