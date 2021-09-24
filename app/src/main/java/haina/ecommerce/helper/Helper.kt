package haina.ecommerce.helper

import android.annotation.SuppressLint
import android.text.TextWatcher
import haina.ecommerce.helper.Helper.convertLongtoTime
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

    fun convertToFormatMoneyNoCode(formatMoney: String?): String? {
        val formatter: NumberFormat = NumberFormat.getNumberInstance()
        formatter.maximumFractionDigits = 0
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

    fun formatDate(date:String):String{
        val day = date.substring(8,10)
        var month = date.substring(5,7)
        val year = date.substring(0,4)
        when (month) {
            "01" -> {
                month = "JAN"
            }
            "02" -> {
                month = "FEB"
            }
            "03" -> {
                month = "MAR"
            }
            "04" -> {
                month = "APR"
            }
            "05" -> {
                month = "MAY"
            }
            "06" -> {
                month = "JUN"
            }
            "07" -> {
                month = "JUL"
            }
            "08" -> {
                month = "AUG"
            }
            "09" -> {
                month = "SEP"
            }
            "10" -> {
                month = "OCT"
            }
            "11" -> {
                month = "NOV"
            }
            "12" -> {
                month = "DEC"
            }
        }
        return "${year}-${month}-${day}"
    }

    fun dateFormat(date: String?):String {
        return if (date.isNullOrEmpty())""
        else {
            val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000000Z'", Locale.getDefault())
            val dateParse = currentFormat.parse( date )
            val toFormat = SimpleDateFormat("MMM, dd yyyy", Locale.getDefault())
            toFormat.format(dateParse!!)
        }
    }

    fun getTimeAgo(date: String?):String{
        return if (date.isNullOrEmpty())""
        else {
            val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000000Z'", Locale.getDefault())
            val past = currentFormat.parse( date )
            val now = Date()
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            if(hours>24){
                val day = TimeUnit.MILLISECONDS.toDays(now.time - past.time)
                return day.toString()+"d"

            }

            return hours.toString()+"h"
        }
    }

    fun dateTimeFormat(date: String?):String {
        return if (date.isNullOrEmpty())""
        else {
            val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000000Z'", Locale.getDefault())
            val dateParse = currentFormat.parse( date )
            val toFormat = SimpleDateFormat("HH:mm  d MMM yyyy", Locale.getDefault())
            toFormat.format(dateParse!!)
        }
    }

    fun dateFormatWorkExperience(date: String?):String {
        return if (date.isNullOrEmpty())""
        else {
            val currentFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dateParse = currentFormat.parse( date )
            val toFormat = SimpleDateFormat("MMM, dd yyyy", Locale.getDefault())
            toFormat.format(dateParse!!)
        }
    }

    fun dateFormatHotelDarma(date: String?):String {
        return if (date.isNullOrEmpty())""
        else {
            val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val dateParse = currentFormat.parse( date )
            val toFormat = SimpleDateFormat("MMM, dd yyyy", Locale.getDefault())
            toFormat.format(dateParse!!)
        }
    }
//    fun addPassenger(totalPassenger:Int, maxPassenger:Int){
//        if (totalPassenger < maxPassenger){
//        }
//    }
}