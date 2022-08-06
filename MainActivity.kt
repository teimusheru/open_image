package com.example.open_image0805

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    private var resultLauncher = registerForActivityResult(
        StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val resultData = result.data
            if (resultData != null) {
                val uri = resultData.data

                val bmp = BitmapFactory.decodeResource(resources, R.drawable.img)
                try {contentResolver.openOutputStream(uri!!).use { outputStream ->
                    val compress = bmp.compress(
                        Bitmap.CompressFormat.PNG,
                        100,
                        outputStream
                    )
                    compress
                }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener { createFile() }
    }

    private fun createFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_TITLE, "img.jpg")
        resultLauncher.launch(intent)
    }
}