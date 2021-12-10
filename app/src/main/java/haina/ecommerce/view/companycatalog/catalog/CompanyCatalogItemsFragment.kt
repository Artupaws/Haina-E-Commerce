package haina.ecommerce.view.companycatalog.catalog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.CompanyDashboardAdapter
import haina.ecommerce.adapter.companycatalog.items.CompanyCatalogItemsAdapter
import haina.ecommerce.databinding.FragmentCompanyCatalogItemsBinding
import haina.ecommerce.model.companycatalog.master.CompanyItem
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import haina.ecommerce.model.companycatalog.master.PaginationCompanyItem
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.view.companycatalog.catalog.filter.CompanyCatalogItemsFilterFragment
import haina.ecommerce.view.companycatalog.dashboard.CompanyDashboardPresenter
import haina.ecommerce.view.job.filter.BottomSheetFilterFragment
import timber.log.Timber


class CompanyCatalogItemsFragment : Fragment(), CompanyCatalogItemsAdapter.ItemAdapterCallback, CompanyCatalogItemsContract.View,CompanyCatalogItemsFilterFragment.Callback {

    private lateinit var _binding: FragmentCompanyCatalogItemsBinding
    private val binding get() = _binding

    private lateinit var presenter: CompanyCatalogItemsPresenter
    private var progressDialog: Dialog? = null
    private lateinit var ctx: Context

    private var page:Int = 1
    private var totalPage:Int= 0

    private var filterSheet: CompanyCatalogItemsFilterFragment? = null

    private lateinit var companyItemCategory:CompanyItemCategory
    private var sortBy:String? = null
    private var sort:String? = null

    private val adapterCompanyItem by lazy {
        CompanyCatalogItemsAdapter(requireActivity(), arrayListOf(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyCatalogItemsBinding.inflate(inflater, container, false)
        ctx = container!!.context
        presenter = CompanyCatalogItemsPresenter(this, ctx)

        companyItemCategory = arguments?.getParcelable<CompanyItemCategory>("CompanyItemCategory")!!

        dialogLoading()

        binding.rvItemList.adapter = adapterCompanyItem

        binding.tvAddItems.setOnClickListener {
            findNavController().navigate(R.id.action_companyCatalogItemsFragment_to_companyAddItemFragment)
        }

        presenter.getCompanyItem(page,companyItemCategory.id!!,sortBy,sort)

        refresh()

        binding.scrollview.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener {
                v, _, _, _, _ ->
            if(!v.canScrollVertically(1)){
                if(page!=totalPage){
                    page++
                    presenter.getCompanyItem(page,companyItemCategory.id!!,sortBy,sort)
                }
                else{
                    binding.ivLoading.visibility = View.GONE
                }

            }
        })

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnFilter.setOnClickListener {
            filterSheet= CompanyCatalogItemsFilterFragment(ctx, companyItemCategory,sortBy+sort, this@CompanyCatalogItemsFragment)
            filterSheet!!.show(parentFragmentManager, filterSheet!!.tag)
        }


        return binding.root
    }

    private fun refresh(){
        binding.swipeRefresh.setOnRefreshListener {
            binding.ivLoading.visibility = View.VISIBLE
            adapterCompanyItem.clear()
            page = 1
            presenter.getCompanyItem(page,companyItemCategory.id!!,sortBy,sort)
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(),android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }


    override fun message(msg: String) {
        Timber.d(msg)
    }

    override fun getCompanyItemList(data: PaginationCompanyItem?) {
        binding.swipeRefresh.isRefreshing = false
        adapterCompanyItem.add(data!!.items)
        totalPage = data.totalPage!!
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun itemOnClick(data: CompanyItem) {
        val bundle = Bundle()
        bundle.putParcelable("CompanyItem",data)

        findNavController().navigate(R.id.action_companyCatalogItemsFragment_to_companyItemDetailFragment,bundle)
    }

    override fun setFilter(category: CompanyItemCategory, sortBy: String?, sort: String?) {
        this.companyItemCategory = category
        this.sortBy = sortBy
        this.sort = sort

        Timber.d("$sortBy$sort")
        binding.ivLoading.visibility = View.VISIBLE
        adapterCompanyItem.clear()
        page = 1
        presenter.getCompanyItem(page,companyItemCategory.id!!, sortBy, sort)

        filterSheet?.dismiss()
    }


}