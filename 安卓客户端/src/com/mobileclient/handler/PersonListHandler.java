package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Person;
public class PersonListHandler extends DefaultHandler {
	private List<Person> personList = null;
	private Person person;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (person != null) { 
            String valueString = new String(ch, start, length); 
            if ("user_name".equals(tempString)) 
            	person.setUser_name(valueString); 
            else if ("password".equals(tempString)) 
            	person.setPassword(valueString); 
            else if ("departmentObj".equals(tempString)) 
            	person.setDepartmentObj(new Integer(valueString).intValue());
            else if ("name".equals(tempString)) 
            	person.setName(valueString); 
            else if ("sex".equals(tempString)) 
            	person.setSex(valueString); 
            else if ("bornDate".equals(tempString)) 
            	person.setBornDate(Timestamp.valueOf(valueString));
            else if ("telephone".equals(tempString)) 
            	person.setTelephone(valueString); 
            else if ("address".equals(tempString)) 
            	person.setAddress(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Person".equals(localName)&&person!=null){
			personList.add(person);
			person = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		personList = new ArrayList<Person>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Person".equals(localName)) {
            person = new Person(); 
        }
        tempString = localName; 
	}

	public List<Person> getPersonList() {
		return this.personList;
	}
}
