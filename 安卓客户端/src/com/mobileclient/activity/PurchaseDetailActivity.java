package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Purchase;
import com.mobileclient.service.PurchaseService;
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
public class PurchaseDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ��������id�ؼ�
	private TextView TV_purchaseId;
	// ����������Ʒ�ؼ�
	private TextView TV_goodObj;
	// �������ü۸�ؼ�
	private TextView TV_price;
	// �������������ؼ�
	private TextView TV_buyCount;
	// �������ý��ؼ�
	private TextView TV_totalMoney;
	// �������ʱ��ؼ�
	private TextView TV_inDate;
	// ���������˿ؼ�
	private TextView TV_operatorMan;
	// ���������˿ؼ�
	private TextView TV_keepMan;
	// �����ֿ�ؼ�
	private TextView TV_storeHouse;
	/* Ҫ�������Ʒ������Ϣ */
	Purchase purchase = new Purchase(); 
	/* ��Ʒ���ù���ҵ���߼��� */
	private PurchaseService purchaseService = new PurchaseService();
	private GoodsService goodsService = new GoodsService();
	private int purchaseId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.purchase_detail);
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
		TV_purchaseId = (TextView) findViewById(R.id.TV_purchaseId);
		TV_goodObj = (TextView) findViewById(R.id.TV_goodObj);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_buyCount = (TextView) findViewById(R.id.TV_buyCount);
		TV_totalMoney = (TextView) findViewById(R.id.TV_totalMoney);
		TV_inDate = (TextView) findViewById(R.id.TV_inDate);
		TV_operatorMan = (TextView) findViewById(R.id.TV_operatorMan);
		TV_keepMan = (TextView) findViewById(R.id.TV_keepMan);
		TV_storeHouse = (TextView) findViewById(R.id.TV_storeHouse);
		Bundle extras = this.getIntent().getExtras();
		purchaseId = extras.getInt("purchaseId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PurchaseDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    purchase = purchaseService.GetPurchase(purchaseId); 
		this.TV_purchaseId.setText(purchase.getPurchaseId() + "");
		Goods goodObj = goodsService.GetGoods(purchase.getGoodObj());
		this.TV_goodObj.setText(goodObj.getGoodName());
		this.TV_price.setText(purchase.getPrice() + "");
		this.TV_buyCount.setText(purchase.getBuyCount() + "");
		this.TV_totalMoney.setText(purchase.getTotalMoney() + "");
		Date inDate = new Date(purchase.getInDate().getTime());
		String inDateStr = (inDate.getYear() + 1900) + "-" + (inDate.getMonth()+1) + "-" + inDate.getDate();
		this.TV_inDate.setText(inDateStr);
		this.TV_operatorMan.setText(purchase.getOperatorMan());
		this.TV_keepMan.setText(purchase.getKeepMan());
		this.TV_storeHouse.setText(purchase.getStoreHouse());
	} 
}
