package haina.ecommerce.view.flight.detailfilldata

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import haina.ecommerce.R
import haina.ecommerce.adapter.flight.AutoCompleteAdapter
import haina.ecommerce.databinding.FragmentDetailFillDataPassengerBinding
import haina.ecommerce.model.flight.CountriesItem
import haina.ecommerce.model.flight.DataNationality
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.roomdatapassenger.DataPassenger
import haina.ecommerce.roomdatapassenger.PassengerDao
import haina.ecommerce.roomdatapassenger.RoomDataPassenger
import java.util.*


class DetailFillDataPassengerFragment : Fragment(), View.OnClickListener, DetailFillDataContract,
    AutoCompleteAdapter.ItemAdapterCallback {

    private lateinit var _binding:FragmentDetailFillDataPassengerBinding
    private val binding get()= _binding
    private lateinit var sharedPref:SharedPreferenceHelper
    private lateinit var database: RoomDataPassenger
    private lateinit var dao:PassengerDao
    private var dateSetListener: DatePickerDialog.OnDateSetListener? = null
    private var age:Int = 0
    private lateinit var presenter: DetailFillDataPresenter
    private var typePassenger:String? = null
    private var genderRadio:String? = null
    private var titleRadio:String? = null
    private var totalPassenger:Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailFillDataPassengerBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireActivity())
        database = RoomDataPassenger.getDatabase(requireActivity())
        presenter = DetailFillDataPresenter(this, requireActivity())
        dao = database.getDataPassengerDao()
        binding.btnSave.setOnClickListener(this)
        binding.etBirthdate.setOnClickListener(this)
        radioGroup()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.toolbarDetailFillDataPassenger.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        val totalPassengerParams = arguments?.getInt("totalPassenger")
        totalPassenger = totalPassengerParams!!
        checkFirstDataPassenger(totalPassengerParams)
        presenter.getListCountry()
        binding.acNationality.threshold = 1
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

    private fun checkFirstDataPassenger(total:Int){
        if (total == 0){
            binding.tvWarningInputDataPassenger.visibility = View.VISIBLE
        } else {
            binding.tvWarningInputDataPassenger.visibility = View.GONE
        }
    }

    private fun checkData(){
        var firstname = binding.etFirstname.text.toString()
        var lastname = binding.etLastName.text.toString()
        var birthdate = binding.etBirthdate.text.toString()
        var gender = genderRadio
        var title = titleRadio
        var nationality = binding.acNationality.text.toString()
        var birthCountry = binding.acBirthNationality.text.toString()
        var idCardNumber:String? = binding.etIdCard.text.toString()
        var typePassengerParams = typePassenger
        var isEmptyType = false
        var isEmptFirstName = false
        var isEmptLastName = false
        var isEmptyBirthdate = false
        var isEmptyGender = false
        var isEmptyNationality = false
        var isEmptyBirthCountry = false
        var isEmptyTitle = false
        var isEmptyIdCard = false
        if (firstname.isNullOrEmpty()){
            binding.etFirstname.error = "Can't Empty"
            isEmptFirstName = true
        } else {
            isEmptFirstName = false
            firstname = binding.etFirstname.text.toString()
            binding.etFirstname.error = null
        }

        if (lastname.isNullOrEmpty()){
            binding.etLastName.error = "Can't Empty"
            isEmptLastName = true
        } else {
            isEmptLastName = false
            lastname = binding.etLastName.text.toString()
            binding.etLastName.error = null
        }

        if (birthdate.isNullOrEmpty()){
            isEmptyBirthdate = true
            isEmptyType = true
            binding.etBirthdate.error = "Can't Empty"
        } else {
            isEmptyBirthdate = false
            isEmptyType = false
            birthdate = binding.etBirthdate.text.toString()
            typePassengerParams = typePassenger
            binding.etBirthdate.error = null
        }

        if (gender == null){
            binding.rbFemale.error = ""
            isEmptyGender = true
        } else {
            gender = genderRadio
            isEmptyGender = false
        }

        if (birthCountry.isNullOrEmpty()){
            isEmptyBirthCountry = true
            binding.acBirthNationality.error = ""
        } else {
            birthCountry = binding.acBirthNationality.text.toString()
            isEmptyBirthCountry = false
            binding.acBirthNationality.error = null
        }

        if (nationality.isNullOrEmpty()){
            isEmptyNationality = true
            binding.acNationality.error = ""
        } else {
            nationality = binding.acNationality.text.toString()
            isEmptyNationality = false
            binding.acNationality.error = null
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

        if (title == null){
            binding.rbMstr.error = ""
            isEmptyTitle = true
        } else {
            title = titleRadio
            isEmptyTitle = false
        }

        if (!isEmptFirstName && !isEmptLastName && !isEmptyBirthdate && !isEmptyGender && !isEmptyNationality &&
            !isEmptyBirthCountry && !isEmptyIdCard && !isEmptyTitle && !isEmptyType){
            saveDataPassenger(DataPassenger(0,firstname, lastname, birthdate, gender!!, nationality, birthCountry, idCardNumber, title!!,
            "", "", null, null, null, typePassengerParams!!))
            findNavController().navigateUp()
        }else if (age < 17){
            Toast.makeText(requireActivity(), "Please input data adult passenger first", Toast.LENGTH_SHORT).show()
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

    private fun radioGroup(){
        binding.rdGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener {
                radioGroup, i ->
            val radio : RadioButton = requireActivity().findViewById(i)
            radioButtonTitle(radio)
        })

        binding.rdGroupGender.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener {
                radioGroup, i ->
            val radio : RadioButton = requireActivity().findViewById(i)
            radioButtonGender(radio)

        })
    }

    private fun radioButtonTitle(view: View){
        val radio: RadioButton = requireActivity().findViewById(binding.rdGroup.checkedRadioButtonId)
        titleRadio = radio.text.toString()
        if(titleRadio!!.isNotEmpty()){
            binding.rbMr.error = null
            binding.rbMrs.error = null
            binding.rbMiss.error = null
            binding.rbMstr.error = null
        }
    }

    private fun radioButtonGender(view: View){
        val radio: RadioButton = requireActivity().findViewById(binding.rdGroupGender.checkedRadioButtonId)
        genderRadio = radio.text.toString()
        if(genderRadio!!.isNotEmpty()){
            binding.rbMale.error = null
            binding.rbFemale.error = null
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
            val date = "$year-$month-$day"
            binding.etBirthdate.setText(date)
            detectAge(year)
        }
    }

    private fun detectAge(year:Int){
        val ageParams = Calendar.getInstance().get(Calendar.YEAR)-year
        age = ageParams
        when {
            ageParams < 17 -> {
                typePassenger = "Child"
                binding.linearIdCard.visibility = View.GONE
            }
            ageParams < 5 -> {
                typePassenger = "Infant"
                binding.linearIdCard.visibility = View.GONE
            }
            else -> {
                typePassenger = "Adult"
                binding.linearIdCard.visibility = View.VISIBLE
            }
        }
    }

    override fun messageGetCountryList(msg: String) {
        Log.d("getNationality", msg)
    }

    override fun getListCountry(data: DataNationality) {
        val arrayAdapter = AutoCompleteAdapter(requireActivity(),android.R.layout.simple_expandable_list_item_1, data.countries, this)
        binding.acNationality.setAdapter(arrayAdapter)
        binding.acBirthNationality.setAdapter(arrayAdapter)
    }

    override fun onAdapterClick(view: View, data: CountriesItem) {
        when(view.id){
            android.R.id.text1 -> {
                if (binding.acNationality.isFocusable){
                    binding.acNationality.setText(data.countryName.toString())
                    binding.acNationality.dismissDropDown()
                    binding.acNationality.isFocusable = false
                } else if (binding.acBirthNationality.isFocusable){
                    binding.acBirthNationality.setText(data.countryName.toString())
                    binding.acBirthNationality.dismissDropDown()
                    binding.acBirthNationality.isFocusable = false
                }

            }
        }
    }

}