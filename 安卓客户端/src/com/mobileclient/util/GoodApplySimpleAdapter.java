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
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
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
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.goodapply_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_goodObj = (TextView)convertView.findViewById(R.id.tv_goodObj);
	  holder.tv_applyCount = (TextView)convertView.findViewById(R.id.tv_applyCount);
	  holder.tv_applyTime = (TextView)convertView.findViewById(R.id.tv_applyTime);
	  holder.tv_personObj = (TextView)convertView.findViewById(R.id.tv_personObj);
	  holder.tv_handlePerson = (TextView)convertView.findViewById(R.id.tv_handlePerson);
	  /*设置各个控件的展示内容*/
	  holder.tv_goodObj.setText("审请的用品：" + (new GoodsService()).GetGoods(mData.get(position).get("goodObj").toString()).getGoodName());
	  holder.tv_applyCount.setText("申请数量：" + mData.get(position).get("applyCount").toString());
	  holder.tv_applyTime.setText("申请时间：" + mData.get(position).get("applyTime").toString());
	  holder.tv_personObj.setText("申请人：" + (new PersonService()).GetPerson(mData.get(position).get("personObj").toString()).getName());
	  holder.tv_handlePerson.setText("经办人：" + mData.get(position).get("handlePerson").toString());
	  /*返回修改好的view*/
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
