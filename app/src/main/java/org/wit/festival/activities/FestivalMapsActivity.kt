package org.wit.festival.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import org.wit.festival.databinding.ActivityFestivalMapsBinding
import org.wit.festival.databinding.ContentFestivalMapsBinding
import org.wit.festival.main.MainApp

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
        }    }
    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.festivals.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }    }

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
        //val festival = marker.tag as FestivalModel
        val tag = marker.tag as Long
        val festival = app.festivals.findById(tag)
        contentBinding.currentTitle.text = festival!!.title
        contentBinding.currentDescription.text = festival.description
        Picasso.get().load(festival.image).into(contentBinding.currentImage)
        return false
    }
}