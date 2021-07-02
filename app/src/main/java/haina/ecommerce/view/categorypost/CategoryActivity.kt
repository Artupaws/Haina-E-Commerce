package haina.ecommerce.view.categorypost

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.categorypost.AdapterCategoryPosting
import haina.ecommerce.databinding.ActivityCategoryBinding
import haina.ecommerce.model.categorypost.DataCategory
import haina.ecommerce.view.property.PropertyActivity

class CategoryActivity : AppCompatActivity(), CategoryPostContract.View, AdapterCategoryPosting.ItemAdapterCallback {

    private lateinit var binding:ActivityCategoryBinding
    private var progressDialog:Dialog? = null
    private lateinit var presenter: CategoryPostPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        presenter = CategoryPostPresenter(this, this)
        setContentView(binding.root)
        dialogLoading()
        binding.toolbar6.setNavigationOnClickListener {
            onBackPressed()
        }
        presenter.getCategoryPost()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun dialogLoading(){
        progressDialog = Dialog(this)
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(getDrawable(android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun getListCategory(data: List<DataCategory?>?) {
        binding.rvCategoryPost.apply {
            adapter = AdapterCategoryPosting(applicationContext, data, this@CategoryActivity)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun messageGetCategory(msg: String) {
        Log.d("dataCategory", msg)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onAdapterClick(view: View, data: DataCategory) {
        when(view.id){
            R.id.linear_data_category -> {
                val intent = Intent(applicationContext, PropertyActivity::class.java)
                intent.putExtra("dataCategory", data)
                startActivity(intent)
            }
        }
    }
}