package haina.ecommerce.view.property

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.adapter.property.AdapterShowProperty
import haina.ecommerce.databinding.ActivityShowPropertyBinding
import haina.ecommerce.model.property.DataShowProperty
import haina.ecommerce.view.property.fragmentshowproperty.ShowPropertyContract
import haina.ecommerce.view.property.fragmentshowproperty.ShowPropertyPresenter

class ShowPropertyActivity : AppCompatActivity() {

    private lateinit var binding:ActivityShowPropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}