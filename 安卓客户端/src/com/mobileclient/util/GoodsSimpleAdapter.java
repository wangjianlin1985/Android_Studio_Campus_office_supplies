package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.GoodClassService;
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

public class GoodsSimpleAdapter extends SimpleAdapter { 
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

    public GoodsSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.goods_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_goodNo = (TextView)convertView.findViewById(R.id.tv_goodNo);
	  holder.tv_goodClassObj = (TextView)convertView.findViewById(R.id.tv_goodClassObj);
	  holder.tv_goodName = (TextView)convertView.findViewById(R.id.tv_goodName);
	  holder.iv_goodPhoto = (ImageView)convertView.findViewById(R.id.iv_goodPhoto);
	  holder.tv_stockCount = (TextView)convertView.findViewById(R.id.tv_stockCount);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_storeHouse = (TextView)convertView.findViewById(R.id.tv_storeHouse);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_goodNo.setText("��Ʒ��ţ�" + mData.get(position).get("goodNo").toString());
	  holder.tv_goodClassObj.setText("��Ʒ���" + (new GoodClassService()).GetGoodClass(Integer.parseInt(mData.get(position).get("goodClassObj").toString())).getGoodClassName());
	  holder.tv_goodName.setText("��Ʒ���ƣ�" + mData.get(position).get("goodName").toString());
	  holder.iv_goodPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener goodPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_goodPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("goodPhoto"),goodPhotoLoadListener);  
	  holder.tv_stockCount.setText("���������" + mData.get(position).get("stockCount").toString());
	  holder.tv_price.setText("���ۣ�" + mData.get(position).get("price").toString());
	  holder.tv_storeHouse.setText("�ֿ⣺" + mData.get(position).get("storeHouse").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_goodNo;
    	TextView tv_goodClassObj;
    	TextView tv_goodName;
    	ImageView iv_goodPhoto;
    	TextView tv_stockCount;
    	TextView tv_price;
    	TextView tv_storeHouse;
    }
} 
