package haina.ecommerce.adapter.restaurant.menu

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.igreenwood.loupe.Loupe
import com.igreenwood.loupe.extensions.createLoupe
import com.igreenwood.loupe.extensions.setOnViewTranslateListener
import haina.ecommerce.R
import haina.ecommerce.databinding.LayoutDetailImageForumBinding
import haina.ecommerce.databinding.ListItemDetailImageBinding
import haina.ecommerce.model.restaurant.master.MenuImage

class AdapterMenuImageDetail: AppCompatActivity() {

    private lateinit var binding: LayoutDetailImageForumBinding

    private val images:  List<MenuImage> by lazy { intent.getParcelableArrayListExtra<MenuImage>("image_list") as List<MenuImage>}
    private val initialPos: Int by lazy { intent.getIntExtra("position", 0) }
    private var adapter: ImageAdapter? = null

    companion object {
        fun createIntent(context: Context, urls: List<MenuImage?>, initialPos: Int): Intent {
            return Intent(context, AdapterMenuImageDetail::class.java).apply {
                putParcelableArrayListExtra("image_list", urls as ArrayList<MenuImage?>)
                putExtra("position", initialPos)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LayoutDetailImageForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

        initToolbar()

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            title = ""
        }
    }

    private fun showToolbar() {
        binding.toolbar.animate()
            .setInterpolator(AccelerateDecelerateInterpolator())
            .translationY(0f)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()

        return true
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, R.anim.fade_out_fast)

    }

    private fun hideToolbar() {
        binding.toolbar.animate()
            .setInterpolator(AccelerateDecelerateInterpolator())
            .translationY(-binding.toolbar.height.toFloat())

    }


    override fun onBackPressed() {
        adapter?.clear()
        super.onBackPressed()
    }

    override fun onDestroy() {
        adapter = null
        super.onDestroy()
    }

    private fun initViewPager() {
        adapter = ImageAdapter(this, images)
        binding.viewpager.adapter = adapter
        binding.viewpager.currentItem = initialPos
    }

    inner class ImageAdapter(var context: Context, var images: List<MenuImage?>) : PagerAdapter() {

        private var loupeMap = hashMapOf<Int, Loupe>()
        private var views = hashMapOf<Int, ImageView>()
        private var currentPos = 0

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val binding = ListItemDetailImageBinding.inflate(LayoutInflater.from(context))
            container.addView(binding.root)
            loadImage(binding.image, binding.container, position)
            views[position] = binding.image
            return binding.root
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
            super.setPrimaryItem(container, position, obj)
            this.currentPos = position
        }

        override fun getCount() = images.size

        private fun loadImage(image: ImageView, container: ViewGroup, position: Int) {

            // swipe to dismiss
            Glide.with(image.context).load(images[position]!!.url)
                .onlyRetrieveFromCache(true)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        val loupe = createLoupe(image, container) {
                            useFlingToDismissGesture = false
                            maxZoom = Loupe.DEFAULT_MAX_ZOOM

                            flingAnimationDuration = Loupe.DEFAULT_ANIM_DURATION
                            scaleAnimationDuration = Loupe.DEFAULT_ANIM_DURATION_LONG
                            overScaleAnimationDuration = Loupe.DEFAULT_ANIM_DURATION_LONG
                            overScrollAnimationDuration = Loupe.DEFAULT_ANIM_DURATION_LONG
                            dismissAnimationDuration = Loupe.DEFAULT_ANIM_DURATION
                            restoreAnimationDuration = Loupe.DEFAULT_ANIM_DURATION
                            viewDragFriction = Loupe.DEFAULT_VIEW_DRAG_FRICTION

                            setOnViewTranslateListener(
                                onStart = { hideToolbar() },
                                onRestore = { showToolbar() },
                                onDismiss = { finish() }
                            )
                        }

                        loupeMap[position] = loupe
                        if (position == initialPos) {
                            startPostponedEnterTransition()
                        }
                        return false
                    }

                }).into(image)

        }

        fun clear() {
            // clear refs
            loupeMap.forEach {
                val loupe = it.value
                // clear refs
                loupe.cleanup()
            }
            loupeMap.clear()
        }
    }
}