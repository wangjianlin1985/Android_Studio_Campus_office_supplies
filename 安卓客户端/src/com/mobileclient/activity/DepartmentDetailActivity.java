package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
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
public class DepartmentDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// �������ű�ſؼ�
	private TextView TV_departmentId;
	// �����������ƿؼ�
	private TextView TV_departmentName;
	// �����������ؼ�
	private TextView TV_departmentType;
	// ������ע�ؼ�
	private TextView TV_departmentMemo;
	/* Ҫ����Ĳ�����Ϣ */
	Department department = new Department(); 
	/* ���Ź���ҵ���߼��� */
	private DepartmentService departmentService = new DepartmentService();
	private int departmentId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.department_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_departmentId = (TextView) findViewById(R.id.TV_departmentId);
		TV_departmentName = (TextView) findViewById(R.id.TV_departmentName);
		TV_departmentType = (TextView) findViewById(R.id.TV_departmentType);
		TV_departmentMemo = (TextView) findViewById(R.id.TV_departmentMemo);
		Bundle extras = this.getIntent().getExtras();
		departmentId = extras.getInt("departmentId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DepartmentDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    department = departmentService.GetDepartment(departmentId); 
		this.TV_departmentId.setText(department.getDepartmentId() + "");
		this.TV_departmentName.setText(department.getDepartmentName());
		this.TV_departmentType.setText(department.getDepartmentType());
		this.TV_departmentMemo.setText(department.getDepartmentMemo());
	} 
}
