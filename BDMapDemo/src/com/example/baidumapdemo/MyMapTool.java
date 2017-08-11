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
 * �ٶȵ�ͼ����������
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
		// ȥ����ͼ�Դ��Ӽ��ؼ�
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
	 * ���õ�ͼ��ʾ���ͣ�0Ϊ��ͨ��ͼ������ֵΪ���ǵ�ͼ��
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
	 * ���õ�ͼ״̬
	 * 
	 * @param target	���ĵ�����
	 * @param zoom	��ͼ�Ŵ���
	 */
	public void setMapStatus(LatLng target, float zoom) {
		// �����ͼ״̬
		MapStatus mMapStatus = new MapStatus.Builder().target(target)
				.zoom(zoom).build();
		// ����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// �ı��ͼ״̬
		mBaiduMap.setMapStatus(mMapStatusUpdate);
	}

	/**
	 * ��Ӹ�����
	 * 
	 * @param point	����
	 * @param resource	ͼƬRes
	 */
	public void addMarker(LatLng point, int resource) {
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(resource);
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);
		mBaiduMap.addOverlay(option);
	}

	/**
	 * ��Ӷ��������
	 * 
	 * @param map1	γ��map
	 * @param map2	����map
	 * @param resources	ͼƬRes
	 * @return ������marker
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
	 * ��Ӷ��������
	 * 
	 * @param latLngs	����List
	 * @param resources	ͼƬRes
	 * @return ������marker
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
	 * ��Ӷ��������
	 * 
	 * @param latLngs	����List
	 * @param resources	ͼƬRes
	 * @return ������marker
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
	 * ��Ӷ��������
	 * 
	 * @param map1	γ��map
	 * @param map2	����map
	 * @param resources	ͼƬRes
	 * @return ������marker
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
	 * ������ָ�����
	 * 
	 * @param LatLng	����
	 * @param txt	����
	 * @param bgColor	����ɫ
	 * @param fontSize	size
	 * @param fontColor	color
	 * @param rotate	Բ�Ƕ�
	 */
	public void addTextMarker(LatLng LatLng, String txt, int bgColor,
			int fontSize, int fontColor, float rotate) {
		OverlayOptions textOption = new TextOptions().bgColor(bgColor)
				.fontSize(fontSize).fontColor(fontColor).text(txt)
				.rotate(rotate).position(LatLng);
		mBaiduMap.addOverlay(textOption);
	}

	/**
	 * �Ŵ��ͼ
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
	 * ��С��ͼ
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
	 * ������������
	 * 
	 * @param marker
	 * @param v
	 */
	public void addPopWindow(final Marker marker, final View v) {
		// ����InfoWindow�ĵ���¼�������
		OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {
			public void onInfoWindowClick() {
				// ��ӵ������¼���Ӧ����
				if (onClick != null)
					onClick.PopWindowClick(marker, v);
			}
		};
		LatLng LatLng = marker.getPosition();
		Point p = mBaiduMap.getProjection().toScreenLocation(LatLng);
		p.y -= 47;
		InfoWindow mInfoWindow = new InfoWindow(v, LatLng, listener);
		// ��ʾInfoWindow
		mBaiduMap.showInfoWindow(mInfoWindow);
		// ��ͼ״̬�ı䣬������ʧ
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {
				// TODO Auto-generated method stub
				// ��ͼ�ı�ȡ������
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
	 * Routeչʾ������Ϣ(·��ͼ)
	 * 
	 * @param Staricon
	 *            ���ͼ��
	 * @param Tericon
	 *            �յ�ͼ��
	 * @param from
	 *            �������
	 * @param to
	 *            �յ�����
	 * @param city
	 *            ���ڳ���
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
					Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT)
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
	 * ���������������¼�
	 * 
	 * @param onClick
	 */
	public void setonPopWindowClick(onPopWindowClick onClick) {
		this.onClick = onClick;
	}

	/**
	 * �����������ӿ�
	 * 
	 * @author Administrator
	 * 
	 */
	public interface onPopWindowClick {
		public void PopWindowClick(Marker marker, View v);
	}

	/**
	 * ������и�����
	 */
	public void clearOverlay() {
		mBaiduMap.clear();
	}

	/**
	 * ·��ͼ���ؽ������
	 * 
	 * @param poiResults
	 */
	public void setRouteResult(RouteResults routeResults) {
		this.routeResults = routeResults;
	}

	/**
	 * ·��ͼ���ؽ���ӿ�
	 * 
	 * @author Administrator
	 * 
	 */
	public interface RouteResults {
		public void Routeresults();
	}

}
