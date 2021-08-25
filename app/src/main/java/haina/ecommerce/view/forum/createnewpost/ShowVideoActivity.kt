package haina.ecommerce.view.forum.createnewpost

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import haina.ecommerce.R
import haina.ecommerce.databinding.ActivityShowVideoBinding

class ShowVideoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityShowVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uriVideo = intent.getStringExtra("uri")

        val mediaController = MediaController(this)
        val uriParams = Uri.parse(uriVideo)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.setVideoURI(uriParams)
        binding.videoView.requestFocus()
        binding.videoView.start()
    }
}