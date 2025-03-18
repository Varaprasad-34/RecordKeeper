package com.complex.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.complex.fragments.cycling.CyclingFragment
import com.complex.fragments.databinding.ActivityMainBinding
import com.complex.fragments.running.RunningFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.setOnItemSelectedListener(this)
    }

    // to create a tool menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    // implement of tool menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuClicked = when (item.itemId) {
            R.id.reset_running -> {
                showConfirmation(RunningFragment.RUNNING)
                true
            }

            R.id.reset_cycling -> {
                showConfirmation(CyclingFragment.CYCLING)
                true
            }

            R.id.reset_all -> {
                showConfirmation(ALL)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
        return menuClicked
    }

    private fun showConfirmation(selected: String) {
        AlertDialog.Builder(this).setTitle("Reset $selected Record")
            .setMessage("Are you sure you want to clear $selected record")
            .setPositiveButton("Yes") { _, _ ->
                when (selected) {
                    ALL -> {
                        getSharedPreferences(RunningFragment.RUNNING, MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(CyclingFragment.CYCLING, MODE_PRIVATE).edit { clear() }
                    }
                    RunningFragment.RUNNING ->{
                        getSharedPreferences(RunningFragment.RUNNING, MODE_PRIVATE).edit { clear() }
                    }
                    CyclingFragment.CYCLING ->{
                        getSharedPreferences(CyclingFragment.CYCLING, MODE_PRIVATE).edit { clear() }
                    }
                }
                refreshScreen()
                showConfirmationSnackBar()
            }.setNegativeButton("No", null).show()
    }

    private fun showConfirmationSnackBar() {
        val snackBar =
            Snackbar.make(binding.root, "Record cleared successfully!", Snackbar.LENGTH_LONG)
        snackBar.anchorView = binding.bottomNav

        snackBar.setAction("Undo") {
            // code
        }
        snackBar.show()
    }

    private fun refreshScreen() {
        when (binding.bottomNav.selectedItemId) {
            R.id.nav_running -> onRunningClicked()
            R.id.nav_cycling -> onCyclingClicked()
            else -> {}
        }
    }

    private fun onRunningClicked() {
        supportFragmentManager.beginTransaction().replace(R.id.frame_content, RunningFragment())
            .commit()
    }

    private fun onCyclingClicked() {
        supportFragmentManager.beginTransaction().replace(R.id.frame_content, CyclingFragment())
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.nav_running -> {
            onRunningClicked(); true
        }

        R.id.nav_cycling -> {
            onCyclingClicked(); true
        }

        else -> false
    }

    companion object {
        const val ALL = "all"
    }
}