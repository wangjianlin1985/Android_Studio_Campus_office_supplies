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

public class GoodUseSimpleAdapter extends SimpleAdapter { 
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

    public GoodUseSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.gooduse_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_goodObj = (TextView)convertView.findViewById(R.id.tv_goodObj);
	  holder.tv_useCount = (TextView)convertView.findViewById(R.id.tv_useCount);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_totalMoney = (TextView)convertView.findViewById(R.id.tv_totalMoney);
	  holder.tv_useTime = (TextView)convertView.findViewById(R.id.tv_useTime);
	  holder.tv_userMan = (TextView)convertView.findViewById(R.id.tv_userMan);
	  holder.tv_operatorMan = (TextView)convertView.findViewById(R.id.tv_operatorMan);
	  /*设置各个控件的展示内容*/
	  holder.tv_goodObj.setText("领用物品：" + (new GoodsService()).GetGoods(mData.get(position).get("goodObj").toString()).getGoodName());
	  holder.tv_useCount.setText("领用数量：" + mData.get(position).get("useCount").toString());
	  holder.tv_price.setText("单价：" + mData.get(position).get("price").toString());
	  holder.tv_totalMoney.setText("金额：" + mData.get(position).get("totalMoney").toString());
	  try {holder.tv_useTime.setText("领用时间：" + mData.get(position).get("useTime").toString().substring(0, 10));} catch(Exception ex){}
	  holder.tv_userMan.setText("领用人：" + mData.get(position).get("userMan").toString());
	  holder.tv_operatorMan.setText("经办人：" + mData.get(position).get("operatorMan").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_goodObj;
    	TextView tv_useCount;
    	TextView tv_price;
    	TextView tv_totalMoney;
    	TextView tv_useTime;
    	TextView tv_userMan;
    	TextView tv_operatorMan;
    }
} 
