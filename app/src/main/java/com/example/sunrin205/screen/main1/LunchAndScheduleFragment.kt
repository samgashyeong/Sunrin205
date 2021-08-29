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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunrin205.Calendar.ReturnDate
import com.example.sunrin205.R
import com.example.sunrin205.data.LunchAndDate
import com.example.sunrin205.databinding.FragmentLunchAndScheduleBinding
import com.example.sunrin205.screen.MainViewModel
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
    private lateinit var vM : MainViewModel
    private var menuAdapter : ArrayList<LunchAndDate> = ArrayList()
    private var dateAdapter : ArrayList<LunchAndDate> = ArrayList()

    val school = School(School.Type.HIGH, School.Region.SEOUL, "B100000658")
    val year = SimpleDateFormat("yyyy", Locale.KOREA).format(date).toInt()
    val month = SimpleDateFormat("M", Locale.KOREA).format(date).toInt()
    val day  = SimpleDateFormat("d", Locale.KOREA).format(date).toInt()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lunch_and_schedule, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vM = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val maxDay = ReturnDate().returnMonthMaxDay(-1)
        var addOneDay: Int
        vM.scheduleList.observe(requireActivity(), {
            addOneDay = 1
            if(vM.scheduleList.value!![day-1].toString().isEmpty()){
                binding.todayDate.text = "오늘 일정은 없습니다."
            }
            else{
                binding.todayDate.text = vM.scheduleList.value!![day-1].toString()
            }
            for(i in 0..7){
                if(day+1+i>maxDay){
                    dateAdapter.add(LunchAndDate("${month+1}월 ${addOneDay++}일", vM.scheduleList.value!![day+i].toString()))
                }
                else{
                    dateAdapter.add(LunchAndDate("${month}월 ${day+i+1}일", vM.scheduleList.value!![day+i].toString()))
                }
                binding.dateRecyclerView.adapter = LunchAndDateAdapter(dateAdapter)
            }
        })

        vM.foodList.observe(requireActivity(), ){
            addOneDay = 1
            Log.d(TAG, "onViewCreated: maxday = $maxDay")
            Log.d(TAG, "onViewCreated: 오늘 급식${vM.foodList.value!![day - 1].lunch}")
            if(vM.foodList.value!![day-1].lunch.isEmpty()){
                binding.todayLunch.text = "오늘의 급식은 없습니다."
            }
            else{
                binding.todayLunch.text = vM.foodList.value!![day-1].lunch
            }
            for(i in 0..7){
                if(day+1+i>maxDay){
                    menuAdapter.add(LunchAndDate("${month+1}월 ${addOneDay++}일", vM.foodList.value!![day+i].lunch))
                }
                else{
                    menuAdapter.add(LunchAndDate("${month}월 ${day+i+1}일", vM.foodList.value!![day+i].lunch))
                }
            }
            binding.lunchRecyclerView.adapter = LunchAndDateAdapter(menuAdapter)
        }
    }
}
