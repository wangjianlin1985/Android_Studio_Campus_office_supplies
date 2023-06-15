package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Person;
import com.mobileclient.service.PersonService;
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
public class PersonDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ������Ա��ſؼ�
	private TextView TV_user_name;
	// ������¼����ؼ�
	private TextView TV_password;
	// �������ڲ��ſؼ�
	private TextView TV_departmentObj;
	// ���������ؼ�
	private TextView TV_name;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// �����������ڿؼ�
	private TextView TV_bornDate;
	// ������ϵ�绰�ؼ�
	private TextView TV_telephone;
	// ������ͥ��ַ�ؼ�
	private TextView TV_address;
	/* Ҫ�������Ա��Ϣ */
	Person person = new Person(); 
	/* ��Ա����ҵ���߼��� */
	private PersonService personService = new PersonService();
	private DepartmentService departmentService = new DepartmentService();
	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.person_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��Ա����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_user_name = (TextView) findViewById(R.id.TV_user_name);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_departmentObj = (TextView) findViewById(R.id.TV_departmentObj);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_bornDate = (TextView) findViewById(R.id.TV_bornDate);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_address = (TextView) findViewById(R.id.TV_address);
		Bundle extras = this.getIntent().getExtras();
		user_name = extras.getString("user_name");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PersonDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    person = personService.GetPerson(user_name); 
		this.TV_user_name.setText(person.getUser_name());
		this.TV_password.setText(person.getPassword());
		Department departmentObj = departmentService.GetDepartment(person.getDepartmentObj());
		this.TV_departmentObj.setText(departmentObj.getDepartmentName());
		this.TV_name.setText(person.getName());
		this.TV_sex.setText(person.getSex());
		Date bornDate = new Date(person.getBornDate().getTime());
		String bornDateStr = (bornDate.getYear() + 1900) + "-" + (bornDate.getMonth()+1) + "-" + bornDate.getDate();
		this.TV_bornDate.setText(bornDateStr);
		this.TV_telephone.setText(person.getTelephone());
		this.TV_address.setText(person.getAddress());
	} 
}
