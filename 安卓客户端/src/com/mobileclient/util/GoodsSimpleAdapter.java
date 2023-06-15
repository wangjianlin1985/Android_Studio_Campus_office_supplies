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
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.goods_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_goodNo = (TextView)convertView.findViewById(R.id.tv_goodNo);
	  holder.tv_goodClassObj = (TextView)convertView.findViewById(R.id.tv_goodClassObj);
	  holder.tv_goodName = (TextView)convertView.findViewById(R.id.tv_goodName);
	  holder.iv_goodPhoto = (ImageView)convertView.findViewById(R.id.iv_goodPhoto);
	  holder.tv_stockCount = (TextView)convertView.findViewById(R.id.tv_stockCount);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.tv_storeHouse = (TextView)convertView.findViewById(R.id.tv_storeHouse);
	  /*设置各个控件的展示内容*/
	  holder.tv_goodNo.setText("物品编号：" + mData.get(position).get("goodNo").toString());
	  holder.tv_goodClassObj.setText("商品类别：" + (new GoodClassService()).GetGoodClass(Integer.parseInt(mData.get(position).get("goodClassObj").toString())).getGoodClassName());
	  holder.tv_goodName.setText("物品名称：" + mData.get(position).get("goodName").toString());
	  holder.iv_goodPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener goodPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_goodPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("goodPhoto"),goodPhotoLoadListener);  
	  holder.tv_stockCount.setText("库存数量：" + mData.get(position).get("stockCount").toString());
	  holder.tv_price.setText("单价：" + mData.get(position).get("price").toString());
	  holder.tv_storeHouse.setText("仓库：" + mData.get(position).get("storeHouse").toString());
	  /*返回修改好的view*/
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
