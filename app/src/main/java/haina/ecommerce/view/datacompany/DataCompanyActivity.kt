package haina.ecommerce.view.datacompany

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.localbroadcastmanager.content.LocalBroadcastManager
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
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity
import haina.ecommerce.view.myaccount.MyAccountFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class DataCompanyActivity : AppCompatActivity(), DataCompanyContract, View.OnClickListener {

    private lateinit var binding: ActivityDataCompanyBinding
    private lateinit var presenter: DataCompanyPresenter
    private var popupAddress: AlertDialog? = null
    private var popupLocation: AlertDialog? = null
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var uri: Uri
    var nameUser:RequestBody = RequestBody.create(MultipartBody.FORM, "")
    var idCompany:RequestBody = RequestBody.create(MultipartBody.FORM, "")
    var idLocation:Int = 0
    var nameLocation:String = ""
    var idCompanyString:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataCompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = DataCompanyPresenter(this, this)
        presenter.getDataCompany()
        presenter.loadListLocation()
        refresh()
        binding.toolbarDataCompany.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarDataCompany.setNavigationOnClickListener { onBackPressed() }
        binding.ivActionEdit.setOnClickListener(this)
        binding.tvAddAddress.setOnClickListener(this)
        binding.ivAddImageCompany.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        val refresh = intent.extras?.get("refresh")
        if (refresh == 1){
            presenter.getDataCompany()
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("jobLocation"))
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("delete"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action){
                "jobLocation" -> {
                    val idLocationFill = intent.getStringExtra("idLocation")!!
                    val nameLocationFill = intent.getStringExtra("nameLocation")!!
                    idLocation = idLocationFill.toInt()
                    nameLocation = nameLocationFill
                    Log.d("location", idLocation.toString()+nameLocation)
                    popupLocation?.dismiss()
                }
                "delete" -> {
                    val idImage = intent.getIntExtra("idImage", 0)
                    presenter.deleteImageCompany(idImage)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }

    private fun refresh(){
        binding.swipeRefreshLayout.setOnRefreshListener {
            presenter.getDataCompany()
        }
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_add_address ->{
                val intent = Intent(applicationContext, AddAddressCompanyActivity::class.java)
                intent.putExtra("idCompany", idCompanyString)
                startActivity(intent)
            }

            R.id.iv_add_image_company ->{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, DataCompanyActivity.PERMISSION_CODE)
                    } else {
                        //permission already granted
                        pickImageFromGallery()
                    }
                } else {
                    //system OS is < Marshmallow
                    pickImageFromGallery()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, DataCompanyActivity.IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            DataCompanyActivity.PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == DataCompanyActivity.IMAGE_PICK_CODE){
            uri = data?.data!!
            val filepath = getRealPathFromURIPath(uri, this)
            val file = File(filepath)
            val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("photo", file.name, mFile)
            presenter.addImageCompany(body, nameUser, idCompany)
        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: DataCompanyActivity): String? {
        val cursor: Cursor? = activity.contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
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
            val intent = Intent(this, AddAddressCompanyActivity::class.java)
            startActivity(intent)
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
            }}

        location.setText(nameLocation)
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
        nameUser = RequestBody.create(MultipartBody.FORM, item.name!!)
        idCompany = RequestBody.create(MultipartBody.FORM, item.id.toString())
        idCompanyString = item.id.toString()
        Log.d("id", item.id.toString())
    }

    override fun loadJobLocation(itemHaina: List<DataItemHaina?>?) {
        popupAddAddress(itemHaina)
    }

    override fun messageGetDataCompany(msg: String) {
        Log.d("messageCompany", msg)
        binding.swipeRefreshLayout.isRefreshing = false
    }

    override fun messageAddPhotoCompany(msg: String) {
        Log.d("messageAddCompany", msg)
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        if (msg == "Success add photo company"){
            presenter.getDataCompany()
        }
    }

    override fun messageDeleteImageCompany(msg: String) {
        if (msg == "1"){
            presenter.getDataCompany()
            Toast.makeText(applicationContext, "Success delete image", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Failed delete image", Toast.LENGTH_SHORT).show()
        }
    }
}