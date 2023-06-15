package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Purchase;
import com.mobileclient.util.HttpUtil;

/*物品购置管理业务逻辑层*/
public class PurchaseService {
	/* 添加物品购置 */
	public String AddPurchase(Purchase purchase) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("purchaseId", purchase.getPurchaseId() + "");
		params.put("goodObj", purchase.getGoodObj());
		params.put("price", purchase.getPrice() + "");
		params.put("buyCount", purchase.getBuyCount() + "");
		params.put("totalMoney", purchase.getTotalMoney() + "");
		params.put("inDate", purchase.getInDate().toString());
		params.put("operatorMan", purchase.getOperatorMan());
		params.put("keepMan", purchase.getKeepMan());
		params.put("storeHouse", purchase.getStoreHouse());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PurchaseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询物品购置 */
	public List<Purchase> QueryPurchase(Purchase queryConditionPurchase) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PurchaseServlet?action=query";
		if(queryConditionPurchase != null) {
			urlString += "&goodObj=" + URLEncoder.encode(queryConditionPurchase.getGoodObj(), "UTF-8") + "";
			urlString += "&operatorMan=" + URLEncoder.encode(queryConditionPurchase.getOperatorMan(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PurchaseListHandler purchaseListHander = new PurchaseListHandler();
		xr.setContentHandler(purchaseListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Purchase> purchaseList = purchaseListHander.getPurchaseList();
		return purchaseList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Purchase purchase = new Purchase();
				purchase.setPurchaseId(object.getInt("purchaseId"));
				purchase.setGoodObj(object.getString("goodObj"));
				purchase.setPrice((float) object.getDouble("price"));
				purchase.setBuyCount(object.getInt("buyCount"));
				purchase.setTotalMoney((float) object.getDouble("totalMoney"));
				purchase.setInDate(Timestamp.valueOf(object.getString("inDate")));
				purchase.setOperatorMan(object.getString("operatorMan"));
				purchase.setKeepMan(object.getString("keepMan"));
				purchase.setStoreHouse(object.getString("storeHouse"));
				purchaseList.add(purchase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return purchaseList;
	}

	/* 更新物品购置 */
	public String UpdatePurchase(Purchase purchase) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("purchaseId", purchase.getPurchaseId() + "");
		params.put("goodObj", purchase.getGoodObj());
		params.put("price", purchase.getPrice() + "");
		params.put("buyCount", purchase.getBuyCount() + "");
		params.put("totalMoney", purchase.getTotalMoney() + "");
		params.put("inDate", purchase.getInDate().toString());
		params.put("operatorMan", purchase.getOperatorMan());
		params.put("keepMan", purchase.getKeepMan());
		params.put("storeHouse", purchase.getStoreHouse());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PurchaseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除物品购置 */
	public String DeletePurchase(int purchaseId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("purchaseId", purchaseId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PurchaseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "物品购置信息删除失败!";
		}
	}

	/* 根据购置id获取物品购置对象 */
	public Purchase GetPurchase(int purchaseId)  {
		List<Purchase> purchaseList = new ArrayList<Purchase>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("purchaseId", purchaseId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PurchaseServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Purchase purchase = new Purchase();
				purchase.setPurchaseId(object.getInt("purchaseId"));
				purchase.setGoodObj(object.getString("goodObj"));
				purchase.setPrice((float) object.getDouble("price"));
				purchase.setBuyCount(object.getInt("buyCount"));
				purchase.setTotalMoney((float) object.getDouble("totalMoney"));
				purchase.setInDate(Timestamp.valueOf(object.getString("inDate")));
				purchase.setOperatorMan(object.getString("operatorMan"));
				purchase.setKeepMan(object.getString("keepMan"));
				purchase.setStoreHouse(object.getString("storeHouse"));
				purchaseList.add(purchase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = purchaseList.size();
		if(size>0) return purchaseList.get(0); 
		else return null; 
	}
}
