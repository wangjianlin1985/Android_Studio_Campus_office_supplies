package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.GoodApply;
public class GoodApplyListHandler extends DefaultHandler {
	private List<GoodApply> goodApplyList = null;
	private GoodApply goodApply;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (goodApply != null) { 
            String valueString = new String(ch, start, length); 
            if ("applyId".equals(tempString)) 
            	goodApply.setApplyId(new Integer(valueString).intValue());
            else if ("goodObj".equals(tempString)) 
            	goodApply.setGoodObj(valueString); 
            else if ("applyCount".equals(tempString)) 
            	goodApply.setApplyCount(new Integer(valueString).intValue());
            else if ("applyTime".equals(tempString)) 
            	goodApply.setApplyTime(valueString); 
            else if ("personObj".equals(tempString)) 
            	goodApply.setPersonObj(valueString); 
            else if ("handlePerson".equals(tempString)) 
            	goodApply.setHandlePerson(valueString); 
            else if ("applyMemo".equals(tempString)) 
            	goodApply.setApplyMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("GoodApply".equals(localName)&&goodApply!=null){
			goodApplyList.add(goodApply);
			goodApply = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		goodApplyList = new ArrayList<GoodApply>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("GoodApply".equals(localName)) {
            goodApply = new GoodApply(); 
        }
        tempString = localName; 
	}

	public List<GoodApply> getGoodApplyList() {
		return this.goodApplyList;
	}
}
