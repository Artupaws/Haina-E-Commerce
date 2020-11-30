package haina.ecommerce.view.register

interface RegisterContract {

    interface Presenter{
        fun loadRegister()
    }

    interface View{
        fun registerUser()
    }

}