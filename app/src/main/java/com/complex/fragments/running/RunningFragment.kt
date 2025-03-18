package com.complex.fragments.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.complex.fragments.databinding.FragmentRunningBinding
import com.complex.fragments.editRecord.EditRecordActivity
import com.complex.fragments.editRecord.INTENT_EXTRA_SCREEN_DATA

class RunningFragment : Fragment() {
    private lateinit var binding: FragmentRunningBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRunningBinding.inflate(inflater, container, false)
        // return inflater.inflate(R.layout.fragment_running, container, false)
        // false because it attached to the activity container not to the root of an activity
        return binding.root
    }

    // this come with Fragment() interface. write code here
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSetOnClickListener()
    }

    override fun onResume() {
        super.onResume()
        displayData()
    }

    private fun displayData() {
        val runningPreferences =
            requireContext().getSharedPreferences(RUNNING, Context.MODE_PRIVATE)

        binding.value5kmTextView.text = runningPreferences.getString("5km $SHARED_PREFERENCE_RECORD_KEY", null)
        binding.date5km.text = runningPreferences.getString("5km $SHARED_PREFERENCE_DATE_KEY", null)
        binding.value10kmTextView.text = runningPreferences.getString("10km $SHARED_PREFERENCE_RECORD_KEY", null)
        binding.date10kmVal.text = runningPreferences.getString("10km $SHARED_PREFERENCE_DATE_KEY", null)
        binding.valueHalfMarathonTextView.text =
            runningPreferences.getString("Half Marathon $SHARED_PREFERENCE_RECORD_KEY", null)
        binding.halfMarathonDate.text = runningPreferences.getString("Half Marathon $SHARED_PREFERENCE_DATE_KEY", null)
        binding.valueMarathonTextView.text = runningPreferences.getString("Marathon $SHARED_PREFERENCE_RECORD_KEY", null)
        binding.marathonDate.text = runningPreferences.getString("Marathon $SHARED_PREFERENCE_DATE_KEY", null)
    }

    private fun setUpSetOnClickListener() {
        binding.container5km.setOnClickListener { launchRunningFragment("5km") }
        binding.container10km.setOnClickListener { launchRunningFragment("10km") }
        binding.containerHalfMarathon.setOnClickListener { launchRunningFragment("Half Marathon") }
        binding.containerMarathon.setOnClickListener { launchRunningFragment("Marathon") }
    }

    private fun launchRunningFragment(distance: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra(INTENT_EXTRA_SCREEN_DATA, EditRecordActivity.ScreenData(distance, RUNNING, "Time"))
        startActivity(intent)
    }
    companion object{
        const val RUNNING = "running"
        const val SHARED_PREFERENCE_RECORD_KEY = "Record"
        const val SHARED_PREFERENCE_DATE_KEY = "Date"
    }
}