package haina.ecommerce.view.companycatalog.catalog.filter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.items.AdapterCompanyCatalogItemsFilter
import haina.ecommerce.adapter.restaurant.AdapterRestaurantFilter
import haina.ecommerce.databinding.FragmentCompanyItemSearchFilterBinding
import haina.ecommerce.model.companycatalog.master.CompanyItemCategory
import timber.log.Timber

class CompanyCatalogItemsFilterFragment(val ctx:Context,var category: CompanyItemCategory,var selectedSort:String?, val callback:Callback): BottomSheetDialogFragment(),CompanyCatalogItemsFilterContract,AdapterCompanyCatalogItemsFilter.Callback {

    private lateinit var _binding: FragmentCompanyItemSearchFilterBinding
    private val binding get() = _binding
    private var sortBy:String? = "name"
    private var sort:String? = "asc"

    private lateinit var presenter: CompanyCatalogItemsFilterPresenter
    private var popupFilter: Dialog? = null

    private val adapterFilter by lazy {
        AdapterCompanyCatalogItemsFilter(arrayListOf(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyItemSearchFilterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = CompanyCatalogItemsFilterPresenter(this,ctx)
        presenter.getCompanyItemCategory()

        binding.toolbarItemSelection.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarItemSelection.setNavigationOnClickListener { dismiss() }
        binding.toolbarItemSelection.title = getString(R.string.item_filter)

        binding.tvCategoryFilter.text = category.name
        when(selectedSort){
            "nameasc"->{
                binding.rbNameAsc.isChecked=true
            }
            "namedesc"->{
                binding.rbNameDesc.isChecked=true
            }
            "priceasc"->{
                binding.rbPriceAsc.isChecked=true
            }
            "pricedesc"->{
                binding.rbPriceDesc.isChecked=true
            }
            "timeasc"->{
                binding.rbTimeAsc.isChecked=true
            }
            "timedesc"->{
                binding.rbTimeDesc.isChecked=true
            }
        }

        binding.btnApply.setOnClickListener{
            when {
                binding.rbNameAsc.isChecked -> {
                    sortBy = "name"
                    sort = "asc"
                }
                binding.rbNameDesc.isChecked -> {
                    sortBy = "name"
                    sort = "desc"
                }
                binding.rbPriceAsc.isChecked -> {
                    sortBy = "price"
                    sort = "asc"
                }
                binding.rbPriceDesc.isChecked -> {
                    sortBy = "price"
                    sort = "desc"
                }
                binding.rbTimeAsc.isChecked -> {
                    sortBy = "time"
                    sort = "asc"
                }
                binding.rbTimeDesc.isChecked -> {
                    sortBy = "time"
                    sort = "desc"
                }

            }

            callback.setFilter(category,sortBy,sort)
        }
        initDialog()
        binding.tvCategoryFilter.setOnClickListener{
            popupFilter!!.show()
        }
    }

    interface Callback{
        fun setFilter(category:CompanyItemCategory,sortBy:String?,sort:String?)
    }

    override fun message(msg: String) {
        Timber.d(msg)
    }

    override fun getCompanyCategoryList(data: List<CompanyItemCategory?>?) {
        adapterFilter.add(data)

    }
    @SuppressLint("SetTextI18n")
    private fun initDialog() {
        popupFilter = Dialog(requireContext())
        popupFilter?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupFilter?.setCancelable(true)
        popupFilter?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupFilter?.window!!
        window.setGravity(Gravity.CENTER)

        val actionClose = popupFilter?.findViewById<ImageView>(R.id.iv_close)
        actionClose?.setOnClickListener { popupFilter?.dismiss() }

        val rvDestination = popupFilter?.findViewById<RecyclerView>(R.id.rv_destination)
        rvDestination?.adapter = adapterFilter

        val searchView = popupFilter?.findViewById<SearchView>(R.id.sv_destination)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (rvDestination?.adapter as AdapterCompanyCatalogItemsFilter).filter.filter(newText)
                (rvDestination?.adapter as AdapterCompanyCatalogItemsFilter).notifyDataSetChanged()
                return true
            }
        })
    }

    override fun CategoryClick(data: CompanyItemCategory) {
        category = data
        binding.tvCategoryFilter.text = data.name
        popupFilter!!.hide()
    }


}