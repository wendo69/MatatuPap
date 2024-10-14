package com.example.matatupap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matatupap.AdminActivity
import com.example.matatupap.UserActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase // Declare a FirebaseDatabase instance
    private lateinit var verificationId: String // To store verification code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance() // Initialize Firebase Database

        val phoneNumberEditText = findViewById<EditText>(R.id.phone_number)
        val otpEditText = findViewById<EditText>(R.id.otp_code)
        val sendOtpButton = findViewById<Button>(R.id.login_button) // Use the login button for sending OTP
        val verifyOtpButton = findViewById<Button>(R.id.verify_otp_button)

        // Send OTP
        sendOtpButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()

            // Configure OTP verification
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber("+254$phoneNumber") // Kenya country code (254)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // Sign in with the credential
                        signInWithPhoneAuthCredential(credential)
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        Toast.makeText(this@MainActivity, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                        super.onCodeSent(verificationId, token)
                        this@MainActivity.verificationId = verificationId
                        Toast.makeText(this@MainActivity, "OTP Sent!", Toast.LENGTH_SHORT).show()
                    }
                }).build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }

        // Verify OTP
        verifyOtpButton.setOnClickListener {
            val otpCode = otpEditText.text.toString()
            val credential = PhoneAuthProvider.getCredential(verificationId, otpCode)
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in successful
                    checkUserRole(auth.currentUser?.phoneNumber)
                } else {
                    Toast.makeText(this, "OTP verification failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkUserRole(phoneNumber: String?) {
        // Fetch user data from Firebase Database and check if the user is an admin
        val userRef = database.getReference("users").child(phoneNumber!!)
        userRef.get().addOnSuccessListener { snapshot ->
            val role = snapshot.child("role").value as String
            if (role == "admin") {
                // Redirect to admin page
                startActivity(Intent(this, AdminActivity::class.java))
            } else {
                // Redirect to user page
                startActivity(Intent(this, UserActivity::class.java))
            }
        }
    }
}
