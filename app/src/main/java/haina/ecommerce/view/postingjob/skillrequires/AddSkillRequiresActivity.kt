package haina.ecommerce.view.postingjob.skillrequires

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterAddSkillRequires
import haina.ecommerce.adapter.AdapterSkillsRequires
import haina.ecommerce.databinding.ActivityAddSkillRequiresBinding
import haina.ecommerce.databinding.ActivityAddSkillsBinding
import haina.ecommerce.model.DataMyJob
import haina.ecommerce.model.DataPostingJob
import haina.ecommerce.model.DataSkillRequires
import haina.ecommerce.view.MainActivity

class AddSkillRequiresActivity : AppCompatActivity(), View.OnClickListener, AddSkillRequiresContract {

    private lateinit var binding:ActivityAddSkillRequiresBinding
    private lateinit var presenter: AddSkillRequiresPresenter
    var isEmptyNameSkill = true
    var idJobVacancy:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSkillRequiresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idJobVacancy = intent.getIntExtra("idJobVacancy", 0)
        Log.d("idJobVacancy", idJobVacancy.toString())
        presenter = AddSkillRequiresPresenter(this, this)
        binding.toolbarAddSkillRequires.title = getString(R.string.requires_skill)
        binding.btnDone.setOnClickListener(this)
        binding.btnAddSkill.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_done -> {
                move()
            }
            R.id.btn_add_skill -> {
                checkAddSkill()
            }
        }
    }

    private fun checkAddSkill(){
        var nameSkill = binding.etSkills.text.toString()

        if (nameSkill.isEmpty()){
            isEmptyNameSkill = true
            binding.outlinedTextAddressCompany.error = getString(R.string.cant_empty)
        } else {
            nameSkill = binding.etSkills.text.toString()
            isEmptyNameSkill = false
        }

        if (!isEmptyNameSkill){
            presenter.addSkillRequires(nameSkill, idJobVacancy)
        } else {
            Toast.makeText(applicationContext, getString(R.string.add_skill_fail), Toast.LENGTH_SHORT).show()
        }

    }

    private fun move(){
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("loginStatus", "3")
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, getString(R.string.add_skill_back_pressed), Toast.LENGTH_LONG).show()
    }

    override fun messageAddSkillRequires(msg: String) {
        if (msg=="1"){
            Toast.makeText(applicationContext, getString(R.string.required_skills_added), Toast.LENGTH_SHORT).show()
            presenter.getSkillRequires(idJobVacancy)
            binding.etSkills.setText("")
        }
    }


    override fun getSkillRequires(item: List<DataSkillRequires?>?) {
        binding.rvSkills.apply {
            val adapterRequireSkill = AdapterAddSkillRequires(applicationContext, item)
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterRequireSkill
        }
    }

    override fun messageGetSkillRequires(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}