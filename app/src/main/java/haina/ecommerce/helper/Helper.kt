package haina.ecommerce.helper

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class Helper  {


    //
    fun convertToFormatMoneyUSD(formatMoney: String): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 1
        formatter.currency = Currency.getInstance("USD")
        return formatter.format(formatMoney.toDouble())
    }

    fun convertToFormatMoneyIDR(formatMoney: String): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 0
        formatter.currency = Currency.getInstance("IDR")
        return formatter.format(formatMoney.toDouble())
    }

    fun convertToFormatMoneyCNY(formatMoney: String): String? {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 1
        formatter.currency = Currency.getInstance("CNY")
        return formatter.format(formatMoney.toDouble())
    }
}