package haina.ecommerce.view.forum.tab.showforum

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
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterCategoryForum
import haina.ecommerce.adapter.forum.AdapterListAllThreads
import haina.ecommerce.adapter.forum.AdapterListHotPost
import haina.ecommerce.databinding.FragmentShowForumBinding
import haina.ecommerce.model.forum.*
import haina.ecommerce.view.forum.bottomsheet.BottomSheetSubforum
import haina.ecommerce.view.forum.createnewpost.NewPostActivity
import haina.ecommerce.view.forum.createsubforum.CreateSubforumActivity
import haina.ecommerce.view.forum.detailforum.DetailForumActivity
import timber.log.Timber
import java.util.ArrayList

class ShowForumFragment : Fragment(), ShowForumContract.View, AdapterCategoryForum.ItemAdapterCallback,
    AdapterListHotPost.ItemAdapterCallback, View.OnClickListener, AdapterListAllThreads.ItemAdapterCallback {

    private lateinit var _binding:FragmentShowForumBinding
    private val binding get() = _binding
    private var progressDialog:Dialog? = null
    private lateinit var presenter: ShowForumPresenter
    private var broadcaster:LocalBroadcastManager? = null
    private var popupCreatePost:Dialog?=null
    private var listCategory:List<DataCategoryForum?>? = null
    private var page:Int = 1
    private var totalPage:Int= 0
    private val positionCollaps: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_collapse)
    }

    private val positionExpand: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_expand)
    }
    private var clicked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShowForumBinding.inflate(inflater, container, false)
        presenter = ShowForumPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        binding.fabCreateForum.setOnClickListener(this)
        binding.ivArrowHotTheads.setOnClickListener(this)
        binding.ivArrowAllThreads.setOnClickListener(this)
        binding.tvLoadMore.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getCategory()
        presenter.getListHotThreads()
        presenter.getListAllThreads(page)
        dialogLoading()
        dialogCreatePost()
        refresh()
        binding.rvForum.adapter = showForumAdapter
        binding.rvAllPost.adapter = allThreadsAdapter
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
                Timber.d("Scroll Down")
                binding.fabCreateForum.visibility = View.GONE
            }
            if (scrollY < oldScrollY){
                Timber.d("Scroll Up")
                binding.fabCreateForum.visibility = View.VISIBLE
            }
            if (scrollY == 0){
                Timber.d("Top")
            }
            if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)){
                if (totalPage > page) binding.tvLoadMore.visibility = View.VISIBLE else binding.tvLoadMore.visibility = View.GONE
            }
        })
    }

    private fun onAddPostClicked(typeClicked: Int) {
        Timber.d(typeClicked.toString())
        setVisibility(clicked, typeClicked)
        setAnimation(clicked, typeClicked)
    }

    private fun setAnimation(clicked:Boolean, typeClicked:Int){
        if (!clicked && typeClicked == 1){
            binding.ivArrowHotTheads.startAnimation(positionExpand)
        } else if (clicked && typeClicked == 1){
            binding.ivArrowHotTheads.startAnimation(positionCollaps)
        }

        if (!clicked && typeClicked == 2){
            binding.ivArrowAllThreads.startAnimation(positionExpand)
        } else if (clicked && typeClicked == 2){
            binding.ivArrowAllThreads.startAnimation(positionCollaps)
        }
    }

    private fun setVisibility(clicked: Boolean, typeClicked: Int){
        if (clicked && typeClicked == 1){
            binding.rvForum.visibility = View.GONE
        } else if (!clicked && typeClicked == 1){
            binding.rvForum.visibility = View.VISIBLE
        }

        if (clicked && typeClicked == 2){
            binding.rvAllPost.visibility = View.GONE
            binding.tvLoadMore.visibility = View.GONE
        } else if (!clicked && typeClicked == 2){
            binding.rvAllPost.visibility = View.VISIBLE
            binding.tvLoadMore.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getListHotThreads()
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
            presenter.getListSubForum()
            presenter.getListHotThreads()
            presenter.getListAllThreads(page)
        }
    }

    override fun messageGetCategory(msg: String) {
        Timber.d(msg)
    }

    override fun messageGetListForum(msg: String) {
        Timber.d(msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun messageGetListSubforum(msg: String) {
        Timber.d(msg)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun messageGiveUpvote(msg: String) {
        Timber.d(msg)
    }

    override fun messageGetAllThreads(msg: String) {
        Timber.d(msg)
    }

    override fun getListCategoryForum(data: List<DataCategoryForum?>?) {
        if (data != null){
            listCategory = data
            binding.fabCreateForum.visibility = View.VISIBLE
        }
//        if (context != null){
//            binding.rvCategory.apply {
//                adapter = AdapterCategoryForum(requireActivity(), data, this@ShowForumFragment)
//                layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
//            }
//        }
    }

    override fun getListForum(data: List<DataItemHotPost?>?) {
        if (context != null){
            showForumAdapter.clear()
            showForumAdapter.add(data)
        }
    }

    override fun getListSubforum(data: List<SubforumData?>?) {
//        if (data !=null){
//            val bundle = Bundle()
//            bundle.putParcelableArrayList("dataSubforum", data as ArrayList<DataSubforum>)
//            childFragmentManager.let {
//                BottomSheetSubforum.instanceBottomSheetSubforum(bundle).apply {
//                    show(it, tag)
//                }
//            }
//        }
    }

    override fun getListAllThreads(data: List<ThreadsItem?>?) {
        if (context != null){
            allThreadsAdapter.clear()
            allThreadsAdapter.add(data)
        }
    }

    override fun getTotalPage(totalPageParams: Int) {
        Timber.d(totalPageParams.toString())
        totalPage = totalPageParams
    }

    private val allThreadsAdapter by lazy {
        AdapterListAllThreads(requireActivity(), arrayListOf(), this, 1)
    }

    private val showForumAdapter by lazy {
        AdapterListHotPost(requireActivity(), arrayListOf(), this, 1)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun categoryForumClick(view: View, data: DataCategoryForum) {
        when(view.id){
            R.id.cv_click -> {
                when(data.id){
                    1 -> {
                        presenter.getListHotThreads()
                    } else -> {
                    presenter.getListSubForum()
                    }
                }
            }
        }
    }

    override fun listForumClick(view: View, isChecked:Boolean, data: DataItemHotPost) {
        when(view.id){
            R.id.iv_upvote -> {
                if (isChecked){
                    data.id?.let { presenter.giveUpvote(it) }
                } else {
                    data.id?.let { presenter.removeUpvote(it) }
                }
            }
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
            R.id.iv_arrow_hot_theads -> {
                onAddPostClicked(1)
                clicked = !clicked
            }
            R.id.iv_arrow_all_threads -> {
                onAddPostClicked(2)
                clicked = !clicked
            }
            R.id.tv_load_more -> {
                presenter.getListAllThreads(page++)
            }
        }
    }

    override fun listAllThreadsClick(view: View, isChecked: Boolean, data: ThreadsItem) {
        when(view.id){
            R.id.iv_upvote -> {
                if (isChecked){
                    data.id?.let { presenter.giveUpvote(it) }
                } else {
                    data.id?.let { presenter.removeUpvote(it) }
                }
            }
            R.id.relative_click -> {
                val dataDetail = DataItemHotPost(data.memberSince, data.commentCount, data.images, data.likeCount, data.authorData, data.author,
                data.created, data.videos, data.title, data.content, data.shareCount, data.authorPhoto, data.userId, data.subforumData, data.id,
                data.viewCount, data.subforumFollow, data.upvoted)
                val intentDetail = Intent(requireActivity(), DetailForumActivity::class.java)
                intentDetail.putExtra("dataDetail", dataDetail)
                startActivity(intentDetail)
            }
        }
    }

}