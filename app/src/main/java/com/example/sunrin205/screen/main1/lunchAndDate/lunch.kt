package com.example.sunrin205.screen.main1.lunchAndDate

import retrofit2.http.GET

interface lunch {
    @GET
    fun getLunchData()
}