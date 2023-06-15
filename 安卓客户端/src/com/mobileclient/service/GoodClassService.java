package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.GoodClass;
import com.mobileclient.util.HttpUtil;

/*物品类别管理业务逻辑层*/
public class GoodClassService {
	/* 添加物品类别 */
	public String AddGoodClass(GoodClass goodClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("goodClassId", goodClass.getGoodClassId() + "");
		params.put("goodClassName", goodClass.getGoodClassName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询物品类别 */
	public List<GoodClass> QueryGoodClass(GoodClass queryConditionGoodClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "GoodClassServlet?action=query";
		if(queryConditionGoodClass != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		GoodClassListHandler goodClassListHander = new GoodClassListHandler();
		xr.setContentHandler(goodClassListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<GoodClass> goodClassList = goodClassListHander.getGoodClassList();
		return goodClassList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<GoodClass> goodClassList = new ArrayList<GoodClass>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GoodClass goodClass = new GoodClass();
				goodClass.setGoodClassId(object.getInt("goodClassId"));
				goodClass.setGoodClassName(object.getString("goodClassName"));
				goodClassList.add(goodClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodClassList;
	}

	/* 更新物品类别 */
	public String UpdateGoodClass(GoodClass goodClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("goodClassId", goodClass.getGoodClassId() + "");
		params.put("goodClassName", goodClass.getGoodClassName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除物品类别 */
	public String DeleteGoodClass(int goodClassId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("goodClassId", goodClassId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "物品类别信息删除失败!";
		}
	}

	/* 根据物品类别id获取物品类别对象 */
	public GoodClass GetGoodClass(int goodClassId)  {
		List<GoodClass> goodClassList = new ArrayList<GoodClass>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("goodClassId", goodClassId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GoodClass goodClass = new GoodClass();
				goodClass.setGoodClassId(object.getInt("goodClassId"));
				goodClass.setGoodClassName(object.getString("goodClassName"));
				goodClassList.add(goodClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = goodClassList.size();
		if(size>0) return goodClassList.get(0); 
		else return null; 
	}
}
