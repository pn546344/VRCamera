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
	TextView tLatitude,tLongitude;			//�n��,�g��
	String bestGPS;
	TextView tv6,tv8,tv10,tv12,tv14,tv16;
	Location testPoint1 = new Location("");	//GPS���է@���I1
	Location testPoint2 = new Location("");	//GPS���է@���I2
	Location testPoint3 = new Location("");	//GPS���է@���I3
	Location testPoint4 = new Location("");	//GPS���է@���I4
	SensorManager sm;
	float currentDegree = 0f;	
	LinkedList<PointData> list = new LinkedList<PointData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getActionBar();
        bar.hide();							//����ActionBar
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
        
        //�]�w���է@���I
        testPoint1.setLatitude(24.86301);	//�v�s��GPS��m
        testPoint1.setLongitude(120.9888763);
        testPoint2.setLatitude(24.8625994);	//�Ϯ��]GPS��m
        testPoint2.setLongitude(120.9896252);
        testPoint3.setLatitude(24.8625921);	//�E�W��GPS��m
        testPoint3.setLongitude(120.9898982);
        testPoint4.setLatitude(24.8637871);	//�q�⤤��GPS��m
        testPoint4.setLongitude(120.9903995);
        
        //����x�s
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
        
        //���o�ù��ѪR�פj�p
        DisplayMetrics metrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.i("ttt", "�ù��e"+metrics.widthPixels);
        Log.i("ttt", "�ù���"+metrics.heightPixels);
        tagview.setScanHeight(metrics.heightPixels);	//�N�ù����ǻ���tagview
        tagview.setScanWidth(metrics.widthPixels); 		//�N�ù��e�ǻ���tagview
        /* 
    	���USensor Listener (registerListener)
    	�Ĥ@�ӰѼ�: Sensor.TYPE_ORIENTATION(��V�ǷP��);
    	�ĤG�ӰѼ�: 
    		SENSOR_DELAY_FASTEST(0�@����);
    		SENSOR_DELAY_GAME(20,000�@����)
    		SENSOR_DELAY_UI(60,000�@����))
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
		// ���m�o�ͧ��ܮ�
		showLocation(arg0);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// ��������m��������
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// ��ҥΦ�m��������
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// ��w�쪬�A���ܮ�
		
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// �ǷP�����i�s����(��V����)
				if (arg0.sensor.getType() == Sensor.TYPE_ORIENTATION) {
					   float degree = arg0.values[0];
					   
					   /*
					   RotateAnimation���O�G�����ܤưʵe��
					    
					   �Ѽƻ���:
					   fromDegrees�G���઺�}�l���סC
					   toDegrees�G���઺�������סC
					   pivotXType�GX�b�����Y�Ҧ��A�i�H���Ȭ�ABSOLUTE�BRELATIVE_TO_SELF�BRELATIVE_TO_PARENT�C
					   pivotXValue�GX���Ъ����Y�ȡC
					   pivotYType�GY�b�����Y�Ҧ��A�i�H���Ȭ�ABSOLUTE�BRELATIVE_TO_SELF�BRELATIVE_TO_PARENT�C
					   pivotYValue�GY���Ъ����Y�ȡC
					   */
					   RotateAnimation ra = new RotateAnimation(
					     currentDegree, // �ʵe�_�l�ɪ��󪺨���
					     -degree,       // �ʵe�����ɪ�����઺����(�i�j��360��)-��ܰf�ɰw����,+��ܶ��ɰw����
					     Animation.RELATIVE_TO_SELF, 0.5f, //�ʵe�۹�󪫥�X�y�Ъ��}�l��m, �q0%~100%������, 50%������X��V���ФW�����I��m
					     Animation.RELATIVE_TO_SELF, 0.5f); //�ʵe�۹�󪫥�Y�y�Ъ��}�l��m, �q0%~100%������, 50%������Y��V���ФW�����I��m

					   ra.setDuration(200); // ����L�{����ɶ�
					   ra.setRepeatCount(-1); // �ʵe���Ʀ��� (-1 ��ܤ@������)
//					   img.startAnimation(ra); // ù�L�Ϥ��ϥα���ʵe
					   currentDegree = -degree; // �O�s����᪺�׼�, currentDegree�O�@�Ӧb�����w�q��float�����ܶq
					   Log.i("ttt", "currentDegree="+degree);
					   tv14.setText((int)degree+"");
					   tagview.setCompass((int)degree);
				}
		
	}

	private double gps2d(Location loc) {
		//��gps���I�y�Ъ�����
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
