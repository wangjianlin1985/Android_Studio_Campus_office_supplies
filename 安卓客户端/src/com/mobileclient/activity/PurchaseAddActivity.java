package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Purchase;
import com.mobileclient.service.PurchaseService;
import com.mobileclient.domain.Goods;
import com.mobileclient.service.GoodsService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class PurchaseAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明购置物品下拉框
	private Spinner spinner_goodObj;
	private ArrayAdapter<String> goodObj_adapter;
	private static  String[] goodObj_ShowText  = null;
	private List<Goods> goodsList = null;
	/*购置物品管理业务逻辑层*/
	private GoodsService goodsService = new GoodsService();
	// 声明购置价格输入框
	private EditText ET_price;
	// 声明购置数量输入框
	private EditText ET_buyCount;
	// 声明购置金额输入框
	private EditText ET_totalMoney;
	// 出版入库时间控件
	private DatePicker dp_inDate;
	// 声明经办人输入框
	private EditText ET_operatorMan;
	// 声明保管人输入框
	private EditText ET_keepMan;
	// 声明仓库输入框
	private EditText ET_storeHouse;
	protected String carmera_path;
	/*要保存的物品购置信息*/
	Purchase purchase = new Purchase();
	/*物品购置管理业务逻辑层*/
	private PurchaseService purchaseService = new PurchaseService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.purchase_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("登记物品购置");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_goodObj = (Spinner) findViewById(R.id.Spinner_goodObj);
		// 获取所有的购置物品
		try {
			goodsList = goodsService.QueryGoods(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int goodsCount = goodsList.size();
		goodObj_ShowText = new String[goodsCount];
		for(int i=0;i<goodsCount;i++) { 
			goodObj_ShowText[i] = goodsList.get(i).getGoodName();
		}
		// 将可选内容与ArrayAdapter连接起来
		goodObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, goodObj_ShowText);
		// 设置下拉列表的风格
		goodObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_goodObj.setAdapter(goodObj_adapter);
		// 添加事件Spinner事件监听
		spinner_goodObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				purchase.setGoodObj(goodsList.get(arg2).getGoodNo());
				purchase.setStoreHouse(goodsList.get(arg2).getStoreHouse());
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_goodObj.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_buyCount = (EditText) findViewById(R.id.ET_buyCount);
		ET_totalMoney = (EditText) findViewById(R.id.ET_totalMoney);
		dp_inDate = (DatePicker)this.findViewById(R.id.dp_inDate);
		ET_operatorMan = (EditText) findViewById(R.id.ET_operatorMan);
		ET_keepMan = (EditText) findViewById(R.id.ET_keepMan);
		ET_storeHouse = (EditText) findViewById(R.id.ET_storeHouse);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加物品购置按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取购置价格*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(PurchaseAddActivity.this, "购置价格输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					purchase.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*验证获取购置数量*/ 
					if(ET_buyCount.getText().toString().equals("")) {
						Toast.makeText(PurchaseAddActivity.this, "购置数量输入不能为空!", Toast.LENGTH_LONG).show();
						ET_buyCount.setFocusable(true);
						ET_buyCount.requestFocus();
						return;	
					}
					purchase.setBuyCount(Integer.parseInt(ET_buyCount.getText().toString()));
					/*购置金额*/ 
//					if(ET_totalMoney.getText().toString().equals("")) {
//						Toast.makeText(PurchaseAddActivity.this, "购置金额输入不能为空!", Toast.LENGTH_LONG).show();
//						ET_totalMoney.setFocusable(true);
//						ET_totalMoney.requestFocus();
//						return;	
//					}
					float totalMoney = purchase.getPrice() * purchase.getBuyCount(); 
					purchase.setTotalMoney(totalMoney);
					/*获取入库时间*/
					Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
					purchase.setInDate(new Timestamp(inDate.getTime()));
					/*验证获取经办人*/ 
					if(ET_operatorMan.getText().toString().equals("")) {
						Toast.makeText(PurchaseAddActivity.this, "经办人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_operatorMan.setFocusable(true);
						ET_operatorMan.requestFocus();
						return;	
					}
					purchase.setOperatorMan(ET_operatorMan.getText().toString());
					/*验证获取保管人*/ 
					if(ET_keepMan.getText().toString().equals("")) {
						Toast.makeText(PurchaseAddActivity.this, "保管人输入不能为空!", Toast.LENGTH_LONG).show();
						ET_keepMan.setFocusable(true);
						ET_keepMan.requestFocus();
						return;	
					}
					purchase.setKeepMan(ET_keepMan.getText().toString());
					 
					/*调用业务逻辑层上传物品购置信息*/
					PurchaseAddActivity.this.setTitle("正在上传物品购置信息，稍等...");
					String result = purchaseService.AddPurchase(purchase);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
