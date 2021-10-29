package haina.ecommerce.view.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import haina.ecommerce.databinding.ActivityRestaurantReviewBinding
import haina.ecommerce.databinding.ActivityShowPropertyBinding

class RestaurantReviewActivity:AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}