package com.mobileclient.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.GoodUse;
import com.mobileclient.service.GoodUseService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.GoodUseSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class GoodUseListActivity extends Activity {
	GoodUseSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	int useId;
	/* ��Ʒ���ò���ҵ���߼������ */
	GoodUseService goodUseService = new GoodUseService();
	/*�����ѯ������������Ʒ���ö���*/
	private GoodUse queryConditionGoodUse;

	private MyProgressDialog dialog; //������	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.gooduse_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//�������ؼ�
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GoodUseListActivity.this, GoodUseQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//�˴���requestCodeӦ�������������е��õ�requestCodeһ��
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("��Ʒ���ò�ѯ�б�");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(GoodUseListActivity.this, GoodUseAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		setViews();
	}

	//���������������secondActivity�з���ʱ���ô˺���
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionGoodUse = (GoodUse)extras.getSerializable("queryConditionGoodUse");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionGoodUse = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//�����߳��н����������ݲ���
				list = getDatas();
				//������ʧ��handler��֪ͨ���߳��������
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new GoodUseSimpleAdapter(GoodUseListActivity.this, list,
	        					R.layout.gooduse_list_item,
	        					new String[] { "goodObj","useCount","price","totalMoney","useTime","userMan","operatorMan" },
	        					new int[] { R.id.tv_goodObj,R.id.tv_useCount,R.id.tv_price,R.id.tv_totalMoney,R.id.tv_useTime,R.id.tv_userMan,R.id.tv_operatorMan,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// ��ӳ������
		lv.setOnCreateContextMenuListener(goodUseListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	int useId = Integer.parseInt(list.get(arg2).get("useId").toString());
            	Intent intent = new Intent();
            	intent.setClass(GoodUseListActivity.this, GoodUseDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putInt("useId", useId);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener goodUseListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			menu.add(0, 0, 0, "�༭��Ʒ������Ϣ"); 
			menu.add(0, 1, 0, "ɾ����Ʒ������Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭��Ʒ������Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ����id
			useId = Integer.parseInt(list.get(position).get("useId").toString());
			Intent intent = new Intent();
			intent.setClass(GoodUseListActivity.this, GoodUseEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("useId", useId);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// ɾ����Ʒ������Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ����id
			useId = Integer.parseInt(list.get(position).get("useId").toString());
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(GoodUseListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = goodUseService.DeleteGoodUse(useId);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯ��Ʒ������Ϣ */
			List<GoodUse> goodUseList = goodUseService.QueryGoodUse(queryConditionGoodUse);
			for (int i = 0; i < goodUseList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("useId",goodUseList.get(i).getUseId());
				map.put("goodObj", goodUseList.get(i).getGoodObj());
				map.put("useCount", goodUseList.get(i).getUseCount());
				map.put("price", goodUseList.get(i).getPrice());
				map.put("totalMoney", goodUseList.get(i).getTotalMoney());
				map.put("useTime", goodUseList.get(i).getUseTime());
				map.put("userMan", goodUseList.get(i).getUserMan());
				map.put("operatorMan", goodUseList.get(i).getOperatorMan());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
