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
 * Poi检索类
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
	 * POI检索监听
	 */
	public OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

		public void onGetPoiResult(PoiResult result) {
			// 获取POI检索结果
			PoiProg.dismiss();
			if (result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
			} else {
				poiinfo = result.getAllPoi();
				if (poiResults != null)
					poiResults.Poiresults();
			}
		}

		public void onGetPoiDetailResult(PoiDetailResult result) {
			// 获取Place详情页检索结果
		}
	};

	/**
	 * POI附近检索
	 * 
	 * @param latLng	中心坐标
	 * @param key	关键字
	 * @param num	搜索半径
	 */
	public void Poistr(LatLng latLng, String key, int num) {
		PoiProg = new ProgressDialog(context);
		PoiProg.setMessage("正在检索...");
		PoiProg.setCancelable(false);
		PoiProg.show();
		mPoiSearch.searchNearby(new PoiNearbySearchOption().location(latLng)
				.keyword(key).radius(num));
	}

	/**
	 * 获取检索结果
	 * 
	 * @return
	 */
	public List<PoiInfo> getPoiInfo() {
		return poiinfo;
	}

	/**
	 * 结果出现后执行监听
	 * 
	 * @param poiResults
	 */
	public void setPoiResults(PoiResults poiResults) {
		this.poiResults = poiResults;
	}

	/**
	 * 检索结果接口
	 * 
	 * @author Administrator
	 * 
	 */
	public interface PoiResults {
		public void Poiresults();
	}
}
