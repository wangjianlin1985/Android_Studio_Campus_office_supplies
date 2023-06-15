package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.GoodUse;
import com.mobileclient.util.HttpUtil;

/*��Ʒ���ù���ҵ���߼���*/
public class GoodUseService {
	/* �����Ʒ���� */
	public String AddGoodUse(GoodUse goodUse) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("useId", goodUse.getUseId() + "");
		params.put("goodObj", goodUse.getGoodObj());
		params.put("useCount", goodUse.getUseCount() + "");
		params.put("price", goodUse.getPrice() + "");
		params.put("totalMoney", goodUse.getTotalMoney() + "");
		params.put("useTime", goodUse.getUseTime().toString());
		params.put("userMan", goodUse.getUserMan());
		params.put("operatorMan", goodUse.getOperatorMan());
		params.put("storeHouse", goodUse.getStoreHouse());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodUseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ��Ʒ���� */
	public List<GoodUse> QueryGoodUse(GoodUse queryConditionGoodUse) throws Exception {
		String urlString = HttpUtil.BASE_URL + "GoodUseServlet?action=query";
		if(queryConditionGoodUse != null) {
			urlString += "&goodObj=" + URLEncoder.encode(queryConditionGoodUse.getGoodObj(), "UTF-8") + "";
			if(queryConditionGoodUse.getUseTime() != null) {
				urlString += "&useTime=" + URLEncoder.encode(queryConditionGoodUse.getUseTime().toString(), "UTF-8");
			}
			urlString += "&operatorMan=" + URLEncoder.encode(queryConditionGoodUse.getOperatorMan(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		GoodUseListHandler goodUseListHander = new GoodUseListHandler();
		xr.setContentHandler(goodUseListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<GoodUse> goodUseList = goodUseListHander.getGoodUseList();
		return goodUseList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<GoodUse> goodUseList = new ArrayList<GoodUse>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GoodUse goodUse = new GoodUse();
				goodUse.setUseId(object.getInt("useId"));
				goodUse.setGoodObj(object.getString("goodObj"));
				goodUse.setUseCount(object.getInt("useCount"));
				goodUse.setPrice((float) object.getDouble("price"));
				goodUse.setTotalMoney((float) object.getDouble("totalMoney"));
				goodUse.setUseTime(Timestamp.valueOf(object.getString("useTime")));
				goodUse.setUserMan(object.getString("userMan"));
				goodUse.setOperatorMan(object.getString("operatorMan"));
				goodUse.setStoreHouse(object.getString("storeHouse"));
				goodUseList.add(goodUse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodUseList;
	}

	/* ������Ʒ���� */
	public String UpdateGoodUse(GoodUse goodUse) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("useId", goodUse.getUseId() + "");
		params.put("goodObj", goodUse.getGoodObj());
		params.put("useCount", goodUse.getUseCount() + "");
		params.put("price", goodUse.getPrice() + "");
		params.put("totalMoney", goodUse.getTotalMoney() + "");
		params.put("useTime", goodUse.getUseTime().toString());
		params.put("userMan", goodUse.getUserMan());
		params.put("operatorMan", goodUse.getOperatorMan());
		params.put("storeHouse", goodUse.getStoreHouse());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodUseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ����Ʒ���� */
	public String DeleteGoodUse(int useId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("useId", useId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodUseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ʒ������Ϣɾ��ʧ��!";
		}
	}

	/* ��������id��ȡ��Ʒ���ö��� */
	public GoodUse GetGoodUse(int useId)  {
		List<GoodUse> goodUseList = new ArrayList<GoodUse>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("useId", useId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodUseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GoodUse goodUse = new GoodUse();
				goodUse.setUseId(object.getInt("useId"));
				goodUse.setGoodObj(object.getString("goodObj"));
				goodUse.setUseCount(object.getInt("useCount"));
				goodUse.setPrice((float) object.getDouble("price"));
				goodUse.setTotalMoney((float) object.getDouble("totalMoney"));
				goodUse.setUseTime(Timestamp.valueOf(object.getString("useTime")));
				goodUse.setUserMan(object.getString("userMan"));
				goodUse.setOperatorMan(object.getString("operatorMan"));
				goodUse.setStoreHouse(object.getString("storeHouse"));
				goodUseList.add(goodUse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = goodUseList.size();
		if(size>0) return goodUseList.get(0); 
		else return null; 
	}
}
