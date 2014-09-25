package com.example.vrcamera;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class CameraView extends	SurfaceView implements Callback, AutoFocusCallback{

	SurfaceHolder holder;
	Context context;
	Camera camera;
	public CameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		//使用surfaceholder控制surfaceview
		holder=getHolder();
		holder.addCallback(this);
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		camera.startPreview();					//啟用相機擷取畫面
		camera.autoFocus(this);					//自動對焦(只會對焦一次)
	}	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// surfaceholder改變時
		try {
			camera = Camera.open();				//開啟照相機
			camera.setPreviewDisplay(holder);	//設定相機視頻的顯示位置
			camera.setDisplayOrientation(90);	//設定擷取的畫面旋轉角度
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// surfaceholder撤銷時
		camera.stopPreview();					//停止相機擷取畫面
		camera.release();						//釋放相機資源
		
	}
	@Override
	public void onAutoFocus(boolean arg0, Camera arg1) {
		// TODO Auto-generated method stub
		
	}

	
	

}
