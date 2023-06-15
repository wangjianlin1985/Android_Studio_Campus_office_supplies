package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.GoodUse;
import com.mobileclient.service.GoodUseService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.GoodUseSimpleAdapter;
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

public class GoodUseListActivity extends Activity {
	GoodUseSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int useId;
	/* 物品领用操作业务逻辑层对象 */
	GoodUseService goodUseService = new GoodUseService();
	/*保存查询参数条件的物品领用对象*/
	private GoodUse queryConditionGoodUse;

	private MyProgressDialog dialog; //进度条	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.gooduse_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//标题栏控件
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GoodUseListActivity.this, GoodUseQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//此处的requestCode应与下面结果处理函中调用的requestCode一致
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("物品领用查询列表");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GoodUseListActivity.this, GoodUseAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//结果处理函数，当从secondActivity中返回时调用此函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionGoodUse = (GoodUse)extras.getSerializable("queryConditionGoodUse");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionGoodUse = null;
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
						adapter = new GoodUseSimpleAdapter(GoodUseListActivity.this, list,
	        					R.layout.gooduse_list_item,
	        					new String[] { "goodObj","useCount","price","totalMoney","useTime","userMan","operatorMan" },
	        					new int[] { R.id.tv_goodObj,R.id.tv_useCount,R.id.tv_price,R.id.tv_totalMoney,R.id.tv_useTime,R.id.tv_userMan,R.id.tv_operatorMan,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// 添加长按点击
		lv.setOnCreateContextMenuListener(goodUseListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int useId = Integer.parseInt(list.get(arg2).get("useId").toString());
            	Intent intent = new Intent();
            	intent.setClass(GoodUseListActivity.this, GoodUseDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("useId", useId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener goodUseListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "编辑物品领用信息"); 
			menu.add(0, 1, 0, "删除物品领用信息");
		}
	};

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //编辑物品领用信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取领用id
			useId = Integer.parseInt(list.get(position).get("useId").toString());
			Intent intent = new Intent();
			intent.setClass(GoodUseListActivity.this, GoodUseEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("useId", useId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// 删除物品领用信息
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// 获取选中行位置
			int position = contextMenuInfo.position;
			// 获取领用id
			useId = Integer.parseInt(list.get(position).get("useId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// 删除
	protected void dialog() {
		Builder builder = new Builder(GoodUseListActivity.this);
		builder.setMessage("确认删除吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = goodUseService.DeleteGoodUse(useId);
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
			/* 查询物品领用信息 */
			List<GoodUse> goodUseList = goodUseService.QueryGoodUse(queryConditionGoodUse);
			for (int i = 0; i < goodUseList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("useId",goodUseList.get(i).getUseId());
				map.put("goodObj", goodUseList.get(i).getGoodObj());
				map.put("useCount", goodUseList.get(i).getUseCount());
				map.put("price", goodUseList.get(i).getPrice());
				map.put("totalMoney", goodUseList.get(i).getTotalMoney());
				map.put("useTime", goodUseList.get(i).getUseTime());
				map.put("userMan", goodUseList.get(i).getUserMan());
				map.put("operatorMan", goodUseList.get(i).getOperatorMan());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
