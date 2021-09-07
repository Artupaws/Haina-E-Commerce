package haina.ecommerce.view.history.historytransaction.unfinish

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterTransactionPulsaUnfinish
import haina.ecommerce.adapter.historytransaction.AdapterTransactionJobUnfinish
import haina.ecommerce.databinding.FragmentTransactionUnfinishBinding
import haina.ecommerce.model.transactionlist.DataTransaction
import haina.ecommerce.model.transactionlist.PendingItem
import haina.ecommerce.model.transactionlist.PendingJobItem
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.history.historytransaction.HistoryTransactionContract
import haina.ecommerce.view.history.historytransaction.TransactionUnfinishPresenter
import haina.ecommerce.view.howtopayment.BottomSheetHowToPayment
import haina.ecommerce.view.login.LoginActivity
import timber.log.Timber
import java.util.ArrayList

class FragmentTransactionUnfinish : Fragment(), View.OnClickListener,
    BottomSheetHowToPayment.ItemClickListener,
    AdapterTransactionPulsaUnfinish.ItemAdapterCallback,
    AdapterTransactionJobUnfinish.ItemAdapterCallback,
HistoryTransactionContract.TransactionUnfinishContract.View{

    private var _binding:FragmentTransactionUnfinishBinding? = null
    private val binding get()= _binding
    private var broadcaster:LocalBroadcastManager? = null
    private lateinit var sharedPref:SharedPreferenceHelper
    private var statusLogin = false
    private var progressDialog : Dialog? = null
    private lateinit var presenter : TransactionUnfinishPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTransactionUnfinishBinding.inflate(inflater, container, false)
        broadcaster = LocalBroadcastManager.getInstance(requireContext())
        sharedPref = SharedPreferenceHelper(requireContext())
        presenter = TransactionUnfinishPresenter(this, requireContext())
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusLogin = sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)
        dialogLoading()
//        showNotLogin(statusLogin)
//        binding?.includeNotLogin?.btnLoginNotLogin?.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceive, IntentFilter("ListTransaction"))
    }

    private val mMessageReceive :BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "ListTransaction" -> {
//                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    val dataTransactionUnfinish = intent.getParcelableExtra<DataTransaction>("Transaction")
                    setupListUnfinishTransaction(dataTransactionUnfinish?.pending)
                    setupListUnfinishTransactionJob(dataTransactionUnfinish?.pendingJob)
                    showIsEmpty(dataTransactionUnfinish?.pending?.let { dataTransactionUnfinish.pendingJob?.size?.plus(it.size) })
                }
            }
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), android.R.color.white))
        val window:Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    private fun setupListUnfinishTransaction(data:List<PendingItem?>?){
//        showIsEmpty(data?.size)
        adapterTransactionUnfinish.clear()
        AdapterTransactionPulsaUnfinish.VIEW_TYPE = 1
        adapterTransactionUnfinish.addTransactionPulsaPending(data)
        binding?.rvUnfinishTransaction?.adapter = adapterTransactionUnfinish
    }

    private fun setupListUnfinishTransactionJob(dataJob:List<PendingJobItem?>?){
//        showIsEmpty(dataJob?.size)
        adapterTransactionUnfinishJob.clear()
        AdapterTransactionPulsaUnfinish.VIEW_TYPE = 2
        adapterTransactionUnfinishJob.addTransactionJobPending(dataJob)
        binding?.rvUnfinishTransactionJob?.adapter = adapterTransactionUnfinishJob
    }

    private val adapterTransactionUnfinish by lazy {
        AdapterTransactionPulsaUnfinish(requireActivity(), arrayListOf(), this)
    }

    private val adapterTransactionUnfinishJob by lazy {
        AdapterTransactionJobUnfinish(requireActivity(), arrayListOf(), this)
    }

//    private fun showNotLogin(statusLogin:Boolean){
//        if (!statusLogin){
//            binding?.includeNotLogin?.linearNotLogin?.visibility = View.VISIBLE
//        } else {
//            binding?.includeNotLogin?.linearNotLogin?.visibility = View.GONE
//        }
//    }
//
    private fun showIsEmpty(listItem:Int?){
        if (listItem == 0){
            binding?.rvUnfinishTransaction?.visibility = View.GONE
            binding?.includeEmpty?.linearEmpty?.visibility = View.VISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceive)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_login_not_login -> {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onItemClick(item: Int) {
//        when(item){
//            "" ->{
//            }
//        }
    }

    override fun onClickAdapter(view: View, data: PendingItem?, adapterPosition:Int, listTransaction:ArrayList<PendingItem?>?) {
        when(view.id){
            R.id.tv_option_menu -> {
                val popup = PopupMenu(requireContext(), view)
                popup.inflate(R.menu.menu_cancel_transaction)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_cancel_transaction -> {
                            presenter.cancelTransaction(data!!.id!!, null)
                            listTransaction?.removeAt(adapterPosition)
                            adapterTransactionUnfinish.notifyItemRemoved(adapterPosition)
                            true
                        } else -> false
                    }
                }
                popup.show()
            }
            R.id.btn_how_pay -> {
                val bundle = Bundle()
                bundle.putParcelable("data", data)
                childFragmentManager.let {
                    BottomSheetHowToPayment.newInstance(bundle).apply {
                        show(it, "data")
                    }
                }
            }
//            R.id.relative_click -> {
//                val intent = Intent(context, DetailTransactionActivity::class.java)
//                    .putExtra("transaction", "finish")
//                    .putExtra("dataFinish", data)
//                startActivity(intent)
//            }
        }
    }

    override fun onTransactionJobClick(view: View, data: PendingJobItem?, adapterPosition:Int, listTransaction:ArrayList<PendingJobItem?>?) {
        when (view.id) {
            R.id.tv_option_menu -> {
                val popup = PopupMenu(requireContext(), view)
                popup.inflate(R.menu.menu_cancel_transaction)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_cancel_transaction -> {
                            presenter.cancelTransaction(null, data?.idJob)
                            listTransaction?.removeAt(adapterPosition)
                            adapterTransactionUnfinishJob.notifyItemRemoved(adapterPosition)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }

    override fun messageCancelTransaction(msg: String) {
        Timber.d(msg)
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}