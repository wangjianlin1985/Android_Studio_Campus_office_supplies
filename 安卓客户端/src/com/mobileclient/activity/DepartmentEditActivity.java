package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
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

public class DepartmentEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明部门编号TextView
	private TextView TV_departmentId;
	// 声明部门名称输入框
	private EditText ET_departmentName;
	// 声明部门类别输入框
	private EditText ET_departmentType;
	// 声明备注输入框
	private EditText ET_departmentMemo;
	protected String carmera_path;
	/*要保存的部门信息*/
	Department department = new Department();
	/*部门管理业务逻辑层*/
	private DepartmentService departmentService = new DepartmentService();

	private int departmentId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.department_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑部门信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_departmentId = (TextView) findViewById(R.id.TV_departmentId);
		ET_departmentName = (EditText) findViewById(R.id.ET_departmentName);
		ET_departmentType = (EditText) findViewById(R.id.ET_departmentType);
		ET_departmentMemo = (EditText) findViewById(R.id.ET_departmentMemo);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		departmentId = extras.getInt("departmentId");
		/*单击修改部门按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取部门名称*/ 
					if(ET_departmentName.getText().toString().equals("")) {
						Toast.makeText(DepartmentEditActivity.this, "部门名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_departmentName.setFocusable(true);
						ET_departmentName.requestFocus();
						return;	
					}
					department.setDepartmentName(ET_departmentName.getText().toString());
					/*验证获取部门类别*/ 
					if(ET_departmentType.getText().toString().equals("")) {
						Toast.makeText(DepartmentEditActivity.this, "部门类别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_departmentType.setFocusable(true);
						ET_departmentType.requestFocus();
						return;	
					}
					department.setDepartmentType(ET_departmentType.getText().toString());
					/*验证获取备注*/ 
					if(ET_departmentMemo.getText().toString().equals("")) {
						Toast.makeText(DepartmentEditActivity.this, "备注输入不能为空!", Toast.LENGTH_LONG).show();
						ET_departmentMemo.setFocusable(true);
						ET_departmentMemo.requestFocus();
						return;	
					}
					department.setDepartmentMemo(ET_departmentMemo.getText().toString());
					/*调用业务逻辑层上传部门信息*/
					DepartmentEditActivity.this.setTitle("正在更新部门信息，稍等...");
					String result = departmentService.UpdateDepartment(department);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    department = departmentService.GetDepartment(departmentId);
		this.TV_departmentId.setText(departmentId+"");
		this.ET_departmentName.setText(department.getDepartmentName());
		this.ET_departmentType.setText(department.getDepartmentType());
		this.ET_departmentMemo.setText(department.getDepartmentMemo());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
