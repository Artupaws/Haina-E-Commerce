package haina.ecommerce.view.property.fragmentshowproperty

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.property.AdapterShowProperty
import haina.ecommerce.databinding.FragmentShowPropertyBinding
import haina.ecommerce.model.property.DataShowProperty
import haina.ecommerce.view.property.ShowPropertyActivity

class ShowPropertyFragment : Fragment(), ShowPropertyContract.View, View.OnClickListener, AdapterShowProperty.ItemAdapterCallback {

    private lateinit var _binding:FragmentShowPropertyBinding
    private val binding get() = _binding
    private lateinit var presenter: ShowPropertyPresenter
    private var progressDialog:Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShowPropertyBinding.inflate(inflater, container, false)
        presenter = ShowPropertyPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialogLoading()
        binding.fabCreatePost.setOnClickListener(this)
        presenter.getShowProperty()
        refresh()
        binding.toolbarShowProperty.setNavigationOnClickListener {
            (requireActivity() as ShowPropertyActivity).onBackPressed()
        }

        binding.rvProperty.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0){
                    binding.fabCreatePost.visibility = View.GONE
                } else if(dy < 0){
                    binding.fabCreatePost.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            presenter.getShowProperty()
        }
    }

    override fun messageGetListProperty(msg: String) {
        Log.d("showProperty", msg)
        binding.swipeRefresh.isRefreshing = false
        if (!msg.contains("Property loaded successfully!")){
            Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun messageAddViews(msg: String) {
        Log.d("addViewProperty",msg)
    }

    override fun getDataProperty(data: List<DataShowProperty?>?) {
        binding.rvProperty.apply {
            adapter = AdapterShowProperty(requireActivity(), data, this@ShowPropertyFragment, true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fab_create_post -> {
                Navigation.findNavController(binding.fabCreatePost).navigate(R.id.action_showPropertyFragment_to_inputDataPropertyFragment2)
            }
        }
    }

    override fun onClickAdapterCity(view: View, data: DataShowProperty) {
        when(view.id){
            R.id.cv_click -> {
                presenter.addViews(data.id!!)
                val bundle=Bundle()
                bundle.putParcelable("dataProperty", data)
                Navigation.findNavController(binding.root).navigate(R.id.action_showPropertyFragment_to_detailPropertyFragment, bundle)
            }
        }
    }

}