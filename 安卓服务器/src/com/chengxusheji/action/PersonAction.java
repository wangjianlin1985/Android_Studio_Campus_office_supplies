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

    /*�������Ҫ��ѯ������: ��Ա���*/
    private String user_name;
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getUser_name() {
        return this.user_name;
    }

    /*�������Ҫ��ѯ������: ���ڲ���*/
    private Department departmentObj;
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String bornDate;
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }
    public String getBornDate() {
        return this.bornDate;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource DepartmentDAO departmentDAO;
    @Resource PersonDAO personDAO;

    /*��������Person����*/
    private Person person;
    public void setPerson(Person person) {
        this.person = person;
    }
    public Person getPerson() {
        return this.person;
    }

    /*��ת�����Person��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Department��Ϣ*/
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        return "add_view";
    }

    /*���Person��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddPerson() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��Ա����Ƿ��Ѿ�����*/
        String user_name = person.getUser_name();
        Person db_person = personDAO.GetPersonByUser_name(user_name);
        if(null != db_person) {
            ctx.put("error",  java.net.URLEncoder.encode("����Ա����Ѿ�����!"));
            return "error";
        }
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(person.getDepartmentObj().getDepartmentId());
            person.setDepartmentObj(departmentObj);
            personDAO.AddPerson(person);
            ctx.put("message",  java.net.URLEncoder.encode("Person��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Person���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPerson��Ϣ*/
    public String QueryPerson() {
        if(currentPage == 0) currentPage = 1;
        if(user_name == null) user_name = "";
        if(name == null) name = "";
        if(bornDate == null) bornDate = "";
        List<Person> personList = personDAO.QueryPersonInfo(user_name, departmentObj, name, bornDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        personDAO.CalculateTotalPageAndRecordNumber(user_name, departmentObj, name, bornDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = personDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryPersonOutputToExcel() { 
        if(user_name == null) user_name = "";
        if(name == null) name = "";
        if(bornDate == null) bornDate = "";
        List<Person> personList = personDAO.QueryPersonInfo(user_name,departmentObj,name,bornDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Person��Ϣ��¼"; 
        String[] headers = { "��Ա���","���ڲ���","����","�Ա�","��������","��ϵ�绰"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Person.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯPerson��Ϣ*/
    public String FrontQueryPerson() {
        if(currentPage == 0) currentPage = 1;
        if(user_name == null) user_name = "";
        if(name == null) name = "";
        if(bornDate == null) bornDate = "";
        List<Person> personList = personDAO.QueryPersonInfo(user_name, departmentObj, name, bornDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        personDAO.CalculateTotalPageAndRecordNumber(user_name, departmentObj, name, bornDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = personDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Person��Ϣ*/
    public String ModifyPersonQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������user_name��ȡPerson����*/
        Person person = personDAO.GetPersonByUser_name(user_name);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("person",  person);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Person��Ϣ*/
    public String FrontShowPersonQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������user_name��ȡPerson����*/
        Person person = personDAO.GetPersonByUser_name(user_name);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("person",  person);
        return "front_show_view";
    }

    /*�����޸�Person��Ϣ*/
    public String ModifyPerson() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(person.getDepartmentObj().getDepartmentId());
            person.setDepartmentObj(departmentObj);
            personDAO.UpdatePerson(person);
            ctx.put("message",  java.net.URLEncoder.encode("Person��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Person��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Person��Ϣ*/
    public String DeletePerson() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            personDAO.DeletePerson(user_name);
            ctx.put("message",  java.net.URLEncoder.encode("Personɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Personɾ��ʧ��!"));
            return "error";
        }
    }

}
