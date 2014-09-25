package com.example.vrcamera;

import android.app.ActionBar;
import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements LocationListener {

	CameraView cView;
	LocationManager lManager;
	TextView tLatitude,tLongitude;			//緯度,經度
	String bestGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getActionBar();
        bar.hide();							//隱藏ActionBar
        cView		= (CameraView)findViewById(R.id.cameraView1);
        tLatitude 	= (TextView)findViewById(R.id.textView2);
        tLongitude 	= (TextView)findViewById(R.id.textView4);
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
    protected void onResume() {
    	if(bestGPS != null){
    		lManager.requestLocationUpdates(bestGPS, 100, 1, this);
    	}
    	super.onResume();
    	
    }
	private void showLocation(Location loc) {
		// TODO Auto-generated method stub
		if(loc != null){
			double latiude = loc.getLatitude();
			double longitude = loc.getLongitude();
			tLatitude.setText(latiude+"");
			tLongitude.setText(longitude+"");
			Toast.makeText(this, "is Update", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// 當位置發生改變時
		showLocation(arg0);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// 當關閉位置供應器時
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// 當啟用位置供應器時
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// 當定位狀態改變時
		
	}

}
