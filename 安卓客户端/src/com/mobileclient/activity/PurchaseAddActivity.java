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
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ����������Ʒ������
	private Spinner spinner_goodObj;
	private ArrayAdapter<String> goodObj_adapter;
	private static  String[] goodObj_ShowText  = null;
	private List<Goods> goodsList = null;
	/*������Ʒ����ҵ���߼���*/
	private GoodsService goodsService = new GoodsService();
	// �������ü۸������
	private EditText ET_price;
	// �����������������
	private EditText ET_buyCount;
	// �������ý�������
	private EditText ET_totalMoney;
	// �������ʱ��ؼ�
	private DatePicker dp_inDate;
	// ���������������
	private EditText ET_operatorMan;
	// ���������������
	private EditText ET_keepMan;
	// �����ֿ������
	private EditText ET_storeHouse;
	protected String carmera_path;
	/*Ҫ�������Ʒ������Ϣ*/
	Purchase purchase = new Purchase();
	/*��Ʒ���ù���ҵ���߼���*/
	private PurchaseService purchaseService = new PurchaseService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.purchase_add); 
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
		// ��ȡ���еĹ�����Ʒ
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
				purchase.setGoodObj(goodsList.get(arg2).getGoodNo());
				purchase.setStoreHouse(goodsList.get(arg2).getStoreHouse());
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_goodObj.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_buyCount = (EditText) findViewById(R.id.ET_buyCount);
		ET_totalMoney = (EditText) findViewById(R.id.ET_totalMoney);
		dp_inDate = (DatePicker)this.findViewById(R.id.dp_inDate);
		ET_operatorMan = (EditText) findViewById(R.id.ET_operatorMan);
		ET_keepMan = (EditText) findViewById(R.id.ET_keepMan);
		ET_storeHouse = (EditText) findViewById(R.id.ET_storeHouse);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*���������Ʒ���ð�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ���ü۸�*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(PurchaseAddActivity.this, "���ü۸����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					purchase.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*��֤��ȡ��������*/ 
					if(ET_buyCount.getText().toString().equals("")) {
						Toast.makeText(PurchaseAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_buyCount.setFocusable(true);
						ET_buyCount.requestFocus();
						return;	
					}
					purchase.setBuyCount(Integer.parseInt(ET_buyCount.getText().toString()));
					/*���ý��*/ 
//					if(ET_totalMoney.getText().toString().equals("")) {
//						Toast.makeText(PurchaseAddActivity.this, "���ý�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
//						ET_totalMoney.setFocusable(true);
//						ET_totalMoney.requestFocus();
//						return;	
//					}
					float totalMoney = purchase.getPrice() * purchase.getBuyCount(); 
					purchase.setTotalMoney(totalMoney);
					/*��ȡ���ʱ��*/
					Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
					purchase.setInDate(new Timestamp(inDate.getTime()));
					/*��֤��ȡ������*/ 
					if(ET_operatorMan.getText().toString().equals("")) {
						Toast.makeText(PurchaseAddActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_operatorMan.setFocusable(true);
						ET_operatorMan.requestFocus();
						return;	
					}
					purchase.setOperatorMan(ET_operatorMan.getText().toString());
					/*��֤��ȡ������*/ 
					if(ET_keepMan.getText().toString().equals("")) {
						Toast.makeText(PurchaseAddActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_keepMan.setFocusable(true);
						ET_keepMan.requestFocus();
						return;	
					}
					purchase.setKeepMan(ET_keepMan.getText().toString());
					 
					/*����ҵ���߼����ϴ���Ʒ������Ϣ*/
					PurchaseAddActivity.this.setTitle("�����ϴ���Ʒ������Ϣ���Ե�...");
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
