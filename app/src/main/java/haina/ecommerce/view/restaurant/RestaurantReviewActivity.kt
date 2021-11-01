package haina.ecommerce.view.restaurant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import haina.ecommerce.R
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import haina.ecommerce.adapter.restaurant.navigation.KeepStateNavigator
import haina.ecommerce.databinding.ActivityRestaurantReviewBinding
import haina.ecommerce.databinding.ActivityShowPropertyBinding

class RestaurantReviewActivity:AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantReviewBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

}