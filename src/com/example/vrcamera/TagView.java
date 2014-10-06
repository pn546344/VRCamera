package com.example.vrcamera;

import android.R.color;
import android.content.Context;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TagView extends SurfaceView implements	Runnable, LocationListener{

	SurfaceHolder holder;
	Context context;
	boolean stop = false;
	Thread t;
	int y = 0;
	int width,height;
	Canvas canvas;
	Bitmap bm;
	LocationManager locManager;
	Location loc ,tagLoc;
	String bestGPS;
	public TagView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		holder = getHolder();
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.powercenter);
		//��LocationManager���oGPS���
		locManager = (LocationManager)this.context.getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        bestGPS = locManager.getBestProvider(criteria, true);
        if(bestGPS != null){
        	loc = locManager.getLastKnownLocation(bestGPS);
        	locManager.requestLocationUpdates(bestGPS, 100, 1, this);
        }
        tagLoc = new Location("");
        tagLoc.setLatitude(24.8637871);
        tagLoc.setLongitude(120.9903995);
        Log.i("ttt", "TagView is work");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			bm =zoomBitmap(bm, 0.5f, 0.5f);
			Log.i("ttt", "zoom");
			break;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!stop)
		{
			if(!holder.getSurface().isValid())
				continue;
			canvas = holder.lockCanvas();	
			canvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR); //���s�e����tagview�����z��
			canvas.drawBitmap(bm,0, 0 ,null );
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setTextSize(100);
			canvas.drawText("�q�⤤��", 300, 100, paint);
			canvas.drawText(loc.distanceTo(tagLoc)+"", 300, 200, paint);
			holder.unlockCanvasAndPost(canvas);
		}
		
	}

	protected void pause() {
		stop = true;
		while (true) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	protected void resume() {
		stop = false;
		t = new Thread(this);
		t.start();
		Log.i("ttt", "start TagView");
	}
	
	Bitmap zoomBitmap(Bitmap bitmap, float width,float height){
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
//		int scaleWidth = (int) ((float)width/w);
//		int scaleHeight = (int) ((float)height/h);
		matrix.setScale(width, height);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	@Override
	public void onLocationChanged(Location location) {
		// ���m�o�ͧ��ܮ�
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// ��w�쪬�A���ܮ�
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// ��ҥΦ�m��������
	}

	@Override
	public void onProviderDisabled(String provider) {
		// ��������m��������
		
	}
}
