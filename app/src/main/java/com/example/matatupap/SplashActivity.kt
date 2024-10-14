package com.example.matatupap

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay for 3 seconds before navigating to MainActivity
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Close SplashActivity
        }, 3000) // 3000ms = 3 seconds
    }
}
