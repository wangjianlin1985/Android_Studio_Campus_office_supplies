package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Goods;
import com.mobileclient.service.GoodsService;
import com.mobileclient.domain.GoodClass;
import com.mobileclient.service.GoodClassService;
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

public class GoodsEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ������Ʒ���TextView
	private TextView TV_goodNo;
	// ������Ʒ���������
	private Spinner spinner_goodClassObj;
	private ArrayAdapter<String> goodClassObj_adapter;
	private static  String[] goodClassObj_ShowText  = null;
	private List<GoodClass> goodClassList = null;
	/*��Ʒ������ҵ���߼���*/
	private GoodClassService goodClassService = new GoodClassService();
	// ������Ʒ���������
	private EditText ET_goodName;
	// ������ƷͼƬͼƬ��ؼ�
	private ImageView iv_goodPhoto;
	private Button btn_goodPhoto;
	protected int REQ_CODE_SELECT_IMAGE_goodPhoto = 1;
	private int REQ_CODE_CAMERA_goodPhoto = 2;
	// ��������ͺ������
	private EditText ET_specModel;
	// ����������λ�����
	private EditText ET_measureUnit;
	// ����������������
	private EditText ET_stockCount;
	// �������������
	private EditText ET_price;
	// ������������
	private EditText ET_totalMoney;
	// �����ֿ������
	private EditText ET_storeHouse;
	// ������ע�����
	private EditText ET_goodMemo;
	protected String carmera_path;
	/*Ҫ����İ칫��Ʒ��Ϣ*/
	Goods goods = new Goods();
	/*�칫��Ʒ����ҵ���߼���*/
	private GoodsService goodsService = new GoodsService();

	private String goodNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.goods_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭�칫��Ʒ��Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_goodNo = (TextView) findViewById(R.id.TV_goodNo);
		spinner_goodClassObj = (Spinner) findViewById(R.id.Spinner_goodClassObj);
		// ��ȡ���е���Ʒ���
		try {
			goodClassList = goodClassService.QueryGoodClass(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int goodClassCount = goodClassList.size();
		goodClassObj_ShowText = new String[goodClassCount];
		for(int i=0;i<goodClassCount;i++) { 
			goodClassObj_ShowText[i] = goodClassList.get(i).getGoodClassName();
		}
		// ����ѡ������ArrayAdapter��������
		goodClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, goodClassObj_ShowText);
		// ����ͼ����������б�ķ��
		goodClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_goodClassObj.setAdapter(goodClassObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_goodClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				goods.setGoodClassObj(goodClassList.get(arg2).getGoodClassId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_goodClassObj.setVisibility(View.VISIBLE);
		ET_goodName = (EditText) findViewById(R.id.ET_goodName);
		iv_goodPhoto = (ImageView) findViewById(R.id.iv_goodPhoto);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_goodPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(GoodsEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_goodPhoto);
			}
		});
		btn_goodPhoto = (Button) findViewById(R.id.btn_goodPhoto);
		btn_goodPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_goodPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_goodPhoto);  
			}
		});
		ET_specModel = (EditText) findViewById(R.id.ET_specModel);
		ET_measureUnit = (EditText) findViewById(R.id.ET_measureUnit);
		ET_stockCount = (EditText) findViewById(R.id.ET_stockCount);
		ET_price = (EditText) findViewById(R.id.ET_price);
		ET_totalMoney = (EditText) findViewById(R.id.ET_totalMoney);
		ET_storeHouse = (EditText) findViewById(R.id.ET_storeHouse);
		ET_goodMemo = (EditText) findViewById(R.id.ET_goodMemo);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		goodNo = extras.getString("goodNo");
		/*�����޸İ칫��Ʒ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��Ʒ����*/ 
					if(ET_goodName.getText().toString().equals("")) {
						Toast.makeText(GoodsEditActivity.this, "��Ʒ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_goodName.setFocusable(true);
						ET_goodName.requestFocus();
						return;	
					}
					goods.setGoodName(ET_goodName.getText().toString());
					if (!goods.getGoodPhoto().startsWith("upload/")) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						GoodsEditActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String goodPhoto = HttpUtil.uploadFile(goods.getGoodPhoto());
						GoodsEditActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						goods.setGoodPhoto(goodPhoto);
					} 
					/*��֤��ȡ����ͺ�*/ 
					if(ET_specModel.getText().toString().equals("")) {
						Toast.makeText(GoodsEditActivity.this, "����ͺ����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_specModel.setFocusable(true);
						ET_specModel.requestFocus();
						return;	
					}
					goods.setSpecModel(ET_specModel.getText().toString());
					/*��֤��ȡ������λ*/ 
					if(ET_measureUnit.getText().toString().equals("")) {
						Toast.makeText(GoodsEditActivity.this, "������λ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_measureUnit.setFocusable(true);
						ET_measureUnit.requestFocus();
						return;	
					}
					goods.setMeasureUnit(ET_measureUnit.getText().toString());
					/*��֤��ȡ�������*/ 
					if(ET_stockCount.getText().toString().equals("")) {
						Toast.makeText(GoodsEditActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_stockCount.setFocusable(true);
						ET_stockCount.requestFocus();
						return;	
					}
					goods.setStockCount(Integer.parseInt(ET_stockCount.getText().toString()));
					/*��֤��ȡ����*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(GoodsEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					goods.setPrice(Float.parseFloat(ET_price.getText().toString()));
					/*��֤��ȡ���*/ 
					if(ET_totalMoney.getText().toString().equals("")) {
						Toast.makeText(GoodsEditActivity.this, "������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_totalMoney.setFocusable(true);
						ET_totalMoney.requestFocus();
						return;	
					}
					goods.setTotalMoney(Float.parseFloat(ET_totalMoney.getText().toString()));
					/*��֤��ȡ�ֿ�*/ 
					if(ET_storeHouse.getText().toString().equals("")) {
						Toast.makeText(GoodsEditActivity.this, "�ֿ����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_storeHouse.setFocusable(true);
						ET_storeHouse.requestFocus();
						return;	
					}
					goods.setStoreHouse(ET_storeHouse.getText().toString());
					/*��֤��ȡ��ע*/ 
					if(ET_goodMemo.getText().toString().equals("")) {
						Toast.makeText(GoodsEditActivity.this, "��ע���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_goodMemo.setFocusable(true);
						ET_goodMemo.requestFocus();
						return;	
					}
					goods.setGoodMemo(ET_goodMemo.getText().toString());
					/*����ҵ���߼����ϴ��칫��Ʒ��Ϣ*/
					GoodsEditActivity.this.setTitle("���ڸ��°칫��Ʒ��Ϣ���Ե�...");
					String result = goodsService.UpdateGoods(goods);
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
	    goods = goodsService.GetGoods(goodNo);
		this.TV_goodNo.setText(goodNo);
		for (int i = 0; i < goodClassList.size(); i++) {
			if (goods.getGoodClassObj() == goodClassList.get(i).getGoodClassId()) {
				this.spinner_goodClassObj.setSelection(i);
				break;
			}
		}
		this.ET_goodName.setText(goods.getGoodName());
		byte[] goodPhoto_data = null;
		try {
			// ��ȡͼƬ����
			goodPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + goods.getGoodPhoto());
			Bitmap goodPhoto = BitmapFactory.decodeByteArray(goodPhoto_data, 0, goodPhoto_data.length);
			this.iv_goodPhoto.setImageBitmap(goodPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_specModel.setText(goods.getSpecModel());
		this.ET_measureUnit.setText(goods.getMeasureUnit());
		this.ET_stockCount.setText(goods.getStockCount() + "");
		this.ET_price.setText(goods.getPrice() + "");
		this.ET_totalMoney.setText(goods.getTotalMoney() + "");
		this.ET_storeHouse.setText(goods.getStoreHouse());
		this.ET_goodMemo.setText(goods.getGoodMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_goodPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_goodPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_goodPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_goodPhoto.setImageBitmap(booImageBm);
				this.iv_goodPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.goods.setGoodPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_goodPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_goodPhoto.setImageBitmap(bm); 
				this.iv_goodPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			goods.setGoodPhoto(filename); 
		}
	}
}
