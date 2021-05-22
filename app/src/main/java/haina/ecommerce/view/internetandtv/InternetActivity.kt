package haina.ecommerce.view.internetandtv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import haina.ecommerce.R
import haina.ecommerce.adapter.service.AdapterSpinnerProductService
import haina.ecommerce.databinding.ActivityInternetBinding
import haina.ecommerce.model.bill.DataBill
import haina.ecommerce.model.productservice.DataProductService
import haina.ecommerce.model.productservice.Product
import haina.ecommerce.view.checkout.CheckoutActivity

class InternetActivity : AppCompatActivity(), InternetContract, View.OnClickListener {

    private lateinit var binding:ActivityInternetBinding
    private lateinit var presenter: InternetPresenter
    private var productCode:String =""
    private var customerNumber:String =""
    private var nameCategory:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInternetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idProductCategory = intent?.getIntExtra("idProductCategory", 0)
        nameCategory = intent?.getStringExtra("category")!!
        presenter = InternetPresenter(this, this)
        presenter.getProductService(idProductCategory!!)
        binding.toolbarInternet.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarInternet.setNavigationOnClickListener { onBackPressed() }

        binding.etCustomerNumber.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                false
            }

            override fun afterTextChanged(p0: Editable?) {
                customerNumber = p0?.toString()!!
                true
            }
        })

        binding.btnNext.setOnClickListener(this)
        setTitle(nameCategory)

    }

    private fun setSpinnerProduct(data:List<DataProductService?>?) {
        val adapterSpinner = AdapterSpinnerProductService(applicationContext, data)
        binding.spnServices.adapter = adapterSpinner
        binding.spnServices.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                productCode = data?.get(p2)?.product.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun setTitle(category:String){
        when(category){
            "Internet Service Provider" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text =  titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "PLN" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text =  titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "PDAM" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text =  titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "Telephone" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text =  titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "TV Cable" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text =  titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "Insurance" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text =  titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
        }
    }

    override fun messageGetProductService(msg: String) {
        Log.d("productService", msg)
    }

    override fun getDataProductService(data: List<DataProductService?>?) {
        setSpinnerProduct(data)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_next -> {
                val productCodeParams = productCode
                var customerNumberParams = customerNumber

                if (customerNumberParams.isNullOrEmpty()){
                    binding.etCustomerNumber.error = "Please fill customer number"
                } else {
                    customerNumberParams = customerNumber
                }

                if (!customerNumberParams.isNullOrEmpty()){
                    val intentToCheckout = Intent(applicationContext, CheckoutActivity::class.java)
                        .putExtra("productCode", productCodeParams)
                        .putExtra("customerNumber", customerNumberParams)
                        .putExtra("typeTransaction", 3)
                    startActivity(intentToCheckout)
                }

            }
        }
    }
}