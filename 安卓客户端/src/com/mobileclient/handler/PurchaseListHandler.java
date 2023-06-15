package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Purchase;
public class PurchaseListHandler extends DefaultHandler {
	private List<Purchase> purchaseList = null;
	private Purchase purchase;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (purchase != null) { 
            String valueString = new String(ch, start, length); 
            if ("purchaseId".equals(tempString)) 
            	purchase.setPurchaseId(new Integer(valueString).intValue());
            else if ("goodObj".equals(tempString)) 
            	purchase.setGoodObj(valueString); 
            else if ("price".equals(tempString)) 
            	purchase.setPrice(new Float(valueString).floatValue());
            else if ("buyCount".equals(tempString)) 
            	purchase.setBuyCount(new Integer(valueString).intValue());
            else if ("totalMoney".equals(tempString)) 
            	purchase.setTotalMoney(new Float(valueString).floatValue());
            else if ("inDate".equals(tempString)) 
            	purchase.setInDate(Timestamp.valueOf(valueString));
            else if ("operatorMan".equals(tempString)) 
            	purchase.setOperatorMan(valueString); 
            else if ("keepMan".equals(tempString)) 
            	purchase.setKeepMan(valueString); 
            else if ("storeHouse".equals(tempString)) 
            	purchase.setStoreHouse(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Purchase".equals(localName)&&purchase!=null){
			purchaseList.add(purchase);
			purchase = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		purchaseList = new ArrayList<Purchase>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Purchase".equals(localName)) {
            purchase = new Purchase(); 
        }
        tempString = localName; 
	}

	public List<Purchase> getPurchaseList() {
		return this.purchaseList;
	}
}
