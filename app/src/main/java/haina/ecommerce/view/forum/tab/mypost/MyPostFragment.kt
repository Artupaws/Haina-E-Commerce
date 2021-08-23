package haina.ecommerce.view.forum.tab.mypost

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterListHotPost
import haina.ecommerce.adapter.forum.AdapterListMyPost
import haina.ecommerce.databinding.FragmentMyPostBinding
import haina.ecommerce.model.forum.DataForum
import haina.ecommerce.model.forum.DataItemHotPost
import haina.ecommerce.model.forum.DataMypost
import haina.ecommerce.model.forum.DataPostDeleted
import haina.ecommerce.view.forum.detailforum.DetailForumActivity
import timber.log.Timber
import java.util.ArrayList

class MyPostFragment : Fragment(), MyPostContract.View, AdapterListMyPost.ItemAdapterCallback {

    private lateinit var _binding: FragmentMyPostBinding
    private val binding get() = _binding
    private var progressDialog: Dialog? = null
    private lateinit var presenter: MyPostPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyPostBinding.inflate(inflater, container, false)
        presenter = MyPostPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loading()
        presenter.getListMypost()
        refresh()
        binding.rvMyPost.adapter = myPostAdapter
    }

    override fun onResume() {
        super.onResume()
        presenter.getListMypost()
    }

    private fun refresh(){
        binding.swipeRefreshLayout2.setOnRefreshListener {
            presenter.getListMypost()
        }
    }

    private fun loading() {
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                android.R.color.white
            )
        )
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageGetListMypost(msg: String) {
        Timber.d(msg)
        binding.swipeRefreshLayout2.isRefreshing = false
        if (msg.contains("No post found!")) {
            binding.includeEmptyMypost.linearEmpty.visibility = View.VISIBLE
            binding.includeEmptyMypost.tvEmpty.text = ""
            binding.rvMyPost.visibility = View.GONE
        } else {
            binding.includeEmptyMypost.linearEmpty.visibility = View.GONE
            binding.rvMyPost.visibility = View.VISIBLE
        }
    }

    override fun messageGiveUpvote(msg: String) {
        Timber.d(msg)
    }

    override fun messageDeleteMyPost(msg: String) {
        Timber.d(msg)
    }

    override fun getListMypost(data: List<DataItemHotPost?>?) {
        myPostAdapter.clear()
        myPostAdapter.add(data)
    }

    override fun getDataMyPostDeleted(data: DataPostDeleted) {

    }

    private val myPostAdapter by lazy {
        AdapterListMyPost(requireActivity(), arrayListOf(), this, 2)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun listMyPostClick(view: View, isChecked: Boolean, data: DataItemHotPost) {
        when (view.id) {
//            R.id.iv_upvote -> {
//                if (isChecked){
//                    presenter.giveUpvote(data.id)
//                } else {
//                    presenter.removeUpvote(data.id)
//                }
//            }
            R.id.relative_click -> {
                val intentDetail = Intent(requireActivity(), DetailForumActivity::class.java)
                intentDetail.putExtra("dataDetail", data)
                startActivity(intentDetail)
            }
        }
    }

    override fun deleteListMyPost(view: View, data: ArrayList<DataItemHotPost?>?, adapterPosition: Int, idPost:Int) {
        when (view.id) {
            R.id.tv_option_menu -> {
                val popup = PopupMenu(requireActivity(), view)
                popup.inflate(R.menu.menu_option_delete_item)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_delete -> {
//                            idPost.let { presenter.deleteMyPost(it) }
                            data?.removeAt(adapterPosition)
                            myPostAdapter.notifyItemRemoved(adapterPosition)
                        }
                    }
                    true
                }
                popup.show()
            }
        }
    }

}