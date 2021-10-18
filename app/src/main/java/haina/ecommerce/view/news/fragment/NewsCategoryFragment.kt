package haina.ecommerce.view.news.fragment

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
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.forum.AdapterCategoryForum
import haina.ecommerce.adapter.forum.AdapterListAllThreads
import haina.ecommerce.adapter.forum.AdapterListHotPost
import haina.ecommerce.model.forum.*
import haina.ecommerce.view.forum.createnewpost.NewPostActivity
import haina.ecommerce.view.forum.createsubforum.CreateSubforumActivity
import haina.ecommerce.view.forum.detailforum.DetailForumActivity
import timber.log.Timber
import java.util.ArrayList
import haina.ecommerce.adapter.forum.AdapterDetailImage
import haina.ecommerce.adapter.news.AdapterNews
import haina.ecommerce.databinding.FragmentNewsCategoryBinding
import haina.ecommerce.databinding.FragmentShowForumBinding
import haina.ecommerce.model.news.DataNewsTable
import haina.ecommerce.model.news.NewsCategory
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.news.DetailNewsWebViewActivity
import haina.ecommerce.view.news.NewsPresenter

class NewsCategoryFragment(val news:NewsCategory) : Fragment(), NewsCategoryContract, AdapterNews.ItemAdapterCallback{

    private lateinit var _binding:FragmentNewsCategoryBinding
    private val binding get() = _binding
    private var progressDialog:Dialog? = null
    private lateinit var presenter: NewsCategoryPresenter
    private var broadcaster:LocalBroadcastManager? = null
    private var popupCreatePost:Dialog?=null
    private var listCategory:List<DataCategoryForum?>? = null
    private lateinit var sharedPref: SharedPreferenceHelper
    private var langParams: String = ""

    private var page:Int = 1
    private var totalPage:Int= 0
    private val positionCollaps: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_collapse)
    }

    private val positionExpand: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.anim_expand)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNewsCategoryBinding.inflate(inflater, container, false)
        presenter = NewsCategoryPresenter(requireContext(), this)
        sharedPref = SharedPreferenceHelper(requireContext())
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        langParams = sharedPref.getValueString(Constants.LANGUAGE_APP).toString()
        setPresenterBasedLanguage(langParams)
        refresh(langParams)

        binding.scrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener {
                v, _, _, _, _ ->
            if(!v.canScrollVertically(1)){
                page++

                Timber.d("last")
            }
        })
    }

    private fun setPresenterBasedLanguage(language: String) {
        when (language) {
            "en" -> {
                presenter.getNewsTableWithCategory(page,"eng",news.id!!)
            }
            "zh" -> {
                presenter.getNewsTableWithCategory(page,"zho",news.id!!)
            }
            else -> {
                presenter.getNewsTableWithCategory(page,"eng",news.id!!)
            }
        }
    }


    private fun refresh(language: String) {
        binding.swipeRefresh.setOnRefreshListener {
            when (language) {
                "en" -> {
                    presenter.getNewsTableWithCategory(page,"eng",news.id!!)
                }
                "zh" -> {
                    presenter.getNewsTableWithCategory(page,"zho",news.id!!)
                }
                else -> {
                    presenter.getNewsTableWithCategory(page,"eng",news.id!!)
                }
            }
        }
    }


    override fun onClick(view: View, data: DataNewsTable) {
        when (view.id) {
            R.id.linear_list -> {
                val intentToWeb = Intent(requireContext(), DetailNewsWebViewActivity::class.java)
                    .putExtra("data", data)
                startActivity(intentToWeb)
            }
        }
    }

    override fun messageGetNews(msg: String) {
    }

    override fun getNews(data: List<DataNewsTable?>?) {
        binding.rvNews.apply {
            adapter = AdapterNews(requireContext(), data, this@NewsCategoryFragment)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

}