package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.GoodUse;
import com.mobileclient.domain.Goods;
import com.mobileclient.service.GoodsService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class GoodUseQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ����������Ʒ������
	private Spinner spinner_goodObj;
	private ArrayAdapter<String> goodObj_adapter;
	private static  String[] goodObj_ShowText  = null;
	private List<Goods> goodsList = null; 
	/*�칫��Ʒ����ҵ���߼���*/
	private GoodsService goodsService = new GoodsService();
	// ����ʱ��ؼ�
	private DatePicker dp_useTime;
	private CheckBox cb_useTime;
	// ���������������
	private EditText ET_operatorMan;
	/*��ѯ�����������浽���������*/
	private GoodUse queryConditionGoodUse = new GoodUse();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.gooduse_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("������Ʒ���ò�ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		spinner_goodObj = (Spinner) findViewById(R.id.Spinner_goodObj);
		// ��ȡ���еİ칫��Ʒ
		try {
			goodsList = goodsService.QueryGoods(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int goodsCount = goodsList.size();
		goodObj_ShowText = new String[goodsCount+1];
		goodObj_ShowText[0] = "������";
		for(int i=1;i<=goodsCount;i++) { 
			goodObj_ShowText[i] = goodsList.get(i-1).getGoodName();
		} 
		// ����ѡ������ArrayAdapter��������
		goodObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, goodObj_ShowText);
		// ����������Ʒ�����б�ķ��
		goodObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_goodObj.setAdapter(goodObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_goodObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionGoodUse.setGoodObj(goodsList.get(arg2-1).getGoodNo()); 
				else
					queryConditionGoodUse.setGoodObj("");
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_goodObj.setVisibility(View.VISIBLE);
		dp_useTime = (DatePicker) findViewById(R.id.dp_useTime);
		cb_useTime = (CheckBox) findViewById(R.id.cb_useTime);
		ET_operatorMan = (EditText) findViewById(R.id.ET_operatorMan);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					if(cb_useTime.isChecked()) {
						/*��ȡ����ʱ��*/
						Date useTime = new Date(dp_useTime.getYear()-1900,dp_useTime.getMonth(),dp_useTime.getDayOfMonth());
						queryConditionGoodUse.setUseTime(new Timestamp(useTime.getTime()));
					} else {
						queryConditionGoodUse.setUseTime(null);
					} 
					queryConditionGoodUse.setOperatorMan(ET_operatorMan.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionGoodUse", queryConditionGoodUse);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
