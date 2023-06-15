package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Person;
import com.mobileclient.service.PersonService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class PersonEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// ������Ա���TextView
	private TextView TV_user_name;
	// ������¼���������
	private EditText ET_password;
	// �������ڲ���������
	private Spinner spinner_departmentObj;
	private ArrayAdapter<String> departmentObj_adapter;
	private static  String[] departmentObj_ShowText  = null;
	private List<Department> departmentList = null;
	/*���ڲ��Ź���ҵ���߼���*/
	private DepartmentService departmentService = new DepartmentService();
	// �������������
	private EditText ET_name;
	// �����Ա������
	private EditText ET_sex;
	// ����������ڿؼ�
	private DatePicker dp_bornDate;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	// ������ͥ��ַ�����
	private EditText ET_address;
	protected String carmera_path;
	/*Ҫ�������Ա��Ϣ*/
	Person person = new Person();
	/*��Ա����ҵ���߼���*/
	private PersonService personService = new PersonService();

	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.person_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭��Ա��Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_user_name = (TextView) findViewById(R.id.TV_user_name);
		ET_password = (EditText) findViewById(R.id.ET_password);
		spinner_departmentObj = (Spinner) findViewById(R.id.Spinner_departmentObj);
		// ��ȡ���е����ڲ���
		try {
			departmentList = departmentService.QueryDepartment(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int departmentCount = departmentList.size();
		departmentObj_ShowText = new String[departmentCount];
		for(int i=0;i<departmentCount;i++) { 
			departmentObj_ShowText[i] = departmentList.get(i).getDepartmentName();
		}
		// ����ѡ������ArrayAdapter��������
		departmentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, departmentObj_ShowText);
		// ����ͼ����������б�ķ��
		departmentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_departmentObj.setAdapter(departmentObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_departmentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				person.setDepartmentObj(departmentList.get(arg2).getDepartmentId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_departmentObj.setVisibility(View.VISIBLE);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_bornDate = (DatePicker)this.findViewById(R.id.dp_bornDate);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_address = (EditText) findViewById(R.id.ET_address);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		user_name = extras.getString("user_name");
		/*�����޸���Ա��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(PersonEditActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					person.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(PersonEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					person.setName(ET_name.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(PersonEditActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					person.setSex(ET_sex.getText().toString());
					/*��ȡ��������*/
					Date bornDate = new Date(dp_bornDate.getYear()-1900,dp_bornDate.getMonth(),dp_bornDate.getDayOfMonth());
					person.setBornDate(new Timestamp(bornDate.getTime()));
					/*��֤��ȡ��ϵ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(PersonEditActivity.this, "��ϵ�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					person.setTelephone(ET_telephone.getText().toString());
					/*��֤��ȡ��ͥ��ַ*/ 
					if(ET_address.getText().toString().equals("")) {
						Toast.makeText(PersonEditActivity.this, "��ͥ��ַ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_address.setFocusable(true);
						ET_address.requestFocus();
						return;	
					}
					person.setAddress(ET_address.getText().toString());
					/*����ҵ���߼����ϴ���Ա��Ϣ*/
					PersonEditActivity.this.setTitle("���ڸ�����Ա��Ϣ���Ե�...");
					String result = personService.UpdatePerson(person);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    person = personService.GetPerson(user_name);
		this.TV_user_name.setText(user_name);
		this.ET_password.setText(person.getPassword());
		for (int i = 0; i < departmentList.size(); i++) {
			if (person.getDepartmentObj() == departmentList.get(i).getDepartmentId()) {
				this.spinner_departmentObj.setSelection(i);
				break;
			}
		}
		this.ET_name.setText(person.getName());
		this.ET_sex.setText(person.getSex());
		Date bornDate = new Date(person.getBornDate().getTime());
		this.dp_bornDate.init(bornDate.getYear() + 1900,bornDate.getMonth(), bornDate.getDate(), null);
		this.ET_telephone.setText(person.getTelephone());
		this.ET_address.setText(person.getAddress());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
