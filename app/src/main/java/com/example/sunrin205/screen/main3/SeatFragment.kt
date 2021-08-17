package com.example.sunrin205.screen.main3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sunrin205.R
import com.example.sunrin205.databinding.FragmentSeatBinding
import com.example.sunrin205.screen.MainViewModel


class SeatFragment : Fragment() {

    private lateinit var binding : FragmentSeatBinding
    private lateinit var vM : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_seat, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vM = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        vM.studentSeat.observe(requireActivity(), {
            val seatList = vM.studentSeat.value

            for(i in 1..seatList!!.size){
                val idStr = "seat_$i"
                val resId = resources.getIdentifier(idStr, "id", requireActivity().packageName)
                requireActivity().findViewById<TextView>(resId).text = seatList!![i-1]
            }
        })
    }
}