package haina.ecommerce.view.datacompany

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterAddressCompany
import haina.ecommerce.adapter.AdapterJobLocation
import haina.ecommerce.adapter.AdapterPhotoCompany
import haina.ecommerce.databinding.ActivityDataCompanyBinding
import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.model.PhotoItemDataCompany

class DataCompanyActivity : AppCompatActivity(), DataCompanyContract, View.OnClickListener {

    private lateinit var binding: ActivityDataCompanyBinding
    private lateinit var presenter: DataCompanyPresenter
    private var popupAddress: AlertDialog? = null
    private var popupLocation: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = DataCompanyPresenter(this, this)
        presenter.getDataCompany()
        presenter.loadListLocation()
        refresh()
        binding.toolbarDataCompany.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDataCompany.setNavigationOnClickListener { onBackPressed() }
        binding.ivActionEdit.setOnClickListener(this)
        binding.tvAddAddress.setOnClickListener(this)

    }

    private fun refresh(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            presenter.getDataCompany()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_action_edit ->{
                binding.etCompanyName.requestFocus()
                binding.etCompanyName.isFocusable
                binding.etDescriptionCompany.requestFocus()
            }
            R.id.tv_add_address ->{
                popupAddress?.show()
            }
        }
    }

    private fun popupAddAddress(itemHaina: List<DataItemHaina?>?){
        val popup = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.popup_address_data_company, null)
        popup.setCancelable(true)
        popup.setView(view)
        val address = view.findViewById<EditText>(haina.ecommerce.R.id.et_address_company)
        val btnAdd = view.findViewById<Button>(R.id.btn_add_address_company)
        val location = view.findViewById<EditText>(R.id.et_location_company)
        popupAddress = popup.create()
        popupAddress?.dismiss()
        btnAdd.setOnClickListener {
            popupAddress?.dismiss()
        }
        location.setOnClickListener {
            val popup = AlertDialog.Builder(this)
            val view: View = layoutInflater.inflate(R.layout.layout_pop_up_list, null)
            popup.setCancelable(true)
            popup.setView(view)
            val action = view.findViewById<TextView>(haina.ecommerce.R.id.tv_action)
            val title = view.findViewById<TextView>(haina.ecommerce.R.id.tv_title)
            val rvJob = view.findViewById<RecyclerView>(haina.ecommerce.R.id.rv_popup)
            val jobLocationAdapter = AdapterJobLocation(this, itemHaina)
            popupLocation = popup.create()
            popupLocation?.show()
            action.setOnClickListener{popupLocation?.dismiss()}
            title.text = "Choose Location"
            rvJob.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = jobLocationAdapter
                jobLocationAdapter.notifyDataSetChanged()
            } }
    }

    override fun getDataCompany(item: DataCompany) {
        val getListPhotoCompany = AdapterPhotoCompany(this, item.photoDataCompany)
        val getListAddressCompany = AdapterAddressCompany(this, item.addressCompanies)
            binding.etCompanyName.setText(item.name)
            Glide.with(this).load(item.iconUrl).skipMemoryCache(false).diskCacheStrategy(
                DiskCacheStrategy.NONE).into(binding.circleImageViewCompany)
            binding.etDescriptionCompany.setText(item.description)
            binding.rvPhotoCompany.apply {
                layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                adapter = getListPhotoCompany
            }
            binding.rvAddressCompany.apply {
                layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                adapter = getListAddressCompany
            }
    }

    override fun loadJobLocation(itemHaina: List<DataItemHaina?>?) {
        popupAddAddress(itemHaina)
    }

    override fun messageGetDataCompany(msg: String) {
        Log.d("messageCompany", msg)
        binding.swipeRefreshLayout.isRefreshing = false
    }
}