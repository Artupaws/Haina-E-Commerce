package haina.ecommerce.view.internetandtv

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import haina.ecommerce.R
import haina.ecommerce.adapter.service.AdapterSpinnerProductService
import haina.ecommerce.databinding.ActivityInternetBinding
import haina.ecommerce.model.bill.DataBill
import haina.ecommerce.model.bill.DataInquiry
import haina.ecommerce.model.bill.DataNoInquiry
import haina.ecommerce.model.bill.RequestBill
import haina.ecommerce.model.productservice.DataProductService
import haina.ecommerce.model.productservice.Product
import haina.ecommerce.view.checkout.CheckoutActivity

class InternetActivity : AppCompatActivity(), InternetContract, View.OnClickListener {

    private lateinit var binding: ActivityInternetBinding
    private lateinit var presenter: InternetPresenter
    private var productCode: String = ""
    private var customerNumber: String = ""
    private var nameCategory: String = ""
    private var requestBill:RequestBill? = null
    private var nameProduct:String=""

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

        binding.etCustomerNumber.addTextChangedListener(object : TextWatcher {
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

    private fun setSpinnerProduct(data: List<DataProductService?>?) {
        val adapterSpinner = AdapterSpinnerProductService(applicationContext, data)
        binding.spnServices.adapter = adapterSpinner
        binding.spnServices.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                productCode = data?.get(p2)?.product.toString()
                nameProduct = data?.get(p2)?.name.toString().toLowerCase()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun setTitle(category: String) {
        when (category) {
            "Internet Service Provider" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text = titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "Electricity" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text = titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "Water" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text = titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "Telephone" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text = titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "TV Cable" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text = titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
            "Insurance" -> {
                binding.toolbarInternet.title = nameCategory
                val titleProvider = "$nameCategory Provider"
                binding.tvTitleProvider.text = titleProvider
                val titleCustomerNumber = "Customer Number $nameCategory"
                binding.tvTitleCustomerNumber.text = titleCustomerNumber
            }
        }
    }

    private fun move(data:DataInquiry) {
        val intentToCheckout = Intent(applicationContext, CheckoutActivity::class.java)
            .putExtra("dataBill", data)
            .putExtra("request", requestBill)
            .putExtra("typeTransaction", 2)
        startActivity(intentToCheckout)
    }

    private fun moveDirect(data:DataNoInquiry) {
        val intentToCheckout = Intent(applicationContext, CheckoutActivity::class.java)
            .putExtra("dataBillNoInquiry", data)
            .putExtra("request", requestBill)
            .putExtra("typeTransaction", 2)
        startActivity(intentToCheckout)
    }

    override fun messageGetProductService(msg: String) {
        Log.d("productService", msg)
    }

    override fun getDataProductService(data: List<DataProductService?>?) {
        setSpinnerProduct(data)
    }

    override fun messageGetBillAmount(msg: String) {
        when(msg){
            "Unauthorized!" -> {
                Toast.makeText(applicationContext, "Please login to continue", Toast.LENGTH_SHORT).show()
            } else -> {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            }
        }
        binding.relativeLoading.visibility = View.GONE
        binding.btnNext.visibility = View.VISIBLE
    }

    override fun messageGetBillDirect(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    override fun getDataBillAmount(data: DataInquiry) {
        if (!data.equals(null)){
            move(data)
        } else {
            Toast.makeText(applicationContext, "Not Found!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getDataBillDirect(data: DataNoInquiry) {
        if (!data.equals(null)){
            moveDirect(data)
        } else {
            Toast.makeText(applicationContext, "Not Found!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_next -> {
                val productCodeParams = productCode
                var customerNumberParams = customerNumber

                if (customerNumberParams.isNullOrEmpty()) {
                    binding.etCustomerNumber.error = "Please fill customer number"
                } else {
                    customerNumberParams = customerNumber
                }

                if (!customerNumberParams.isNullOrEmpty()) {
                    setPresenter(nameProduct, customerNumberParams, productCodeParams)
                    requestBill = RequestBill(productCodeParams, null, null, customerNumberParams, null)
                    binding.relativeLoading.visibility = View.VISIBLE
                    binding.btnNext.visibility = View.GONE
                }
                this.currentFocus?.let { view ->
                    val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
        }
    }

    private fun setPresenter(productNameParams:String, orderId:String, productCode:String){
        when(productNameParams){
            "indovision" -> {
                presenter.getDirectBill(orderId, productCode)
            }
            "indihome" -> {
                presenter.getBillAmount(orderId, productCode)
            }
            "cbn" -> {
                presenter.getDirectBill(orderId, productCode)
            }
            "indosatnet" -> {
                presenter.getDirectBill(orderId, productCode)
            }
            "centrinnet" -> {
                presenter.getDirectBill(orderId, productCode)
            }
            "pln" -> {
                presenter.getBillAmount(orderId, productCode)
            }
            "aetra" ->{
                presenter.getBillAmount(orderId, productCode)
            }
            "palyja" ->{
                presenter.getBillAmount(orderId, productCode)
            }
            "firstmedia" ->{
                presenter.getDirectBill(orderId, productCode)
            }
            "prudential" -> {
                presenter.getDirectBill(orderId, productCode)
            }
            "sinarmas life" -> {
                presenter.getDirectBill(orderId, productCode)
            }
        }
    }

}