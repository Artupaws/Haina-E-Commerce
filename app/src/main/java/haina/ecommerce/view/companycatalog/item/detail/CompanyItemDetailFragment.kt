package haina.ecommerce.view.companycatalog.item.detail

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import haina.ecommerce.R
import haina.ecommerce.adapter.companycatalog.items.CompanyItemMediaAdapter
import haina.ecommerce.databinding.FragmentCompanyItemDetailBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.companycatalog.master.CompanyItem
import timber.log.Timber


class CompanyItemDetailFragment : Fragment(), CompanyItemDetailContract.View {

    private lateinit var _binding : FragmentCompanyItemDetailBinding
    private val binding get() = _binding

    private lateinit var presenter: CompanyItemDetailPresenter
    private var progressDialog: Dialog? = null
    private lateinit var ctx: Context
    private var helper: Helper = Helper

    private lateinit var dataItem:CompanyItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyItemDetailBinding.inflate(inflater, container, false)

        ctx = container!!.context
        presenter = CompanyItemDetailPresenter(this, ctx)

        dialogLoading()

        dataItem = arguments?.getParcelable("CompanyItem")!!

        presenter.getItemDetail(dataItem.id!!)


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

    override fun getItemDetail(data: CompanyItem?) {

        Glide.with(this).load(data!!.company!!.iconUrl).into(binding.ivCompanyLogo)

        binding.tvItemName.text = data.itemName
        binding.tvItemDesc.text = data.itemDescription
        binding.tvPrice.text = helper.convertToFormatMoneyIDR(data.itemPrice!!.toString())

        binding.tvCompanyName.text = data.company!!.name
        binding.tvItemCatalog.text = data.itemCatalog
        binding.tvItemCategory.text = data.itemCategory

        binding.ivCompanyLogo.setOnClickListener {
            findNavController().navigate(R.id.action_companyItemDetailFragment_to_companyOverviewFragment)
        }
        binding.tvCompanyName.setOnClickListener {
            findNavController().navigate(R.id.action_companyItemDetailFragment_to_companyOverviewFragment)
        }

        val adapter = CompanyItemMediaAdapter()

        binding.rvItemMedia.adapter = adapter
        binding.rvItemMedia.layoutManager = GridLayoutManager(context, 3)

        binding.btnCall.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:"+data.company.contactNumber)
            startActivity(callIntent)
        }
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.hide()
    }


}