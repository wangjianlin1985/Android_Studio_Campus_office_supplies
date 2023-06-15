package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.DepartmentService;
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

public class PersonSimpleAdapter extends SimpleAdapter { 
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

    public PersonSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.person_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_user_name = (TextView)convertView.findViewById(R.id.tv_user_name);
	  holder.tv_departmentObj = (TextView)convertView.findViewById(R.id.tv_departmentObj);
	  holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
	  holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
	  holder.tv_bornDate = (TextView)convertView.findViewById(R.id.tv_bornDate);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_user_name.setText("��Ա��ţ�" + mData.get(position).get("user_name").toString());
	  holder.tv_departmentObj.setText("���ڲ��ţ�" + (new DepartmentService()).GetDepartment(Integer.parseInt(mData.get(position).get("departmentObj").toString())).getDepartmentName());
	  holder.tv_name.setText("������" + mData.get(position).get("name").toString());
	  holder.tv_sex.setText("�Ա�" + mData.get(position).get("sex").toString());
	  try {holder.tv_bornDate.setText("�������ڣ�" + mData.get(position).get("bornDate").toString().substring(0, 10));} catch(Exception ex){}
	  /*�����޸ĺõ�view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_user_name;
    	TextView tv_departmentObj;
    	TextView tv_name;
    	TextView tv_sex;
    	TextView tv_bornDate;
    }
} 
