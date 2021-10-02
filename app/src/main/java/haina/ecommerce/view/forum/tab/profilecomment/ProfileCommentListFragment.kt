package haina.ecommerce.view.forum.tab.profilecomment

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterCategoryForum
import haina.ecommerce.adapter.forum.AdapterListAllThreads
import haina.ecommerce.adapter.forum.AdapterListHotPost
import haina.ecommerce.adapter.forum.AdapterListUserComment
import haina.ecommerce.databinding.FragmentShowForumBinding
import haina.ecommerce.model.forum.*
import haina.ecommerce.view.forum.createnewpost.NewPostActivity
import haina.ecommerce.view.forum.createsubforum.CreateSubforumActivity
import haina.ecommerce.view.forum.detailforum.DetailForumActivity
import timber.log.Timber
import java.util.ArrayList
import kotlin.properties.Delegates

class ProfileCommentListFragment : Fragment(),ProfileCommentListContract.View,
    AdapterListUserComment.ItemAdapterCallback, View.OnClickListener{

    private lateinit var _binding:FragmentShowForumBinding
    private val binding get() = _binding
    private var progressDialog:Dialog? = null
    private lateinit var presenter: ProfileCommentListPresenter
    private var broadcaster:LocalBroadcastManager? = null
    private var popupCreatePost:Dialog?=null
    private var listCategory:List<DataCategoryForum?>? = null
    private var page:Int = 1
    private var totalPage:Int= 0

    private var idUser by Delegates.notNull<Int>()
    private val positionCollaps: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_collapse)
    }

    private val positionExpand: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_expand)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShowForumBinding.inflate(inflater, container, false)
        presenter = ProfileCommentListPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        binding.fabCreateForum.setOnClickListener(this)
//        binding.relativeAllThreads.setOnClickListener(this)
//        binding.relativeHotThreads.setOnClickListener(this)
        binding.tvLoadMore.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idUser = arguments?.getInt("idUser",0)!!
        presenter.getListProfileComments(idUser,page)
//        presenter.getListAllThreads(page)
        dialogLoading()
        dialogCreatePost()
        refresh()
        binding.rvForum.adapter = commentsAdapter
        binding.rvForum.visibility = View.VISIBLE
//        binding.rvAllPost.adapter = allThreadsAdapter
//        binding.rvForum.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (dy > 0){
//                    binding.fabCreateForum.visibility = View.GONE
//                } else {
//                    binding.fabCreateForum.visibility = View.VISIBLE
//                }
//            }
//        })

        binding.scrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener {
                v, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY){
                binding.fabCreateForum.visibility = View.GONE
            }
            if (scrollY < oldScrollY){
                binding.fabCreateForum.visibility = View.VISIBLE
            }
            if(!v.canScrollVertically(1)){
                Timber.d("last")
                binding.tvLoadMore.visibility = View.VISIBLE
            }
        })
    }


    override fun onResume() {
        super.onResume()
        presenter.getListProfileComments(idUser,page)
    }

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun dialogCreatePost(){
        popupCreatePost = Dialog(requireActivity())
        popupCreatePost?.setContentView(R.layout.popup_add_post)
        popupCreatePost?.setCancelable(true)
        popupCreatePost?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window: Window = popupCreatePost?.window!!
        window.setGravity(Gravity.CENTER)
        val btnCreateSubforum = popupCreatePost?.findViewById<Button>(R.id.btn_create_subforum)
        val btnNewPost = popupCreatePost?.findViewById<Button>(R.id.btn_new_post)
        btnCreateSubforum?.setOnClickListener {
            val intent = Intent(requireActivity(), CreateSubforumActivity::class.java)
            intent.putParcelableArrayListExtra("listCategory", listCategory as ArrayList<DataCategoryForum>)
            startActivity(intent)
            popupCreatePost?.dismiss()
        }
        btnNewPost?.setOnClickListener {
            val intent = Intent(requireActivity(), NewPostActivity::class.java)
            startActivity(intent)
            popupCreatePost?.dismiss()
        }
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getListProfileComments(idUser,page)
        }
    }


    override fun messageGetComment(msg: String) {
        Timber.d(msg)
    }

    override fun getListComment(data: DataAllCommentUser) {

        if (data != null){
            commentsAdapter.clear()
            commentsAdapter.add(data.comments)
        }
    }

    private val commentsAdapter by lazy {
        AdapterListUserComment(requireActivity(), arrayListOf(), this, 1)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }


    override fun commentClick(view: View, isChecked:Boolean, data: CommentsItem) {
        when(view.id){
            R.id.relative_click -> {
                val intentDetail = Intent(requireActivity(), DetailForumActivity::class.java)
                intentDetail.putExtra("dataDetail", data)
                startActivity(intentDetail)
            }
        }
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab_create_forum -> {
                popupCreatePost?.show()
            }
            R.id.tv_load_more -> {
                presenter.getListProfileComments(idUser,page++)
            }
        }
    }


}