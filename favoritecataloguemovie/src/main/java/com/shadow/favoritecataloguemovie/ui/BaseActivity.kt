@file:Suppress("DEPRECATION")

package com.shadow.favoritecataloguemovie.ui

import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
            .apply {
                val textView = view.findViewById<TextView>(android.R.id.message)
                textView.gravity = Gravity.CENTER
                textView.textSize = 14f
            }
            .show()
    }


}