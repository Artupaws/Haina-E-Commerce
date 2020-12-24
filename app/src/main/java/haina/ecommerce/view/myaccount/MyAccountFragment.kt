package haina.ecommerce.view.myaccount

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentMyAccountBinding
import haina.ecommerce.model.DataUser
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.datacompany.DataCompanyActivity
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.login.LoginActivity
import haina.ecommerce.view.register.company.RegisterCompanyActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MyAccountFragment : Fragment(), View.OnClickListener, MyAccountContract {

    private var _binding: FragmentMyAccountBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPref: SharedPreferenceHelper
    private var broadcaster: LocalBroadcastManager? = null
    private var popupLogout: AlertDialog? = null
    private lateinit var presenter: MyAccountPresenter
    private lateinit var uri: Uri

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireContext())
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        presenter = MyAccountPresenter(this, requireContext())
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding.includeLogin.linearNotLogin.visibility = View.GONE
            binding.linearMenu.visibility = View.VISIBLE
        } else {
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            binding.linearMenu.visibility = View.INVISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getDataUserProfile()
        refresh()
        binding.includeLogin.btnLoginNotLogin.setOnClickListener(this)
        binding.ivNotification.setOnClickListener(this)
        binding.linearLogout.setOnClickListener(this)
        binding.ivProfile.setOnClickListener(this)
        binding.tvActionEditProfile.setOnClickListener(this)
        binding.linearRegisterCompany.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login_not_login -> {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }

            R.id.iv_notification -> {
                if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
                    val snackbar = Snackbar.make(binding.ivNotification, "You are logged", Snackbar.LENGTH_SHORT)
                            .setAction("Close", null)
                    snackbar.show()
                } else {
                    val snackbar = Snackbar.make(binding.ivNotification, "Please login for access notification", Snackbar.LENGTH_SHORT)
                            .setAction("Close", null)
                    snackbar.show()
                }
            }

            R.id.linear_logout -> {
                showPopupLogout()
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
        }
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            presenter.getDataUserProfile()
        })
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MyAccountFragment.PERMISSION_CODE -> {
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
        if (resultCode == Activity.RESULT_OK && requestCode == MyAccountFragment.IMAGE_PICK_CODE){
            binding.ivProfile.setImageURI(data?.data)
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
        startActivityForResult(intent, MyAccountFragment.IMAGE_PICK_CODE)
    }

    @SuppressLint("InflateParams")
    private fun showPopupLogout(){
        val popup = AlertDialog.Builder(activity)
        val view: View = layoutInflater.inflate(R.layout.popup_logout, null)
        popup.setCancelable(false)
        popup.setView(view)
        val actionCancel = view.findViewById<TextView>(R.id.tv_action_cancel)
        val actionYes = view.findViewById<TextView>(R.id.tv_action_yes)
        val title = view.findViewById<TextView>(R.id.tv_title)
        popupLogout = popup.create()
        title.text = "Logout"
        actionCancel.setOnClickListener { popupLogout?.dismiss() }
        actionYes.setOnClickListener {
            sharedPref.removeValue(Constants.PREF_IS_LOGIN)
            presenter.resetTokenUser()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("loginStatus", "1")
            startActivity(intent)
        activity?.finish()}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        popupLogout?.dismiss()
    }

    override fun successGetDataUser(msg: String) {
        Log.d("getDataSuccess", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun errorGetDataUSer(msg: String) {
        Log.d("getDataError", msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun getDataUser(data: DataUser?) {
        sharedPref.save(Constants.PREF_USERNAME, data?.username.toString())
        sharedPref.save(Constants.PREF_EMAIL, data?.email.toString())
        sharedPref.save(Constants.PREF_FULLNAME, data?.fullname.toString())
        binding.tvNameUser.text = data?.fullname.toString()
        activity?.let { Glide.with(it).load(data?.photo).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.ivProfile) }
    }

    override fun successLogout(msg: String) {
        Log.d("successLogout", msg)
    }

    override fun errorLogout(msg: String) {
        Log.d("errorLogout", msg)
    }

    override fun resetTokenUser(data: String?) {
        sharedPref.save(Constants.PREF_TOKEN_LOGIN, data.toString())
    }

    override fun successChangeImageProfile(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
        presenter.getDataUserProfile()
    }

    override fun errorChangeImageProfile(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun checkDataCompany(msg: String) {
        if (msg == "Company Registered" && sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)){
            val intent = Intent(activity, DataCompanyActivity::class.java)
            startActivity(intent)
        } else if (msg == "Company Unregistered" && sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)){
            val intent = Intent(activity, RegisterCompanyActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Please Login First", Toast.LENGTH_SHORT).show()
        }
    }

}