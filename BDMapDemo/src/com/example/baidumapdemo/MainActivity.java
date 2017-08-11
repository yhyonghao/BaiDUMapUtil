package com.example.baidumapdemo;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.example.baidumapdemo.MyLocationTool.LocationResults;
import com.example.baidumapdemo.MyPoiTool.PoiResults;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.Context;

public class MainActivity extends Activity implements PoiResults,
		LocationResults {
	private Context context = MainActivity.this;
	private MapView mapView;
	private MyApplication application;
	private MyMapTool mapTool;
	private MyLocationTool locationTool;
	private MyPoiTool poiTool;
	private List<PoiInfo> poiInfos;
	int res = R.drawable.icon_marka;

	public LocationClient mLocationClient = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		//工具类实例化必须在主线程进行
		setContentView(R.layout.activity_main);
		application = MyApplication.getInstance();
		mapView = (MapView) findViewById(R.id.bmapView);
		mapTool = new MyMapTool(context, mapView);
		locationTool = new MyLocationTool(context);
		poiTool = new MyPoiTool(context);
		locationTool.setLocationResults(this);
		poiTool.setPoiResults(this);
		locationTool.setOption(3000, true, false);
		locationTool.Location();

	}

	@Override
	public void Poiresults() {
		// 检索完成
		poiInfos = poiTool.getPoiInfo();
		if (poiInfos != null) {
			Log.d("POI", poiInfos.get(0).name);
			Log.d("POI", poiInfos.get(0).name);
			Log.d("POI", poiInfos.get(0).name);
			Log.d("POI", poiInfos.get(0).name);
			Log.d("POI", poiInfos.get(0).name);
			Log.d("POI", poiInfos.get(0).name);
		}
		List<LatLng> list = new ArrayList<LatLng>();
		for (int i = 0; i < poiInfos.size(); i++) {
			LatLng lng = poiInfos.get(i).location;
			list.add(lng);
		}
		mapTool.addmarkers(list, res);
		mapTool.TransitRouteOverlay(res, res, application.getMylatLng(),
				list.get(0), application.getCity());
	}

	@Override
	public void Locationresults() {
		// TODO Auto-generated method stub
		mapTool.setMapStatus(application.getMylatLng(), 13.0f);
		poiTool.Poistr(application.getMylatLng(), "高尔夫球场", 100000);
	}
}
