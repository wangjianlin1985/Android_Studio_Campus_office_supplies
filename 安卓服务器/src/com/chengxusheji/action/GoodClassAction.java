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
import com.chengxusheji.dao.GoodClassDAO;
import com.chengxusheji.domain.GoodClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class GoodClassAction extends BaseAction {

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

    private int goodClassId;
    public void setGoodClassId(int goodClassId) {
        this.goodClassId = goodClassId;
    }
    public int getGoodClassId() {
        return goodClassId;
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
    @Resource GoodClassDAO goodClassDAO;

    /*待操作的GoodClass对象*/
    private GoodClass goodClass;
    public void setGoodClass(GoodClass goodClass) {
        this.goodClass = goodClass;
    }
    public GoodClass getGoodClass() {
        return this.goodClass;
    }

    /*跳转到添加GoodClass视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加GoodClass信息*/
    @SuppressWarnings("deprecation")
    public String AddGoodClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            goodClassDAO.AddGoodClass(goodClass);
            ctx.put("message",  java.net.URLEncoder.encode("GoodClass添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodClass添加失败!"));
            return "error";
        }
    }

    /*查询GoodClass信息*/
    public String QueryGoodClass() {
        if(currentPage == 0) currentPage = 1;
        List<GoodClass> goodClassList = goodClassDAO.QueryGoodClassInfo(currentPage);
        /*计算总的页数和总的记录数*/
        goodClassDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = goodClassDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = goodClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodClassList",  goodClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryGoodClassOutputToExcel() { 
        List<GoodClass> goodClassList = goodClassDAO.QueryGoodClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GoodClass信息记录"; 
        String[] headers = { "物品类别id","物品类别名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<goodClassList.size();i++) {
        	GoodClass goodClass = goodClassList.get(i); 
        	dataset.add(new String[]{goodClass.getGoodClassId() + "",goodClass.getGoodClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"GoodClass.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询GoodClass信息*/
    public String FrontQueryGoodClass() {
        if(currentPage == 0) currentPage = 1;
        List<GoodClass> goodClassList = goodClassDAO.QueryGoodClassInfo(currentPage);
        /*计算总的页数和总的记录数*/
        goodClassDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = goodClassDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = goodClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodClassList",  goodClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的GoodClass信息*/
    public String ModifyGoodClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键goodClassId获取GoodClass对象*/
        GoodClass goodClass = goodClassDAO.GetGoodClassByGoodClassId(goodClassId);

        ctx.put("goodClass",  goodClass);
        return "modify_view";
    }

    /*查询要修改的GoodClass信息*/
    public String FrontShowGoodClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键goodClassId获取GoodClass对象*/
        GoodClass goodClass = goodClassDAO.GetGoodClassByGoodClassId(goodClassId);

        ctx.put("goodClass",  goodClass);
        return "front_show_view";
    }

    /*更新修改GoodClass信息*/
    public String ModifyGoodClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            goodClassDAO.UpdateGoodClass(goodClass);
            ctx.put("message",  java.net.URLEncoder.encode("GoodClass信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodClass信息更新失败!"));
            return "error";
       }
   }

    /*删除GoodClass信息*/
    public String DeleteGoodClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            goodClassDAO.DeleteGoodClass(goodClassId);
            ctx.put("message",  java.net.URLEncoder.encode("GoodClass删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodClass删除失败!"));
            return "error";
        }
    }

}
