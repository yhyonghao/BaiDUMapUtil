package com.example.baidumapdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.model.LatLng;

public class MyLocationTool {
	public LocationClient mLocationClient = null;
	private Context context;
	public ProgressDialog LocationProg;
	private MyApplication application;
	public MyLocationListener myListener;
	private LocationResults LocationResults;

	public MyLocationTool(Context context) {
		this.context = context;
		application = MyApplication.getInstance();
		Activity activity = (Activity) context;
		myListener = new MyLocationListener();
		mLocationClient = new LocationClient(activity.getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
	}

	/**
	 * 设置定位参数(默认百度经纬度，高精度模式)
	 * 
	 * @param timer	定位时间间隔
	 * @param Address	是否开启详细地址
	 * @param DeviDir	是否开启手机指向
	 */
	public void setOption(int timer, boolean Address, boolean DeviDir) {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(timer);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(Address);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(DeviDir);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);
	}

	/**
	 * 开始定位（数据在application中获取）
	 */
	public void Location() {
		LocationProg = new ProgressDialog(context);
		LocationProg.setMessage("正在定位...");
		LocationProg.setCancelable(false);
		LocationProg.show();
		mLocationClient.start();
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestLocation();
		else
			Log.d("LocSDK3", "locClient is null or not started");
	}

	/**
	 * 定位监听
	 */
	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				LocationProg.dismiss();
				return;
			}

			String City = location.getCity();
			double Latitude = location.getLatitude();
			double Longitude = location.getLongitude();
			String province = location.getProvince();
			LatLng mylatLng = new LatLng(Latitude, Longitude);

			application.setCity(City);
			application.setLatitude(Latitude);
			application.setLongitude(Longitude);
			application.setProvince(province);
			application.setMylatLng(mylatLng);
			mLocationClient.stop();
			LocationProg.dismiss();
			if (LocationResults != null)
				LocationResults.Locationresults();
		}

		public void onReceivePoi(BDLocation poiLocation) {
			// 将在下个版本中去除poi功能
			if (poiLocation == null) {
				return;
			}

		}
	}

	/**
	 * 结果出现后执行监听
	 * 
	 * @param LocationResults
	 */
	public void setLocationResults(LocationResults LocationResults) {
		this.LocationResults = LocationResults;
	}

	/**
	 * 定位结果接口
	 * 
	 * @author Administrator
	 * 
	 */
	public interface LocationResults {
		public void Locationresults();
	}
}
