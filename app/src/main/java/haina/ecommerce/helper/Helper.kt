package haina.ecommerce.helper

import java.text.NumberFormat
import java.util.*

class Helper  {


    //
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

    fun changeFormatMoneyToValue(formatRupiah: String?): String? {
        val xRupiah = formatRupiah?.substring(1)
        val doubleRupiah = xRupiah?.substring(1)
        val valueRupiah = doubleRupiah?.replace("[-+.^:,]".toRegex(), "")
        return valueRupiah?.replace(" ".toRegex(), "")
    }

}