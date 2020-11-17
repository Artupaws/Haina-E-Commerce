package haina.ecommerce.helper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

@SuppressLint("AppCompatCustomView")

class FontAwesome : TextView {
    constructor(context: Context?) : super(context) {
        initBrands()
        initSolid()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initBrands()
        initSolid()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initBrands()
        initSolid()
    }

    private fun initBrands() {
        if (!isInEditMode) {
            val tf = Typeface.createFromAsset(context.assets,
                    "font/FontAwesomeBrands.otf")
            typeface = tf
        }
    }

    private fun initSolid() {
        if (!isInEditMode) {
            val tf = Typeface.createFromAsset(context.assets,
                    "font/FontAwesomeSolid.otf")
            typeface = tf
        }
    }
}