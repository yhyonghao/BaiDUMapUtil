package com.example.baidumapdemo;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	public static MyApplication instance;
	public static Context context;
	// 经度
	private double Longitude;
	// 纬度
	private double Latitude;
	// 城市
	private String City;
	// 省份
	private String province;
	// 我的坐标
	private LatLng mylatLng;

	public LatLng getMylatLng() {
		return mylatLng;
	}

	public void setMylatLng(LatLng mylatLng) {
		this.mylatLng = mylatLng;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		context = getApplicationContext();
		SDKInitializer.initialize(context);
	}

	public static MyApplication getInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

}
