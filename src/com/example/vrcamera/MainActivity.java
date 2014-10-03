package com.example.vrcamera;

import java.util.LinkedList;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements LocationListener {

	TagView tagview;
	CameraView cView;
	LocationManager lManager;
	TextView tLatitude,tLongitude;			//緯度,經度
	String bestGPS;
	TextView tv6,tv8,tv10,tv12;
	Location testPoint1 = new Location("");	//GPS測試作標點1
	Location testPoint2 = new Location("");	//GPS測試作標點2
	Location testPoint3 = new Location("");	//GPS測試作標點3
	Location testPoint4 = new Location("");	//GPS測試作標點4
	
	LinkedList<PointData> list = new LinkedList<PointData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getActionBar();
        bar.hide();							//隱藏ActionBar
        tagview		= (TagView)findViewById(R.id.tagView1);
        cView		= (CameraView)findViewById(R.id.cameraView1);
//        tagview.setZOrderMediaOverlay(true);
        tagview.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        tagview.setZOrderOnTop(true);
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
        
        //資料儲存
        PointData p1 = new PointData();
        PointData p2 = new PointData();
        PointData p3 = new PointData();
        PointData p4 = new PointData();
        Bitmap a = BitmapFactory.decodeResource(getResources(), R.drawable.zongshan);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.libreay);
        Bitmap c = BitmapFactory.decodeResource(getResources(), R.drawable.hungchao);
        Bitmap d = BitmapFactory.decodeResource(getResources(), R.drawable.powercenter);
        p1.setBitmap(a);
        p1.setLatitude(24.86301);
        p1.setLongitude(120.9888763);
        p2.setBitmap(b);
        p2.setLatitude(24.8625994);
        p2.setLongitude(120.9896252);
        p3.setBitmap(c);
        p3.setLatitude(24.8625921);
        p3.setLongitude(120.9898982);
        p4.setBitmap(d);
        p4.setLatitude(24.8637871);
        p4.setLongitude(120.9903995);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        
    }
    
    @Override
    protected void onResume() {
    	if(bestGPS != null){
    		lManager.requestLocationUpdates(bestGPS, 100, 1, this);
    	}
    	tagview.resume();
    	Log.i("ttt", "onResume start");
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
