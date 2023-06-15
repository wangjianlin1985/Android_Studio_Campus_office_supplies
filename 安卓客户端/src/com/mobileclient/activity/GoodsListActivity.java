package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.Goods;
import com.mobileclient.service.GoodsService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.GoodsSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class GoodsListActivity extends Activity {
	GoodsSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String goodNo;
	/* 办公用品操作业务逻辑层对象 */
	GoodsService goodsService = new GoodsService();
	/*保存查询参数条件的办公用品对象*/
	private Goods queryConditionGoods;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.goods_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GoodsListActivity.this, GoodsQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("办公用品查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GoodsListActivity.this, GoodsAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		
		if(declare.getIdentify().equals("user")) {
			add_btn.setVisibility(View.GONE);
		}
		
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionGoods = (Goods)extras.getSerializable("queryConditionGoods");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionGoods = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//在子线程中进行下载数据操作
				list = getDatas();
				//发送消失到handler，通知主线程下载完成
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new GoodsSimpleAdapter(GoodsListActivity.this, list,
	        					R.layout.goods_list_item,
	        					new String[] { "goodNo","goodClassObj","goodName","goodPhoto","stockCount","price","storeHouse" },
	        					new int[] { R.id.tv_goodNo,R.id.tv_goodClassObj,R.id.tv_goodName,R.id.iv_goodPhoto,R.id.tv_stockCount,R.id.tv_price,R.id.tv_storeHouse,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(goodsListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String goodNo = list.get(arg2).get("goodNo").toString();
            	Intent intent = new Intent();
            	intent.setClass(GoodsListActivity.this, GoodsDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("goodNo", goodNo);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener goodsListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			Declare declare = (Declare) GoodsListActivity.this.getApplication();
			if(declare.getIdentify().equals("admin")) {
				menu.add(0, 0, 0, "编辑办公用品信息"); 
				menu.add(0, 1, 0, "删除办公用品信息");
			}
			
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑办公用品信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取物品编号
			goodNo = list.get(position).get("goodNo").toString();
			Intent intent = new Intent();
			intent.setClass(GoodsListActivity.this, GoodsEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("goodNo", goodNo);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除办公用品信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取物品编号
			goodNo = list.get(position).get("goodNo").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(GoodsListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = goodsService.DeleteGoods(goodNo);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* 查询办公用品信息 */
			List<Goods> goodsList = goodsService.QueryGoods(queryConditionGoods);
			for (int i = 0; i < goodsList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("goodNo", goodsList.get(i).getGoodNo());
				map.put("goodClassObj", goodsList.get(i).getGoodClassObj());
				map.put("goodName", goodsList.get(i).getGoodName());
				/*byte[] goodPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ goodsList.get(i).getGoodPhoto());// 获取图片数据
				BitmapFactory.Options goodPhoto_opts = new BitmapFactory.Options();  
				goodPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(goodPhoto_data, 0, goodPhoto_data.length, goodPhoto_opts); 
				goodPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(goodPhoto_opts, -1, 100*100); 
				goodPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap goodPhoto = BitmapFactory.decodeByteArray(goodPhoto_data, 0, goodPhoto_data.length, goodPhoto_opts);
					map.put("goodPhoto", goodPhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("goodPhoto", HttpUtil.BASE_URL+ goodsList.get(i).getGoodPhoto());
				map.put("stockCount", goodsList.get(i).getStockCount());
				map.put("price", goodsList.get(i).getPrice());
				map.put("storeHouse", goodsList.get(i).getStoreHouse());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
