package haina.ecommerce.view.restaurant.detail.review.filter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haina.ecommerce.R
import haina.ecommerce.adapter.restaurant.AdapterRestaurantFilter
import haina.ecommerce.adapter.vacancy.AdapterDataCreateVacancy
import haina.ecommerce.databinding.FragmentBottomsheetPaxHotelBinding
import haina.ecommerce.databinding.FragmentJobFilterBinding
import haina.ecommerce.databinding.FragmentRestaurantFilterBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.restaurant.master.CuisineAndTypeData
import haina.ecommerce.model.vacancy.VacancySpecialistItem
import haina.ecommerce.view.hotels.selection.BottomSheetHotelFragment
import haina.ecommerce.view.job.filter.BottomSheetFilterPresenter
import timber.log.Timber

class RestaurantReviewFilter(val callback:Callback) : BottomSheetDialogFragment(), AdapterRestaurantFilter.Callback {

    private lateinit var _binding: FragmentRestaurantFilterBinding
    private val binding get() = _binding
    private var broadcaster: LocalBroadcastManager? = null


    private val helper: Helper = Helper

    private var minRating:Int = 0
    private var idCuisine:String? = null
    private var idType:String? = null
    private var idCity:Int? = null

    private var popupFilter: Dialog? = null


    interface Callback {
        fun passFilterData(cuisineName:String?, typeName:String?)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantFilterBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDialog()

        binding.toolbarRestaurantSelection.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarRestaurantSelection.setNavigationOnClickListener { dismiss()}
        binding.toolbarRestaurantSelection.title = "Restaurant Filter"
        binding.rsliderRating.addOnChangeListener { _, value, _ ->
            minRating = value.toInt()
            binding.tvRating.text = "$value Stars or more"
        }

        binding.btnApply.setOnClickListener{
            callback.passFilterData(idCuisine,idType)
        }

        binding.tvCuisineType.setOnClickListener{
            AdapterRestaurantFilter.VIEW_TYPE = 1
            cuisineFilter()
            popupFilter!!.show()
        }

        binding.tvRestaurantType.setOnClickListener{
            AdapterRestaurantFilter.VIEW_TYPE = 2
            typeFilter()
            popupFilter!!.show()
        }

        binding.ivCancelCuisine.setOnClickListener {
            idCuisine = null
            binding.tvCuisineType.text = "Show All"
            binding.ivCancelCuisine.visibility = View.GONE
        }

        binding.ivCancelType.setOnClickListener {
            idType = null
            binding.tvRestaurantType.text = "Show All"
            binding.ivCancelType.visibility = View.GONE
        }
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
                (rvDestination?.adapter as AdapterRestaurantFilter).filter.filter(newText)
                (rvDestination?.adapter as AdapterRestaurantFilter).notifyDataSetChanged()
                return true
            }
        })
    }
    private fun cuisineFilter(){
        val searchView = popupFilter?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupFilter?.findViewById<TextView>(R.id.tv_title_popup)
        title?.text = "Cuisine Type"
        searchView?.queryHint = "Search Cuisine Here"
    }

    private fun typeFilter(){
        val searchView = popupFilter?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupFilter?.findViewById<TextView>(R.id.tv_title_popup)
        title?.text = "Restaurant Type"
        searchView?.queryHint = "Search Restaurant Type Here"
    }

    fun updateCuisine(data: List<CuisineAndTypeData?>?){
        adapterFilter.addCuisine(data)
    }

    fun updateType(data: List<CuisineAndTypeData?>?){
        adapterFilter.addType(data)
    }

    private val adapterFilter by lazy {
        AdapterRestaurantFilter(arrayListOf(), arrayListOf(),this)
    }

    override fun listCuisineClick(view: View, data: CuisineAndTypeData) {
        popupFilter!!.dismiss()
        idCuisine = data.name
        binding.tvCuisineType.text = data.name
        binding.ivCancelCuisine.visibility = View.VISIBLE
    }

    override fun listTypeClick(view: View, data: CuisineAndTypeData) {
        popupFilter!!.dismiss()
        idType = data.name
        binding.tvRestaurantType.text = data.name
        binding.ivCancelType.visibility = View.VISIBLE
    }

}