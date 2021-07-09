package haina.ecommerce.view.water

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import haina.ecommerce.R
import haina.ecommerce.adapter.service.AdapterSpinnerProductService
import haina.ecommerce.databinding.ActivityWaterBinding
import haina.ecommerce.model.productservice.DataProductService
import haina.ecommerce.view.internetandtv.InternetContract
import haina.ecommerce.view.internetandtv.InternetPresenter

class WaterActivity : AppCompatActivity() {

    private lateinit var binding:ActivityWaterBinding
    private lateinit var presenter: InternetPresenter
    private var productCode:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idProductCategory = intent?.getIntExtra("idProductCategory", 0)
        presenter.getProductService(idProductCategory!!)
        binding.toolbarWater.title = "Water PDAM"
        binding.toolbarWater.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarWater.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setSpinnerProduct(data:List<DataProductService?>?) {
        val adapterSpinner = AdapterSpinnerProductService(applicationContext, data)
        binding.spnWaterManagement.adapter = adapterSpinner
        binding.spnWaterManagement.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                productCode = data?.get(p2)?.product.toString()
                Toast.makeText(applicationContext, productCode, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

}