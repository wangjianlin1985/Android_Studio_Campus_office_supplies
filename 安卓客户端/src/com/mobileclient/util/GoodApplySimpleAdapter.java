package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.GoodsService;
import com.mobileclient.service.PersonService;
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

public class GoodApplySimpleAdapter extends SimpleAdapter { 
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

    public GoodApplySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.goodapply_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_goodObj = (TextView)convertView.findViewById(R.id.tv_goodObj);
	  holder.tv_applyCount = (TextView)convertView.findViewById(R.id.tv_applyCount);
	  holder.tv_applyTime = (TextView)convertView.findViewById(R.id.tv_applyTime);
	  holder.tv_personObj = (TextView)convertView.findViewById(R.id.tv_personObj);
	  holder.tv_handlePerson = (TextView)convertView.findViewById(R.id.tv_handlePerson);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_goodObj.setText("�������Ʒ��" + (new GoodsService()).GetGoods(mData.get(position).get("goodObj").toString()).getGoodName());
	  holder.tv_applyCount.setText("����������" + mData.get(position).get("applyCount").toString());
	  holder.tv_applyTime.setText("����ʱ�䣺" + mData.get(position).get("applyTime").toString());
	  holder.tv_personObj.setText("�����ˣ�" + (new PersonService()).GetPerson(mData.get(position).get("personObj").toString()).getName());
	  holder.tv_handlePerson.setText("�����ˣ�" + mData.get(position).get("handlePerson").toString());
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_goodObj;
    	TextView tv_applyCount;
    	TextView tv_applyTime;
    	TextView tv_personObj;
    	TextView tv_handlePerson;
    }
} 
