package com.complex.fragments.cycling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.complex.fragments.databinding.FragmentCyclingBinding
import com.complex.fragments.editRecord.EditRecordActivity
import com.complex.fragments.editRecord.INTENT_EXTRA_SCREEN_DATA
import com.complex.fragments.running.RunningFragment

class CyclingFragment : Fragment() {

    private lateinit var binding: FragmentCyclingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCyclingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun displayRecords() {
        val cyclingPreferences =
            requireContext().getSharedPreferences(CYCLING, Context.MODE_PRIVATE)


        binding.valueLongestRideTextView.text = cyclingPreferences.getString(
            "Longest Ride ${RunningFragment.SHARED_PREFERENCE_RECORD_KEY}",
            null
        )
        binding.longestDate.text = cyclingPreferences.getString(
            "Longest Ride ${RunningFragment.SHARED_PREFERENCE_DATE_KEY}",
            null
        )
        binding.valueAverageRideTextView.text = cyclingPreferences.getString(
            "Average Ride ${RunningFragment.SHARED_PREFERENCE_RECORD_KEY}",
            null
        )
        binding.averageDate.text = cyclingPreferences.getString(
            "Average Ride ${RunningFragment.SHARED_PREFERENCE_DATE_KEY}",
            null
        )
        binding.valueBiggestClimbTextView.text = cyclingPreferences.getString(
            "Biggest Ride ${RunningFragment.SHARED_PREFERENCE_RECORD_KEY}",
            null
        )
        binding.biggestDate.text = cyclingPreferences.getString(
            "Biggest Ride ${RunningFragment.SHARED_PREFERENCE_DATE_KEY}",
            null
        )
    }

    private fun setUpClickListeners() {
        binding.containerLongestRide.setOnClickListener {
            launchCyclingFragment(
                "Longest Ride",
                "Distance"
            )
        }
        binding.containerAverageRide.setOnClickListener {
            launchCyclingFragment(
                "Average Ride",
                "Distance"
            )
        }
        binding.containerBiggestClimb.setOnClickListener {
            launchCyclingFragment(
                "Biggest Ride",
                "Height"
            )
        }

    }

    private fun launchCyclingFragment(record: String, recordFieldHint: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(
            INTENT_EXTRA_SCREEN_DATA,
            EditRecordActivity.ScreenData(record, CYCLING, recordFieldHint)
        )
        startActivity(intent)
    }

    companion object {
        const val CYCLING = "cycling"
    }
}