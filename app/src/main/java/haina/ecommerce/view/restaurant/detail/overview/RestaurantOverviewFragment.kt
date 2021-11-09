package haina.ecommerce.view.restaurant.detail.overview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentRestaurantDetailBinding
import haina.ecommerce.databinding.LayoutRestaurantOverviewBinding
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.view.restaurant.detail.RestaurantDetailPresenter
import timber.log.Timber

class RestaurantOverviewFragment(val data:RestaurantData):
    Fragment()
    , RestaurantOverviewContract.View
{

    private lateinit var _binding:LayoutRestaurantOverviewBinding
    private val binding get() = _binding
    private lateinit var presenter: RestaurantOverviewPresenter
    private var broadcaster:LocalBroadcastManager? = null
    private lateinit var ctx: Context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = LayoutRestaurantOverviewBinding.inflate(inflater, container, false)
        presenter = RestaurantOverviewPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRestaurantData()

        presenter.getRestaurantMenu(data!!.id!!)

    }

    //view function
    private fun setRestaurantData(){
        binding.tvPhoneNumber.text = data.phone
        binding.tvFullAddress.text = "${data.address} (${data.detailAddress})"
    }

    //contract function
    override fun message(msg: String) {
        Timber.d(msg)
    }

    override fun getMenuList(data: RestaurantData?) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

}