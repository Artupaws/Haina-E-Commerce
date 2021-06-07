package haina.ecommerce.helper

import android.annotation.SuppressLint
import haina.ecommerce.helper.Helper.convertLongtoTime
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun convertToFormatMoneyUSD(formatMoney: String): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 2
        formatter.currency = Currency.getInstance("USD")
        return formatter.format(formatMoney.toDouble())
    }

    fun convertToFormatMoneyIDR(formatMoney: String): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 1
        formatter.currency = Currency.getInstance("IDR")
        return formatter.format(formatMoney.toDouble())
    }

    fun convertToFormatMoneyCNY(formatMoney: String): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 2
        formatter.currency = Currency.getInstance("CNY")
        return formatter.format(formatMoney.toDouble())
    }

    fun convertToFormatMoneyIDRFilter(formatMoney: String?): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 0
        formatter.currency = Currency.getInstance("IDR")
        return formatter.format(formatMoney?.toDouble())
    }

    fun convertToFormatMoneySalary(formatMoney: String): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 0
        formatter.currency = Currency.getInstance("IDR")
        return formatter.format(formatMoney.toDouble())
    }

    fun changeFormatMoneyToValue(formatRupiah: String): String {
        val xRupiah = formatRupiah.substring(0)
        val doubleRupiah = xRupiah.substring(0)
        val valueRupiah = doubleRupiah.replace("[-+.^:,]".toRegex(), "")
        return valueRupiah.replace(" ".toRegex(), "")
    }

    fun changeMoneyToValue(formatRupiah: String): String {
        val a = formatRupiah.substring(0, 3).replace("IDR", formatRupiah.substring(3, formatRupiah.length))
        val xRupiah = a.substring(0)
        val doubleRupiah = xRupiah.substring(0)
        val valueRupiah = doubleRupiah.replace("[-+.^:,]".toRegex(), "")
        return valueRupiah.replace(" ".toRegex(), "")
    }

    fun getOnlyDateFromStringDate(date: String): String {
        return date.substring(9, 10)
    }

    fun formatPhoneNumber(number:String):String{
        var phoneNumber = number.replace("[-+.^:,]".toRegex(), "")
        if (phoneNumber.substring(0, 3) == "62 " && number.length == 14) {
            phoneNumber = number.substring(0, 3).replace("62 ", "0${number.substring(3, 14)}")
        }
        if (phoneNumber.substring(0, 3) == "62 " && number.length == 15){
            phoneNumber = number.substring(0, 3).replace("62 ", "0${number.substring(3, 15)}")
        }
        if (phoneNumber.substring(0, 3) == "62 " && number.length == 13){
            phoneNumber = number.substring(0, 3).replace("62 ", "0${number.substring(3, 13)}")
        }
        return phoneNumber
    }

    fun changeFormatMoneyToValueFilter(formatRupiah: String?): String? {
        val xRupiah = formatRupiah?.substring(1)
        val doubleRupiah = xRupiah?.substring(2)
        val valueRupiah = doubleRupiah?.replace("[-+.^:,]".toRegex(), "")
        return valueRupiah?.replace(" ".toRegex(), "")
    }

    @SuppressLint("SimpleDateFormat")
    fun Long.convertLongtoTime(dateFormat:String) :String {
       val date = Date(this)
       val format = SimpleDateFormat(dateFormat)
       return format.format(date)
    }

//    fun addPassenger(totalPassenger:Int, maxPassenger:Int){
//        if (totalPassenger < maxPassenger){
//        }
//    }
}