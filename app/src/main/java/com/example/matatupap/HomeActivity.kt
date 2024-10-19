package com.example.matatupap

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        // Set up BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set Home as selected by default
        bottomNavigationView.selectedItemId = R.id.nav_home

        // Handle navigation item clicks
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already on Home, do nothing
                    true
                }
                R.id.nav_receipts -> {
                    // Open Receipts Activity
                    startActivity(Intent(this, ReceiptsActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    // Open Profile Activity
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Set up search icon click listener
        val searchIcon = findViewById<ImageView>(R.id.search_icon)
        searchIcon.setOnClickListener {
            // Show search dialog
            showSearchDialog()
        }
    }

    private fun showSearchDialog() {
        // Create a simple input dialog for searching Sacco
        val searchEditText = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Search Sacco")
            .setMessage("Enter Sacco Name or Number")
            .setView(searchEditText)
            .setPositiveButton("Search") { dialog, which ->
                val query = searchEditText.text.toString()
                performSearch(query)
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun performSearch(query: String) {
        // Placeholder for search logic (you can filter your sacco list here or implement search with backend)
    }
}
