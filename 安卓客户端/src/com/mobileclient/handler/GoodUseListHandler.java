package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.GoodUse;
public class GoodUseListHandler extends DefaultHandler {
	private List<GoodUse> goodUseList = null;
	private GoodUse goodUse;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (goodUse != null) { 
            String valueString = new String(ch, start, length); 
            if ("useId".equals(tempString)) 
            	goodUse.setUseId(new Integer(valueString).intValue());
            else if ("goodObj".equals(tempString)) 
            	goodUse.setGoodObj(valueString); 
            else if ("useCount".equals(tempString)) 
            	goodUse.setUseCount(new Integer(valueString).intValue());
            else if ("price".equals(tempString)) 
            	goodUse.setPrice(new Float(valueString).floatValue());
            else if ("totalMoney".equals(tempString)) 
            	goodUse.setTotalMoney(new Float(valueString).floatValue());
            else if ("useTime".equals(tempString)) 
            	goodUse.setUseTime(Timestamp.valueOf(valueString));
            else if ("userMan".equals(tempString)) 
            	goodUse.setUserMan(valueString); 
            else if ("operatorMan".equals(tempString)) 
            	goodUse.setOperatorMan(valueString); 
            else if ("storeHouse".equals(tempString)) 
            	goodUse.setStoreHouse(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("GoodUse".equals(localName)&&goodUse!=null){
			goodUseList.add(goodUse);
			goodUse = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		goodUseList = new ArrayList<GoodUse>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("GoodUse".equals(localName)) {
            goodUse = new GoodUse(); 
        }
        tempString = localName; 
	}

	public List<GoodUse> getGoodUseList() {
		return this.goodUseList;
	}
}
