package haina.ecommerce.helper

import android.app.AlertDialog
import android.content.Context
import haina.ecommerce.R
import java.text.NumberFormat
import java.util.*

class Helper  {

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

    fun convertToFormatMoneyIDRFilter(formatMoney: String): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 0
        formatter.currency = Currency.getInstance("IDR")
        return formatter.format(formatMoney.toDouble())
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

    fun formatPhoneNumber(number:String):String{
        var newFormatNumber = number
        if (number.substring(0, 3) == "+62" && number.length == 14) {
            newFormatNumber = number.substring(0, 3).replace("+62", "0${number.substring(3, 14)}")
        }
        if (number.substring(0, 3) == "+62" && number.length == 15){
            newFormatNumber = number.substring(0, 3).replace("+62", "0${number.substring(3, 15)}")
        }
        if (number.substring(0, 3) == "+62" && number.length == 13){
            newFormatNumber = number.substring(0, 3).replace("+62", "0${number.substring(3, 13)}")
        }
        return newFormatNumber
    }

    fun changeFormatMoneyToValueFilter(formatRupiah: String?): String? {
        val xRupiah = formatRupiah?.substring(1)
        val doubleRupiah = xRupiah?.substring(2)
        val valueRupiah = doubleRupiah?.replace("[-+.^:,]".toRegex(), "")
        return valueRupiah?.replace(" ".toRegex(), "")
    }
}