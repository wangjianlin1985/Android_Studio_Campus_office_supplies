package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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

public class GoodClassEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ������Ʒ���idTextView
	private TextView TV_goodClassId;
	// ������Ʒ������������
	private EditText ET_goodClassName;
	protected String carmera_path;
	/*Ҫ�������Ʒ�����Ϣ*/
	GoodClass goodClass = new GoodClass();
	/*��Ʒ������ҵ���߼���*/
	private GoodClassService goodClassService = new GoodClassService();

	private int goodClassId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.goodclass_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭��Ʒ�����Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_goodClassId = (TextView) findViewById(R.id.TV_goodClassId);
		ET_goodClassName = (EditText) findViewById(R.id.ET_goodClassName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		goodClassId = extras.getInt("goodClassId");
		/*�����޸���Ʒ���ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��Ʒ�������*/ 
					if(ET_goodClassName.getText().toString().equals("")) {
						Toast.makeText(GoodClassEditActivity.this, "��Ʒ����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_goodClassName.setFocusable(true);
						ET_goodClassName.requestFocus();
						return;	
					}
					goodClass.setGoodClassName(ET_goodClassName.getText().toString());
					/*����ҵ���߼����ϴ���Ʒ�����Ϣ*/
					GoodClassEditActivity.this.setTitle("���ڸ�����Ʒ�����Ϣ���Ե�...");
					String result = goodClassService.UpdateGoodClass(goodClass);
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
	    goodClass = goodClassService.GetGoodClass(goodClassId);
		this.TV_goodClassId.setText(goodClassId+"");
		this.ET_goodClassName.setText(goodClass.getGoodClassName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
