package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.GoodClass;
import com.mobileclient.service.GoodClassService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class GoodClassDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明物品类别id控件
	private TextView TV_goodClassId;
	// 声明物品类别名称控件
	private TextView TV_goodClassName;
	/* 要保存的物品类别信息 */
	GoodClass goodClass = new GoodClass(); 
	/* 物品类别管理业务逻辑层 */
	private GoodClassService goodClassService = new GoodClassService();
	private int goodClassId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.goodclass_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看物品类别详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_goodClassId = (TextView) findViewById(R.id.TV_goodClassId);
		TV_goodClassName = (TextView) findViewById(R.id.TV_goodClassName);
		Bundle extras = this.getIntent().getExtras();
		goodClassId = extras.getInt("goodClassId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GoodClassDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    goodClass = goodClassService.GetGoodClass(goodClassId); 
		this.TV_goodClassId.setText(goodClass.getGoodClassId() + "");
		this.TV_goodClassName.setText(goodClass.getGoodClassName());
	} 
}
