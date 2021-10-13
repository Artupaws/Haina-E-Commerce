package haina.ecommerce.view.forum.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterDetailImage
import haina.ecommerce.adapter.forum.AdapterListAllThreads
import haina.ecommerce.adapter.forum.AdapterListSubforum
import haina.ecommerce.databinding.FragmentBottomSheetSubforumBinding
import haina.ecommerce.model.forum.*
import haina.ecommerce.view.forum.detailforum.DetailForumActivity
import haina.ecommerce.view.forum.tab.myforum.DetailMySubforum
import timber.log.Timber
import java.util.ArrayList

class BottomSheetSubforum : BottomSheetDialogFragment(),View.OnClickListener,
    AdapterListSubforum.ItemAdapterCallback, AdapterListAllThreads.ItemAdapterCallback {

    private lateinit var _binding:FragmentBottomSheetSubforumBinding
    private val binding get() = _binding
    private var broadcaster:LocalBroadcastManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBottomSheetSubforumBinding.inflate(inflater, container, false)
        binding.ivClose.setOnClickListener(this)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dataSubforum = arguments?.getParcelable<DataSearch>("dataSubforum")
        if (dataSubforum?.post?.isEmpty() == true){
            binding.rvPost.visibility = View.GONE
            binding.tvTitlePost.visibility = View.GONE
        } else{
            binding.rvPost.visibility = View.VISIBLE
            binding.tvTitlePost.visibility = View.VISIBLE
        }
        if (dataSubforum?.subforum?.isEmpty() == true){
            binding.rvSubforum.visibility = View.GONE
            binding.tvTitleSubforumSearch.visibility = View.GONE
        } else{
            binding.rvSubforum.visibility = View.VISIBLE
            binding.tvTitleSubforumSearch.visibility = View.VISIBLE
        }
        binding.rvPost.adapter = adapterListThreads
        binding.rvSubforum.adapter = adapterSubforum
        adapterSubforum.clear()
        dataSubforum?.let { adapterSubforum.add(it.subforum) }
        adapterListThreads.clear()
        dataSubforum?.let { adapterListThreads.add(it.post) }
    }

    private val adapterSubforum by lazy {
        AdapterListSubforum.VIEW_TYPE = 3
        AdapterListSubforum(requireActivity(), arrayListOf(), this)
    }

    private val adapterListThreads by lazy {
        AdapterListAllThreads(requireActivity(), arrayListOf(), this, 1)
    }

    companion object {
        @JvmStatic
        fun instanceBottomSheetSubforum(bundle: Bundle):BottomSheetSubforum{
            val fragment = BottomSheetSubforum()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_close -> {
                dismiss()
            }
        }
    }

    override fun listSubforumClick(view: View, data: SubforumData) {
        when(view.id){
            R.id.cv_click -> {
                startActivity(Intent(requireActivity(), DetailMySubforum::class.java)
                    .putExtra("dataDetail", data))
                dismiss()
            }
        }
    }

    override fun listAllThreadsClick(view: View, isChecked: Boolean, data: ThreadsItem) {
        when(view.id){
            R.id.relative_click -> {
                val dataParams = DataItemHotPost(data.memberSince, data.commentCount, data.images, data.likeCount,
                    data.authorData, data.author, data.created,"", data.videos, data.title, data.content, data.shareCount,
                data.authorPhoto, data.userId, data.subforumData, data.id, data.viewCount, data.subforumFollow,data.upvoted)
                val intentDetail = Intent(requireActivity(), DetailForumActivity::class.java)
                intentDetail.putExtra("dataDetail", dataParams)
                startActivity(intentDetail)
            }
        }
    }

    override fun detailPhoto(listImage: List<ImagesItem?>?, position: Int) {
        val intentDetailPhoto = Intent(requireActivity(), AdapterDetailImage::class.java)
        intentDetailPhoto.putParcelableArrayListExtra("image_list",listImage as ArrayList<ImagesItem>)
        intentDetailPhoto.putExtra("position",position)
        startActivity(intentDetailPhoto)
    }
}