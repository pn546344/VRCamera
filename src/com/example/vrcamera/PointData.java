package com.example.vrcamera;

import android.graphics.Bitmap;

public class PointData {

	Bitmap bitmap;
	double latitude = 0 , longitude = 0;
	
	protected void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	protected void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	protected void setLongitude() {
		this.longitude = longitude;
	}
	
	protected Bitmap getBitmap() {
		return bitmap;
	}
	
	protected double getLatitude() {
		return latitude;
	}
	
	protected double getLongitude() {
		return	longitude;
	}
}
