package haina.ecommerce.view.restaurant.detail.review

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.databinding.LayoutRestaurantOverviewBinding
import haina.ecommerce.databinding.LayoutRestaurantReviewListBinding
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.view.restaurant.detail.overview.RestaurantOverviewPresenter

class RestaurantReviewListFragment(val data: RestaurantData):
    Fragment()
    ,RestaurantReviewListContract.View
{

    private lateinit var _binding: LayoutRestaurantReviewListBinding
    private val binding get() = _binding
    private lateinit var presenter: RestaurantReviewListPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var ctx: Context


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = LayoutRestaurantReviewListBinding.inflate(inflater, container, false)
        presenter = RestaurantReviewListPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        presenter.getReviewList(data.id!!)

    }

    override fun message(msg: String) {
        TODO("Not yet implemented")
    }

    override fun getReviewList(data: RestaurantData?) {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun dismissLoading() {
        TODO("Not yet implemented")
    }
}