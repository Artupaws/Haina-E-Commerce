package haina.ecommerce.view.forum.tab.myforum

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterListSubforum
import haina.ecommerce.adapter.forum.AdapterListSubforumFollow
import haina.ecommerce.databinding.FragmentMyForumBinding
import haina.ecommerce.model.forum.DataMypost
import haina.ecommerce.model.forum.DataSubforum
import haina.ecommerce.model.forum.DataSubforumHotPost
import haina.ecommerce.model.forum.SubforumData
import timber.log.Timber

class MySubforumFragment : Fragment(), MySubforumContract.View,
    AdapterListSubforum.ItemAdapterCallback, View.OnClickListener, AdapterListSubforumFollow.ItemAdapterCallback {

    private lateinit var _binding:FragmentMyForumBinding
    private val binding get() = _binding
    private var progressDialog:Dialog? = null
    private lateinit var presenter: MySubforumPresenter
    private val positionCollaps: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_collapse)
    }

    private val positionExpand: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_expand)
    }
    private var clicked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyForumBinding.inflate(inflater, container, false)
        presenter = MySubforumPresenter(this, requireActivity())
        binding.ivArrowMyforum.setOnClickListener(this)
        binding.ivArrow.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading()
        presenter.getListSubForum()
        presenter.getListSubForumFollow()
        binding.rvMyforum.adapter = mySubforumAdapter
        binding.rvMyforumFollow.adapter = mySubforumFollowAdapter
        AdapterListSubforum.VIEW_TYPE = 1
        AdapterListSubforumFollow.VIEW_TYPE = 1
        refresh()
    }

    override fun onResume() {
        super.onResume()
        presenter.getListSubForum()
        presenter.getListSubForumFollow()
        AdapterListSubforum.VIEW_TYPE = 1
        AdapterListSubforumFollow.VIEW_TYPE = 1
    }

    private fun loading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun refresh(){
        binding.swipeRefreshLayout2.setOnRefreshListener {
            presenter.getListSubForum()
            presenter.getListSubForumFollow()
        }
    }

    private fun onAddPostClicked(typeClicked: Int) {
        Timber.d(typeClicked.toString())
        setVisibility(clicked, typeClicked)
        setAnimation(clicked, typeClicked)
    }

    private fun setAnimation(clicked:Boolean, typeClicked:Int){
            if (!clicked && typeClicked == 1){
                binding.ivArrowMyforum.startAnimation(positionExpand)
            } else if (clicked && typeClicked == 1){
                binding.ivArrowMyforum.startAnimation(positionCollaps)
            }

            if (!clicked && typeClicked == 2){
                binding.ivArrow.startAnimation(positionExpand)
            } else if (clicked && typeClicked == 2){
                binding.ivArrow.startAnimation(positionCollaps)
            }
    }

    private fun setVisibility(clicked: Boolean, typeClicked: Int){
            if (clicked && typeClicked == 1){
                binding.frameMysubforum.visibility = View.GONE
            } else if (!clicked && typeClicked == 1){
                binding.frameMysubforum.visibility = View.VISIBLE
            }

            if (clicked && typeClicked == 2){
                binding.frameFollow.visibility = View.GONE
            } else if (!clicked && typeClicked == 2){
                binding.frameFollow.visibility = View.VISIBLE
            }
    }

    override fun messageGetListSubforum(msg: String) {
        Timber.d(msg)
        binding.swipeRefreshLayout2.isRefreshing = false
    }

    override fun messageGetListFollow(msg: String) {
        Timber.d(msg)
        binding.swipeRefreshLayout2.isRefreshing = false
    }

    override fun getListSubforum(data: List<SubforumData?>?) {
        mySubforumAdapter.clear()
        mySubforumAdapter.add(data)
        binding.tvTotalMyforum.text = data?.size.toString()
        if (data != null) {
            if (data.isEmpty()){
                binding.includeEmpty.linearEmpty.visibility = View.VISIBLE
                binding.includeEmpty.tvEmpty.text = ""
                binding.rvMyforum.visibility = View.GONE
            } else{
                binding.includeEmpty.linearEmpty.visibility = View.GONE
                binding.rvMyforum.visibility = View.VISIBLE
            }
        }
    }

    override fun getListSubforumFollow(data: List<SubforumData?>?) {
        mySubforumFollowAdapter.clear()
        mySubforumFollowAdapter.add(data)
        binding.tvTotalFollow.text = data?.size.toString()
        if (data != null) {
            if (data.isEmpty()){
                binding.includeEmptyFollow.linearEmpty.visibility = View.VISIBLE
                binding.includeEmptyFollow.tvEmpty.text = ""
                binding.rvMyforumFollow.visibility = View.GONE
            } else{
                binding.includeEmptyFollow.linearEmpty.visibility = View.GONE
                binding.rvMyforumFollow.visibility = View.VISIBLE
            }
        }
    }

    private val mySubforumAdapter by lazy {
        AdapterListSubforum(requireActivity(), arrayListOf(), this)
    }

    private val mySubforumFollowAdapter by lazy {
        AdapterListSubforumFollow(requireActivity(), arrayListOf(), this)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun listSubforumClick(view: View, data: SubforumData) {
        when(view.id){
            R.id.cv_click -> {
                startActivity(Intent(requireActivity(), DetailMySubforum::class.java)
                    .putExtra("dataDetail", data))
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_arrow_myforum -> {
                onAddPostClicked(1)
                clicked = !clicked
            }
            R.id.iv_arrow -> {
                onAddPostClicked(2)
                clicked = !clicked
            }
        }
    }

}