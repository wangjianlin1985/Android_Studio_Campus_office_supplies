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

/*部门管理业务逻辑层*/
public class DepartmentService {
	/* 添加部门 */
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

	/* 查询部门 */
	public List<Department> QueryDepartment(Department queryConditionDepartment) throws Exception {
		String urlString = HttpUtil.BASE_URL + "DepartmentServlet?action=query";
		if(queryConditionDepartment != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
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
		//第2种是基于json数据格式解析，我们采用的是第2种
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

	/* 更新部门 */
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

	/* 删除部门 */
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
			return "部门信息删除失败!";
		}
	}

	/* 根据部门编号获取部门对象 */
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
