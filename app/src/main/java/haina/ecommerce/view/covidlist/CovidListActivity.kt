package haina.ecommerce.view.covidlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.adapter.AdapterCovid
import haina.ecommerce.databinding.ActivityCovidListBinding
import haina.ecommerce.model.DataCovid

class CovidListActivity : AppCompatActivity(), CovidListContract {

    private lateinit var binding: ActivityCovidListBinding
    private lateinit var presenter: CovidListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCovidListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = CovidListPresenter(this)

        binding.toolbar.title = "List Province"
        binding.toolbar.setNavigationIcon(haina.ecommerce.R.drawable.ic_back_black)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        presenter.loadCovidList()
    }


    override fun errorMessage(msg: String) {
        Log.d("message covid list", msg)
    }

    override fun loadListCovid(list: List<DataCovid?>?) {
        val adapterCovid = AdapterCovid(this, list)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapterCovid
            adapterCovid.notifyDataSetChanged()
        }
    }
}