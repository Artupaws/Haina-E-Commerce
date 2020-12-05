package haina.ecommerce.view.base

import androidx.fragment.app.FragmentActivity

open class BaseContract {

    interface Presenter<in T> {
//        fun subscribe()
//        fun unsubscribe()
        fun attach(view: FragmentActivity?)
    }

    interface View {

    }
}