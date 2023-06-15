package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.GoodUse;
import com.mobileclient.service.GoodUseService;
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
public class GoodUseAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����������Ʒ������
	private Spinner spinner_goodObj;
	private ArrayAdapter<String> goodObj_adapter;
	private static  String[] goodObj_ShowText  = null;
	private List<Goods> goodsList = null;
	/*������Ʒ����ҵ���߼���*/
	private GoodsService goodsService = new GoodsService();
	// �����������������
	private EditText ET_useCount;
	// �������������
	private EditText ET_price;
	// ������������
	private EditText ET_totalMoney;
	// ��������ʱ��ؼ�
	private DatePicker dp_useTime;
	// ���������������
	private EditText ET_userMan;
	// ���������������
	private EditText ET_operatorMan;
	// �����ֿ������
	private EditText ET_storeHouse;
	protected String carmera_path;
	/*Ҫ�������Ʒ������Ϣ*/
	GoodUse goodUse = new GoodUse();
	/*��Ʒ���ù���ҵ���߼���*/
	private GoodUseService goodUseService = new GoodUseService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.gooduse_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�Ǽ���Ʒ����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_goodObj = (Spinner) findViewById(R.id.Spinner_goodObj);
		// ��ȡ���е�������Ʒ
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
		// ����ѡ������ArrayAdapter��������
		goodObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, goodObj_ShowText);
		// ���������б�ķ��
		goodObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_goodObj.setAdapter(goodObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_goodObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				goodUse.setGoodObj(goodsList.get(arg2).getGoodNo()); 
				goodUse.setStoreHouse(goodsList.get(arg2).getStoreHouse());
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_goodObj.setVisibility(View.VISIBLE);
		ET_useCount = (EditText) findViewById(R.id.ET_useCount);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_totalMoney = (EditText) findViewById(R.id.ET_totalMoney);
		dp_useTime = (DatePicker)this.findViewById(R.id.dp_useTime);
		ET_userMan = (EditText) findViewById(R.id.ET_userMan);
		ET_operatorMan = (EditText) findViewById(R.id.ET_operatorMan);
		ET_storeHouse = (EditText) findViewById(R.id.ET_storeHouse);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*���������Ʒ���ð�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��������*/ 
					if(ET_useCount.getText().toString().equals("")) {
						Toast.makeText(GoodUseAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_useCount.setFocusable(true);
						ET_useCount.requestFocus();
						return;	
					}
					goodUse.setUseCount(Integer.parseInt(ET_useCount.getText().toString()));
					/*��֤��ȡ����*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(GoodUseAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					goodUse.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*��֤��ȡ���*/ 
//					if(ET_totalMoney.getText().toString().equals("")) {
//						Toast.makeText(GoodUseAddActivity.this, "������벻��Ϊ��!", Toast.LENGTH_LONG).show();
//						ET_totalMoney.setFocusable(true);
//						ET_totalMoney.requestFocus();
//						return;	
//					}
					goodUse.setTotalMoney(goodUse.getPrice() * goodUse.getUseCount());
					/*��ȡ����ʱ��*/
					Date useTime = new Date(dp_useTime.getYear()-1900,dp_useTime.getMonth(),dp_useTime.getDayOfMonth());
					goodUse.setUseTime(new Timestamp(useTime.getTime()));
					/*��֤��ȡ������*/ 
					if(ET_userMan.getText().toString().equals("")) {
						Toast.makeText(GoodUseAddActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_userMan.setFocusable(true);
						ET_userMan.requestFocus();
						return;	
					}
					goodUse.setUserMan(ET_userMan.getText().toString());
					/*��֤��ȡ������*/ 
					if(ET_operatorMan.getText().toString().equals("")) {
						Toast.makeText(GoodUseAddActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_operatorMan.setFocusable(true);
						ET_operatorMan.requestFocus();
						return;	
					}
					goodUse.setOperatorMan(ET_operatorMan.getText().toString());
//					/*��֤��ȡ�ֿ�*/ 
//					if(ET_storeHouse.getText().toString().equals("")) {
//						Toast.makeText(GoodUseAddActivity.this, "�ֿ����벻��Ϊ��!", Toast.LENGTH_LONG).show();
//						ET_storeHouse.setFocusable(true);
//						ET_storeHouse.requestFocus();
//						return;	
//					}
//					goodUse.setStoreHouse(ET_storeHouse.getText().toString());
					/*����ҵ���߼����ϴ���Ʒ������Ϣ*/
					GoodUseAddActivity.this.setTitle("�����ϴ���Ʒ������Ϣ���Ե�...");
					String result = goodUseService.AddGoodUse(goodUse);
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
