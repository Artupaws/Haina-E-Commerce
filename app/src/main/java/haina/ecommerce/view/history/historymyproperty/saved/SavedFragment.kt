package haina.ecommerce.view.history.historymyproperty.saved

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.property.AdapterPropertySaved
import haina.ecommerce.databinding.FragmentSavedBinding
import haina.ecommerce.model.property.DataShowProperty
import haina.ecommerce.room.roomsavedproperty.DataSavedProperty
import haina.ecommerce.room.roomsavedproperty.RoomDataSavedProperty
import haina.ecommerce.room.roomsavedproperty.SavedPropertyDao
import java.util.*


class SavedFragment : Fragment(), AdapterPropertySaved.ItemAdapterCallback, SavedPropertyContract.View {

    private lateinit var _binding:FragmentSavedBinding
    private val binding get() = _binding
    private lateinit var dao: SavedPropertyDao
    private lateinit var database: RoomDataSavedProperty
    private lateinit var listPhotoProperty: ArrayList<DataSavedProperty>
    private var progressDialog:Dialog? = null
    private lateinit var presenter:SavedPropertyPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        database = RoomDataSavedProperty.getDatabase(requireActivity())
        dao = database.getDataPropertyDao()
        presenter = SavedPropertyPresenter(this, requireActivity())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getListDataProperty(database, dao)
        dialogLoading()
    }

    private fun getListDataProperty(database: RoomDataSavedProperty, dao: SavedPropertyDao) {
        listPhotoProperty = arrayListOf()
        listPhotoProperty.addAll(dao.getAll())
        if (listPhotoProperty.size < 1){
            binding.rvProperty.visibility = View.GONE
        } else {
            binding.rvProperty.visibility = View.VISIBLE
            setupListDataPassenger(listPhotoProperty)
        }
    }

    private fun setupListDataPassenger(listPhoto:List<DataSavedProperty>){
        val adapterParams = AdapterPropertySaved(requireActivity(), listPhoto, this@SavedFragment)
        binding.rvProperty.adapter?.notifyDataSetChanged()
        binding.rvProperty.apply {
            adapter = adapterParams
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        getListDataProperty(database, dao)
    }

    override fun onClickAdapterCity(view: View, data: DataSavedProperty) {
        when(view.id){
            R.id.linear_list -> {
                presenter.getShowProperty(data.id)
            }
        }
    }

    private fun dialogLoading(){
        progressDialog = Dialog(requireActivity())
        progressDialog?.setContentView(R.layout.dialog_loader)
        progressDialog?.setCancelable(false)
        progressDialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), android.R.color.white))
        val window: Window = progressDialog?.window!!
        window.setGravity(Gravity.CENTER)
    }

    override fun messageGetListProperty(msg: String) {
        Log.d("viewProperty", msg)
    }

    override fun getDataProperty(data: DataShowProperty?) {
        if (data?.title != ""){
            val intentDetail = Intent(requireActivity(), DetailMyPropertySavedActivity::class.java)
            intentDetail.putExtra("dataProperty", data)
            startActivity(intentDetail)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }

}