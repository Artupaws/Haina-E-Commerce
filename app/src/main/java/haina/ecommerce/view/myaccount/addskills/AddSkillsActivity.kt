package haina.ecommerce.view.myaccount.addskills

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterSkillsUser
import haina.ecommerce.databinding.ActivityAddSkillsBinding
import haina.ecommerce.model.DataSkillsUser

class AddSkillsActivity : AppCompatActivity(), AddSkillsContract, View.OnClickListener {

    private lateinit var binding: ActivityAddSkillsBinding
    private lateinit var presenter: AddSkillPresenter
    private var isEmptySkills = true
    private var broadcaster: LocalBroadcastManager? = null
    var statusAddSkill: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSkillsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        broadcaster = LocalBroadcastManager.getInstance(this)
        presenter = AddSkillPresenter(this, this)
        presenter.showListSkill()

        binding.toolbarAddSkills.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarAddSkills.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarAddSkills.title = "Add Skills"
        binding.btnAddSkill.setOnClickListener(this)

        binding.etSkills.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    binding.outlinedTextAddressCompany.error = null
                    true
                }
            }

            override fun afterTextChanged(s: Editable?) {
                false
            }

        })

    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, IntentFilter("deleteSkill"))
    }

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "deleteSkill" -> {
                    val deleteSkill = intent.getStringExtra("nameSkill")
                    presenter.deleteSkills(deleteSkill!!)
                }
            }
        }

    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_add_skill -> {
                checkSkillsValue()
            }
        }
    }

    private fun checkSkillsValue() {
        var skillName = binding.etSkills.text.toString()

        if (skillName.isEmpty()) {
            isEmptySkills = true
            binding.outlinedTextAddressCompany.error = getString(R.string.cant_empty)
        } else {
            isEmptySkills = false
            skillName = binding.etSkills.text.toString()
        }

        if (!isEmptySkills) {
            presenter.addSkills(skillName)
        } else {
            Toast.makeText(applicationContext, getString(R.string.fill_data_alert), Toast.LENGTH_SHORT).show()
        }

    }

    private fun move() {
        onBackPressed()
    }

    override fun getListSkills(data: List<DataSkillsUser?>?) {
        val adapterDocument = AdapterSkillsUser(this, data)
        binding.rvSkills.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = adapterDocument
        }
    }

    override fun messageAddSkills(msg: String) {
        if (msg == "1") {
            move()
            presenter.showListSkill()
        } else {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
            binding.etSkills.setText("")
        }
    }

    override fun messageGetListSkills(msg: String) {
        Log.d("loadListSkill", msg)
    }

    override fun messageDeleteSkills(msg: String) {
        if (msg.contains("Success!")) {
            presenter.showListSkill()
            Toast.makeText(applicationContext, getString(R.string.delete_skill_success), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, getString(R.string.delete_skill_fail), Toast.LENGTH_SHORT).show()
        }
    }

}