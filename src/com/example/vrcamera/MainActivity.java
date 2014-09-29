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
	TextView tLatitude,tLongitude;			//�n��,�g��
	String bestGPS;
	TextView tv6,tv8,tv10,tv12;
	Location testPoint1 = new Location("");	//GPS���է@���I1
	Location testPoint2 = new Location("");	//GPS���է@���I2
	Location testPoint3 = new Location("");	//GPS���է@���I3
	Location testPoint4 = new Location("");	//GPS���է@���I4
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getActionBar();
        bar.hide();							//����ActionBar
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
        
        //�]�w���է@���I
        testPoint1.setLatitude(24.86301);	//�v�s��GPS��m
        testPoint1.setLongitude(120.9888763);
        testPoint2.setLatitude(24.8625994);	//�Ϯ��]GPS��m
        testPoint2.setLongitude(120.9896252);
        testPoint3.setLatitude(24.8625921);	//�E�W��GPS��m
        testPoint3.setLongitude(120.9898982);
        testPoint4.setLatitude(24.8637871);	//�q�⤤��GPS��m
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

}
