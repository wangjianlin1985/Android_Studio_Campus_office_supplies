package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class PurchaseEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ��������idTextView
	private TextView TV_purchaseId;
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

	private int purchaseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.purchase_edit); 
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
		TV_purchaseId = (TextView) findViewById(R.id.TV_purchaseId);
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
		// ����ͼ����������б�ķ��
		goodObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_goodObj.setAdapter(goodObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_goodObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				purchase.setGoodObj(goodsList.get(arg2).getGoodNo()); 
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
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		purchaseId = extras.getInt("purchaseId");
		/*�����޸���Ʒ���ð�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ���ü۸�*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(PurchaseEditActivity.this, "���ü۸����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					purchase.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*��֤��ȡ��������*/ 
					if(ET_buyCount.getText().toString().equals("")) {
						Toast.makeText(PurchaseEditActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_buyCount.setFocusable(true);
						ET_buyCount.requestFocus();
						return;	
					}
					purchase.setBuyCount(Integer.parseInt(ET_buyCount.getText().toString()));
					/*��֤��ȡ���ý��*/ 
					if(ET_totalMoney.getText().toString().equals("")) {
						Toast.makeText(PurchaseEditActivity.this, "���ý�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_totalMoney.setFocusable(true);
						ET_totalMoney.requestFocus();
						return;	
					}
					purchase.setTotalMoney(Float.parseFloat(ET_totalMoney.getText().toString()));
					/*��ȡ��������*/
					Date inDate = new Date(dp_inDate.getYear()-1900,dp_inDate.getMonth(),dp_inDate.getDayOfMonth());
					purchase.setInDate(new Timestamp(inDate.getTime()));
					/*��֤��ȡ������*/ 
					if(ET_operatorMan.getText().toString().equals("")) {
						Toast.makeText(PurchaseEditActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_operatorMan.setFocusable(true);
						ET_operatorMan.requestFocus();
						return;	
					}
					purchase.setOperatorMan(ET_operatorMan.getText().toString());
					/*��֤��ȡ������*/ 
					if(ET_keepMan.getText().toString().equals("")) {
						Toast.makeText(PurchaseEditActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_keepMan.setFocusable(true);
						ET_keepMan.requestFocus();
						return;	
					}
					purchase.setKeepMan(ET_keepMan.getText().toString());
					/*��֤��ȡ�ֿ�*/ 
					if(ET_storeHouse.getText().toString().equals("")) {
						Toast.makeText(PurchaseEditActivity.this, "�ֿ����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_storeHouse.setFocusable(true);
						ET_storeHouse.requestFocus();
						return;	
					}
					purchase.setStoreHouse(ET_storeHouse.getText().toString());
					/*����ҵ���߼����ϴ���Ʒ������Ϣ*/
					PurchaseEditActivity.this.setTitle("���ڸ�����Ʒ������Ϣ���Ե�...");
					String result = purchaseService.UpdatePurchase(purchase);
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
	    purchase = purchaseService.GetPurchase(purchaseId);
		this.TV_purchaseId.setText(purchaseId+"");
		for (int i = 0; i < goodsList.size(); i++) {
			if (purchase.getGoodObj().equals(goodsList.get(i).getGoodNo())) {
				this.spinner_goodObj.setSelection(i);
				break;
			}
		}
		this.ET_price.setText(purchase.getPrice() + "");
		this.ET_buyCount.setText(purchase.getBuyCount() + "");
		this.ET_totalMoney.setText(purchase.getTotalMoney() + "");
		Date inDate = new Date(purchase.getInDate().getTime());
		this.dp_inDate.init(inDate.getYear() + 1900,inDate.getMonth(), inDate.getDate(), null);
		this.ET_operatorMan.setText(purchase.getOperatorMan());
		this.ET_keepMan.setText(purchase.getKeepMan());
		this.ET_storeHouse.setText(purchase.getStoreHouse());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
