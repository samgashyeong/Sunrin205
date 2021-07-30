package com.example.sunrin205.screen

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sunrin205.*
import com.example.sunrin205.databinding.ActivityMainBinding
import com.example.sunrin205.school.School
import com.example.sunrin205.screen.main1.LunchAndScheduleFragment
import com.example.sunrin205.screen.main2.TimeTableFragment
import com.example.sunrin205.screen.main3.SeatFragment
import com.example.sunrin205.screen.main4.SettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }
}