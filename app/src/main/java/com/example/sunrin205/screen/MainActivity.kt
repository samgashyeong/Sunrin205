package com.example.sunrin205.screen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sunrin205.*
import com.example.sunrin205.Calendar.ReturnDate
import com.example.sunrin205.databinding.ActivityMainBinding
import com.example.sunrin205.screen.main1.LunchAndScheduleFragment
import com.example.sunrin205.screen.main2.TimeTable
import com.example.sunrin205.screen.main2.TimeTableFragment
import com.example.sunrin205.screen.main3.SeatFragment
import com.example.sunrin205.screen.main4.SettingFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.go.neis.api.Menu
import kr.go.neis.api.Schedule
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
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

    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.frame, mainFragment2).commit()
        binding.bottomView.selectedItemId = R.id.schedule
        vM = ViewModelProvider(this).get(MainViewModel::class.java)
        db = FirebaseFirestore.getInstance()

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
        getTimeTable()
        getStudentSeat()
    }

    private fun getStudentSeat() {
        val list : ArrayList<String> = ArrayList()
        db.collection("Seat").document("seat").get()
            .addOnSuccessListener {
                Log.d(TAG, "getStudentSeat: seat : ${it.data!!["seat"]}")
                vM.studentSeat.value = it.data!!["seat"] as ArrayList<String>
            }
            .addOnFailureListener {
                Toast.makeText(this, "데이터를 불러오는데에 실패하셧습니다.", Toast.LENGTH_SHORT).show()
            }
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

            if(vM.foodList.value == null  && vM.foodList.value ==  null){
                menu = school.getMonthlyMenu(year, month) as ArrayList<Menu>
                menu.addAll(school.getMonthlyMenu(year, month+1) as ArrayList<Menu>)

                Log.d(TAG, "getLunchList: menu $menu")

                schoolDate = school.getMonthlySchedule(year, month) as ArrayList<Schedule>
                schoolDate.addAll(school.getMonthlySchedule(year, month+1))
            }

            withContext(Dispatchers.Main){
                Log.d(TAG, "getLunchList: $schoolDate")
                vM.foodList.value = menu
                vM.scheduleList.value = schoolDate
            }
        }
    }


    private fun getTimeTable(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://open.neis.go.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TimeTable::class.java)
        val fDayLDay = ReturnDate().returnTodayWeekendFirstDayLastDay()

        GlobalScope.launch(Dispatchers.IO) {
            val execution = retrofit.getTimeTable(fDayLDay[0], fDayLDay[1]).awaitResponse()

            Log.d(TAG, "getTimeTable: ${execution}")

            if(execution.isSuccessful){
                val isExecution = execution.body()
                Log.d(TAG, "getTimeTable: 시간표데이터 $isExecution")

                withContext(Dispatchers.Main){
                    vM.timeTable.value = isExecution
                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}