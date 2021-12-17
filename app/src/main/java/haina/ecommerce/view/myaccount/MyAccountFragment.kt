package haina.ecommerce.view.myaccount

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentMyAccountBinding
import haina.ecommerce.model.DataUser
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.datacompany.DataCompanyActivity
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.datacompany.address.AddAddressCompanyActivity
import haina.ecommerce.view.login.LoginActivity
import haina.ecommerce.view.myaccount.changepassword.ChangePasswordActivity
import haina.ecommerce.view.myaccount.detailaccount.DetailAccountActivity
import haina.ecommerce.view.notification.NotificationActivity
import haina.ecommerce.view.register.company.RegisterCompanyActivity
import haina.ecommerce.view.webview.WebViewActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File

class MyAccountFragment : Fragment(), View.OnClickListener, MyAccountContract.View {

    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding
    lateinit var sharedPref: SharedPreferenceHelper
    private var broadcaster: LocalBroadcastManager? = null
    private var popupLogout: AlertDialog? = null
    private var progressDialog:Dialog? = null
    private lateinit var presenter: MyAccountPresenter
    private lateinit var uri: Uri
    private lateinit var language:String

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireContext())
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        presenter = MyAccountPresenter(this, requireContext())
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_account, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_password -> {
                if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
                    val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
                    startActivity(intent)
                } else {
                    val snackbar = Snackbar.make(binding?.ivNotificationAccount!!, "Please login for change password", Snackbar.LENGTH_SHORT)
                            .setAction("Close", null)
                    snackbar.show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding?.includeLogin?.linearNotLogin?.visibility = View.GONE
            binding?.linearMenu?.visibility = View.VISIBLE
        } else {
            binding?.includeLogin?.linearNotLogin?.visibility = View.VISIBLE
            binding?.linearMenu?.visibility = View.INVISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getDataUserProfile()

        binding?.includeLogin?.btnLoginNotLogin?.setOnClickListener(this)
        binding?.ivNotificationAccount?.setOnClickListener(this)
        binding?.linearLogout?.setOnClickListener(this)
        binding?.linearHelp?.setOnClickListener(this)
        binding?.ivProfile?.setOnClickListener(this)
        binding?.tvActionEditProfile?.setOnClickListener(this)
        binding?.linearRegisterCompany?.setOnClickListener(this)
        binding?.tvActionEditProfile?.setOnClickListener(this)
        binding?.btnSetting?.setOnClickListener(this)
        refresh()
        setLanguage(sharedPref.getValueString(Constants.LANGUAGE_APP).toString())
        switchLanguage()
        showPopupLogout()
        dialogLoading()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login_not_login -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }

            R.id.linear_help -> {
                val intent = Intent(activity, WebViewActivity::class.java)
                intent.putExtra("url","https://support.hainaservice.com/en/")

                startActivity(intent)
            }

            R.id.iv_notification_account -> {
                if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
                    val intent = Intent(activity, NotificationActivity::class.java)
                    startActivity(intent)
                } else {
                    val snackbar = Snackbar.make(binding?.ivNotificationAccount!!, "Please login for access notification", Snackbar.LENGTH_SHORT)
                            .setAction("Close", null)
                    snackbar.show()
                }
            }

            R.id.linear_logout -> {
                popupLogout?.show()
            }

            R.id.iv_profile -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (activity?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, MyAccountFragment.PERMISSION_CODE)
                    } else {
                        //permission already granted
                        pickImageFromGallery()
                    }
                } else {
                    //system OS is < Marshmallow
                    pickImageFromGallery()
                }
            }

            R.id.linear_register_company -> {
                presenter.checkDataCompany()
            }

            R.id.tv_action_edit_profile -> {
                val intent = Intent(activity, DetailAccountActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_setting -> {
                val popup = PopupMenu(requireContext(), binding?.btnSetting!!)
                popup.inflate(R.menu.menu_account)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_change_password ->{
                            if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
                                val intent = Intent(requireContext(), ChangePasswordActivity::class.java)
                                startActivity(intent)
                            } else {
                                val snackbar = Snackbar.make(binding?.ivNotificationAccount!!, "Please login for change password", Snackbar.LENGTH_SHORT)
                                        .setAction("Close", null)
                                snackbar.show()
                            }
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }

    private fun refresh() {
        binding?.swipeRefresh?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.getDataUserProfile()
        })
    }

    override fun onResume() {
        super.onResume()
        presenter.getDataUserProfile()
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(activity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding?.ivProfile?.setImageURI(data?.data)
            uri = data?.data!!
            val filepath = getRealPathFromURIPath(uri, this)
            val file = File(filepath)
            val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("photo", file.name, mFile)
            presenter.changeImageProfile(body)
        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: MyAccountFragment): String? {
        val cursor: Cursor? = activity.requireContext().contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun switchLanguage(){
        binding?.switchLanguage?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isChecked){
                binding?.tvNameLanguage?.text = getString(R.string.chinese)
                language = "zh"
            } else {
                binding?.tvNameLanguage?.text = getString(R.string.english)
                language = "en"
            }
            val intentLanguage = Intent("setLanguage")
                    .putExtra("language",language)
            broadcaster?.sendBroadcast(intentLanguage)
        }
    }

    private fun setLanguage(codeLanguage:String){
        if (codeLanguage == "en"){
            binding?.switchLanguage?.isChecked = false
            binding?.tvNameLanguage?.text = getString(R.string.english)
        } else if (codeLanguage == "zh"){
            binding?.switchLanguage?.isChecked = true
            binding?.tvNameLanguage?.text = getString(R.string.chinese)
        }
    }

    @SuppressLint("InflateParams")
    private fun showPopupLogout() {
        val popup = AlertDialog.Builder(activity)
        val view: View = layoutInflater.inflate(R.layout.popup_logout, null)
        popup.setCancelable(false)
        popup.setView(view)
        val actionCancel = view.findViewById<TextView>(R.id.tv_action_cancel)
        val actionYes = view.findViewById<TextView>(R.id.tv_action_yes)
        val title = view.findViewById<TextView>(R.id.tv_title)
        popupLogout = popup.create()
        title.text = context?.getString(R.string.logout)
        actionCancel.setOnClickListener { popupLogout?.dismiss() }
        actionYes.setOnClickListener {
            popupLogout?.dismiss()
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            sharedPref.removeValue(Constants.PREF_USERNAME)
            presenter.resetTokenUser()
            val intent = Intent(requireContext(), MainActivity::class.java)
            FirebaseAuth.getInstance().signOut()
            intent.putExtra("loginStatus", "1")
            startActivity(intent)
            activity?.finish()
        }
    }

    override fun messageGetDataUser(msg: String) {
        Timber.d(msg)
        binding?.swipeRefresh?.isRefreshing = false
    }

    override fun getDataUser(data: DataUser?) {
        Timber.d(data.toString())

        binding?.ivNotificationAccount?.badgeValue=data?.notificationCount!!
        sharedPref.save(Constants.PREF_PHONE_NUMBER, data?.phone.toString())
        sharedPref.save(Constants.PREF_EMAIL, data?.email.toString())
        binding?.tvNameUser?.text = data?.fullname.toString()
        activity?.let { Glide.with(it).load(data?.photo).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding?.ivProfile!!) }
    }

    override fun messageLogout(msg: String) {
        Timber.d(msg)
    }

    override fun resetTokenUser(data: String?) {
        sharedPref.save(Constants.PREF_TOKEN_LOGIN, data.toString())
    }

    override fun messageChangeImageProfile(msg: String) {
        Timber.d(msg)
        presenter.getDataUserProfile()
    }

    override fun messageCheckDataCompany(msg: String) {
        Timber.d(msg)
        if (msg == "Company Registered" && sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            val intent = Intent(requireContext(), DataCompanyActivity::class.java)
            startActivity(intent)
        } else if (!msg.contains("Company Registered") && sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            val intent = Intent(requireContext(), RegisterCompanyActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Please Login First", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), android.R.color.white))
        val window:Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}