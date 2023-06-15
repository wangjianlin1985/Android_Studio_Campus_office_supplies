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
	// 声明返回按钮
	private Button btnReturn;
	// 声明人员编号控件
	private TextView TV_user_name;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明所在部门控件
	private TextView TV_departmentObj;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_sex;
	// 声明出生日期控件
	private TextView TV_bornDate;
	// 声明联系电话控件
	private TextView TV_telephone;
	// 声明家庭地址控件
	private TextView TV_address;
	/* 要保存的人员信息 */
	Person person = new Person(); 
	/* 人员管理业务逻辑层 */
	private PersonService personService = new PersonService();
	private DepartmentService departmentService = new DepartmentService();
	private String user_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.person_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看人员详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
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
	/* 初始化显示详情界面的数据 */
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
