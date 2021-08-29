package haina.ecommerce.view.posting.newvacancy

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.vacancy.AdapterDataCreateVacancy
import haina.ecommerce.databinding.ActivitySkillAndEducationBinding
import haina.ecommerce.model.vacancy.DataCreateVacancy
import haina.ecommerce.model.vacancy.RequestCreateVacancy
import haina.ecommerce.model.vacancy.VacancyEducationItem
import haina.ecommerce.model.vacancy.VacancySkillItem
import timber.log.Timber
import java.util.ArrayList

class  SkillAndEducationActivity : AppCompatActivity(), View.OnClickListener, AdapterDataCreateVacancy.AdapterCallbackSkillEdu {

    private lateinit var binding:ActivitySkillAndEducationBinding
    private var request:RequestCreateVacancy? = null
    private var listSkills=ArrayList<VacancySkillItem>()
    private var listSkillId=ArrayList<String>()
    private var popupDialogSkills:Dialog? = null
    private var popupDialogLastEdu:Dialog? = null
    private lateinit var dataCreateVacancy:DataCreateVacancy
    private var idEdu:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkillAndEducationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnNext.setOnClickListener(this)
        binding.btnAddSkills.setOnClickListener(this)
        binding.etLastEducation.setOnClickListener(this)

        binding.toolbarSkillEducation.setNavigationOnClickListener {
            onBackPressed()
        }

        dataCreateVacancy = intent.getParcelableExtra("dataCreateVacancy")!!
        request = intent.getParcelableExtra("dataRequest")
        Timber.d(dataCreateVacancy.vacancySkill?.size.toString())
        popupDialogExperience(dataCreateVacancy.vacancySkill)
        popupDialogLastEducation(dataCreateVacancy.vacancyEducation)
    }

    private val adapterSkills by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, null, arrayListOf(), null,
            null, null, null, null, this, null, null)
    }

    private val adapterLastEdu by lazy {
        AdapterDataCreateVacancy(applicationContext, null, arrayListOf(), null, null, null,
            null, null, null, null, this, null, null)
    }

    private val adapterSkillChoosed by lazy {
        AdapterDataCreateVacancy(applicationContext, null, null, null, null, null,
            null, null, null, null, this, null, arrayListOf())
    }

    private fun popupDialogExperience(data: List<VacancySkillItem?>?) {
        adapterSkills.clear()
        adapterSkills.addVacancySkills(data)
        popupDialogSkills = Dialog(this)
        popupDialogSkills?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDialogSkills?.setCancelable(true)
        popupDialogSkills?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDialogSkills?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDialogSkills?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDialogSkills?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupDialogSkills?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupDialogSkills?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupDialogSkills?.dismiss() }
        title?.text = "Skills"
        searchView?.queryHint = "Search Skill Here"
        rvDestination?.adapter = adapterSkills
    }

    private fun popupDialogLastEducation(data: List<VacancyEducationItem?>?) {
        adapterLastEdu.clear()
        adapterLastEdu.addVacancyEdu(data)
        popupDialogLastEdu = Dialog(this)
        popupDialogLastEdu?.setContentView(R.layout.layout_popup_dialog_destination_flight)
        popupDialogLastEdu?.setCancelable(true)
        popupDialogLastEdu?.window?.setBackgroundDrawableResource(R.color.white)
        val window: Window = popupDialogLastEdu?.window!!
        window.setGravity(Gravity.CENTER)
        val actionClose = popupDialogLastEdu?.findViewById<ImageView>(R.id.iv_close)
        val rvDestination = popupDialogLastEdu?.findViewById<RecyclerView>(R.id.rv_destination)
        val searchView = popupDialogLastEdu?.findViewById<SearchView>(R.id.sv_destination)
        val title = popupDialogLastEdu?.findViewById<TextView>(R.id.tv_title_popup)
        actionClose?.setOnClickListener { popupDialogLastEdu?.dismiss() }
        title?.text = "Last Education"
        searchView?.visibility = View.GONE
        rvDestination?.adapter = adapterLastEdu
    }

    private fun checkDataCreateVacancy(){
        val isEmptySkills:Boolean
        val isEmptyLastEducation:Boolean

        var skills = listSkills
        var lastEdu = binding.etLastEducation.text.toString()

        if (skills.size == 0){
            isEmptySkills = true
            binding.tvTitleSkills.error = getString(R.string.cant_empty)
        } else {
            isEmptySkills = false
            binding.tvTitleSkills.error = null
            skills = listSkills
        }

        if (lastEdu.isNullOrEmpty()){
            isEmptyLastEducation = true
            binding.tvTitleLastEducation.error = getString(R.string.cant_empty)
        } else {
            isEmptyLastEducation = false
            binding.tvTitleLastEducation.error = null
            lastEdu = binding.etLastEducation.text.toString()
        }

        if (!isEmptySkills && !isEmptyLastEducation){
            val separator = ", "
            val stringIdSkill = listSkillId.joinToString(separator)
            request = RequestCreateVacancy(this.request!!.position,
                this.request!!.idCompany, this.request!!.idSpecialist, this.request!!.level, this.request!!.type, this.request!!.description,
            this.request!!.experience, idEdu, this.request!!.minSalary, this.request!!.maxSalary, this.request!!.salaryDisplay,
            this.request!!.address, this.request!!.idCity, null, null, stringIdSkill)
            startActivity(Intent(applicationContext, PackagePriceVacancyActivity::class.java)
                .putExtra("dataRequest", request)
                .putExtra("dataCreateVacancy", dataCreateVacancy))
            Timber.d(stringIdSkill)
        } else {
            Toast.makeText(applicationContext, "Please complete the form", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_next -> {
                checkDataCreateVacancy()
            }
            R.id.btn_add_skills -> {
                AdapterDataCreateVacancy.VIEW_TYPE = 6
                popupDialogSkills?.show()
            }
            R.id.et_last_education -> {
                AdapterDataCreateVacancy.VIEW_TYPE = 7
                popupDialogLastEdu?.show()
            }
        }
    }

    override fun listSkillsClick(view: View, data: VacancySkillItem) {
        when(view.id){
            R.id.relative_click -> {
                listSkills.add(data)
                listSkillId.add(data.id.toString())
                popupDialogSkills?.dismiss()
                Timber.d(listSkills.toString())
                AdapterDataCreateVacancy.VIEW_TYPE = 8
                adapterSkillChoosed.clear()
                adapterSkillChoosed.addVacancySkillChoosed(listSkills)
                binding.rvSkillChoose.adapter = adapterSkillChoosed
            }
        }
    }

    override fun listEduClick(view: View, data: VacancyEducationItem) {
        when(view.id){
            R.id.relative_click -> {
                idEdu = data.id
                binding.etLastEducation.setText(data.name)
                popupDialogLastEdu?.dismiss()
            }
        }
    }

    override fun listChipsSkillClick(view: View, data: VacancySkillItem) {
        when(view.id){
            R.id.chip -> {
                listSkills.remove(data)
                AdapterDataCreateVacancy.VIEW_TYPE = 8
                adapterSkillChoosed.clear()
                adapterSkillChoosed.addVacancySkillChoosed(listSkills)
                binding.rvSkillChoose.adapter = adapterSkillChoosed
            }
        }
    }

}