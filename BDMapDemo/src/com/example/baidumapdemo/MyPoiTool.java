package com.example.baidumapdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

/**
 * Poi������
 * 
 * @author Administrator
 * 
 */
public class MyPoiTool {
	public PoiSearch mPoiSearch;
	private Context context;
	private PoiResults poiResults;
	public ProgressDialog PoiProg;
	private List<PoiInfo> poiinfo = new ArrayList<PoiInfo>();

	public MyPoiTool(Context context) {
		this.context = context;
		// TODO Auto-generated constructor stub
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
	}

	/**
	 * POI��������
	 */
	public OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

		public void onGetPoiResult(PoiResult result) {
			// ��ȡPOI�������
			PoiProg.dismiss();
			if (result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
			} else {
				poiinfo = result.getAllPoi();
				if (poiResults != null)
					poiResults.Poiresults();
			}
		}

		public void onGetPoiDetailResult(PoiDetailResult result) {
			// ��ȡPlace����ҳ�������
		}
	};

	/**
	 * POI��������
	 * 
	 * @param latLng	��������
	 * @param key	�ؼ���
	 * @param num	�����뾶
	 */
	public void Poistr(LatLng latLng, String key, int num) {
		PoiProg = new ProgressDialog(context);
		PoiProg.setMessage("���ڼ���...");
		PoiProg.setCancelable(false);
		PoiProg.show();
		mPoiSearch.searchNearby(new PoiNearbySearchOption().location(latLng)
				.keyword(key).radius(num));
	}

	/**
	 * ��ȡ�������
	 * 
	 * @return
	 */
	public List<PoiInfo> getPoiInfo() {
		return poiinfo;
	}

	/**
	 * ������ֺ�ִ�м���
	 * 
	 * @param poiResults
	 */
	public void setPoiResults(PoiResults poiResults) {
		this.poiResults = poiResults;
	}

	/**
	 * ��������ӿ�
	 * 
	 * @author Administrator
	 * 
	 */
	public interface PoiResults {
		public void Poiresults();
	}
}
