package haina.ecommerce.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import haina.ecommerce.R
import haina.ecommerce.adapter.AdapterNews
import haina.ecommerce.model.News
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.layout_menu_service.*

class ExploreFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listNews = arrayListOf(
                News(title = "Trump Continues to Postpone Transition, Biden: Many People Will Die","CNBC Indonesia", R.drawable.biden),
                News(title = "Whatsapp Change Archive Feature, from Holiday Mode to Read Later","CNN", R.drawable.whatsapp),
                News(title = "How to Play PS5 Games on PS4","CNN", R.drawable.ps5),
                News(title = "Gold Price Today November 17th, Down to IDR 980 Thousand per Gram","CNN", R.drawable.gold),
                News(title = "Pakistan is planning to turn Bollywood legend's house into a museum","CNN", R.drawable.pakistan),
                News(title = "Erdogan Visits Northern Cyprus, Greece and Cyprus Furious","CNN", R.drawable.erdogan),
                News(title = "Recognizing the Meaning of the 'Prestigious List' of UNESCO Biosphere Reserves","CNN", R.drawable.unesco)
        )

        val newsAdapter = AdapterNews(listNews)
        rv_news.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }

        linear_other.setOnClickListener(this)

    }

    companion object{
        fun newInstance(): ExploreFragment{
            val fragment = ExploreFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.linear_other ->{
                Navigation.findNavController(p0).navigate(HomeFragmentDirections.actionHomeFragmentToOtherFragment())
            }
        }
    }
}