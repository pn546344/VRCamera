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
		//�ϥ�surfaceholder����surfaceview
		holder=getHolder();
		holder.addCallback(this);
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		camera.startPreview();					//�ҥά۾��^���e��
		camera.autoFocus(this);					//�۰ʹ�J(�u�|��J�@��)
	}	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// surfaceholder���ܮ�
		try {
			camera = Camera.open();				//�}�ҷӬ۾�
			camera.setPreviewDisplay(holder);	//�]�w�۾����W����ܦ�m
			camera.setDisplayOrientation(90);	//�]�w�^�����e�����ਤ��
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// surfaceholder�M�P��
		camera.stopPreview();					//����۾��^���e��
		camera.release();						//����۾��귽
		
	}
	@Override
	public void onAutoFocus(boolean arg0, Camera arg1) {
		// TODO Auto-generated method stub
		
	}

	
	

}
