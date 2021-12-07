package haina.ecommerce.view.companycatalog.catalog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.CompanyDashboardAdapter
import haina.ecommerce.adapter.companycatalog.items.CompanyCatalogItemsAdapter
import haina.ecommerce.databinding.FragmentCompanyCatalogItemsBinding
import haina.ecommerce.model.companycatalog.master.CompanyItem
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import haina.ecommerce.model.restaurant.master.RestaurantData
import haina.ecommerce.view.companycatalog.dashboard.CompanyDashboardPresenter
import timber.log.Timber


class CompanyCatalogItemsFragment : Fragment(), CompanyCatalogItemsAdapter.ItemAdapterCallback, CompanyCatalogItemsContract.View {

    private lateinit var _binding: FragmentCompanyCatalogItemsBinding
    private val binding get() = _binding

    private lateinit var presenter: CompanyCatalogItemsPresenter
    private var progressDialog: Dialog? = null
    private lateinit var ctx: Context

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

        val companyItemCategory = arguments?.getParcelable<CompanyItemCategory>("CompanyItemCategory")!!

        dialogLoading()

        binding.rvItemList.adapter = adapterCompanyItem

        binding.tvAddItems.setOnClickListener {
            findNavController().navigate(R.id.action_companyCatalogItemsFragment_to_companyAddItemFragment)
        }

        presenter.getCompanyItem(companyItemCategory.id!!)

        return binding.root
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

    override fun getCompanyItemList(data: List<CompanyItem?>?) {
        adapterCompanyItem.add(data)
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.hide()
    }

    override fun itemOnClick(data: CompanyItem) {
        val bundle = Bundle()
        bundle.putParcelable("CompanyItem",data)

        findNavController().navigate(R.id.action_companyCatalogItemsFragment_to_companyItemDetailFragment,bundle)
    }


}