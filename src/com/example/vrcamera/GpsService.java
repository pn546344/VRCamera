package com.example.vrcamera;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class GpsService extends Service implements LocationListener{

	LocationManager lManager;
	String bestGPS;
	double latiude;
	double longitude;
	IBinder myBinder = new myBinder();
	Handler handler;
	
	class myBinder extends Binder{
		GpsService getService(){
			return GpsService.this;
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		if(bestGPS != null){
    		lManager.requestLocationUpdates(bestGPS, 100, 1, this);
    	}
		return myBinder;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		lManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		bestGPS = lManager.getBestProvider(criteria, true);
		if(bestGPS != null){
			Location loc = lManager.getLastKnownLocation(bestGPS);
			showLocation(loc);	
		}
		
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub
		super.onRebind(intent);
	}
	
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void showLocation(Location loc) {
		// TODO Auto-generated method stub
		if(loc != null){
			 latiude = loc.getLatitude();
			 longitude = loc.getLongitude();
//			Toast.makeText(this, "is Update", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// 當位置發生改變時
		showLocation(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// 當定位狀態改變時
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// 當啟用位置供應器時
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// 當關閉位置供應器時
		
	}
	double getLongitude(){
		return longitude;
	}
	double getLatiude(){
		return latiude;
	}
}
