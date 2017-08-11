package com.example.baidumapdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

/**
 * 百度地图基础工具类
 * 
 * @author Ghost
 * 
 */

public class MyMapTool {
	public BaiduMap mBaiduMap;
	private onPopWindowClick onClick;
	private Context context;
	public RoutePlanSearch mSearch;
	private RouteResults routeResults;

	public MyMapTool(Context context, MapView mapView) {
		this.context = context;
		// 去掉地图自带加减控件
		int count = mapView.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = mapView.getChildAt(i);

			if (child instanceof ZoomControls) {
				child.setVisibility(View.INVISIBLE);
			}
		}
		mBaiduMap = mapView.getMap();
		mSearch = RoutePlanSearch.newInstance();
	}

	/**
	 * 设置地图显示类型（0为普通地图，其他值为卫星地图）
	 * 
	 * @param arg
	 */
	public void setMapType(int arg) {
		if (arg == 0) {
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		} else {
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		}
	}

	/**
	 * 设置地图状态
	 * 
	 * @param target	中心点坐标
	 * @param zoom	地图放大倍数
	 */
	public void setMapStatus(LatLng target, float zoom) {
		// 定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder().target(target)
				.zoom(zoom).build();
		// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 改变地图状态
		mBaiduMap.setMapStatus(mMapStatusUpdate);
	}

	/**
	 * 添加覆盖物
	 * 
	 * @param point	坐标
	 * @param resource	图片Res
	 */
	public void addMarker(LatLng point, int resource) {
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(resource);
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);
		mBaiduMap.addOverlay(option);
	}

	/**
	 * 添加多个覆盖物
	 * 
	 * @param map1	纬度map
	 * @param map2	经度map
	 * @param resources	图片Res
	 * @return 覆盖物marker
	 */
	public List<Marker> addMarkers(Map<String, Double> map1,
			Map<String, Double> map2, int resources) {
		List<Marker> lst = new ArrayList<Marker>();
		int n = map1.size();
		for (int i = 0; i < n; i++) {
			LatLng latlng = new LatLng(map1.get(i), map2.get(i));
			OverlayOptions o = new MarkerOptions().position(latlng)
					.icon(BitmapDescriptorFactory.fromResource(resources))
					.zIndex(i + 1);
			Marker mMarker = (Marker) (mBaiduMap.addOverlay(o));
			lst.add(mMarker);
		}
		return lst;
	}

	/**
	 * 添加多个覆盖物
	 * 
	 * @param latLngs	坐标List
	 * @param resources	图片Res
	 * @return 覆盖物marker
	 */
	public List<Marker> addMarkers(List<LatLng> latLngs, int resources) {
		List<Marker> lst = new ArrayList<Marker>();
		int n = latLngs.size();
		for (int i = 0; i < n; i++) {
			OverlayOptions o = new MarkerOptions().position(latLngs.get(i))
					.icon(BitmapDescriptorFactory.fromResource(resources))
					.zIndex(i + 1);
			Marker mMarker = (Marker) (mBaiduMap.addOverlay(o));
			lst.add(mMarker);
		}
		return lst;
	}

	/**
	 * 添加多个覆盖物
	 * 
	 * @param latLngs	坐标List
	 * @param resources	图片Res
	 * @return 覆盖物marker
	 */
	public void addmarkers(List<LatLng> latLngs, int resources) {

		int n = latLngs.size();
		for (int i = 0; i < n; i++) {
			OverlayOptions o = new MarkerOptions().position(latLngs.get(i))
					.icon(BitmapDescriptorFactory.fromResource(resources))
					.zIndex(i + 1);
			mBaiduMap.addOverlay(o);
		}

	}

	/**
	 * 添加多个覆盖物
	 * 
	 * @param map1	纬度map
	 * @param map2	经度map
	 * @param resources	图片Res
	 * @return 覆盖物marker
	 */
	public void addmarkers(Map<String, Double> map1, Map<String, Double> map2,
			int resources) {
		int n = map1.size();
		for (int i = 0; i < n; i++) {
			LatLng latlng = new LatLng(map1.get(i), map2.get(i));
			OverlayOptions o = new MarkerOptions().position(latlng)
					.icon(BitmapDescriptorFactory.fromResource(resources))
					.zIndex(i + 1);
			mBaiduMap.addOverlay(o);
		}
	}

	/**
	 * 添加文字覆盖物
	 * 
	 * @param LatLng	坐标
	 * @param txt	文字
	 * @param bgColor	背景色
	 * @param fontSize	size
	 * @param fontColor	color
	 * @param rotate	圆角度
	 */
	public void addTextMarker(LatLng LatLng, String txt, int bgColor,
			int fontSize, int fontColor, float rotate) {
		OverlayOptions textOption = new TextOptions().bgColor(bgColor)
				.fontSize(fontSize).fontColor(fontColor).text(txt)
				.rotate(rotate).position(LatLng);
		mBaiduMap.addOverlay(textOption);
	}

	/**
	 * 放大地图
	 * 
	 */
	public void boostMap() {
		float zoomfloat = mBaiduMap.getMapStatus().zoom;
		zoomfloat = zoomfloat + 1.0f;
		if (zoomfloat <= 19) {
			MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(zoomfloat);
			mBaiduMap.setMapStatus(msu);
		} else {
			zoomfloat = 19;
		}
	}

	/**
	 * 缩小地图
	 * 
	 */
	public void lessenMap() {
		float zoomfloat = mBaiduMap.getMapStatus().zoom;
		zoomfloat = zoomfloat - 1.0f;
		if (zoomfloat >= 3) {
			MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(zoomfloat);
			mBaiduMap.setMapStatus(msu);
		} else {
			zoomfloat = 3;
		}
	}

	/**
	 * 弹出窗覆盖物
	 * 
	 * @param marker
	 * @param v
	 */
	public void addPopWindow(final Marker marker, final View v) {
		// 创建InfoWindow的点击事件监听者
		OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
			public void onInfoWindowClick() {
				// 添加点击后的事件响应代码
				if (onClick != null)
					onClick.PopWindowClick(marker, v);
			}
		};
		LatLng LatLng = marker.getPosition();
		Point p = mBaiduMap.getProjection().toScreenLocation(LatLng);
		p.y -= 47;
		InfoWindow mInfoWindow = new InfoWindow(v, LatLng, listener);
		// 显示InfoWindow
		mBaiduMap.showInfoWindow(mInfoWindow);
		// 地图状态改变，弹窗消失
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {
				// TODO Auto-generated method stub
				// 地图改变取消窗口
				mBaiduMap.hideInfoWindow();
			}

			@Override
			public void onMapStatusChangeFinish(MapStatus arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMapStatusChange(MapStatus arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Route展示换乘信息(路线图)
	 * 
	 * @param Staricon
	 *            起点图标
	 * @param Tericon
	 *            终点图标
	 * @param from
	 *            起点坐标
	 * @param to
	 *            终点坐标
	 * @param city
	 *            所在城市
	 */
	public void TransitRouteOverlay(final int Staricon, final int Tericon,
			LatLng from, LatLng to, String city) {

		class MyTransitRouteOverlay extends TransitRouteOverlay {

			public MyTransitRouteOverlay(BaiduMap baiduMap) {
				super(baiduMap);
			}

			@Override
			public BitmapDescriptor getStartMarker() {
				// TODO Auto-generated method stub
				return BitmapDescriptorFactory.fromResource(Staricon);
			}

			@Override
			public BitmapDescriptor getTerminalMarker() {
				// TODO Auto-generated method stub
				return BitmapDescriptorFactory.fromResource(Tericon);
			}
		}
		OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
			public void onGetWalkingRouteResult(WalkingRouteResult result) {

			}

			public void onGetTransitRouteResult(TransitRouteResult result) {
				if (result == null
						|| result.error != SearchResult.ERRORNO.NO_ERROR) {
					Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT)
							.show();
				}
				if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
					// result.getSuggestAddrInfo()
					return;
				}
				if (result.error == SearchResult.ERRORNO.NO_ERROR) {
					TransitRouteOverlay overlay = new MyTransitRouteOverlay(
							mBaiduMap);
					try {
						mBaiduMap.setOnMarkerClickListener(overlay);
						overlay.setData(result.getRouteLines().get(0));
						overlay.addToMap();
						overlay.zoomToSpan();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (routeResults != null)
						routeResults.Routeresults();
				}
			}

			public void onGetDrivingRouteResult(DrivingRouteResult result) {
				//
			}
		};
		mSearch.setOnGetRoutePlanResultListener(listener);
		PlanNode stNode2 = PlanNode.withLocation(from);
		PlanNode enNode2 = PlanNode.withLocation(to);
		mSearch.transitSearch((new TransitRoutePlanOption()).from(stNode2)
				.city(city).to(enNode2));
	}

	/**
	 * 弹出窗单击监听事件
	 * 
	 * @param onClick
	 */
	public void setonPopWindowClick(onPopWindowClick onClick) {
		this.onClick = onClick;
	}

	/**
	 * 弹出窗单击接口
	 * 
	 * @author Administrator
	 * 
	 */
	public interface onPopWindowClick {
		public void PopWindowClick(Marker marker, View v);
	}

	/**
	 * 清楚所有覆盖物
	 */
	public void clearOverlay() {
		mBaiduMap.clear();
	}

	/**
	 * 路线图返回结果监听
	 * 
	 * @param poiResults
	 */
	public void setRouteResult(RouteResults routeResults) {
		this.routeResults = routeResults;
	}

	/**
	 * 路线图返回结果接口
	 * 
	 * @author Administrator
	 * 
	 */
	public interface RouteResults {
		public void Routeresults();
	}

}
