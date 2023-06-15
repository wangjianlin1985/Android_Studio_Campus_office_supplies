package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Goods;
import com.mobileclient.service.GoodsService;
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
public class GoodsDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������Ʒ��ſؼ�
	private TextView TV_goodNo;
	// ������Ʒ���ؼ�
	private TextView TV_goodClassObj;
	// ������Ʒ���ƿؼ�
	private TextView TV_goodName;
	// ������ƷͼƬͼƬ��
	private ImageView iv_goodPhoto;
	// ��������ͺſؼ�
	private TextView TV_specModel;
	// ����������λ�ؼ�
	private TextView TV_measureUnit;
	// ������������ؼ�
	private TextView TV_stockCount;
	// �������ۿؼ�
	private TextView TV_price;
	// �������ؼ�
	private TextView TV_totalMoney;
	// �����ֿ�ؼ�
	private TextView TV_storeHouse;
	// ������ע�ؼ�
	private TextView TV_goodMemo;
	/* Ҫ����İ칫��Ʒ��Ϣ */
	Goods goods = new Goods(); 
	/* �칫��Ʒ����ҵ���߼��� */
	private GoodsService goodsService = new GoodsService();
	private GoodClassService goodClassService = new GoodClassService();
	private String goodNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.goods_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴�칫��Ʒ����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_goodNo = (TextView) findViewById(R.id.TV_goodNo);
		TV_goodClassObj = (TextView) findViewById(R.id.TV_goodClassObj);
		TV_goodName = (TextView) findViewById(R.id.TV_goodName);
		iv_goodPhoto = (ImageView) findViewById(R.id.iv_goodPhoto); 
		TV_specModel = (TextView) findViewById(R.id.TV_specModel);
		TV_measureUnit = (TextView) findViewById(R.id.TV_measureUnit);
		TV_stockCount = (TextView) findViewById(R.id.TV_stockCount);
		TV_price = (TextView) findViewById(R.id.TV_price);
		TV_totalMoney = (TextView) findViewById(R.id.TV_totalMoney);
		TV_storeHouse = (TextView) findViewById(R.id.TV_storeHouse);
		TV_goodMemo = (TextView) findViewById(R.id.TV_goodMemo);
		Bundle extras = this.getIntent().getExtras();
		goodNo = extras.getString("goodNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				GoodsDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    goods = goodsService.GetGoods(goodNo); 
		this.TV_goodNo.setText(goods.getGoodNo());
		GoodClass goodClassObj = goodClassService.GetGoodClass(goods.getGoodClassObj());
		this.TV_goodClassObj.setText(goodClassObj.getGoodClassName());
		this.TV_goodName.setText(goods.getGoodName());
		byte[] goodPhoto_data = null;
		try {
			// ��ȡͼƬ����
			goodPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + goods.getGoodPhoto());
			Bitmap goodPhoto = BitmapFactory.decodeByteArray(goodPhoto_data, 0,goodPhoto_data.length);
			this.iv_goodPhoto.setImageBitmap(goodPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_specModel.setText(goods.getSpecModel());
		this.TV_measureUnit.setText(goods.getMeasureUnit());
		this.TV_stockCount.setText(goods.getStockCount() + "");
		this.TV_price.setText(goods.getPrice() + "");
		this.TV_totalMoney.setText(goods.getTotalMoney() + "");
		this.TV_storeHouse.setText(goods.getStoreHouse());
		this.TV_goodMemo.setText(goods.getGoodMemo());
	} 
}
