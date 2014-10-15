package com.example.vrcamera;

import java.util.LinkedList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements LocationListener, SensorEventListener {

	TagView tagview;
	CameraView cView;
	LocationManager lManager;
	TextView tLatitude,tLongitude;			//緯度,經度
	String bestGPS;
	TextView tv6,tv8,tv10,tv12,tv14,tv16;
	Location testPoint1 = new Location("");	//GPS測試作標點1
	Location testPoint2 = new Location("");	//GPS測試作標點2
	Location testPoint3 = new Location("");	//GPS測試作標點3
	Location testPoint4 = new Location("");	//GPS測試作標點4
	SensorManager sm;
	float currentDegree = 0f;	
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
        tv14 = (TextView)findViewById(R.id.textView14);
        tv16 = (TextView)findViewById(R.id.textView16);
        
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
        
        //取得螢幕解析度大小
        DisplayMetrics metrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.i("ttt", "螢幕寬"+metrics.widthPixels);
        Log.i("ttt", "螢幕高"+metrics.heightPixels);
        tagview.setScanHeight(metrics.heightPixels);	//將螢幕高傳遞給tagview
        tagview.setScanWidth(metrics.widthPixels); 		//將螢幕寬傳遞給tagview
        /* 
    	註冊Sensor Listener (registerListener)
    	第一個參數: Sensor.TYPE_ORIENTATION(方向傳感器);
    	第二個參數: 
    		SENSOR_DELAY_FASTEST(0毫秒延遲);
    		SENSOR_DELAY_GAME(20,000毫秒延遲)
    		SENSOR_DELAY_UI(60,000毫秒延遲))
  */
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),
        		SensorManager.SENSOR_DELAY_FASTEST);
        
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
			tagview.setGPS(loc.distanceTo(testPoint4)+"");
			tv16.setText(gps2d(loc)+"");
			tagview.setGPS2d(gps2d(loc));
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

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// 傳感器報告新的值(方向改變)
				if (arg0.sensor.getType() == Sensor.TYPE_ORIENTATION) {
					   float degree = arg0.values[0];
					   
					   /*
					   RotateAnimation類別：旋轉變化動畫類
					    
					   參數說明:
					   fromDegrees：旋轉的開始角度。
					   toDegrees：旋轉的結束角度。
					   pivotXType：X軸的伸縮模式，可以取值為ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
					   pivotXValue：X坐標的伸縮值。
					   pivotYType：Y軸的伸縮模式，可以取值為ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
					   pivotYValue：Y坐標的伸縮值。
					   */
					   RotateAnimation ra = new RotateAnimation(
					     currentDegree, // 動畫起始時物件的角度
					     -degree,       // 動畫結束時物件旋轉的角度(可大於360度)-表示逆時針旋轉,+表示順時針旋轉
					     Animation.RELATIVE_TO_SELF, 0.5f, //動畫相對於物件的X座標的開始位置, 從0%~100%中取值, 50%為物件的X方向坐標上的中點位置
					     Animation.RELATIVE_TO_SELF, 0.5f); //動畫相對於物件的Y座標的開始位置, 從0%~100%中取值, 50%為物件的Y方向坐標上的中點位置

					   ra.setDuration(200); // 旋轉過程持續時間
					   ra.setRepeatCount(-1); // 動畫重複次數 (-1 表示一直重複)
//					   img.startAnimation(ra); // 羅盤圖片使用旋轉動畫
					   currentDegree = -degree; // 保存旋轉後的度數, currentDegree是一個在類中定義的float類型變量
					   Log.i("ttt", "currentDegree="+degree);
					   tv14.setText((int)degree+"");
					   tagview.setCompass((int)degree);
				}
		
	}

	private double gps2d(Location loc) {
		//算gps兩點座標的角度
		double d = 0;
		double lat_a=0,lng_a=0,lat_b=24.8637871,lng_b=120.9903995;
		lat_a = loc.getLatitude();
		lng_a = loc.getLongitude();
		lat_a=lat_a*Math.PI/180;
		lng_a=lng_a*Math.PI/180;
		lat_b=lat_b*Math.PI/180;
		lng_b=lng_b*Math.PI/180;

		d=Math.sin(lat_a)*Math.sin(lat_b)+Math.cos(lat_a)*Math.cos(lat_b)*Math.cos(lng_b-lng_a);
		d=Math.sqrt(1-d*d);
		d=Math.cos(lat_b)*Math.sin(lng_b-lng_a)/d;
		d=Math.asin(d)*180/Math.PI;

		// d = Math.round(d*10000);
		return d;
		}
	
}
