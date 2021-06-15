package haina.ecommerce.view.flight.detailfilldata

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AutoTextCompleteViewAdapter
import haina.ecommerce.databinding.FragmentDetailFillDataPassengerBinding
import haina.ecommerce.model.flight.DataNationality
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.roomdatapassenger.DataPassenger
import haina.ecommerce.roomdatapassenger.PassengerDao
import haina.ecommerce.roomdatapassenger.RoomDataPassenger
import java.util.*

class DetailFillDataPassengerFragment : Fragment(), View.OnClickListener, DetailFillDataContract, AutoTextCompleteViewAdapter.ItemAdapterCallback {

    private lateinit var _binding:FragmentDetailFillDataPassengerBinding
    private val binding get()= _binding
    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var database: RoomDataPassenger
    private lateinit var dao:PassengerDao
    private var dateSetListener: DatePickerDialog.OnDateSetListener? = null
    private var age:Int = 0
    private lateinit var presenter: DetailFillDataPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailFillDataPassengerBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireActivity())
        database = RoomDataPassenger.getDatabase(requireActivity())
        presenter = DetailFillDataPresenter(this, requireActivity())
        dao = database.getDataPassengerDao()
        binding.btnSave.setOnClickListener(this)
        binding.etBirthdate.setOnClickListener(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbarDetailFillDataPassenger.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        presenter.getListCountry()
        setTextBirthDate()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_save -> {
                checkData()
            }
            R.id.et_birthdate -> {
                setDatePicker()
            }
        }
    }

    private fun checkData(){
        var fullname = binding.etFullname.text.toString()
        var birthdate = binding.etBirthdate.text.toString()
        var idCardNumber:String? = binding.etIdCard.text.toString()
        var isEmptyFullname = false
        var isEmptyBirthdate = false
        var isEmptyIdCard = false
        if (fullname.isNullOrEmpty()){
            binding.etFullname.error = "Can't Empty"
            isEmptyFullname = true
        } else {
            isEmptyFullname = false
            fullname = binding.etFullname.text.toString()
            binding.etFullname.error = null
        }

        if (birthdate.isNullOrEmpty()){
            isEmptyBirthdate = true
            binding.etBirthdate.error = "Can't Empty"
        } else {
            isEmptyBirthdate = false
            birthdate = binding.etBirthdate.text.toString()
            binding.etBirthdate.error = null
        }

        if (idCardNumber.isNullOrEmpty() && age > 16){
            binding.etIdCard.error = "Can't Empty"
            isEmptyIdCard = true
        } else if (idCardNumber.isNullOrEmpty() && age < 17){
            isEmptyIdCard = false
        } else {
            isEmptyIdCard = false
            idCardNumber = binding.etIdCard.text.toString()
        }

        if (!isEmptyFullname && !isEmptyBirthdate && !isEmptyIdCard){
            saveDataPassenger(DataPassenger(0,fullname,birthdate,idCardNumber))
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireActivity(), "Please fill in data corectly", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDataPassenger(datapassenger: DataPassenger){
        if (dao.getById(datapassenger.id).isEmpty()){
            dao.insert(datapassenger)
        }else{
            dao.update(datapassenger)
        }
    }

    private fun setDatePicker(){
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH)
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(requireActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,
                year, month, day)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun setTextBirthDate(){
        dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var month = month
            month += 1
            Log.d("date", "onDateSet: dd/mm/yyyy: $day-$month-$year")
            val date = "$day-$month-$year"
            binding.etBirthdate.setText(date)
            detectAge(year)
        }
    }

    private fun detectAge(year:Int){
        val ageParams = Calendar.getInstance().get(Calendar.YEAR)-year
        age = ageParams
        if (ageParams < 17){
            binding.linearIdCard.visibility = View.GONE
        } else {
            binding.linearIdCard.visibility = View.VISIBLE
        }
    }

    override fun messageGetCountryList(msg: String) {
        Log.d("getNationality", msg)
    }

    override fun getListCountry(data: DataNationality) {
        val adapterParams = AutoTextCompleteViewAdapter(requireActivity(), R.layout.layout_autotextcomplete, data.countries, this)
        binding.acNationality.setAdapter(adapterParams)
        binding.acNationality.text
    }

    override fun onAdapterClick(view: View, data: String?) {
        when(view.id){
            R.id.tv_country -> {
                Toast.makeText(requireActivity(), data, Toast.LENGTH_SHORT).show()
            }
        }
    }

}