package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.PersonDAO;
import com.chengxusheji.domain.Person;
import com.chengxusheji.dao.DepartmentDAO;
import com.chengxusheji.domain.Department;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PersonAction extends BaseAction {

    /*界面层需要查询的属性: 人员编号*/
    private String user_name;
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getUser_name() {
        return this.user_name;
    }

    /*界面层需要查询的属性: 所在部门*/
    private Department departmentObj;
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 出生日期*/
    private String bornDate;
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }
    public String getBornDate() {
        return this.bornDate;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource DepartmentDAO departmentDAO;
    @Resource PersonDAO personDAO;

    /*待操作的Person对象*/
    private Person person;
    public void setPerson(Person person) {
        this.person = person;
    }
    public Person getPerson() {
        return this.person;
    }

    /*跳转到添加Person视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Department信息*/
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        return "add_view";
    }

    /*添加Person信息*/
    @SuppressWarnings("deprecation")
    public String AddPerson() {
        ActionContext ctx = ActionContext.getContext();
        /*验证人员编号是否已经存在*/
        String user_name = person.getUser_name();
        Person db_person = personDAO.GetPersonByUser_name(user_name);
        if(null != db_person) {
            ctx.put("error",  java.net.URLEncoder.encode("该人员编号已经存在!"));
            return "error";
        }
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(person.getDepartmentObj().getDepartmentId());
            person.setDepartmentObj(departmentObj);
            personDAO.AddPerson(person);
            ctx.put("message",  java.net.URLEncoder.encode("Person添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Person添加失败!"));
            return "error";
        }
    }

    /*查询Person信息*/
    public String QueryPerson() {
        if(currentPage == 0) currentPage = 1;
        if(user_name == null) user_name = "";
        if(name == null) name = "";
        if(bornDate == null) bornDate = "";
        List<Person> personList = personDAO.QueryPersonInfo(user_name, departmentObj, name, bornDate, currentPage);
        /*计算总的页数和总的记录数*/
        personDAO.CalculateTotalPageAndRecordNumber(user_name, departmentObj, name, bornDate);
        /*获取到总的页码数目*/
        totalPage = personDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = personDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("personList",  personList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("user_name", user_name);
        ctx.put("departmentObj", departmentObj);
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("name", name);
        ctx.put("bornDate", bornDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryPersonOutputToExcel() { 
        if(user_name == null) user_name = "";
        if(name == null) name = "";
        if(bornDate == null) bornDate = "";
        List<Person> personList = personDAO.QueryPersonInfo(user_name,departmentObj,name,bornDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Person信息记录"; 
        String[] headers = { "人员编号","所在部门","姓名","性别","出生日期","联系电话"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<personList.size();i++) {
        	Person person = personList.get(i); 
        	dataset.add(new String[]{person.getUser_name(),person.getDepartmentObj().getDepartmentName(),
person.getName(),person.getSex(),new SimpleDateFormat("yyyy-MM-dd").format(person.getBornDate()),person.getTelephone()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Person.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询Person信息*/
    public String FrontQueryPerson() {
        if(currentPage == 0) currentPage = 1;
        if(user_name == null) user_name = "";
        if(name == null) name = "";
        if(bornDate == null) bornDate = "";
        List<Person> personList = personDAO.QueryPersonInfo(user_name, departmentObj, name, bornDate, currentPage);
        /*计算总的页数和总的记录数*/
        personDAO.CalculateTotalPageAndRecordNumber(user_name, departmentObj, name, bornDate);
        /*获取到总的页码数目*/
        totalPage = personDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = personDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("personList",  personList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("user_name", user_name);
        ctx.put("departmentObj", departmentObj);
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("name", name);
        ctx.put("bornDate", bornDate);
        return "front_query_view";
    }

    /*查询要修改的Person信息*/
    public String ModifyPersonQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键user_name获取Person对象*/
        Person person = personDAO.GetPersonByUser_name(user_name);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("person",  person);
        return "modify_view";
    }

    /*查询要修改的Person信息*/
    public String FrontShowPersonQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键user_name获取Person对象*/
        Person person = personDAO.GetPersonByUser_name(user_name);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("person",  person);
        return "front_show_view";
    }

    /*更新修改Person信息*/
    public String ModifyPerson() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(person.getDepartmentObj().getDepartmentId());
            person.setDepartmentObj(departmentObj);
            personDAO.UpdatePerson(person);
            ctx.put("message",  java.net.URLEncoder.encode("Person信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Person信息更新失败!"));
            return "error";
       }
   }

    /*删除Person信息*/
    public String DeletePerson() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            personDAO.DeletePerson(user_name);
            ctx.put("message",  java.net.URLEncoder.encode("Person删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Person删除失败!"));
            return "error";
        }
    }

}
