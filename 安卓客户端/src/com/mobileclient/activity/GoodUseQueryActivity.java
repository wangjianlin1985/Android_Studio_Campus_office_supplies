package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.GoodUse;
import com.mobileclient.domain.Goods;
import com.mobileclient.service.GoodsService;

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
public class GoodUseQueryActivity extends Activity {
	// 声明查询按钮
	private Button btnQuery;
	// 声明领用物品下拉框
	private Spinner spinner_goodObj;
	private ArrayAdapter<String> goodObj_adapter;
	private static  String[] goodObj_ShowText  = null;
	private List<Goods> goodsList = null; 
	/*办公用品管理业务逻辑层*/
	private GoodsService goodsService = new GoodsService();
	// 领用时间控件
	private DatePicker dp_useTime;
	private CheckBox cb_useTime;
	// 声明经办人输入框
	private EditText ET_operatorMan;
	/*查询过滤条件保存到这个对象中*/
	private GoodUse queryConditionGoodUse = new GoodUse();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.gooduse_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置物品领用查询条件");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_goodObj = (Spinner) findViewById(R.id.Spinner_goodObj);
		// 获取所有的办公用品
		try {
			goodsList = goodsService.QueryGoods(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int goodsCount = goodsList.size();
		goodObj_ShowText = new String[goodsCount+1];
		goodObj_ShowText[0] = "不限制";
		for(int i=1;i<=goodsCount;i++) { 
			goodObj_ShowText[i] = goodsList.get(i-1).getGoodName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		goodObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, goodObj_ShowText);
		// 设置领用物品下拉列表的风格
		goodObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_goodObj.setAdapter(goodObj_adapter);
		// 添加事件Spinner事件监听
		spinner_goodObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionGoodUse.setGoodObj(goodsList.get(arg2-1).getGoodNo()); 
				else
					queryConditionGoodUse.setGoodObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_goodObj.setVisibility(View.VISIBLE);
		dp_useTime = (DatePicker) findViewById(R.id.dp_useTime);
		cb_useTime = (CheckBox) findViewById(R.id.cb_useTime);
		ET_operatorMan = (EditText) findViewById(R.id.ET_operatorMan);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					if(cb_useTime.isChecked()) {
						/*获取领用时间*/
						Date useTime = new Date(dp_useTime.getYear()-1900,dp_useTime.getMonth(),dp_useTime.getDayOfMonth());
						queryConditionGoodUse.setUseTime(new Timestamp(useTime.getTime()));
					} else {
						queryConditionGoodUse.setUseTime(null);
					} 
					queryConditionGoodUse.setOperatorMan(ET_operatorMan.getText().toString());
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionGoodUse", queryConditionGoodUse);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
