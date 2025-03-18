package com.complex.fragments.editRecord

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.complex.fragments.databinding.ActivityEditRecordsBinding
import com.complex.fragments.running.RunningFragment
import java.io.Serializable

const val INTENT_EXTRA_SCREEN_DATA = "screen_data"

class EditRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditRecordsBinding
    private val screenData: ScreenData by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(
                INTENT_EXTRA_SCREEN_DATA,
                ScreenData::class.java
            ) as ScreenData
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA) as ScreenData
        }

    }
    private lateinit var recordPreference: SharedPreferences // rather then declaring twice in 2 fun we declared globally
    // this also use to prevent passing val into every function
    // private val record by lazy { intent.getStringExtra("Distance") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recordPreference =
            getSharedPreferences(screenData.sharedPreferencesName, Context.MODE_PRIVATE)
        setUpUi()
        displayRecord()
    }

    // for back arrow to return previous screen only to know not neccessary
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpUi() {
        title = "${screenData.record} Record"
        binding.textEditLayout.hint = screenData.recordFieldHint
        binding.inputSaveBtn.setOnClickListener {
            saveRecord()
            finish()
        }
        binding.deleteBtn.setOnClickListener {
            clearRecord()
            finish()
        }
    }

    private fun clearRecord() {
        recordPreference.edit() {
            remove("${screenData.record} Record")
            remove("${screenData.record} Date")
        }
    }

    private fun displayRecord() {
        val runningPreference = getSharedPreferences(RunningFragment.RUNNING, Context.MODE_PRIVATE)
        binding.textEditRecord.setText(
            runningPreference.getString(
                "${screenData.record} Record",
                null
            )
        )
        binding.editTextDate.setText(runningPreference.getString("${screenData.record} Date", null))
    }

    private fun saveRecord() {
        val record = binding.textEditRecord.text.toString()
        val data = binding.editTextDate.text.toString()
        recordPreference.edit {
            putString("${this@EditRecordActivity.screenData.record} Record", record)
            putString("${this@EditRecordActivity.screenData.record} Date", data)
        }
    }

    data class ScreenData(
        val record: String,
        val sharedPreferencesName: String,
        val recordFieldHint: String
    ) : Serializable

}