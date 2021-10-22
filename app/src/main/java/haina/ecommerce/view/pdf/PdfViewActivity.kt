package haina.ecommerce.view.pdf

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import haina.ecommerce.databinding.ActivityPdfViewerBinding
import haina.ecommerce.databinding.ActivityWebviewBinding
import haina.ecommerce.model.ArticlesItem
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

import android.R
import com.github.barteksc.pdfviewer.PDFView


class PdfViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfViewerBinding
    var pdfView:PDFView? = null
    var getUrl:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUrl = intent.extras?.get("url").toString()
        binding.toolbar3.setNavigationIcon(haina.ecommerce.R.drawable.ic_back_black)
        binding.toolbar3.setNavigationOnClickListener { onBackPressed() }


        pdfView = binding.pdfViewer
        RetrivePDFfromUrl().execute()
    }

    inner class RetrivePDFfromUrl: AsyncTask<String, Void, InputStream>() {
        override fun doInBackground(vararg p0: String?): InputStream {
            var inputStream: InputStream? = null
            try {
                val url = URL(getUrl)
                // below is the step where we are
                // creating our connection.
                val urlConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                if (urlConnection.responseCode == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = BufferedInputStream(urlConnection.getInputStream())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return inputStream!!
        }
        override fun onPostExecute(inputStream: InputStream?) {
            // after the execution of our async task we are loading our pdf in our pdf view.
            binding.ivLoading.visibility = View.GONE
            pdfView!!.fromStream(inputStream).load()
        }


    }
}