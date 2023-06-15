package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Goods;
import com.mobileclient.domain.GoodClass;
import com.mobileclient.service.GoodClassService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class GoodsQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明物品编号输入框
	private EditText ET_goodNo;
	// 声明商品类别下拉框
	private Spinner spinner_goodClassObj;
	private ArrayAdapter<String> goodClassObj_adapter;
	private static  String[] goodClassObj_ShowText  = null;
	private List<GoodClass> goodClassList = null; 
	/*物品类别管理业务逻辑层*/
	private GoodClassService goodClassService = new GoodClassService();
	// 声明物品名称输入框
	private EditText ET_goodName;
	/*查询过滤条件保存到这个对象中*/
	private Goods queryConditionGoods = new Goods();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.goods_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置办公用品查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_goodNo = (EditText) findViewById(R.id.ET_goodNo);
		spinner_goodClassObj = (Spinner) findViewById(R.id.Spinner_goodClassObj);
		// 获取所有的物品类别
		try {
			goodClassList = goodClassService.QueryGoodClass(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int goodClassCount = goodClassList.size();
		goodClassObj_ShowText = new String[goodClassCount+1];
		goodClassObj_ShowText[0] = "不限制";
		for(int i=1;i<=goodClassCount;i++) { 
			goodClassObj_ShowText[i] = goodClassList.get(i-1).getGoodClassName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		goodClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, goodClassObj_ShowText);
		// 设置商品类别下拉列表的风格
		goodClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_goodClassObj.setAdapter(goodClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_goodClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionGoods.setGoodClassObj(goodClassList.get(arg2-1).getGoodClassId()); 
				else
					queryConditionGoods.setGoodClassObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_goodClassObj.setVisibility(View.VISIBLE);
		ET_goodName = (EditText) findViewById(R.id.ET_goodName);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionGoods.setGoodNo(ET_goodNo.getText().toString());
					queryConditionGoods.setGoodName(ET_goodName.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionGoods", queryConditionGoods);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
