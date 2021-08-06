package com.example.sunrin205.screen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sunrin205.*
import com.example.sunrin205.Calendar.ReturnDate
import com.example.sunrin205.data.LunchAndDate
import com.example.sunrin205.data.lunchData.lunchData
import com.example.sunrin205.databinding.ActivityMainBinding
import com.example.sunrin205.school.School
import com.example.sunrin205.screen.main1.LunchAndDateAdapter
import com.example.sunrin205.screen.main1.LunchAndScheduleFragment
import com.example.sunrin205.screen.main2.TimeTableFragment
import com.example.sunrin205.screen.main3.SeatFragment
import com.example.sunrin205.screen.main4.SettingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.go.neis.api.Menu
import kr.go.neis.api.Schedule
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var vM : MainViewModel
    private val date = Calendar.getInstance().time
    private val mainFragment1 by lazy { LunchAndScheduleFragment() }
    private val mainFragment2 by lazy { TimeTableFragment() }
    private val mainFragment3 by lazy { SeatFragment() }
    private val mainFragment4 by lazy { SettingFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.frame, mainFragment2).commit()
        binding.bottomView.selectedItemId = R.id.schedule
        vM = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.bottomView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.lunchAndDate ->{
                    changeFragment(mainFragment1)
                    true
                }
                R.id.schedule ->{
                    changeFragment(mainFragment2)
                    true
                }
                R.id.seat ->{
                    changeFragment(mainFragment3)
                    true
                }
                R.id.setting ->{
                    changeFragment(mainFragment4)
                    true
                }
                else->{
                    changeFragment(mainFragment2)
                    true
                }
            }
        }
        val fdld = ReturnDate().returnTodayWeekendFirstDayLastDay()
        Log.d(TAG, "onCreate: 마지막날 오늘날$fdld")
        getLunchList()
    }

    private fun getLunchList() {
        val school = kr.go.neis.api.School(
            kr.go.neis.api.School.Type.HIGH,
            kr.go.neis.api.School.Region.SEOUL,
            "B100000658"
        )
        var menu : ArrayList<Menu> = ArrayList()
        var schoolDate : ArrayList<Schedule> = ArrayList()

        GlobalScope.launch(Dispatchers.IO) {
            val year = SimpleDateFormat("yyyy", Locale.KOREA).format(date).toInt()
            val month = SimpleDateFormat("M", Locale.KOREA).format(date).toInt()
            val day  = SimpleDateFormat("d", Locale.KOREA).format(date).toInt()
            menu = school.getMonthlyMenu(year, month) as ArrayList<Menu>
            menu.addAll(school.getMonthlyMenu(year, month+1) as ArrayList<Menu>)
            
            schoolDate = school.getMonthlySchedule(year, month) as ArrayList<Schedule>
            schoolDate.addAll(school.getMonthlySchedule(year, month+1))
            withContext(Dispatchers.Main){
                vM.foodList.value = menu
                vM.scheduleList.value = schoolDate
            }

        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}