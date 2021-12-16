package haina.ecommerce.view.topup

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.TabAdapterInternet
import haina.ecommerce.databinding.ActivityTopupBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.DataUser
import haina.ecommerce.model.pulsaanddata.ProductPhone
import haina.ecommerce.model.pulsaanddata.PulsaItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.topup.pulsa.PulsaFragment

class TopupActivity : AppCompatActivity(), View.OnClickListener, TopupContract.View {

    private lateinit var binding: ActivityTopupBinding
    private lateinit var sharedPref: SharedPreferenceHelper
    private lateinit var presenter: TopupPresenter
    private val PICK_CONTACT = 0
    private var broadcaster: LocalBroadcastManager? = null
    private var phoneNumber: String? = null
    private val helper: Helper = Helper
    private var loadStatus:Int? = 0
    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPreferenceHelper(this)
        presenter = TopupPresenter(this, this)
        broadcaster = LocalBroadcastManager.getInstance(this)

        presenter.getDataUserProfile()
        binding.toolbarTopup.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarTopup.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarTopup.title = getString(R.string.topup_title)
        stateIconLoad(loadStatus!!)
        binding.imagePhoneBook.setOnClickListener(this)
        binding.viewPagerInternet.adapter = TabAdapterInternet(supportFragmentManager, 0)
        binding.tabLayoutInternet.setupWithViewPager(binding.viewPagerInternet)

        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                false
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                false
            }
            override fun afterTextChanged(s: Editable?) {
                if (s?.isNotEmpty()!!) {
                    if (s.length >= 10){
                        phoneNumber = s.toString()
                        presenter.getProviderName(phoneNumber!!)
                        sharedPref.save(Constants.PREF_PHONE_NUMBER_PULSA, phoneNumber!!)
                    } else {
                        binding.etPhoneNumber.error = getString(R.string.invalid_phone)
                    }
                }
            }
        })
        getPhoneNumber()
        refresh()
        dialogLoading()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.image_phone_book -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.READ_CONTACTS)
                        requestPermissions(permissions, PICK_CONTACT)
                    } else {
                        //permission already granted
                        getContact()
                    }
                } else {
                    //system OS is < Marshmallow
                    getContact()
                }
            }
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext,R.color.white))
        val window : Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    fun getNumber():String{
        binding.etPhoneNumber.text.toString()
        return binding.etPhoneNumber.text.toString()
    }

    companion object {
        //image pick code
        private val PHONE_NUMBER = 100

        //Permission code
        private val PERMISSION_CODE = 200
    }

    private fun stateIconLoad(loadStatus:Int){
        if (loadStatus == 1){
            binding.linearInputNumber.visibility = View.VISIBLE
            binding.iconLoad.visibility = View.GONE
        } else {
            binding.linearInputNumber.visibility = View.GONE
            binding.iconLoad.visibility = View.VISIBLE
        }
    }

    private fun getPhoneNumber() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as
                TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE)
                requestPermissions(permissions, TopupActivity.PERMISSION_CODE)
            } else {
                //permission already granted
                val phoneNumber = tm.line1Number
                if (phoneNumber != null) {
                    binding.etPhoneNumber.setText(helper.formatPhoneNumber(phoneNumber))
                }
            }
        } else {
            //system OS is < Marshmallow
            val phoneNumber = tm.line1Number
            if (phoneNumber != null) {
                binding.etPhoneNumber.setText(helper.formatPhoneNumber(phoneNumber))
            }
        }
    }

    private fun refresh() {
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getDataUserProfile()
        }
    }

    private fun setPhoneUser(phone: String?) {
        binding.etPhoneNumber.setText(helper.formatPhoneNumber(phone!!))
        presenter.getProviderName(phone)
        sharedPref.save(Constants.PREF_PHONE_NUMBER_PULSA, phone)
    }

    private fun getContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, PICK_CONTACT)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PICK_CONTACT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact()
                } else {
                    Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICK_CONTACT -> if (resultCode == RESULT_OK) {
                val contactData: Uri = data?.data!!
                val c: Cursor = managedQuery(contactData, null, null, null, null)
                if (c.moveToFirst()) {
                    val id: String = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                    val hasPhone: String = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    if (hasPhone.equals("1", ignoreCase = true)) {
                        val phones: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null)
                        phones?.moveToFirst()
                        val cNumber = phones?.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))!!
                        val valueRupiah = cNumber.replace("[-+.^:,]".toRegex(), "")
                        setPhoneUser(helper.formatPhoneNumber(cNumber))
                    }
                }
            }
        }
    }

    override fun messageGetDataUser(msg: String) {
        Log.d("getDataUser", msg)
        loadStatus = 1
        stateIconLoad(loadStatus!!)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun messageGetProviderName(msg: String) {
        Log.d("getProviderName", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getDataUser(data: DataUser?) {
        setPhoneUser(data?.phone)
        phoneNumber = data?.phone
    }

    override fun getProviderName(data: ProductPhone?) {
        Glide.with(applicationContext).load(data?.provider?.photoUrl).into(binding.ivLogoProvider)
        val sendPulsa = Intent("productPhone")
                .putExtra("pulsa", data)
        broadcaster?.sendBroadcast(sendPulsa)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}