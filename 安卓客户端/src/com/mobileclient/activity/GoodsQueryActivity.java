package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Goods;
import com.mobileclient.domain.GoodClass;
import com.mobileclient.service.GoodClassService;

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
public class GoodsQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// ������Ʒ��������
	private EditText ET_goodNo;
	// ������Ʒ���������
	private Spinner spinner_goodClassObj;
	private ArrayAdapter<String> goodClassObj_adapter;
	private static  String[] goodClassObj_ShowText  = null;
	private List<GoodClass> goodClassList = null; 
	/*��Ʒ������ҵ���߼���*/
	private GoodClassService goodClassService = new GoodClassService();
	// ������Ʒ���������
	private EditText ET_goodName;
	/*��ѯ�����������浽���������*/
	private Goods queryConditionGoods = new Goods();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.goods_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���ð칫��Ʒ��ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_goodNo = (EditText) findViewById(R.id.ET_goodNo);
		spinner_goodClassObj = (Spinner) findViewById(R.id.Spinner_goodClassObj);
		// ��ȡ���е���Ʒ���
		try {
			goodClassList = goodClassService.QueryGoodClass(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int goodClassCount = goodClassList.size();
		goodClassObj_ShowText = new String[goodClassCount+1];
		goodClassObj_ShowText[0] = "������";
		for(int i=1;i<=goodClassCount;i++) { 
			goodClassObj_ShowText[i] = goodClassList.get(i-1).getGoodClassName();
		} 
		// ����ѡ������ArrayAdapter��������
		goodClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, goodClassObj_ShowText);
		// ������Ʒ��������б�ķ��
		goodClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_goodClassObj.setAdapter(goodClassObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_goodClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionGoods.setGoodClassObj(goodClassList.get(arg2-1).getGoodClassId()); 
				else
					queryConditionGoods.setGoodClassObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_goodClassObj.setVisibility(View.VISIBLE);
		ET_goodName = (EditText) findViewById(R.id.ET_goodName);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionGoods.setGoodNo(ET_goodNo.getText().toString());
					queryConditionGoods.setGoodName(ET_goodName.getText().toString());
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionGoods", queryConditionGoods);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
