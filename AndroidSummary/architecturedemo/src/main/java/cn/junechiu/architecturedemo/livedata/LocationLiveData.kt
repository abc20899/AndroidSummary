package cn.junechiu.architecturedemo.livedata

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.LiveData

class LocationLiveData : LiveData<Location>() {

    var locationManager: LocationManager? = null

    var listener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            value = location //更新位置信息
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = LocationLiveData()
    }

    fun init(context: Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, listener)
    }

    override fun onInactive() {
        super.onInactive()
        locationManager?.removeUpdates(listener)
    }
}