package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class GoodUseEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ��������idTextView
	private TextView TV_useId;
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

	private int useId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.gooduse_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭��Ʒ������Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_useId = (TextView) findViewById(R.id.TV_useId);
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
		// ����ͼ����������б�ķ��
		goodObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_goodObj.setAdapter(goodObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_goodObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				goodUse.setGoodObj(goodsList.get(arg2).getGoodNo()); 
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
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		useId = extras.getInt("useId");
		/*�����޸���Ʒ���ð�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��������*/ 
					if(ET_useCount.getText().toString().equals("")) {
						Toast.makeText(GoodUseEditActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_useCount.setFocusable(true);
						ET_useCount.requestFocus();
						return;	
					}
					goodUse.setUseCount(Integer.parseInt(ET_useCount.getText().toString()));
					/*��֤��ȡ����*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(GoodUseEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					goodUse.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*��֤��ȡ���*/ 
					if(ET_totalMoney.getText().toString().equals("")) {
						Toast.makeText(GoodUseEditActivity.this, "������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_totalMoney.setFocusable(true);
						ET_totalMoney.requestFocus();
						return;	
					}
					goodUse.setTotalMoney(Float.parseFloat(ET_totalMoney.getText().toString()));
					/*��ȡ��������*/
					Date useTime = new Date(dp_useTime.getYear()-1900,dp_useTime.getMonth(),dp_useTime.getDayOfMonth());
					goodUse.setUseTime(new Timestamp(useTime.getTime()));
					/*��֤��ȡ������*/ 
					if(ET_userMan.getText().toString().equals("")) {
						Toast.makeText(GoodUseEditActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_userMan.setFocusable(true);
						ET_userMan.requestFocus();
						return;	
					}
					goodUse.setUserMan(ET_userMan.getText().toString());
					/*��֤��ȡ������*/ 
					if(ET_operatorMan.getText().toString().equals("")) {
						Toast.makeText(GoodUseEditActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_operatorMan.setFocusable(true);
						ET_operatorMan.requestFocus();
						return;	
					}
					goodUse.setOperatorMan(ET_operatorMan.getText().toString());
					/*��֤��ȡ�ֿ�*/ 
					if(ET_storeHouse.getText().toString().equals("")) {
						Toast.makeText(GoodUseEditActivity.this, "�ֿ����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_storeHouse.setFocusable(true);
						ET_storeHouse.requestFocus();
						return;	
					}
					goodUse.setStoreHouse(ET_storeHouse.getText().toString());
					/*����ҵ���߼����ϴ���Ʒ������Ϣ*/
					GoodUseEditActivity.this.setTitle("���ڸ�����Ʒ������Ϣ���Ե�...");
					String result = goodUseService.UpdateGoodUse(goodUse);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    goodUse = goodUseService.GetGoodUse(useId);
		this.TV_useId.setText(useId+"");
		for (int i = 0; i < goodsList.size(); i++) {
			if (goodUse.getGoodObj().equals(goodsList.get(i).getGoodNo())) {
				this.spinner_goodObj.setSelection(i);
				break;
			}
		}
		this.ET_useCount.setText(goodUse.getUseCount() + "");
		this.ET_price.setText(goodUse.getPrice() + "");
		this.ET_totalMoney.setText(goodUse.getTotalMoney() + "");
		Date useTime = new Date(goodUse.getUseTime().getTime());
		this.dp_useTime.init(useTime.getYear() + 1900,useTime.getMonth(), useTime.getDate(), null);
		this.ET_userMan.setText(goodUse.getUserMan());
		this.ET_operatorMan.setText(goodUse.getOperatorMan());
		this.ET_storeHouse.setText(goodUse.getStoreHouse());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
