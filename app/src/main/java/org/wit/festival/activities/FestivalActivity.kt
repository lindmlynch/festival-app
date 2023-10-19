package org.wit.festival.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.festival.databinding.ActivityFestivalBinding
import org.wit.festival.main.MainApp
import org.wit.festival.models.FestivalModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class FestivalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFestivalBinding
    var festival = FestivalModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFestivalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Festival Activity started...")
        binding.btnAdd.setOnClickListener() {
            festival.title = binding.festivalTitle.text.toString()
            festival.description = binding.description.text.toString()
            if (festival.title.isNotEmpty()) {
                app.festivals.add(festival.copy())
                i("add Button Pressed: ${festival}")
                for (i in app.festivals.indices) {
                    i("Festival[$i]:${this.app.festivals[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}