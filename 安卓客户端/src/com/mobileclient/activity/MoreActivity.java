package com.mobileclient.activity;
 
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobileclient.app.Declare;
import com.mobileclient.util.ActivityUtils;
public class MoreActivity extends Activity {
	private String[] strs = new String[]{"部门管理","人员管理","物品类别管理","办公用品管理","关于"}; 
	private ListView list = null;
	private TextView mName;
	private ImageView mIcon;
	private Button unlogin;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置当前Activity界面布局
		setContentView(R.layout.more);
		// 通过findViewById方法实例化组件
		mName = (TextView) this.findViewById(R.id.username);
		mIcon = (ImageView) this.findViewById(R.id.icon);
		list = (ListView) this.findViewById(R.id.list);
		mIcon.setImageResource(R.drawable.icon);
		mIcon.setEnabled(true);
		unlogin = (Button) this.findViewById(R.id.unlogin);
		unlogin.setVisibility(View.GONE);
		final Declare declare = (Declare) MoreActivity.this.getApplication();
		mName.setText(declare.getUserName());
		if(declare.getIdentify().equals("user")) strs = new String[] {"修改个人信息","关于"};
		
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(declare.getIdentify().equals("admin")) {
					if(arg2 == 0){
						Intent intent  = new Intent(MoreActivity.this, DepartmentListActivity.class);
						startActivity(intent);
					}
					if(arg2 == 1){
						Intent intent  = new Intent(MoreActivity.this, PersonListActivity.class);
						startActivity(intent);
					}
					if(arg2 == 2){
						Intent intent  = new Intent(MoreActivity.this, GoodClassListActivity.class);
						startActivity(intent);
					}
					if(arg2 == 3){
						Intent intent  = new Intent(MoreActivity.this, GoodsListActivity.class);
						startActivity(intent);
					}
					 
					if(arg2 == 4){
						Intent intent  = new Intent(MoreActivity.this, AboutActivity.class);
						startActivity(intent);
					}
				} else {
					if(arg2 == 0){ 
						Intent intent = new Intent();
						intent.setClass(MoreActivity.this, PersonEditActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("user_name", declare.getUserName());
						intent.putExtras(bundle);
						startActivityForResult(intent,ActivityUtils.EDIT_CODE);
					}
					if(arg2 == 1){
						Intent intent  = new Intent(MoreActivity.this, AboutActivity.class);
						startActivity(intent);
					}
				}
				


			}
		});
		list.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				// TODO Auto-generated method stub
				ListViewItem item = new ListViewItem(MoreActivity.this, strs[arg0]);
				return item;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return strs.length;
			}
		});
		
		
		mIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MoreActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		}); 
	}
	
	
	public class ListViewItem extends RelativeLayout{
		private TextView mTvColumnName;//栏目名称
		private ImageView mIvColumnImg;//栏目logo
		public ListViewItem(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		public ListViewItem(Context context,String columnInfo) {
			super(context,null);
			View view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
			mTvColumnName = (TextView) view.findViewById(R.id.column_name);
			mIvColumnImg = (ImageView) view.findViewById(R.id.column_img);
			mTvColumnName.setText(columnInfo);
			addView(view);
		}
	}
	 
}
