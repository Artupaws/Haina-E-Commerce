package haina.ecommerce.view.restaurant.detail.review.addreview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentRestaurantAddReviewBinding
import haina.ecommerce.model.restaurant.master.ReviewData
import timber.log.Timber

class RestaurantAddReviewFragment :
    Fragment()
    ,RestaurantAddReviewContract.View
{
    private lateinit var _binding: FragmentRestaurantAddReviewBinding
    private val binding get() = _binding
    private lateinit var presenter: RestaurantAddReviewPresenter
    private var progressDialog: Dialog? = null
    private var broadcaster: LocalBroadcastManager? = null
    private lateinit var ctx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRestaurantAddReviewBinding.inflate(inflater, container, false)
        presenter = RestaurantAddReviewPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        ctx = container!!.context

        dialogLoading()

        return binding.root
    }

    //View Function
    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }
    //End Of View Function

    //Contract Function
    override fun message(code: Int, msg: String) {
        Timber.d(msg)
    }

    override fun addReview(data: ReviewData?) {

    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.hide()

    }
    //End of Contract Function
}