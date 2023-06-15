package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Goods;
import com.mobileclient.util.HttpUtil;

/*�칫��Ʒ����ҵ���߼���*/
public class GoodsService {
	/* ��Ӱ칫��Ʒ */
	public String AddGoods(Goods goods) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("goodNo", goods.getGoodNo());
		params.put("goodClassObj", goods.getGoodClassObj() + "");
		params.put("goodName", goods.getGoodName());
		params.put("goodPhoto", goods.getGoodPhoto());
		params.put("specModel", goods.getSpecModel());
		params.put("measureUnit", goods.getMeasureUnit());
		params.put("stockCount", goods.getStockCount() + "");
		params.put("price", goods.getPrice() + "");
		params.put("totalMoney", goods.getTotalMoney() + "");
		params.put("storeHouse", goods.getStoreHouse());
		params.put("goodMemo", goods.getGoodMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ�칫��Ʒ */
	public List<Goods> QueryGoods(Goods queryConditionGoods) throws Exception {
		String urlString = HttpUtil.BASE_URL + "GoodsServlet?action=query";
		if(queryConditionGoods != null) {
			urlString += "&goodNo=" + URLEncoder.encode(queryConditionGoods.getGoodNo(), "UTF-8") + "";
			urlString += "&goodClassObj=" + queryConditionGoods.getGoodClassObj();
			urlString += "&goodName=" + URLEncoder.encode(queryConditionGoods.getGoodName(), "UTF-8") + "";
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		GoodsListHandler goodsListHander = new GoodsListHandler();
		xr.setContentHandler(goodsListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Goods> goodsList = goodsListHander.getGoodsList();
		return goodsList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Goods> goodsList = new ArrayList<Goods>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Goods goods = new Goods();
				goods.setGoodNo(object.getString("goodNo"));
				goods.setGoodClassObj(object.getInt("goodClassObj"));
				goods.setGoodName(object.getString("goodName"));
				goods.setGoodPhoto(object.getString("goodPhoto"));
				goods.setSpecModel(object.getString("specModel"));
				goods.setMeasureUnit(object.getString("measureUnit"));
				goods.setStockCount(object.getInt("stockCount"));
				goods.setPrice((float) object.getDouble("price"));
				goods.setTotalMoney((float) object.getDouble("totalMoney"));
				goods.setStoreHouse(object.getString("storeHouse"));
				goods.setGoodMemo(object.getString("goodMemo"));
				goodsList.add(goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return goodsList;
	}

	/* ���°칫��Ʒ */
	public String UpdateGoods(Goods goods) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("goodNo", goods.getGoodNo());
		params.put("goodClassObj", goods.getGoodClassObj() + "");
		params.put("goodName", goods.getGoodName());
		params.put("goodPhoto", goods.getGoodPhoto());
		params.put("specModel", goods.getSpecModel());
		params.put("measureUnit", goods.getMeasureUnit());
		params.put("stockCount", goods.getStockCount() + "");
		params.put("price", goods.getPrice() + "");
		params.put("totalMoney", goods.getTotalMoney() + "");
		params.put("storeHouse", goods.getStoreHouse());
		params.put("goodMemo", goods.getGoodMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ���칫��Ʒ */
	public String DeleteGoods(String goodNo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("goodNo", goodNo);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�칫��Ʒ��Ϣɾ��ʧ��!";
		}
	}

	/* ������Ʒ��Ż�ȡ�칫��Ʒ���� */
	public Goods GetGoods(String goodNo)  {
		List<Goods> goodsList = new ArrayList<Goods>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("goodNo", goodNo);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "GoodsServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Goods goods = new Goods();
				goods.setGoodNo(object.getString("goodNo"));
				goods.setGoodClassObj(object.getInt("goodClassObj"));
				goods.setGoodName(object.getString("goodName"));
				goods.setGoodPhoto(object.getString("goodPhoto"));
				goods.setSpecModel(object.getString("specModel"));
				goods.setMeasureUnit(object.getString("measureUnit"));
				goods.setStockCount(object.getInt("stockCount"));
				goods.setPrice((float) object.getDouble("price"));
				goods.setTotalMoney((float) object.getDouble("totalMoney"));
				goods.setStoreHouse(object.getString("storeHouse"));
				goods.setGoodMemo(object.getString("goodMemo"));
				goodsList.add(goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = goodsList.size();
		if(size>0) return goodsList.get(0); 
		else return null; 
	}
}
