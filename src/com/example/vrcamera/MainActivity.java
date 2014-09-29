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
	TextView tv6,tv8,tv10,tv12;
	Location testPoint1 = new Location("");	//GPS測試作標點1
	Location testPoint2 = new Location("");	//GPS測試作標點2
	Location testPoint3 = new Location("");	//GPS測試作標點3
	Location testPoint4 = new Location("");	//GPS測試作標點4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getActionBar();
        bar.hide();							//隱藏ActionBar
        cView		= (CameraView)findViewById(R.id.cameraView1);
        tLatitude 	= (TextView)findViewById(R.id.textView2);
        tLongitude 	= (TextView)findViewById(R.id.textView4);
        tv6 = (TextView)findViewById(R.id.textView6);
        tv8 = (TextView)findViewById(R.id.textView8);
        tv10 = (TextView)findViewById(R.id.textView10);
        tv12 = (TextView)findViewById(R.id.textView12);
        lManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        bestGPS = lManager.getBestProvider(criteria, true);
        if(bestGPS != null){
        	Location loc = lManager.getLastKnownLocation(bestGPS);
        	showLocation(loc);
        }
        
        //設定測試作標點
        testPoint1.setLatitude(24.86301);	//宗山樓GPS位置
        testPoint1.setLongitude(120.9888763);
        testPoint2.setLatitude(24.8625994);	//圖書館GPS位置
        testPoint2.setLongitude(120.9896252);
        testPoint3.setLatitude(24.8625921);	//鴻超樓GPS位置
        testPoint3.setLongitude(120.9898982);
        testPoint4.setLatitude(24.8637871);	//電算中心GPS位置
        testPoint4.setLongitude(120.9903995);
        
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
			double a = loc.distanceTo(testPoint1);
			tv6.setText(loc.distanceTo(testPoint1)+"m");
			tv8.setText(loc.distanceTo(testPoint2)+"m");
			tv10.setText(loc.distanceTo(testPoint3)+"m");
			tv12.setText(loc.distanceTo(testPoint4)+"m");
			
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
