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
	// 声明返回按钮
	private Button btnReturn;
	// 声明物品编号控件
	private TextView TV_goodNo;
	// 声明商品类别控件
	private TextView TV_goodClassObj;
	// 声明物品名称控件
	private TextView TV_goodName;
	// 声明物品图片图片框
	private ImageView iv_goodPhoto;
	// 声明规格型号控件
	private TextView TV_specModel;
	// 声明计量单位控件
	private TextView TV_measureUnit;
	// 声明库存数量控件
	private TextView TV_stockCount;
	// 声明单价控件
	private TextView TV_price;
	// 声明金额控件
	private TextView TV_totalMoney;
	// 声明仓库控件
	private TextView TV_storeHouse;
	// 声明备注控件
	private TextView TV_goodMemo;
	/* 要保存的办公用品信息 */
	Goods goods = new Goods(); 
	/* 办公用品管理业务逻辑层 */
	private GoodsService goodsService = new GoodsService();
	private GoodClassService goodClassService = new GoodClassService();
	private String goodNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.goods_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看办公用品详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
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
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    goods = goodsService.GetGoods(goodNo); 
		this.TV_goodNo.setText(goods.getGoodNo());
		GoodClass goodClassObj = goodClassService.GetGoodClass(goods.getGoodClassObj());
		this.TV_goodClassObj.setText(goodClassObj.getGoodClassName());
		this.TV_goodName.setText(goods.getGoodName());
		byte[] goodPhoto_data = null;
		try {
			// 获取图片数据
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
