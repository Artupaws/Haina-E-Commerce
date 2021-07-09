package haina.ecommerce.view.posting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import haina.ecommerce.R
import haina.ecommerce.adapter.TabAdapterPosting
import haina.ecommerce.databinding.FragmentPostingBinding
import haina.ecommerce.model.DataCompany
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.categorypost.CategoryActivity
import haina.ecommerce.view.postingjob.datajob.PostingJobActivity
import haina.ecommerce.view.register.company.RegisterCompanyActivity

class PostingFragment : Fragment(), View.OnClickListener, PostingContract {

    private var _binding: FragmentPostingBinding? = null
    private lateinit var presenter: PostingPresenter
    private val binding get() = _binding!!
    private var popupCheckDataCompany: AlertDialog? = null
    lateinit var sharedPref: SharedPreferenceHelper
    private val rotatePostIconOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
                activity,
                R.anim.rotate_icon_post
        )
    }
    private val rotatePostIconClose: Animation by lazy {
        AnimationUtils.loadAnimation(
                activity,
                R.anim.rotate_close_post
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
                activity,
                R.anim.fab_from_bottom
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
                activity,
                R.anim.fab_to_bottom
        )
    }
    private var clicked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPostingBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireContext())
        presenter = PostingPresenter(this, requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener(this)
        binding.floatingActionButton2.setOnClickListener(this)
        binding.floatingActionButton3.setOnClickListener(this)
        binding.viewPagerPosting.adapter = TabAdapterPosting(requireActivity().supportFragmentManager, 0)
        binding.tabLayout.setupWithViewPager(binding.viewPagerPosting)
        showPopup()
    }

    private fun onAddPostClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.floatingActionButton.startAnimation(rotatePostIconOpen)
            binding.floatingActionButton2.startAnimation(fromBottom)
            binding.floatingActionButton3.startAnimation(fromBottom)
            binding.tvTitlePostingJob.startAnimation(fromBottom)
            binding.tvTitleJobApplication.startAnimation(fromBottom)
        } else {
            binding.floatingActionButton.startAnimation(rotatePostIconClose)
            binding.floatingActionButton2.startAnimation(toBottom)
            binding.floatingActionButton3.startAnimation(toBottom)
            binding.tvTitlePostingJob.startAnimation(toBottom)
            binding.tvTitleJobApplication.startAnimation(toBottom)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.floatingActionButton2.visibility = View.VISIBLE
            binding.floatingActionButton3.visibility = View.VISIBLE
            binding.tvTitlePostingJob.visibility = View.VISIBLE
            binding.tvTitleJobApplication.visibility = View.VISIBLE
        } else {
            binding.floatingActionButton2.visibility = View.INVISIBLE
            binding.floatingActionButton3.visibility = View.INVISIBLE
            binding.tvTitlePostingJob.visibility = View.INVISIBLE
            binding.tvTitleJobApplication.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.floatingActionButton -> {
                onAddPostClicked()
            }

            R.id.floatingActionButton3 -> {
                presenter.checkRegisterCompany()
            }

            R.id.floatingActionButton2 -> {
                val intentToCategory = Intent(requireActivity(), CategoryActivity::class.java)
                startActivity(intentToCategory)
            }
        }
    }

    private fun showPopup() {
        val popup = AlertDialog.Builder(requireContext())
        val view: View = layoutInflater.inflate(R.layout.popup_check_register_company, null)
        popup.setCancelable(true)
        popup.setView(view)
        val actionCancel = view.findViewById<TextView>(haina.ecommerce.R.id.tv_action_cancel)
        val actionYes = view.findViewById<TextView>(haina.ecommerce.R.id.tv_action_yes)
        popupCheckDataCompany = popup.create()
        popupCheckDataCompany?.dismiss()
        actionCancel.setOnClickListener { popupCheckDataCompany?.dismiss() }
        actionYes.setOnClickListener {
            val intent = Intent(activity, RegisterCompanyActivity::class.java)
            activity?.startActivity(intent)
            popupCheckDataCompany?.dismiss()
        }
    }

    override fun checkRegisterCompanyTrue(msg: String, item:DataCompany?) {
        if (msg == "Company Registered" && sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            val intent = Intent(activity, PostingJobActivity::class.java)
            startActivity(intent)
        } else if (msg == "Company Unregistered" && sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            popupCheckDataCompany?.show()
        } else {
            Toast.makeText(requireContext(), "Please Login First", Toast.LENGTH_SHORT).show()
        }
    }

    override fun checkRegisterCompanyFalse(msg: String) {
        Log.d("anjayani", msg)
    }

}