package com.example.vrcamera;

import android.R.color;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TagView extends SurfaceView implements	Runnable{

	SurfaceHolder holder;
	Context context;
	boolean stop = false;
	Thread t;
	int y = 0;
	int width,height , compass;
	Canvas canvas;
	Bitmap bm;
	LocationManager locManager;
	Location loc ,tagLoc;
	String bestGPS;
	String GPS = null;
	int a=0,b=0,c=0;
	int angle = 0;
	double scanHeight ,scanWidth, gpsAngle;
	ProgressDialog dialog ;
	//LocationManager lManager;
	public TagView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		holder = getHolder();
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.powercenter);
		dialog = new ProgressDialog(this.context);
		/*
		 * 在其他地方使用Context服務
		 * lManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		 */
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(!stop)
		{
			if(GPS.equals(null)){
				dialog.setMessage("GPS準備中....");
				dialog.show();
				continue;
			}
			if(!holder.getSurface().isValid())
				continue;
			dialog.cancel();
			canvas = holder.lockCanvas();	
			canvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR); //重製畫面使tagview維持透明
//			canvas.drawBitmap(bm,0, 0 ,null );
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setTextSize(10);
			
			//讓圖片及文字可以自動依照距離變化大小
			if(!GPS.equals(null))
			{
				if(Double.parseDouble(GPS) > 300)
				{
					canvas.drawText(GPS, 300, 200, paint);
					canvas.drawText("電算中心", 300, 100, paint);
					if(a==0)
					{
						bm = BitmapFactory.decodeResource(getResources(), R.drawable.powercenter);
						bm =zoomBitmap(bm, 0.3f, 0.3f);
						a++;
						b=0;
						c=0;
					}
					canvas.drawBitmap(bm,0, 0 ,null );
			}else if(Double.parseDouble(GPS) > 100 && Double.parseDouble(GPS) <= 300 )
				{
					canvas.drawText(GPS, 300, 200, paint);
					canvas.drawText("電算中心", bm.getWidth()+20, (float) (scanHeight/3)+100, paint);
					
					if(b==0)
					{	
						
						bm = BitmapFactory.decodeResource(getResources(), R.drawable.powercenter);
						//bm =zoomBitmap(bm, 0.5f, 0.5f);
						b++;
						a=0;
						c=0;
					}
					angle = (int) (compass-gpsAngle);
					if(angle >0 && angle <=90)
						canvas.drawBitmap(bm,(float) (scanWidth/2-angle*10), (float) (scanHeight/3) ,null );
					if(angle<0)
						canvas.drawBitmap(bm,(float) (scanWidth/2-angle*10), (float) (scanHeight/3) ,null );
					if(angle==0)
						canvas.drawBitmap(bm,(float) (scanWidth/2), (float) (scanHeight/3) ,null );
					Log.i("aaa", "angle="+angle);
				}else if(Double.parseDouble(GPS)<=100 )
				{
					canvas.drawText(GPS, 300, 200, paint);
					canvas.drawText("電算中心", bm.getWidth()+20, (float) (scanHeight/3*2)+100, paint);
					if(c==0)
					{
						
						bm = BitmapFactory.decodeResource(getResources(), R.drawable.powercenter);
						c++;
						a=0;
						b=0;
					}
					canvas.drawBitmap(bm,0, (float) (scanHeight/3*2) ,null );
				}
			}
//			Log.i("ttt", GPS);
//			Log.i("ttt", "圖片寬:"+bm.getWidth());
//			Log.i("ttt", "圖片高:"+bm.getHeight()+"");
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

	public void setGPS(String gps) {
		GPS = gps;
	}
	
	public void setGPS2d(double gps) {
		//設定兩點gps的方位角
		gpsAngle = gps;
	}
	
	public void setCompass(int compass) {
		//設定羅盤角度
		this.compass = compass;
	}
	
	public void setScanHeight(double height) {
		scanHeight = height;
	}
	
	public void setScanWidth(double width) {
		scanWidth = width;
	}

}
