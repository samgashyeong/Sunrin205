package com.example.sunrin205.screen.main1

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunrin205.R
import com.example.sunrin205.data.LunchAndDate
import com.example.sunrin205.databinding.FragmentLunchAndScheduleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.go.neis.api.Schedule
import kr.go.neis.api.School
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LunchAndScheduleFragment : Fragment() {

    private lateinit var binding: FragmentLunchAndScheduleBinding
    private val date = Calendar.getInstance().time
    private lateinit var menu: List<kr.go.neis.api.Menu>
    private lateinit var schoolDate: List<Schedule>
    private var menuAdapter : ArrayList<LunchAndDate> = ArrayList()
    private var dateAdapter : ArrayList<LunchAndDate> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lunch_and_schedule, container, false)

        val school = School(School.Type.HIGH, School.Region.SEOUL, "B100000658")
        
        GlobalScope.launch(Dispatchers.IO) {
            val year = SimpleDateFormat("yyyy", Locale.KOREA).format(date).toInt()
            val month = SimpleDateFormat("M", Locale.KOREA).format(date).toInt()
            val day  = SimpleDateFormat("d", Locale.KOREA).format(date).toInt()
            menu = school.getMonthlyMenu(
                year
                , month
            )
            schoolDate = school.getMonthlySchedule(year, month)

            withContext(Dispatchers.Main){
                if(menu[day-1].lunch.isEmpty()){
                    binding.todayLunch.text = "오늘의 급식은 없습니다."
                }
                else{
                    binding.todayLunch.text = menu[day-1].lunch
                }
                if(schoolDate[day-1].toString().isEmpty()){
                    binding.todayDate.text = "오늘 일정은 없습니다."
                }
                else{
                    binding.todayDate.text = schoolDate[day-1].toString()
                }
                for(i in 0..7){
                    menuAdapter.add(LunchAndDate("${month}월 ${8+i}일", menu[8-1+i].lunch))
                    dateAdapter.add(LunchAndDate("${month}월 ${4+i}일", schoolDate[4-1+i].toString()))
                }
                binding.lunchRecyclerView.adapter = LunchAndDateAdapter(menuAdapter)
                binding.dateRecyclerView.adapter = LunchAndDateAdapter(dateAdapter)
                binding.LunchPgb.visibility = View.INVISIBLE
                binding.datePgb.visibility = View.INVISIBLE
            }
        }

        return binding.root
    }

}
