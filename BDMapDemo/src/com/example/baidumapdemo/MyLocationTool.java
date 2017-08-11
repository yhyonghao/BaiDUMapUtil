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
		mLocationClient = new LocationClient(activity.getApplicationContext()); // ����LocationClient��
		mLocationClient.registerLocationListener(myListener); // ע���������
	}

	/**
	 * ���ö�λ����(Ĭ�ϰٶȾ�γ�ȣ��߾���ģʽ)
	 * 
	 * @param timer	��λʱ����
	 * @param Address	�Ƿ�����ϸ��ַ
	 * @param DeviDir	�Ƿ����ֻ�ָ��
	 */
	public void setOption(int timer, boolean Address, boolean DeviDir) {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ���ö�λģʽ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		option.setScanSpan(timer);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(Address);// ���صĶ�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(DeviDir);// ���صĶ�λ��������ֻ���ͷ�ķ���
		mLocationClient.setLocOption(option);
	}

	/**
	 * ��ʼ��λ��������application�л�ȡ��
	 */
	public void Location() {
		LocationProg = new ProgressDialog(context);
		LocationProg.setMessage("���ڶ�λ...");
		LocationProg.setCancelable(false);
		LocationProg.show();
		mLocationClient.start();
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestLocation();
		else
			Log.d("LocSDK3", "locClient is null or not started");
	}

	/**
	 * ��λ����
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
			// �����¸��汾��ȥ��poi����
			if (poiLocation == null) {
				return;
			}

		}
	}

	/**
	 * ������ֺ�ִ�м���
	 * 
	 * @param LocationResults
	 */
	public void setLocationResults(LocationResults LocationResults) {
		this.LocationResults = LocationResults;
	}

	/**
	 * ��λ����ӿ�
	 * 
	 * @author Administrator
	 * 
	 */
	public interface LocationResults {
		public void Locationresults();
	}
}
