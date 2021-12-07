package haina.ecommerce.view.companycatalog.dashboard

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.CompanyDashboardAdapter
import haina.ecommerce.databinding.FragmentCompanyDashboardBinding
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import timber.log.Timber

class CompanyDashboardFragment :
    Fragment(),
    CompanyDashboardContract.View,
    CompanyDashboardAdapter.ItemAdapterCallback {

    private lateinit var _binding: FragmentCompanyDashboardBinding
    private val binding get() = _binding

    private lateinit var presenter: CompanyDashboardPresenter
    private var progressDialog: Dialog? = null
    private lateinit var ctx: Context


    private val adapterCompanyItemCategory by lazy {
        CompanyDashboardAdapter(requireActivity(), arrayListOf(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyDashboardBinding.inflate(inflater, container, false)
        ctx = container!!.context
        presenter = CompanyDashboardPresenter(this, ctx)

        dialogLoading()

        binding.rvCompanyCategories.adapter = adapterCompanyItemCategory


        presenter.getCompanyItemCategory()
        adapterCompanyItemCategory.clear()

        binding.cardView6.setOnClickListener {
            findNavController().navigate(R.id.action_companyDashboardFragment_to_companyGlobalSearchFragment)
        }

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

    override fun getCompanyCategoryList(data: List<CompanyItemCategory?>?) {
        adapterCompanyItemCategory.add(data)
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.hide()
    }

    override fun categoryOnClick(data: CompanyItemCategory) {
        val bundle = Bundle()
        bundle.putParcelable("CompanyItemCategory",data)

        findNavController().navigate(R.id.action_companyDashboardFragment_to_companyCatalogItemsFragment,bundle)
    }
}