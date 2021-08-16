package com.example.sunrin205.Calendar

import android.content.ContentValues.TAG
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReturnDate {
    private val date = Calendar.getInstance().time
    private val cal = Calendar.getInstance()

    fun returnTodayYear(): Int{
        return SimpleDateFormat("yyyy", Locale.KOREA).format(date).toInt()
    }

    fun returnTodayMonth(): Int{
        return SimpleDateFormat("M", Locale.KOREA).format(date).toInt()
    }

    fun returnTodayWeekendDay(): Int{
        return SimpleDateFormat("d", Locale.KOREA).format(date).toInt()
    }

    fun returnMonthMaxDay(month: Int): Int{
        cal.add(Calendar.DAY_OF_MONTH, month-1)
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun returnTodayWeekendFirstDayLastDay(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()

        //first day of week

        //first day of week
        cal[Calendar.DAY_OF_WEEK] = 1

        val year1 = cal[Calendar.YEAR]
        val month1 = cal[Calendar.MONTH] + 1
        val day1 = cal[Calendar.DAY_OF_MONTH]
        list.add("${year1}${month1}${day1}")

        //last day of week

        //last day of week
        cal[Calendar.DAY_OF_WEEK] = 7

        val year7 = cal[Calendar.YEAR]
        val month7 = cal[Calendar.MONTH] + 1
        val day7 = cal[Calendar.DAY_OF_MONTH]
        list.add("${year7}${month7}${day7}")

        return list
    }

    fun getDateWeekendDay(funDate : String) : String{


        Log.d(TAG, "getDateWeekendDay: date : $date")
        
        val formatter = SimpleDateFormat("yyyyMMdd")

        val date = formatter.parse(funDate)

        val cal = Calendar.getInstance()
        cal.time = date


        val dayNum = cal[Calendar.DAY_OF_WEEK]

        Log.d(TAG, "getDateWeekendDay: dayNum : $dayNum")


        var convertedString = ""

        when (dayNum) {
            2 -> convertedString = "mon"
            3 -> convertedString = "tues"
            4 -> convertedString = "wednes"
            5 -> convertedString = "thurs"
            6 -> convertedString = "fri"
        }

        return convertedString
    }



}