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

/*��Ʒ������ҵ���߼���*/
public class GoodClassService {
	/* �����Ʒ��� */
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

	/* ��ѯ��Ʒ��� */
	public List<GoodClass> QueryGoodClass(GoodClass queryConditionGoodClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "GoodClassServlet?action=query";
		if(queryConditionGoodClass != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
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
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
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

	/* ������Ʒ��� */
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

	/* ɾ����Ʒ��� */
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
			return "��Ʒ�����Ϣɾ��ʧ��!";
		}
	}

	/* ������Ʒ���id��ȡ��Ʒ������ */
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
