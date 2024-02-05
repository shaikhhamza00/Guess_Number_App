package com.example.guessnumber

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var headingTextView: TextView
    private lateinit var commentTextView: TextView
    private lateinit var previousAttemptsTextView: TextView
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var correctImageView: ImageView

    private var targetNumber: Int = 0
    private var attempts: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        headingTextView = findViewById(R.id.headingTextView)
        commentTextView = findViewById(R.id.commentTextView)
        previousAttemptsTextView = findViewById(R.id.previousGuessTextView)
        editText = findViewById(R.id.editText)
        button = findViewById(R.id.button)
        correctImageView = findViewById(R.id.correctImageView)

        // Generate a random number between 1 and 1000
        targetNumber = Random().nextInt(1000) + 1

        button.setOnClickListener {
            checkGuess()
        }
    }

    private fun checkGuess() {
        // Get user's guess
        val userGuess = editText.text.toString().toIntOrNull()

        if (userGuess != null) {
            // Increment attempts
            attempts++

            // Display previous guesses
            val previousGuesses = previousAttemptsTextView.text.toString()
            previousAttemptsTextView.text = "$previousGuesses $userGuess"

            // Check if the guess is correct
            if (userGuess == targetNumber) {
                commentTextView.text = "Congratulations!\nYou guessed the right number $targetNumber in $attempts attempts."

                button.isEnabled = false

                // Show correct image
                correctImageView.visibility = View.VISIBLE

                // Add continuous zoom in and out effect for 5 seconds with increased speed
                val animator = ValueAnimator.ofFloat(1.0f, 1.5f)
                animator.repeatMode = ValueAnimator.REVERSE
                animator.repeatCount = ValueAnimator.INFINITE
                animator.interpolator = LinearInterpolator()
                animator.duration = 1000 // 5 seconds (reduce the duration for increased speed)

                animator.addUpdateListener { valueAnimator ->
                    val animatedValue = valueAnimator.animatedValue as Float
                    commentTextView.scaleX = animatedValue
                    commentTextView.scaleY = animatedValue
                }

                animator.start()
            } else {
                // Provide hints
                if (userGuess < targetNumber) {
                    commentTextView.text = "Try again. The number is higher."
                } else {
                    commentTextView.text = "Try again. The number is lower."
                }
            }
        } else {
            commentTextView.text = "Invalid input. Please enter a valid number."
        }

        // Clear the EditText after each guess
        editText.text.clear()
    }
}
