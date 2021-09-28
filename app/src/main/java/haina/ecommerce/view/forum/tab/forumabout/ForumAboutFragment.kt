package haina.ecommerce.view.forum.tab.forumabout

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.adapter.forum.AdapterListHotPost
import haina.ecommerce.adapter.forum.AdapterListModerator
import haina.ecommerce.databinding.FragmentForumAboutBinding
import haina.ecommerce.model.forum.Moderator
import haina.ecommerce.model.forum.SubforumEngagement
import haina.ecommerce.view.forum.tab.forumpost.ForumListPostPresenter
import timber.log.Timber
import java.util.ArrayList
import kotlin.properties.Delegates

class ForumAboutFragment : Fragment(),ForumAboutContract.View,AdapterListModerator.ItemAdapterCallback{

    private lateinit var _binding: FragmentForumAboutBinding
    private val binding get() = _binding
    private var progressDialog: Dialog? = null
    private lateinit var presenter: ForumAboutPresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var idForum by Delegates.notNull<Int>()
    private var dataEngagement:SubforumEngagement? = null


    private val moderatorAdapter by lazy {
        AdapterListModerator(requireActivity(), arrayListOf(), this, 1)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentForumAboutBinding.inflate(inflater, container, false)
        presenter = ForumAboutPresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
//        binding.relativeAllThreads.setOnClickListener(this)
//        binding.relativeHotThreads.setOnClickListener(this)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idForum = arguments?.getInt("idForum",0)!!
        dataEngagement = arguments?.getParcelable("dataEngagement")!!
        presenter.getModList(idForum)
        binding.rvModerator.adapter = moderatorAdapter
        binding.rvModerator.visibility = View.VISIBLE

        binding.tvFollowersCount.text = dataEngagement!!.followersCount.toString()
        binding.tvLikeCount.text = dataEngagement!!.likes.toString()
        binding.tvTotalView.text = dataEngagement!!.views.toString()
        binding.tvPostCount.text = dataEngagement!!.postCount.toString()

    }

    override fun messageGetAbout(msg: String) {
        Timber.d(msg)
    }

    override fun getForumDetail(msg: String) {

    }

    override fun getListModerator(data: List<Moderator?>?) {
        moderatorAdapter.clear()
        moderatorAdapter.add(data)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun listProfileClick(view: View, isChecked: Boolean, data: Moderator) {
    }

}