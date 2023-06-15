package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.GoodClass;
import com.mobileclient.service.GoodClassService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class GoodClassDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������Ʒ���id�ؼ�
	private TextView TV_goodClassId;
	// ������Ʒ������ƿؼ�
	private TextView TV_goodClassName;
	/* Ҫ�������Ʒ�����Ϣ */
	GoodClass goodClass = new GoodClass(); 
	/* ��Ʒ������ҵ���߼��� */
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
		setContentView(R.layout.goodclass_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��Ʒ�������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_goodClassId = (TextView) findViewById(R.id.TV_goodClassId);
		TV_goodClassName = (TextView) findViewById(R.id.TV_goodClassName);
		Bundle extras = this.getIntent().getExtras();
		goodClassId = extras.getInt("goodClassId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GoodClassDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    goodClass = goodClassService.GetGoodClass(goodClassId); 
		this.TV_goodClassId.setText(goodClass.getGoodClassId() + "");
		this.TV_goodClassName.setText(goodClass.getGoodClassName());
	} 
}
