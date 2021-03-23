package haina.ecommerce.view.topup

import android.Manifest
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
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
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

class TopupActivity : AppCompatActivity(), View.OnClickListener, TopupContract {

    private lateinit var binding: ActivityTopupBinding
    private lateinit var sharedPref: SharedPreferenceHelper
    private lateinit var presenter: TopupPresenter
    private val PICK_CONTACT = 0
    private var broadcaster: LocalBroadcastManager? = null
    private var phoneNumber: String? = null
    private val helper: Helper = Helper()

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
        binding.toolbarTopup.title = "Internet"
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
                        binding.etPhoneNumber.error = "input valid phone number"
                    }
                }
            }
        })
        getPhoneNumber()
        refresh()
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

    companion object {
        //image pick code
        private val PHONE_NUMBER = 100

        //Permission code
        private val PERMISSION_CODE = 200
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
                    binding.etPhoneNumber.setText(phoneNumber)
                }
            }
        } else {
            //system OS is < Marshmallow
            val phoneNumber = tm.line1Number
            if (phoneNumber != null) {
                binding.etPhoneNumber.setText(phoneNumber)
            }
        }
    }

    private fun refresh() {
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getDataUserProfile()
        }
    }

    private fun setPhoneUser(phone: String?) {
        binding.etPhoneNumber.setText(phone)
        presenter.getProviderName(phone!!)
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
                var cNumber :String? = ""
                if (c.moveToFirst()) {
                    val id: String = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                    val hasPhone: String = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    if (hasPhone.equals("1", ignoreCase = true)) {
                        val phones: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null)
                        phones?.moveToFirst()
                        cNumber = phones?.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))!!
                        setPhoneUser(helper.formatPhoneNumber(cNumber))
                    }
                }
                val intentResetPrice = Intent("resetPrice")
                        .putExtra("reset","true")
                broadcaster?.sendBroadcast(intentResetPrice)
            }
        }
    }

    override fun messageGetDataUser(msg: String) {
        Log.d("getDataUser", msg)
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
        binding.tvNameProvider.text = data?.provider?.name
        val sendPulsa = Intent("productPhone")
                .putExtra("pulsa", data)
        broadcaster?.sendBroadcast(sendPulsa)
    }

    private fun intentPhoneNumber(phoneNumber:String?){
        val sendPhoneNumber = Intent("phoneNumber")
                .putExtra("number", phoneNumber)
        broadcaster?.sendBroadcast(sendPhoneNumber)
    }
}