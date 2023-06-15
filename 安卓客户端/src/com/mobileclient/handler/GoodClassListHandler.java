package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.GoodClass;
public class GoodClassListHandler extends DefaultHandler {
	private List<GoodClass> goodClassList = null;
	private GoodClass goodClass;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (goodClass != null) { 
            String valueString = new String(ch, start, length); 
            if ("goodClassId".equals(tempString)) 
            	goodClass.setGoodClassId(new Integer(valueString).intValue());
            else if ("goodClassName".equals(tempString)) 
            	goodClass.setGoodClassName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("GoodClass".equals(localName)&&goodClass!=null){
			goodClassList.add(goodClass);
			goodClass = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		goodClassList = new ArrayList<GoodClass>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("GoodClass".equals(localName)) {
            goodClass = new GoodClass(); 
        }
        tempString = localName; 
	}

	public List<GoodClass> getGoodClassList() {
		return this.goodClassList;
	}
}
