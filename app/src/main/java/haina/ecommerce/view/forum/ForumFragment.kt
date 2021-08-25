package haina.ecommerce.view.forum

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.TabAdapterForum
import haina.ecommerce.databinding.FragmentForumBinding
import haina.ecommerce.model.forum.DataSearch
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.forum.bottomsheet.BottomSheetSubforum
import haina.ecommerce.view.forum.forumactivity.ActivityForumActivity
import haina.ecommerce.view.login.LoginActivity
import timber.log.Timber


class ForumFragment : Fragment(), View.OnClickListener, ForumFragmentContract.View {

    private lateinit var _binding:FragmentForumBinding
    private val binding get() = _binding
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private lateinit var presenter: ForumFragmentPresenter
    private var progressDialog:Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentForumBinding.inflate(inflater, container, false)
        presenter = ForumFragmentPresenter(this, requireActivity())
        sharedPreferenceHelper = SharedPreferenceHelper(requireActivity())
        binding.includeLogin.btnLoginNotLogin.setOnClickListener(this)
        binding.ivActionActivityForum.setOnClickListener(this)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogLoading()
        binding.vpTransaction.adapter = TabAdapterForum(childFragmentManager, 0)
        binding.vpTransaction.offscreenPageLimit = 3
        binding.tabTransaction.setupWithViewPager(binding.vpTransaction)
        binding.toolbarFragmentForum.inflateMenu(R.menu.menu_search)
        binding.toolbarFragmentForum.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.action_activity_forum -> {
                    val intent = Intent(requireActivity(), ActivityForumActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.action_search -> {
                    val searchView = menuItem.actionView as androidx.appcompat.widget.SearchView
                    searchView.queryHint = "Search"
                    searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if (query?.length!! >= 2){
                                query.let {presenter.getSearch(it)}
                                requireActivity().currentFocus?.let { view ->
                                    val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                                    imm?.hideSoftInputFromWindow(view.windowToken, 0)
                                }
                            } else {
                                Toast.makeText(requireActivity(), "minimum 2 characters", Toast.LENGTH_SHORT).show()
                            }
                            return true
                        }
                        override fun onQueryTextChange(newText: String?): Boolean {
                            return false
                        }
                    })
                    true
                }
                else -> false
            }
        }
//        val menu = binding.toolbarFragmentForum.menu
//        val search = menu.findItem(R.id.action_search)
//        val searchView = search.actionView as androidx.appcompat.widget.SearchView
//        searchView.queryHint = "Search"
//        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (query?.length!! >= 2){
//                    query.let {presenter.getSearch(it)}
//                    requireActivity().currentFocus?.let { view ->
//                        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
//                        imm?.hideSoftInputFromWindow(view.windowToken, 0)
//                    }
//                } else {
//                    Toast.makeText(requireActivity(), "minimum 2 characters", Toast.LENGTH_SHORT).show()
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_activity_forum -> {
                Timber.d("Clicked Activity Forum")
                val intent = Intent(requireActivity(), ActivityForumActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        if (sharedPreferenceHelper.getValueBoolien(Constants.PREF_IS_LOGIN)){
            binding.vpTransaction.visibility = View.VISIBLE
            binding.includeLogin.linearNotLogin.visibility = View.GONE
        } else {
            binding.includeLogin.linearNotLogin.visibility = View.VISIBLE
            binding.vpTransaction.visibility = View.GONE
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window:Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login_not_login -> {
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
            }
            R.id.iv_action_activity_forum -> {
                startActivity(Intent(requireActivity(), ActivityForumActivity::class.java))
            }
        }
    }

    override fun messageGetSearch(msg: String) {
        Timber.d(msg)
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun getDataSearch(data: DataSearch?) {
        val bundle = Bundle()
            bundle.putParcelable("dataSubforum", data)
            childFragmentManager.let {
                BottomSheetSubforum.instanceBottomSheetSubforum(bundle).apply {
                    show(it, tag)
                }
            }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}