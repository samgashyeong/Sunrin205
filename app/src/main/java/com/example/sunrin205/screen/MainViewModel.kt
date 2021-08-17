package com.example.sunrin205.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunrin205.data.lunchData.lunchData
import com.example.sunrin205.data.timeTableData.timeTableData
import kr.go.neis.api.Menu
import kr.go.neis.api.Schedule

class MainViewModel : ViewModel() {
    val foodList : MutableLiveData<ArrayList<Menu>> = MutableLiveData()
    val scheduleList : MutableLiveData<ArrayList<Schedule>> = MutableLiveData()
    val timeTable : MutableLiveData<timeTableData> = MutableLiveData()
    val studentSeat : MutableLiveData<ArrayList<String>> = MutableLiveData()
    val schedule : MutableLiveData<ArrayList<String>> = MutableLiveData()
}