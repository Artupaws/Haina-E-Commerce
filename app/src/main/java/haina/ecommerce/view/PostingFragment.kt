package haina.ecommerce.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterSelling
import haina.ecommerce.databinding.FragmentPostingBinding
import haina.ecommerce.model.Selling
import haina.ecommerce.preference.SharedPreferenceHelper
import haina.ecommerce.util.Constants
import haina.ecommerce.view.login.LoginActivity
import haina.ecommerce.view.postingjob.PostingJobActivity

class PostingFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPostingBinding? = null
    private val binding get() = _binding!!
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
    lateinit var sharedPref: SharedPreferenceHelper
    private var clicked = false
    private var broadcaster: LocalBroadcastManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPostingBinding.inflate(inflater, container, false)
        sharedPref = SharedPreferenceHelper(requireContext())
        broadcaster = LocalBroadcastManager.getInstance(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener(this)
        binding.floatingActionButton2.setOnClickListener(this)
        binding.floatingActionButton3.setOnClickListener(this)
        binding.includeLogin.btnLogin.setOnClickListener(this)

        val listSelling = arrayListOf<Selling>()

        val sellingAdapter = AdapterSelling(requireContext(), listSelling)
        binding.rvPost.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = sellingAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getValueBoolien(Constants.PREF_IS_LOGIN)) {
            binding.includeLogin.linearNotLogin.visibility = View.GONE
            binding.rvPost.visibility = View.VISIBLE
            binding.floatingActionButton.visibility = View.VISIBLE
        }

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
                val intent = Intent(activity, PostingJobActivity::class.java)
                startActivity(intent)
            }

            R.id.floatingActionButton2 -> {
//                val intent = Intent(activity, PostingJobActivity::class.java)
//                startActivity(intent)
            }

            R.id.btn_login ->{
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

}