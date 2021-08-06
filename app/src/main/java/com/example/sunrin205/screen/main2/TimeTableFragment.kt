package com.example.sunrin205.screen.main2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunrin205.R
import com.example.sunrin205.databinding.FragmentTimeTableBinding
import com.example.sunrin205.screen.MainViewModel

class TimeTableFragment : Fragment() {

    private lateinit var vM : MainViewModel
    private lateinit var binding: FragmentTimeTableBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_time_table, container, false)

        /*vM.studentSeat.observe(requireActivity(), {
        })*/


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vM = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
}