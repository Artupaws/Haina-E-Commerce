package haina.ecommerce.view.howtopayment

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import haina.ecommerce.R
import haina.ecommerce.adapter.howtopay.AdapterHowToPayment
import haina.ecommerce.databinding.FragmentBottomSheetHowToPaymentBinding
import haina.ecommerce.helper.Helper
import haina.ecommerce.model.howtopay.Data
import haina.ecommerce.model.transactionlist.PendingItem

class BottomSheetHowToPayment : BottomSheetDialogFragment(), HowToPayContract {

    private var _binding: FragmentBottomSheetHowToPaymentBinding? = null
    private val binding get() = _binding
    private lateinit var presenter: HowToPayPresenter
    private val helper:Helper = Helper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetHowToPaymentBinding.inflate(inflater, container, false)
        presenter = HowToPayPresenter(this, requireActivity())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbarHowToPayment?.title = requireContext().getString(R.string.how_to_payment)
        binding?.toolbarHowToPayment?.setNavigationIcon(R.drawable.ic_close_black)
        binding?.toolbarHowToPayment?.setNavigationOnClickListener { dismiss() }
        val data = arguments?.get("data")
        data as PendingItem
        Log.d("bottomSheet", data.payment!!.vaNumber)
        presenter.getHowToPay(data.payment.idPaymentMethod!!)
        binding?.tvTotalPayment?.text = helper.convertToFormatMoneyIDRFilter(data.totalPayment.toString())
        binding?.tvNumberAccount?.text = data.payment.vaNumber
    }

    private var mListener: ItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            mListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface ItemClickListener {
        fun onItemClick(item: String)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BottomSheetHowToPayment {
            val fragment = BottomSheetHowToPayment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun messageGetHowToPay(msg: String) {
        Log.d("howToPay", msg)
    }

    override fun getDataHowToPay(data: Data?) {
        val adapterHowToPay = AdapterHowToPayment(requireActivity(), data?.instructions)
        binding?.rvVirtualAccount?.apply {
            adapter = adapterHowToPay
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }
}