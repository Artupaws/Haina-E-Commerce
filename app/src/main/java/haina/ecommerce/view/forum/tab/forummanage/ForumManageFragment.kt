package haina.ecommerce.view.forum.tab.forummanage

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import haina.ecommerce.adapter.forum.AdapterListBannedUser
import haina.ecommerce.adapter.forum.AdapterListHotPost
import haina.ecommerce.adapter.forum.AdapterListManageModerator
import haina.ecommerce.adapter.forum.AdapterListModerator
import haina.ecommerce.databinding.FragmentForumAboutBinding
import haina.ecommerce.databinding.FragmentForumManageBinding
import haina.ecommerce.model.forum.*
import haina.ecommerce.view.forum.tab.forumpost.ForumListPostPresenter
import timber.log.Timber
import java.util.ArrayList
import kotlin.properties.Delegates

class ForumManageFragment : Fragment(),ForumManageContract.View,AdapterListModerator.ItemAdapterCallback,AdapterListManageModerator.ItemAdapterCallback,AdapterListBannedUser.ItemAdapterCallback{

    private lateinit var _binding: FragmentForumManageBinding
    private val binding get() = _binding
    private var progressDialog: Dialog? = null
    private lateinit var presenter: ForumManagePresenter
    private var broadcaster: LocalBroadcastManager? = null
    private var idForum by Delegates.notNull<Int>()

    private val moderatorAdapter by lazy {
        AdapterListManageModerator(requireActivity(), arrayListOf(), this, 1)
    }

    private val banAdapter by lazy {
        AdapterListBannedUser(requireActivity(), arrayListOf(), this, 1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentForumManageBinding.inflate(inflater, container, false)
        presenter = ForumManagePresenter(this, requireActivity())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
//        binding.relativeAllThreads.setOnClickListener(this)
//        binding.relativeHotThreads.setOnClickListener(this)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        idForum = arguments?.getInt("idForum",0)!!
        presenter.getModList(idForum)
        presenter.getBanList(idForum)
        binding.rvModerator.adapter = moderatorAdapter
        binding.rvBan.adapter = banAdapter

        binding.rvModerator.visibility = View.VISIBLE

        binding.btnAddModerator.setOnClickListener {
            showAddModeratorDialog()
        }
    }

    private fun showAddModeratorDialog(){

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

    override fun getListBannedUser(data: List<DataBannedUser?>?) {
        banAdapter.clear()
        banAdapter.add(data)
    }

    override fun getRemoveData(data: RemoveModeratorData) {
        presenter.getModList(idForum)
    }

    override fun getRemoveBanned(data: DataRemoveBan) {
        presenter.getBanList(idForum)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun listProfileClick(view: View, isChecked: Boolean, data: Moderator) {
    }

    override fun removeModerator(data: Moderator) {
        moderatorAdapter.clear()
        presenter.removeModerator(data.subforumId!!,data.userId!!)
    }

    override fun removeBanned(data: DataBannedUser) {
        banAdapter.clear()
        presenter.removeBanned(data.subforumId!!,data.userId!!)
    }

}