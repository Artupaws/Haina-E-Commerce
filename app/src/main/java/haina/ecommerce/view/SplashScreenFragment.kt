package haina.ecommerce.view

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import haina.ecommerce.R

class SplashScreenFragment : Fragment() {

    lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        handler = Handler()
        handler.postDelayed({
            container?.let { Navigation.findNavController(it).navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment()) }
        }, 2000)

        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        handler = Handler()
//        handler.postDelayed({
//            Navigation.findNavController(view).navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToHomeFragment())
//        }, 2000)
//
//    }

}