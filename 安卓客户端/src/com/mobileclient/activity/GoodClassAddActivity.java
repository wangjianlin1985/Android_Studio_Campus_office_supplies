package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class GoodClassAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ������Ʒ������������
	private EditText ET_goodClassName;
	protected String carmera_path;
	/*Ҫ�������Ʒ�����Ϣ*/
	GoodClass goodClass = new GoodClass();
	/*��Ʒ������ҵ���߼���*/
	private GoodClassService goodClassService = new GoodClassService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.goodclass_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�����Ʒ���");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_goodClassName = (EditText) findViewById(R.id.ET_goodClassName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*���������Ʒ���ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��Ʒ�������*/ 
					if(ET_goodClassName.getText().toString().equals("")) {
						Toast.makeText(GoodClassAddActivity.this, "��Ʒ����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_goodClassName.setFocusable(true);
						ET_goodClassName.requestFocus();
						return;	
					}
					goodClass.setGoodClassName(ET_goodClassName.getText().toString());
					/*����ҵ���߼����ϴ���Ʒ�����Ϣ*/
					GoodClassAddActivity.this.setTitle("�����ϴ���Ʒ�����Ϣ���Ե�...");
					String result = goodClassService.AddGoodClass(goodClass);
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
