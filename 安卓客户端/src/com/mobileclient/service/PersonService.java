package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Person;
import com.mobileclient.util.HttpUtil;

/*��Ա����ҵ���߼���*/
public class PersonService {
	/* �����Ա */
	public String AddPerson(Person person) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", person.getUser_name());
		params.put("password", person.getPassword());
		params.put("departmentObj", person.getDepartmentObj() + "");
		params.put("name", person.getName());
		params.put("sex", person.getSex());
		params.put("bornDate", person.getBornDate().toString());
		params.put("telephone", person.getTelephone());
		params.put("address", person.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PersonServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ��Ա */
	public List<Person> QueryPerson(Person queryConditionPerson) throws Exception {
		String urlString = HttpUtil.BASE_URL + "PersonServlet?action=query";
		if(queryConditionPerson != null) {
			urlString += "&user_name=" + URLEncoder.encode(queryConditionPerson.getUser_name(), "UTF-8") + "";
			urlString += "&departmentObj=" + queryConditionPerson.getDepartmentObj();
			urlString += "&name=" + URLEncoder.encode(queryConditionPerson.getName(), "UTF-8") + "";
			if(queryConditionPerson.getBornDate() != null) {
				urlString += "&bornDate=" + URLEncoder.encode(queryConditionPerson.getBornDate().toString(), "UTF-8");
			}
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		PersonListHandler personListHander = new PersonListHandler();
		xr.setContentHandler(personListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Person> personList = personListHander.getPersonList();
		return personList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<Person> personList = new ArrayList<Person>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Person person = new Person();
				person.setUser_name(object.getString("user_name"));
				person.setPassword(object.getString("password"));
				person.setDepartmentObj(object.getInt("departmentObj"));
				person.setName(object.getString("name"));
				person.setSex(object.getString("sex"));
				person.setBornDate(Timestamp.valueOf(object.getString("bornDate")));
				person.setTelephone(object.getString("telephone"));
				person.setAddress(object.getString("address"));
				personList.add(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personList;
	}

	/* ������Ա */
	public String UpdatePerson(Person person) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", person.getUser_name());
		params.put("password", person.getPassword());
		params.put("departmentObj", person.getDepartmentObj() + "");
		params.put("name", person.getName());
		params.put("sex", person.getSex());
		params.put("bornDate", person.getBornDate().toString());
		params.put("telephone", person.getTelephone());
		params.put("address", person.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PersonServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ����Ա */
	public String DeletePerson(String user_name) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", user_name);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PersonServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "��Ա��Ϣɾ��ʧ��!";
		}
	}

	/* ������Ա��Ż�ȡ��Ա���� */
	public Person GetPerson(String user_name)  {
		List<Person> personList = new ArrayList<Person>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("user_name", user_name);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "PersonServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Person person = new Person();
				person.setUser_name(object.getString("user_name"));
				person.setPassword(object.getString("password"));
				person.setDepartmentObj(object.getInt("departmentObj"));
				person.setName(object.getString("name"));
				person.setSex(object.getString("sex"));
				person.setBornDate(Timestamp.valueOf(object.getString("bornDate")));
				person.setTelephone(object.getString("telephone"));
				person.setAddress(object.getString("address"));
				personList.add(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = personList.size();
		if(size>0) return personList.get(0); 
		else return null; 
	}
}
