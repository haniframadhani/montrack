package triplegato.montrack


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import triplegato.montrack.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val AUTOCOMPLETE_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the Places API (Replace YOUR_API_KEY with your actual API key)
        Places.initialize(applicationContext, "")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val pickLocationButton: Button = findViewById(R.id.pickLocationButton)

        pickLocationButton.setOnClickListener {
            // Launch the location picker
            launchPlacePicker()
        }
    }

    private fun launchPlacePicker() {
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(this)

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(data!!)
            updateMarker(place.latLng, place.name)
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status = Autocomplete.getStatusFromIntent(data!!)
            Toast.makeText(this, "Error: ${status.statusMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateMarker(latLng: LatLng?, title: String?) {
        if (latLng != null) {
            mMap.clear() // Clear existing markers
            mMap.addMarker(MarkerOptions().position(latLng).title(title ?: "Selected Location"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

            // Provide feedback to the user
            Toast.makeText(this, "Location Selected: ${title ?: "Selected Location"}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error: Unable to get location coordinates", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Set a default marker at Sydney
        val sydney = LatLng(-7.83287952602761, 110.38317108346173)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in UAD"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))

        // Set a listener to handle map clicks
        mMap.setOnMapClickListener { latLng ->
            // Handle the map click event
            updateMarker(latLng, null)
        }
    }
}