package org.wit.festival.activities

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import org.wit.festival.R
import org.wit.festival.databinding.ActivityFestivalMapsBinding
import org.wit.festival.databinding.ContentFestivalMapsBinding
import org.wit.festival.main.MainApp
import org.wit.festival.models.FestivalModel

class FestivalMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityFestivalMapsBinding
    private lateinit var contentBinding: ContentFestivalMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFestivalMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        app = application as MainApp
        contentBinding = ContentFestivalMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)

        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    private fun configureMap() {
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
        map.uiSettings.isZoomControlsEnabled = true

        app.festivals.findByUserId(currentUserId).forEach { festival ->
            val loc = LatLng(festival.lat, festival.lng)
            val options = MarkerOptions().title(festival.title).position(loc)
            map.addMarker(options)?.tag = festival
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, festival.zoom))
        }

        map.setOnMarkerClickListener(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val festival = marker.tag as? FestivalModel
        festival?.let {
            contentBinding.currentTitle.text = it.title
            contentBinding.currentDescription.text = it.description
            Picasso.get().load(it.image).into(contentBinding.currentImage)
        }
        return false
    }
}
