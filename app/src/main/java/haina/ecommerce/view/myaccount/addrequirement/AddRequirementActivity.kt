package haina.ecommerce.view.myaccount.addrequirement

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityAddRequirementBinding
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.applyjob.ApplyJobActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*


class AddRequirementActivity : AppCompatActivity(), View.OnClickListener, AddRequirementContract {

    private lateinit var binding:ActivityAddRequirementBinding
    private var titleToolbar:String? = null
    var path: String? = null
    var fileName: String? = null
    var idCategoryDocument: String? = null
    lateinit var sharedPref : SharedPreferenceHelper
    private val BUFFER_SIZE = 1024 * 2
    private var stateActivity = false
    private var displayNamePdf = ""
    private var uriPdf:Uri? = null
    private lateinit var presenter: AddRequirementPresenter
    val IMAGE_DIRECTORY = "/haina_document"
    companion object {
        //pick Pdf
        private val PICK_PDF = 21

        //Permission code
        private val PERMISSION_CODE_PDF = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRequirementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPreferenceHelper(this)
        presenter = AddRequirementPresenter(this, this)
        titleToolbar = intent.getStringExtra("title")

        stateActivity = intent.getBooleanExtra("fromApplyJob", false)
        binding.toolbarAddRequirement.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarAddRequirement.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarAddRequirement.title = titleToolbar
        binding.linearClick.setOnClickListener(this)
        binding.btnUploadDocument.setOnClickListener(this)

        setTitleFileName()
        setIdCategoryDocument()
        requestMultiplePermissions()

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.linear_click -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, AddRequirementActivity.PERMISSION_CODE_PDF)
                    } else {
                        //permission already granted
                        pickPdf()
                    }
                } else {
                    //system OS is < Marshmallow
                    pickPdf()
                }
            }

            R.id.btn_upload_document -> {
                if (binding.tvDocument.text.isNotEmpty()){
                    binding.relativeLoading.visibility = View.VISIBLE
                    binding.btnUploadDocument.visibility = View.INVISIBLE
                            checkUpload(path)
                } else{
                    Toast.makeText(applicationContext, "Please insert your document first!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            uriPdf = data!!.data
            val uriString = uriPdf.toString()
            val myFile = File(uriString)
            var cursor:Cursor? = null
            path = myFile.absolutePath
//            path = getFilePathFromURI(this, uri)
            if (uriString.startsWith("content://")){
                try {
                     cursor = contentResolver.query(uriPdf!!, null,null,null, null)
                    if (cursor != null && cursor.moveToFirst()){
                        displayNamePdf = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)).toString()
                    }
                } finally {
                    cursor?.close()
                }
            } else if (uriString.startsWith("file://")){
                displayNamePdf = myFile.name
            }
            binding.tvDocument.text = displayNamePdf
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getFilePathFromURI(context: Context?, contentUri: Uri?): String? {
        //copy file and send new file path
        val fileName = getFileName(contentUri)
        val wallpaperDirectory: File = File(Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }
        if (!TextUtils.isEmpty(fileName)) {
            val copyFile = File(wallpaperDirectory.toString() + File.separator + fileName)
            // create folder if not exists
            copy(applicationContext, contentUri, copyFile)
            return copyFile.absolutePath
        }
        return null
    }

    private fun getFileName(uri: Uri?): String? {
        if (uri == null) return null
        var fileName: String? = null
        val path = uri.path
        val cut = path!!.lastIndexOf('/')
        if (cut != -1) {
            fileName = path.substring(cut + 1)
        }
        return fileName
    }

    private fun setTitleFileName(){
        when {
            titleToolbar!!.contains("Add Resume") -> {
                fileName = "Resume ${sharedPref.getValueString(Constants.PREF_FULLNAME)}.pdf"
            }
            titleToolbar!!.contains("Add Portfolio") -> {
                fileName = "Portfolio ${sharedPref.getValueString(Constants.PREF_FULLNAME)}.pdf"
            }
            titleToolbar!!.contains("Add Certificate") -> {
                fileName = "Certificate ${sharedPref.getValueString(Constants.PREF_FULLNAME)}.pdf"
            }
        }
    }

    private fun setIdCategoryDocument(){
        when {
            titleToolbar!!.contains("Add Resume") -> {
                idCategoryDocument = "1"
            }
            titleToolbar!!.contains("Add Portfolio") -> {
                idCategoryDocument = "2"
            }
            titleToolbar!!.contains("Add Certificate") -> {
                idCategoryDocument = "3"
            }
        }
    }

    private fun copy(context: Context, srcUri: Uri?, dstFile: File?) {
        try {
            val inputStream = context.contentResolver.openInputStream(srcUri!!)
                    ?: return
            val outputStream: OutputStream = FileOutputStream(dstFile)
            copystream(inputStream, outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class, IOException::class)
    private fun copystream(input: InputStream?, output: OutputStream?): Int {
        val buffer = ByteArray(BUFFER_SIZE)
        val `in` = BufferedInputStream(input, BUFFER_SIZE)
        val out = BufferedOutputStream(output, BUFFER_SIZE)
        var count = 0
        var n = 0
        try {
            while (`in`.read(buffer, 0, BUFFER_SIZE).also { n = it } != -1) {
                out.write(buffer, 0, n)
                count += n
            }
            out.flush()
        } finally {
            try {
                out.close()
            } catch (e: IOException) {
                Log.e(e.toString(), java.lang.String.valueOf(e))
            }
            try {
                `in`.close()
            } catch (e: IOException) {
                Log.e(e.toString(), java.lang.String.valueOf(e))
            }
        }
        return count
    }

    private fun checkUpload(path: String?){
        // Parsing any Media type file
        val file = File(path!!)
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("*/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("docs", file.name, requestBody)
        val filename: RequestBody = RequestBody.create(MediaType.parse("text/plain"), fileName!!)
        val idCategory = RequestBody.create(MediaType.parse("text/plain"), idCategoryDocument!!)
            presenter.uploadDocument(fileToUpload, filename, idCategory)
    }

    private fun pickPdf() {
        //Intent to pick pdf
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "application/pdf"
        intent = Intent.createChooser(intent, "Choose a file")
        startActivityForResult(intent, 1)
    }

    override fun messageUploadDocument(msg: String) {
        Log.d("uploadDocument", msg)
        if (msg.isNotEmpty()){
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            binding.relativeLoading.visibility = View.INVISIBLE
            binding.btnUploadDocument.visibility = View.VISIBLE
            onBackPressed()
        }
    }

    private fun requestMultiplePermissions() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(applicationContext, "All permissions are granted, ready for upload!", Toast.LENGTH_SHORT).show()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            Toast.makeText(applicationContext, getString(R.string.grant_permission), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                }).withErrorListener { Toast.makeText(applicationContext, "Some Error! ", Toast.LENGTH_SHORT).show() }
                .onSameThread()
                .check()
    }
}