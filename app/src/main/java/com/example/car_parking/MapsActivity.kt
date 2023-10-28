package com.example.car_parking

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.internal.PolylineEncoding
import com.google.maps.model.TravelMode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class mapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener,GoogleMap.OnMarkerClickListener ,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private lateinit var mMap: GoogleMap

    private lateinit var lastlocation : Location
    private lateinit var fusedlocation : FusedLocationProviderClient
    private lateinit var latLng: LatLng
    private lateinit var currentlocation: LatLng
    private var polyline: Polyline? = null

   private lateinit var locationSearch: EditText
    private lateinit var location : String

    companion object{

        private const val  LOCATION_REQ_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedlocation = LocationServices.getFusedLocationProviderClient(this)
        val cu = findViewById<Button>(R.id.cureentlocationbtn)
        cu.setOnClickListener {
            setMap()
        }
        locationSearch = findViewById(R.id.editText)
        val bundle : Bundle? = intent.extras
        val haltname: String? = bundle?.getString("name")



           if (haltname != null) {
                locationSearch.setText(haltname.toString())
            }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setMap()

    }

    private fun setMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQ_CODE
            )
            return
        }
        mMap.isMyLocationEnabled = true

        fusedlocation.lastLocation.addOnSuccessListener {
            location->

            if(location != null){
                lastlocation = location

               currentlocation = LatLng(location.latitude,location.longitude)
           //     currentlocation = LatLng(-34.0, 151.0)
                packemarlronMap(currentlocation)

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlocation,12f))

            }

        }

    }

    private fun packemarlronMap(currentlocation: LatLng) {

        val markerOptions = MarkerOptions().position(currentlocation)
       markerOptions.title("$currentlocation")
        markerOptions.icon(BitmapFromVector(getApplicationContext(), R.drawable.mycarss))
        //add marker
       markerOptions.anchor(0.5F, 0.5F)
       markerOptions.rotation(-100F)
        mMap.addMarker(markerOptions)



    }
    private fun BitmapFromVector(context: Context, vectorResId:Int): BitmapDescriptor? {
        //drawable generator
        var vectorDrawable: Drawable
        vectorDrawable= ContextCompat.getDrawable(context,vectorResId)!!
        vectorDrawable.setBounds(0,0,vectorDrawable.intrinsicWidth,vectorDrawable.intrinsicHeight)
        //bitmap genarator
        var bitmap: Bitmap
        bitmap= Bitmap.createBitmap(vectorDrawable.intrinsicWidth,vectorDrawable.intrinsicHeight,Bitmap.Config.ARGB_8888)
        //canvas genaret
        var canvas: Canvas
        //pass bitmap in canvas constructor
        canvas= Canvas(bitmap)
        //pass canvas in drawable
        vectorDrawable.draw(canvas)
        //return BitmapDescriptorFactory
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    override fun onMarkerClick(p0: Marker) = false
    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun onConnected(p0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }
    fun searchLocation(view: View){



        location = locationSearch.text.toString()
        var addressList: List<Address>? = null

        if (location == null || location == ""){
            Toast.makeText(this, "provide location", Toast.LENGTH_SHORT).show()
        }else{
            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)
                locationSearch.text.clear()
            }catch (e: IOException){
                e.printStackTrace()
            }

            val address = addressList!![0]
             latLng = LatLng(address.latitude, address.longitude)
            mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        }
    }

    fun goplace(view: View) {
        if (currentlocation == null || latLng == null) {
            Toast.makeText(this, "Pleass on GPS", Toast.LENGTH_SHORT).show()
        } else {


            /*  val options =
                PolylineOptions().add(currentlocation, latLng).color(Color.BLUE).width(10f)
            polyline?.remove()

            polyline = mMap.addPolyline(options)
            val boundsBuilder = LatLngBounds.builder()
            boundsBuilder.include(currentlocation)
            boundsBuilder.include(latLng)
            val bounds = boundsBuilder.build()
            val padding = 100 // Padding in pixels
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            mMap.animateCamera(cameraUpdate)*/

            val apiKey = ""
            val context = GeoApiContext.Builder().apiKey(apiKey).build()


            GlobalScope.launch {
                try {
                    val result = DirectionsApi.newRequest(context)
                        .mode(TravelMode.DRIVING)
                        .origin("${currentlocation.latitude},${currentlocation.longitude}")
                        .destination("${latLng.latitude},${latLng.longitude}")
                        .await()

                    runOnUiThread {
                        // Process the result to display directions on the map
                        if (result.routes.isNotEmpty()) {
                            val route = result.routes[0]
                            val decodedPath =
                                PolylineEncoding.decode(route.overviewPolyline.encodedPath)
                            val newPolylineOptions = PolylineOptions()

                            for (latLng in decodedPath) {
                                newPolylineOptions.add(LatLng(latLng.lat, latLng.lng))
                            }

                            newPolylineOptions.width(10f)
                            newPolylineOptions.color(Color.BLUE)
                            mMap.addPolyline(newPolylineOptions)
                        }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }


            }
       }
    }
}



