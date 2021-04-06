package haina.ecommerce.view.howtopayment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import haina.ecommerce.R
import haina.ecommerce.databinding.FragmentBottomSheetHowToPaymentBinding

class BottomSheetHowToPayment : BottomSheetDialogFragment() {

    private var _binding:FragmentBottomSheetHowToPaymentBinding? = null
    private val  binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBottomSheetHowToPaymentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.toolbarHowToPayment?.title = requireContext().getString(R.string.how_to_payment)
        binding?.toolbarHowToPayment?.setNavigationIcon(R.drawable.ic_close_black)
        binding?.toolbarHowToPayment?.setNavigationOnClickListener { dismiss() }
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

//    @SuppressLint("RestrictedApi")
//    override fun setupDialog(dialog: Dialog, style: Int) {
//        super.setupDialog(dialog, style)
//        val rootView = View.inflate(context, R.layout.fragment_bottom_sheet_how_to_payment, null)
//        dialog.setContentView(rootView)
//
//        val bottomSheet = dialog.window?.findViewById(R.id.design_bottom_sheet) as FrameLayout
//        val behaviour = BottomSheetBehavior.from(bottomSheet)
//
//        behaviour.peekHeight = 0
//    }

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

}