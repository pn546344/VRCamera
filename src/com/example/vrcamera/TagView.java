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
import android.util.AttributeSet;
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
	int width,height;
	Canvas canvas;
	Bitmap bm;
	public TagView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		holder = getHolder();
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.powercenter);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			bm =zoomBitmap(bm, 1000, 1000);
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
// 			canvas.drawARGB(0,255, 255,255);
//			canvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR);
			
			canvas.drawBitmap(bm,0, 0 ,null );
			Paint paint = new Paint();
			paint.setColor(Color.BLACK);
			paint.setTextSize(100);
			canvas.drawText("¹qºâ¤¤¤ß", 300, 100, paint);
			canvas.drawText("500m", 300, 200, paint);
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
	Bitmap zoomBitmap(Bitmap bitmap, int width,int height){
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		int scaleWidth = (int) ((float)width/w);
		int scaleHeight = (int) ((float)height/h);
		matrix.setScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bm, 0, 0, w, h, matrix, true);
		
		return newbmp;
	}
}
