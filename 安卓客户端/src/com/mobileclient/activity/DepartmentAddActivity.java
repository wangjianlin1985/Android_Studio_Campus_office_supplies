package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Department;
import com.mobileclient.service.DepartmentService;
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
public class DepartmentAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����������������
	private EditText ET_departmentName;
	// ����������������
	private EditText ET_departmentType;
	// ������ע�����
	private EditText ET_departmentMemo;
	protected String carmera_path;
	/*Ҫ����Ĳ�����Ϣ*/
	Department department = new Department();
	/*���Ź���ҵ���߼���*/
	private DepartmentService departmentService = new DepartmentService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.department_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("��Ӳ���");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_departmentName = (EditText) findViewById(R.id.ET_departmentName);
		ET_departmentType = (EditText) findViewById(R.id.ET_departmentType);
		ET_departmentMemo = (EditText) findViewById(R.id.ET_departmentMemo);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������Ӳ��Ű�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��������*/ 
					if(ET_departmentName.getText().toString().equals("")) {
						Toast.makeText(DepartmentAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_departmentName.setFocusable(true);
						ET_departmentName.requestFocus();
						return;	
					}
					department.setDepartmentName(ET_departmentName.getText().toString());
					/*��֤��ȡ�������*/ 
					if(ET_departmentType.getText().toString().equals("")) {
						Toast.makeText(DepartmentAddActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_departmentType.setFocusable(true);
						ET_departmentType.requestFocus();
						return;	
					}
					department.setDepartmentType(ET_departmentType.getText().toString());
					/*��֤��ȡ��ע*/ 
					if(ET_departmentMemo.getText().toString().equals("")) {
						Toast.makeText(DepartmentAddActivity.this, "��ע���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_departmentMemo.setFocusable(true);
						ET_departmentMemo.requestFocus();
						return;	
					}
					department.setDepartmentMemo(ET_departmentMemo.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					DepartmentAddActivity.this.setTitle("�����ϴ�������Ϣ���Ե�...");
					String result = departmentService.AddDepartment(department);
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
