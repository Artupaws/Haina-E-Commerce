package haina.ecommerce.view.datacompany.address

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterJobLocation
import haina.ecommerce.databinding.ActivityAddAddressCompanyBinding
import haina.ecommerce.model.DataCompany
import haina.ecommerce.model.DataItemHaina
import haina.ecommerce.view.datacompany.DataCompanyActivity
import haina.ecommerce.view.datacompany.DataCompanyContract
import haina.ecommerce.view.datacompany.DataCompanyPresenter

class AddAddressCompanyActivity : AppCompatActivity(), AddressCompanyContract, View.OnClickListener {

    private lateinit var binding: ActivityAddAddressCompanyBinding
    private lateinit var presenter: AddressCompanyPresenter
    private var popupLocation: AlertDialog? = null
    private var broadcaster: LocalBroadcastManager? = null
    var idLocation:Int? = 0
    var idCity:Int? = 0
    var nameLocation:String? = ""
    var idCompany:String? = ""
    var address:String? = ""
    val refresh:String?= "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idCompany = intent.getStringExtra("idCompany")
        address = intent.getStringExtra("address")
        nameLocation = intent.getStringExtra("nameLocation")
        idCity = intent.getIntExtra("idLocation", 0)
        setEditText()

        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = AddressCompanyPresenter(this, this)
        presenter.getListLocation()

        binding.toolbarAddressCompany.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarAddressCompany.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarAddressCompany.title = "Address Company"
        binding.etLocationCompany.setOnClickListener(this)
        binding.btnAddAddressCompany.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("jobLocation"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action){
                "jobLocation" -> {
                    val idLocationFill = intent.getStringExtra("idLocation")!!
                    val nameLocationFill = intent.getStringExtra("nameLocation")!!
                    idLocation = idLocationFill.toInt()
                    binding.etLocationCompany.setText(nameLocationFill)
                    Log.d("location", idLocation.toString()+nameLocation)
                    popupLocation?.dismiss()
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_add_address_company -> {
                binding.btnAddAddressCompany.visibility = View.INVISIBLE
                binding.relativeLoading.visibility = View.VISIBLE
                presenter.addAddressCompany(idCompany!!, binding.etAddressCompany.text.toString(), idLocation.toString())
            }

            R.id.et_location_company ->{
                popupLocation?.show()
            }
        }
    }

    override fun getListLocation(list: List<DataItemHaina?>?) {
        val popup = AlertDialog.Builder(this)
        val view: View = layoutInflater.inflate(R.layout.layout_pop_up_list, null)
        popup.setCancelable(true)
        popup.setView(view)
        val action = view.findViewById<TextView>(haina.ecommerce.R.id.tv_action)
        val title = view.findViewById<TextView>(haina.ecommerce.R.id.tv_title)
        val rvJob = view.findViewById<RecyclerView>(haina.ecommerce.R.id.rv_popup)
        val jobLocationAdapter = AdapterJobLocation(this, list)
        popupLocation = popup.create()
        popupLocation?.dismiss()
        action.setOnClickListener{popupLocation?.dismiss()}
        title.text = "Job Location"
        rvJob.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = jobLocationAdapter
            jobLocationAdapter.notifyDataSetChanged()
        }
    }

    override fun messageAddressCompany(msg: String) {
        if (msg == "1"){
            binding.btnAddAddressCompany.visibility = View.VISIBLE
            binding.relativeLoading.visibility = View.INVISIBLE
            Toast.makeText(this, "Success add address company", Toast.LENGTH_SHORT).show()
            move()
        } else {
            binding.btnAddAddressCompany.visibility = View.VISIBLE
            binding.relativeLoading.visibility = View.INVISIBLE
            Toast.makeText(this, "Failed add address company", Toast.LENGTH_SHORT).show()
        }
    }

    override fun messageGetListLocation(msg: String) {
        Log.d("getListLocation", msg)
    }

    private fun move(){
        val move = Intent("refresh")
            .putExtra("fromAddAddress", refresh)
        broadcaster?.sendBroadcast(move)
        onBackPressed()
    }

    private fun setEditText(){
        binding.etAddressCompany.setText(address)
        binding.etLocationCompany.setText(nameLocation)
        if (binding.etAddressCompany.text!!.isNotEmpty() && binding.etLocationCompany.text!!.isNotEmpty()){
            binding.btnAddAddressCompany.text = "update address company"
        }
    }
}