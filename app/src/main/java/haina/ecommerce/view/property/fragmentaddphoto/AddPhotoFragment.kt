package haina.ecommerce.view.property.fragmentaddphoto

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.property.AdapterListPhotoProperty
import haina.ecommerce.databinding.FragmentAddPhotoBinding
import haina.ecommerce.model.property.PhotoProprety
import haina.ecommerce.model.property.RequestDataProperty
import haina.ecommerce.room.roomphotoproperty.DataProperty
import haina.ecommerce.room.roomphotoproperty.PropertyDao
import haina.ecommerce.room.roomphotoproperty.RoomDataProperty
import haina.ecommerce.view.MainActivity
import haina.ecommerce.view.property.FinishPropertyActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class AddPhotoFragment : Fragment(),View.OnClickListener, AdapterListPhotoProperty.ItemAdapterCallback, AddPhotoContract.View {

    private lateinit var _binding:FragmentAddPhotoBinding
    private val binding get() = _binding
    private var popupAddPhoto : Dialog? = null
    private lateinit var request:RequestDataProperty
    private var uri: Uri = Uri.EMPTY
    private var typeUpload:String = ""
    private var multipartProperty: MultipartBody.Part? = null
    private lateinit var dao:PropertyDao
    private lateinit var database:RoomDataProperty
    private lateinit var listPhotoProperty: ArrayList<DataProperty>
    private var listPhotoArray = ArrayList<MultipartBody.Part>()
    private var photo:MultipartBody.Part? = null
    private var progressDialog:Dialog? = null
    private lateinit var presenter:AddPhotoPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddPhotoBinding.inflate(inflater, container, false)
        database = RoomDataProperty.getDatabase(requireActivity())
        dao = database.getDataPropertyDao()
        presenter = AddPhotoPresenter(this, requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddPhoto.setOnClickListener(this)
        binding.btnPostingProperty.setOnClickListener(this)
        binding.toolbarAddPhotoProperty.setNavigationOnClickListener {
            findNavController().navigateUp()
            deleteAll()
        }
        deleteAll()
        request = arguments?.getParcelable("dataRequest")!!
        dialogMethodUpload()
        dialogLoading()
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //image capture code
        private val IMAGE_CAPTURE_CODE = 1002

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

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_add_photo -> {
                checkPermission()
            }
            R.id.btn_posting_property -> {
                checkPhoto()
            }
        }
    }

    private fun checkPhoto(){
        val typeProperty:RequestBody = RequestBody.create(MultipartBody.FORM, request.typeProperty)
        val condition:RequestBody = RequestBody.create(MultipartBody.FORM, request.condition)
        val title:RequestBody = RequestBody.create(MultipartBody.FORM, request.title)
        val year:RequestBody = RequestBody.create(MultipartBody.FORM, request.year.toString())
        val city:RequestBody = RequestBody.create(MultipartBody.FORM, request.city.toString())
        val floor:RequestBody = RequestBody.create(MultipartBody.FORM, request.floor.toString())
        //val bedRoom:RequestBody = RequestBody.create(MultipartBody.FORM, request.bedRoom.toString())
        //val bathRoom:RequestBody = RequestBody.create(MultipartBody.FORM, request.bathRoom.toString())
        val address:RequestBody = RequestBody.create(MultipartBody.FORM, request.address)
        val latitude:RequestBody = RequestBody.create(MultipartBody.FORM, "0")
        val longitude:RequestBody = RequestBody.create(MultipartBody.FORM, "0")
        val priceSell:RequestBody = RequestBody.create(MultipartBody.FORM, request.priceSell)
        val priceRent:RequestBody = RequestBody.create(MultipartBody.FORM, request.priceRent)
        val description:RequestBody = RequestBody.create(MultipartBody.FORM, request.description)
        val facility:RequestBody = RequestBody.create(MultipartBody.FORM, request.facility)
        if (listPhotoArray.size == 0){
            Toast.makeText(requireActivity(), getString(R.string.upload_min_one), Toast.LENGTH_SHORT).show()
        }else {
            presenter.createPostProperty(typeProperty, condition, title, year, city, floor,
                request.bedRoom, request.bathRoom, request.buildingArea, request.surfaceArea, request.typeCertificate, address, latitude, longitude,
                priceSell, priceRent, facility, description, listPhotoArray)
        }
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                || requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
                popupAddPhoto?.show()
            } else {
                //permission already granted
                popupAddPhoto?.show()
            }
        } else {
            //system OS is < Marshmallow
            popupAddPhoto?.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val bytes = ByteArrayOutputStream()
        if (resultCode == Activity.RESULT_OK && typeUpload == "camera") {
            if (Build.VERSION.SDK_INT < 28) {
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                val path = MediaStore.Images.Media.insertImage(requireActivity().contentResolver, bitmap, "Haina-Property", null)
                val filePath = getRealPathFromURIPath(Uri.parse(path), this)
                val file = File(filePath)
                val mFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
                multipartProperty = MultipartBody.Part.createFormData("images[]", file.name, mFile)
                photo = multipartProperty!!
                savePhotoProperty(DataProperty(0, uri.toString()))
                listPhotoArray.add(multipartProperty!!)
            } else {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                val bitmap = ImageDecoder.decodeBitmap(source)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes)
                val path = MediaStore.Images.Media.insertImage(requireActivity().contentResolver, bitmap, "Haina-Property", null)
                val filePath = getRealPathFromURIPath(Uri.parse(path), this)
                val file = File(filePath)
                val mFile: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
                multipartProperty = MultipartBody.Part.createFormData("images[]", file.name, mFile)
                photo = multipartProperty!!
                savePhotoProperty(DataProperty(0, uri.toString()))
                listPhotoArray.add(multipartProperty!!)
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && typeUpload == "gallery"){
            uri = data?.data!!
            val filepath = getRealPathFromURIPath(uri, this)
                val file = File(filepath)
                val mFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                val body = MultipartBody.Part.createFormData("images[]", file.name, mFile)
            savePhotoProperty(DataProperty(0, uri.toString()))
            photo = body
            listPhotoArray.add(body)
        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: AddPhotoFragment): String? {
        val cursor: Cursor? = activity.requireContext().contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        values.put(MediaStore.Images.Media.TITLE, "Property")
        uri = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    private fun getListDataProperty(database: RoomDataProperty, dao: PropertyDao) {
        listPhotoProperty = arrayListOf()
        listPhotoProperty.addAll(dao.getAll())
        if (listPhotoProperty.size < 1){
            binding.btnPostingProperty.visibility = View.GONE
            binding.rvPhotoProperty.visibility = View.GONE
        } else {
            binding.btnPostingProperty.visibility = View.VISIBLE
            binding.rvPhotoProperty.visibility = View.VISIBLE
            setupListDataPassenger(listPhotoProperty)
        }
    }

    private fun setupListDataPassenger(listPhoto:List<DataProperty>){
        binding.rvPhotoProperty.apply {
            adapter = AdapterListPhotoProperty(requireActivity(), listPhoto, this@AddPhotoFragment)
            layoutManager = GridLayoutManager(requireActivity(), 4)
        }
    }

    override fun onResume() {
        super.onResume()
        getListDataProperty(database, dao)
    }

    private fun savePhotoProperty(dataProperty:DataProperty){
        if (dao.getById(dataProperty.id).isEmpty()){
            dao.insert(dataProperty)
        }else{
            dao.update(dataProperty)
        }
    }

    private fun deleteProperty(dataProperty:DataProperty){
        dao.delete(dataProperty)
    }

    private fun deleteAll(){
        dao.deleteAll()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogMethodUpload(){
        popupAddPhoto = Dialog(requireActivity())
        popupAddPhoto?.setContentView(R.layout.popup_method_upload_photo)
        popupAddPhoto?.setCancelable(true)
        popupAddPhoto?.window?.setBackgroundDrawable(requireActivity().getDrawable(android.R.color.transparent))
        val window: Window = popupAddPhoto?.window!!
        window.setGravity(Gravity.CENTER)
        val camera = popupAddPhoto?.findViewById<TextView>(R.id.tv_action_camera)
        val gallery = popupAddPhoto?.findViewById<TextView>(R.id.tv_action_gallery)

        camera?.setOnClickListener {
            typeUpload = "camera"
            openCamera()
            popupAddPhoto?.dismiss()
        }

        gallery?.setOnClickListener {
            typeUpload = "gallery"
            pickImageFromGallery()
            popupAddPhoto?.dismiss()
        }
    }

    override fun onClickDeleteImage(view: View, data: DataProperty) {
        when(view.id){
            R.id.iv_action -> {
                deleteProperty(data)
                getListDataProperty(database, dao)
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(requireActivity().getDrawable(android.R.color.white))
        val window:Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageCreatePost(msg: String) {
        Timber.d(msg)
        if (msg.contains("Success!")){
            startActivity(Intent(requireActivity(), FinishPropertyActivity::class.java).putExtra("finish", "property"))
            requireActivity().finishAffinity()
            deleteAll()
        } else{
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}