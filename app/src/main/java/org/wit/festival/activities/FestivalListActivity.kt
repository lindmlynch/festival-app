package org.wit.festival.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import org.wit.festival.R
import org.wit.festival.adapters.FestivalAdapter
import org.wit.festival.adapters.FestivalListener
import org.wit.festival.databinding.ActivityFestivalListBinding
import org.wit.festival.main.MainApp
import org.wit.festival.models.FestivalModel


class FestivalListActivity : AppCompatActivity(), FestivalListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityFestivalListBinding
    private var position: Int = 0

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )    { }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityFestivalListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = FestivalAdapter(app.festivals.findAll(),this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, FestivalActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, FestivalMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
            R.id.item_signUp -> {
                val launcherIntent = Intent(this, SignUpActivity::class.java)
                startActivity(launcherIntent)
            }
            R.id.item_login -> {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }

            R.id.item_logout -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.festivals.findAll().size)
            }
        }

    override fun onFestivalClick(festival: FestivalModel, pos : Int) {
        val launcherIntent = Intent(this, FestivalActivity::class.java)
        launcherIntent.putExtra("festival_edit", festival)
        position = pos
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.festivals.findAll().size)
            }
            else // Deleting
                if (it.resultCode == 99)     (binding.recyclerView.adapter)?.notifyItemRemoved(position)
        }
    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        loadFestivals()
    }

    private fun loadFestivals() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
        val userFestivals = app.festivals.findByUserId(userId)
        binding.recyclerView.adapter = FestivalAdapter(userFestivals, this)
    }
}



