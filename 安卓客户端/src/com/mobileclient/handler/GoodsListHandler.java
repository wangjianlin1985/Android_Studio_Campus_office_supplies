package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Goods;
public class GoodsListHandler extends DefaultHandler {
	private List<Goods> goodsList = null;
	private Goods goods;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (goods != null) { 
            String valueString = new String(ch, start, length); 
            if ("goodNo".equals(tempString)) 
            	goods.setGoodNo(valueString); 
            else if ("goodClassObj".equals(tempString)) 
            	goods.setGoodClassObj(new Integer(valueString).intValue());
            else if ("goodName".equals(tempString)) 
            	goods.setGoodName(valueString); 
            else if ("goodPhoto".equals(tempString)) 
            	goods.setGoodPhoto(valueString); 
            else if ("specModel".equals(tempString)) 
            	goods.setSpecModel(valueString); 
            else if ("measureUnit".equals(tempString)) 
            	goods.setMeasureUnit(valueString); 
            else if ("stockCount".equals(tempString)) 
            	goods.setStockCount(new Integer(valueString).intValue());
            else if ("price".equals(tempString)) 
            	goods.setPrice(new Float(valueString).floatValue());
            else if ("totalMoney".equals(tempString)) 
            	goods.setTotalMoney(new Float(valueString).floatValue());
            else if ("storeHouse".equals(tempString)) 
            	goods.setStoreHouse(valueString); 
            else if ("goodMemo".equals(tempString)) 
            	goods.setGoodMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Goods".equals(localName)&&goods!=null){
			goodsList.add(goods);
			goods = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		goodsList = new ArrayList<Goods>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Goods".equals(localName)) {
            goods = new Goods(); 
        }
        tempString = localName; 
	}

	public List<Goods> getGoodsList() {
		return this.goodsList;
	}
}
