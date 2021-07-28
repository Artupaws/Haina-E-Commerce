package haina.ecommerce.view.history.historymyproperty.ads

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import haina.ecommerce.adapter.property.AdapterShowProperty
import haina.ecommerce.databinding.FragmentMyAdsBinding
import haina.ecommerce.model.property.DataShowProperty
import haina.ecommerce.view.property.detailmyproperty.DetailMyPropertyActivity

class MyAdsFragment : Fragment(), AdapterShowProperty.ItemAdapterCallback {

    private lateinit var _binding:FragmentMyAdsBinding
    private val binding get() = _binding
    private lateinit var broadcaster:LocalBroadcastManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyAdsBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(mMessageReceiver, IntentFilter("MyProperty"))
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(mMessageReceiver)
    }

    private val mMessageReceiver:BroadcastReceiver = object:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "MyProperty" -> {
                    val dataAds = intent.getParcelableArrayListExtra<DataShowProperty>("Ads")
                    binding.rvProperty.apply {
                        adapter = AdapterShowProperty(requireActivity(), dataAds, this@MyAdsFragment, false)
                        layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                    }
                }
            }
        }
    }

    override fun onClickAdapterCity(view: View, data: DataShowProperty) {
        Log.d("idProperty", data.id.toString())
        val intentDetail = Intent(requireActivity(), DetailMyPropertyActivity::class.java)
        intentDetail.putExtra("dataProperty", data)
        startActivity(intentDetail)
    }

}