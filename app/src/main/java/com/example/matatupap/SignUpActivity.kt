package com.example.matatupap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val phoneNumberEditText = findViewById<EditText>(R.id.sign_up_phone_number)
        val passwordEditText = findViewById<EditText>(R.id.sign_up_password)
        val signUpButton = findViewById<Button>(R.id.sign_up_button)

        // Handle sign-up button click event
        signUpButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both phone number and password", Toast.LENGTH_SHORT).show()
            } else {
                // Create a new user with email and password
                auth.createUserWithEmailAndPassword("$phoneNumber@matatupap.com", password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign up success
                            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                            finish()  // Close the sign-up activity
                        } else {
                            // If sign up fails, display a message to the user
                            Toast.makeText(this, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}
