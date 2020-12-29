package haina.ecommerce.view.myaccount.addrequirement

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityAddRequirementBinding
import haina.ecommerce.view.myaccount.MyAccountFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.*

class AddRequirementActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding:ActivityAddRequirementBinding
    private var titleToolbar:String? = null
    private lateinit var uri: Uri

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

        titleToolbar = intent.getStringExtra("title")
        binding.toolbarAddRequirement.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarAddRequirement.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarAddRequirement.title = titleToolbar
        binding.linearClick.setOnClickListener(this)

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
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_PDF && data != null){
            uri = data.data!!
            binding.tvDocument.text = uri.toString()
            val filepath = getRealPathFromURIPath(uri, this)
            val file = File(filepath)
            val mFile: RequestBody = RequestBody.create(MediaType.parse("application/pdf"), file)
            val body = MultipartBody.Part.createFormData("docs", file.name, mFile)
        }
    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: AddRequirementActivity): String? {
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        return if (cursor == null) {
            contentURI.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            cursor.getString(idx)
        }
    }

    private fun pickPdf() {
        //Intent to pick pdf
        var intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        intent = Intent.createChooser(intent, "Choose a file")
        startActivityForResult(intent, AddRequirementActivity.PICK_PDF)
    }
}