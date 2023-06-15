package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.GoodsService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class PurchaseSimpleAdapter extends SimpleAdapter { 
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //ͼƬ�첽���������,���ڴ滺����ļ�����
    private SyncImageLoader syncImageLoader;

    public PurchaseSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*��һ��װ�����viewʱ=null,���½�һ������inflate��Ⱦһ��view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.purchase_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_goodObj = (TextView)convertView.findViewById(R.id.tv_goodObj);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_buyCount = (TextView)convertView.findViewById(R.id.tv_buyCount);
	  holder.tv_totalMoney = (TextView)convertView.findViewById(R.id.tv_totalMoney);
	  holder.tv_inDate = (TextView)convertView.findViewById(R.id.tv_inDate);
	  holder.tv_operatorMan = (TextView)convertView.findViewById(R.id.tv_operatorMan);
	  holder.tv_storeHouse = (TextView)convertView.findViewById(R.id.tv_storeHouse);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_goodObj.setText("������Ʒ��" + (new GoodsService()).GetGoods(mData.get(position).get("goodObj").toString()).getGoodName());
	  holder.tv_price.setText("���ü۸�" + mData.get(position).get("price").toString());
	  holder.tv_buyCount.setText("����������" + mData.get(position).get("buyCount").toString());
	  holder.tv_totalMoney.setText("���ý�" + mData.get(position).get("totalMoney").toString());
	  try {holder.tv_inDate.setText("���ʱ�䣺" + mData.get(position).get("inDate").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_operatorMan.setText("�����ˣ�" + mData.get(position).get("operatorMan").toString());
	  holder.tv_storeHouse.setText("�ֿ⣺" + mData.get(position).get("storeHouse").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_goodObj;
    	TextView tv_price;
    	TextView tv_buyCount;
    	TextView tv_totalMoney;
    	TextView tv_inDate;
    	TextView tv_operatorMan;
    	TextView tv_storeHouse;
    }
} 
