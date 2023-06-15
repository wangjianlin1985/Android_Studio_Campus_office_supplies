package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Department;
import com.mobileclient.util.HttpUtil;

/*���Ź���ҵ���߼���*/
public class DepartmentService {
	/* ��Ӳ��� */
	public String AddDepartment(Department department) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("departmentId", department.getDepartmentId() + "");
		params.put("departmentName", department.getDepartmentName());
		params.put("departmentType", department.getDepartmentType());
		params.put("departmentMemo", department.getDepartmentMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DepartmentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ���� */
	public List<Department> QueryDepartment(Department queryConditionDepartment) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DepartmentServlet?action=query";
		if(queryConditionDepartment != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		DepartmentListHandler departmentListHander = new DepartmentListHandler();
		xr.setContentHandler(departmentListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Department> departmentList = departmentListHander.getDepartmentList();
		return departmentList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Department> departmentList = new ArrayList<Department>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Department department = new Department();
				department.setDepartmentId(object.getInt("departmentId"));
				department.setDepartmentName(object.getString("departmentName"));
				department.setDepartmentType(object.getString("departmentType"));
				department.setDepartmentMemo(object.getString("departmentMemo"));
				departmentList.add(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departmentList;
	}

	/* ���²��� */
	public String UpdateDepartment(Department department) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("departmentId", department.getDepartmentId() + "");
		params.put("departmentName", department.getDepartmentName());
		params.put("departmentType", department.getDepartmentType());
		params.put("departmentMemo", department.getDepartmentMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DepartmentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ������ */
	public String DeleteDepartment(int departmentId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("departmentId", departmentId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DepartmentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "������Ϣɾ��ʧ��!";
		}
	}

	/* ���ݲ��ű�Ż�ȡ���Ŷ��� */
	public Department GetDepartment(int departmentId)  {
		List<Department> departmentList = new ArrayList<Department>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("departmentId", departmentId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "DepartmentServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Department department = new Department();
				department.setDepartmentId(object.getInt("departmentId"));
				department.setDepartmentName(object.getString("departmentName"));
				department.setDepartmentType(object.getString("departmentType"));
				department.setDepartmentMemo(object.getString("departmentMemo"));
				departmentList.add(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = departmentList.size();
		if(size>0) return departmentList.get(0); 
		else return null; 
	}
}
