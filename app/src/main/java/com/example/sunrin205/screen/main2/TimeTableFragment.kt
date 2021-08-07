package com.example.sunrin205.screen.main2

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sunrin205.Calendar.ReturnDate
import com.example.sunrin205.R
import com.example.sunrin205.databinding.FragmentTimeTableBinding
import com.example.sunrin205.screen.MainViewModel
import java.util.*

class TimeTableFragment : Fragment() {

    private lateinit var vM : MainViewModel
    private lateinit var binding: FragmentTimeTableBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_time_table, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vM = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        vM.timeTable.observe(requireActivity(), {
            Log.d(TAG, "onViewCreated: vM.timeTable.value!!.hisTimetable : ${vM.timeTable.value!!.hisTimetable}")
            if(vM.timeTable.value!!.hisTimetable != null){
                val timeTable = vM.timeTable.value!!.hisTimetable[1].row

                for(i in timeTable){
                    val weekendDay = ReturnDate().getDateWeekendDay(i.ALL_TI_YMD)
                    val idStr = "${weekendDay}_${i.PERIO}"
                    if(idStr.count() >= 5){
                        val resId = resources.getIdentifier(idStr, "id", requireActivity().packageName)
                        requireActivity().findViewById<TextView>(resId).text = i.ITRT_CNTNT
                    }
                }
            }
        })
    }
}