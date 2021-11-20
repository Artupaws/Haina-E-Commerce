package haina.ecommerce.view.restaurant.detail.review

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.adapter.restaurant.review.AdapterRestaurantReview
import haina.ecommerce.databinding.LayoutRestaurantReviewListBinding
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.model.restaurant.master.ReviewPagination
import timber.log.Timber

class RestaurantReviewListFragment(val data: RestaurantData,val callback:FragmentCallback):
    Fragment()
    ,RestaurantReviewListContract
{

    private var page:Int = 1
    private var totalPage:Int= 0

    private lateinit var _binding: LayoutRestaurantReviewListBinding
    private val binding get() = _binding
    private lateinit var presenter: RestaurantReviewListPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var ctx: Context

//    Lazy Adapter
    private val adapterReviewList by lazy {
        AdapterRestaurantReview(requireActivity(), arrayListOf())
    }
//    End Lazy Adapter

    interface FragmentCallback{
        fun writeReview(data:RestaurantData)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = LayoutRestaurantReviewListBinding.inflate(inflater, container, false)
        presenter = RestaurantReviewListPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.rvReviewList.adapter = adapterReviewList

        presenter.getReviewList(data.id!!,page)


        binding.rvReviewList.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    Timber.d("last")
                    if(page!=totalPage) {
                        binding.ivLoading.visibility = View.VISIBLE
                        binding.tvEndPage.visibility=View.GONE
                        page++
                        presenter.getReviewList(data.id, page)
                    }else{
                        binding.tvEndPage.visibility=View.VISIBLE
                        binding.ivLoading.visibility = View.GONE
                    }
                }
            }
        })
        binding.lnWriteReview.setOnClickListener {
            callback.writeReview(data)
        }

        setReviewData()
    }

    //view function
    @SuppressLint("SetTextI18n")
    private fun setReviewData(){
        binding.tvRating.text = data.rating
        binding.tvReviewCount.text = "${data.reviews.toString()} Reviews"
    }
    //end of view function

    //contract function
    override fun message(code:Int,msg: String) {
        if(code==404){
            binding.tvEndPage.visibility=View.VISIBLE
            binding.ivLoading.visibility = View.GONE
        }
    }

    override fun getReviewList(data: ReviewPagination?) {
        adapterReviewList.add(data!!.reviews)
        totalPage = data.totalPage!!
        if(totalPage == 1){
            binding.tvEndPage.visibility=View.VISIBLE
            binding.ivLoading.visibility = View.GONE
        }
    }
    //end of contract function


}