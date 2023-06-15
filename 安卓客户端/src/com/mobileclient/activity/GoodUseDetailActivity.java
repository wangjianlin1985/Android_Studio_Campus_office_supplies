package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.GoodUse;
import com.mobileclient.service.GoodUseService;
import com.mobileclient.domain.Goods;
import com.mobileclient.service.GoodsService;
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
public class GoodUseDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ��������id�ؼ�
	private TextView TV_useId;
	// ����������Ʒ�ؼ�
	private TextView TV_goodObj;
	// �������������ؼ�
	private TextView TV_useCount;
	// �������ۿؼ�
	private TextView TV_price;
	// �������ؼ�
	private TextView TV_totalMoney;
	// ��������ʱ��ؼ�
	private TextView TV_useTime;
	// ���������˿ؼ�
	private TextView TV_userMan;
	// ���������˿ؼ�
	private TextView TV_operatorMan;
	// �����ֿ�ؼ�
	private TextView TV_storeHouse;
	/* Ҫ�������Ʒ������Ϣ */
	GoodUse goodUse = new GoodUse(); 
	/* ��Ʒ���ù���ҵ���߼��� */
	private GoodUseService goodUseService = new GoodUseService();
	private GoodsService goodsService = new GoodsService();
	private int useId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.gooduse_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��Ʒ��������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_useId = (TextView) findViewById(R.id.TV_useId);
		TV_goodObj = (TextView) findViewById(R.id.TV_goodObj);
		TV_useCount = (TextView) findViewById(R.id.TV_useCount);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_totalMoney = (TextView) findViewById(R.id.TV_totalMoney);
		TV_useTime = (TextView) findViewById(R.id.TV_useTime);
		TV_userMan = (TextView) findViewById(R.id.TV_userMan);
		TV_operatorMan = (TextView) findViewById(R.id.TV_operatorMan);
		TV_storeHouse = (TextView) findViewById(R.id.TV_storeHouse);
		Bundle extras = this.getIntent().getExtras();
		useId = extras.getInt("useId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GoodUseDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    goodUse = goodUseService.GetGoodUse(useId); 
		this.TV_useId.setText(goodUse.getUseId() + "");
		Goods goodObj = goodsService.GetGoods(goodUse.getGoodObj());
		this.TV_goodObj.setText(goodObj.getGoodName());
		this.TV_useCount.setText(goodUse.getUseCount() + "");
		this.TV_price.setText(goodUse.getPrice() + "");
		this.TV_totalMoney.setText(goodUse.getTotalMoney() + "");
		Date useTime = new Date(goodUse.getUseTime().getTime());
		String useTimeStr = (useTime.getYear() + 1900) + "-" + (useTime.getMonth()+1) + "-" + useTime.getDate();
		this.TV_useTime.setText(useTimeStr);
		this.TV_userMan.setText(goodUse.getUserMan());
		this.TV_operatorMan.setText(goodUse.getOperatorMan());
		this.TV_storeHouse.setText(goodUse.getStoreHouse());
	} 
}
