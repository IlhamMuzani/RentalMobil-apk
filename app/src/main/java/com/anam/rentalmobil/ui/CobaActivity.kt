//import android.location.Geocoder
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.model.LatLng
//import java.io.IOException
//import java.util.*
//
//override fun onMapReady(map: GoogleMap) {
//    googleMap = map
//    googleMap.uiSettings.isZoomControlsEnabled = true
//    googleMap.isMyLocationEnabled = true
//
//    fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->
//        if (location != null) {
//            lastLocation = location
//
//            if (Constant.LATITUDE.isEmpty()) {
//                currentLatLng = LatLng(location.latitude, location.longitude)
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
//
//                Constant.LATITUDE = location.latitude.toString()
//                Constant.LONGITUDE = location.longitude.toString()
//
//                mapText(location.latitude, location.longitude)
//            } else {
//                currentLatLng =
//                    LatLng(Constant.LATITUDE.toDouble(), Constant.LONGITUDE.toDouble())
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
//            }
//
//            marker.position(currentLatLng)
//            googleMap.addMarker(marker)
//        }
//    }
//
//    googleMap.setOnMapClickListener { latLng ->
//        marker.position(latLng)
//        marker.title(latLng.latitude.toString() + " : " + latLng.longitude.toString())
//
//        Constant.LATITUDE = latLng.latitude.toString()
//        Constant.LONGITUDE = latLng.longitude.toString()
//
//        mapText(latLng.latitude, latLng.longitude)
//
//        googleMap.clear()
//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//        googleMap.addMarker(marker)
//    }
//}
//
//override fun mapText(latitude: Double, longitude: Double) {
//    val geocoder = Geocoder(this, Locale.getDefault())
//    try {
//        val addressList = geocoder.getFromLocation(latitude, longitude, 1)
//        if (addressList != null && addressList.size > 0) {
//            Constant.AREA =
//                addressList[0].subLocality + ", " + addressList[0].locality
//            Constant.CITY = addressList[0].subAdminArea
//            Constant.PROVINCE = addressList[0].adminArea
//        }
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//}