package org.wit.festival.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.festival.R
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
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        if (intent.hasExtra("festival_edit")) {
            festival = intent.extras?.getParcelable("festival_edit")!!
            binding.festivalTitle.setText(festival.title)
            binding.description.setText(festival.description)
            binding.date.setText(festival.date)
        }

        binding.btnAdd.setOnClickListener() {
            festival.title = binding.festivalTitle.text.toString()
            festival.description = binding.description.text.toString()
            festival.date = binding.date.text.toString()
            festival.valueForMoney = binding.valueForMoney.rating
            festival.accessibility = binding.accessibility.rating
            festival.familyFriendly = binding.familyFriendly.rating
            if (festival.title.isNotEmpty()) {
                app.festivals.create(festival.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_festival, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}