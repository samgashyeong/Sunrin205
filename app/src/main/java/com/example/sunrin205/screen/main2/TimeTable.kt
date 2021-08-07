package com.example.sunrin205.screen.main2

import com.example.sunrin205.data.timeTableData.timeTableData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TimeTable {
    @GET("/hub/hisTimetable?KEY=048fefe2350a418db1c895eda7f876ff&Type=json&ATPT_OFCDC_SC_CODE=B10&SD_SCHUL_CODE=7010536&GRADE=2&CLASS_NM=5")
    fun getTimeTable(@Query("TI_FROM_YMD") FirstDay : String, @Query("TI_TO_YMD") LastDay : String) : Call<timeTableData>
}