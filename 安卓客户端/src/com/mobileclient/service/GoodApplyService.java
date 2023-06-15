package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.GoodApply;
import com.mobileclient.util.HttpUtil;

/*��Ʒ�������ҵ���߼���*/
public class GoodApplyService {
	/* �����Ʒ���� */
	public String AddGoodApply(GoodApply goodApply) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("applyId", goodApply.getApplyId() + "");
		params.put("goodObj", goodApply.getGoodObj());
		params.put("applyCount", goodApply.getApplyCount() + "");
		params.put("applyTime", goodApply.getApplyTime());
		params.put("personObj", goodApply.getPersonObj());
		params.put("handlePerson", goodApply.getHandlePerson());
		params.put("applyMemo", goodApply.getApplyMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodApplyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ��Ʒ���� */
	public List<GoodApply> QueryGoodApply(GoodApply queryConditionGoodApply) throws Exception {
		String urlString = HttpUtil.BASE_URL + "GoodApplyServlet?action=query";
		if(queryConditionGoodApply != null) {
			urlString += "&goodObj=" + URLEncoder.encode(queryConditionGoodApply.getGoodObj(), "UTF-8") + "";
			urlString += "&applyTime=" + URLEncoder.encode(queryConditionGoodApply.getApplyTime(), "UTF-8") + "";
			urlString += "&personObj=" + URLEncoder.encode(queryConditionGoodApply.getPersonObj(), "UTF-8") + "";
			urlString += "&handlePerson=" + URLEncoder.encode(queryConditionGoodApply.getHandlePerson(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		GoodApplyListHandler goodApplyListHander = new GoodApplyListHandler();
		xr.setContentHandler(goodApplyListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<GoodApply> goodApplyList = goodApplyListHander.getGoodApplyList();
		return goodApplyList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<GoodApply> goodApplyList = new ArrayList<GoodApply>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GoodApply goodApply = new GoodApply();
				goodApply.setApplyId(object.getInt("applyId"));
				goodApply.setGoodObj(object.getString("goodObj"));
				goodApply.setApplyCount(object.getInt("applyCount"));
				goodApply.setApplyTime(object.getString("applyTime"));
				goodApply.setPersonObj(object.getString("personObj"));
				goodApply.setHandlePerson(object.getString("handlePerson"));
				goodApply.setApplyMemo(object.getString("applyMemo"));
				goodApplyList.add(goodApply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodApplyList;
	}

	/* ������Ʒ���� */
	public String UpdateGoodApply(GoodApply goodApply) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("applyId", goodApply.getApplyId() + "");
		params.put("goodObj", goodApply.getGoodObj());
		params.put("applyCount", goodApply.getApplyCount() + "");
		params.put("applyTime", goodApply.getApplyTime());
		params.put("personObj", goodApply.getPersonObj());
		params.put("handlePerson", goodApply.getHandlePerson());
		params.put("applyMemo", goodApply.getApplyMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodApplyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ����Ʒ���� */
	public String DeleteGoodApply(int applyId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("applyId", applyId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodApplyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ʒ������Ϣɾ��ʧ��!";
		}
	}

	/* ��������id��ȡ��Ʒ������� */
	public GoodApply GetGoodApply(int applyId)  {
		List<GoodApply> goodApplyList = new ArrayList<GoodApply>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("applyId", applyId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodApplyServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				GoodApply goodApply = new GoodApply();
				goodApply.setApplyId(object.getInt("applyId"));
				goodApply.setGoodObj(object.getString("goodObj"));
				goodApply.setApplyCount(object.getInt("applyCount"));
				goodApply.setApplyTime(object.getString("applyTime"));
				goodApply.setPersonObj(object.getString("personObj"));
				goodApply.setHandlePerson(object.getString("handlePerson"));
				goodApply.setApplyMemo(object.getString("applyMemo"));
				goodApplyList.add(goodApply);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = goodApplyList.size();
		if(size>0) return goodApplyList.get(0); 
		else return null; 
	}
}
