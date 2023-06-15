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
import com.chengxusheji.dao.GoodApplyDAO;
import com.chengxusheji.domain.GoodApply;
import com.chengxusheji.dao.GoodsDAO;
import com.chengxusheji.domain.Goods;
import com.chengxusheji.dao.PersonDAO;
import com.chengxusheji.domain.Person;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class GoodApplyAction extends BaseAction {

    /*界面层需要查询的属性: 审请的用品*/
    private Goods goodObj;
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }
    public Goods getGoodObj() {
        return this.goodObj;
    }

    /*界面层需要查询的属性: 申请时间*/
    private String applyTime;
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
    public String getApplyTime() {
        return this.applyTime;
    }

    /*界面层需要查询的属性: 申请人*/
    private Person personObj;
    public void setPersonObj(Person personObj) {
        this.personObj = personObj;
    }
    public Person getPersonObj() {
        return this.personObj;
    }

    /*界面层需要查询的属性: 经办人*/
    private String handlePerson;
    public void setHandlePerson(String handlePerson) {
        this.handlePerson = handlePerson;
    }
    public String getHandlePerson() {
        return this.handlePerson;
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

    private int applyId;
    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }
    public int getApplyId() {
        return applyId;
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
    @Resource GoodsDAO goodsDAO;
    @Resource PersonDAO personDAO;
    @Resource GoodApplyDAO goodApplyDAO;

    /*待操作的GoodApply对象*/
    private GoodApply goodApply;
    public void setGoodApply(GoodApply goodApply) {
        this.goodApply = goodApply;
    }
    public GoodApply getGoodApply() {
        return this.goodApply;
    }

    /*跳转到添加GoodApply视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Goods信息*/
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        /*查询所有的Person信息*/
        List<Person> personList = personDAO.QueryAllPersonInfo();
        ctx.put("personList", personList);
        return "add_view";
    }

    /*添加GoodApply信息*/
    @SuppressWarnings("deprecation")
    public String AddGoodApply() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(goodApply.getGoodObj().getGoodNo());
            goodApply.setGoodObj(goodObj);
            Person personObj = personDAO.GetPersonByUser_name(goodApply.getPersonObj().getUser_name());
            goodApply.setPersonObj(personObj);
            goodApplyDAO.AddGoodApply(goodApply);
            ctx.put("message",  java.net.URLEncoder.encode("GoodApply添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodApply添加失败!"));
            return "error";
        }
    }

    /*查询GoodApply信息*/
    public String QueryGoodApply() {
        if(currentPage == 0) currentPage = 1;
        if(applyTime == null) applyTime = "";
        if(handlePerson == null) handlePerson = "";
        List<GoodApply> goodApplyList = goodApplyDAO.QueryGoodApplyInfo(goodObj, applyTime, personObj, handlePerson, currentPage);
        /*计算总的页数和总的记录数*/
        goodApplyDAO.CalculateTotalPageAndRecordNumber(goodObj, applyTime, personObj, handlePerson);
        /*获取到总的页码数目*/
        totalPage = goodApplyDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = goodApplyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodApplyList",  goodApplyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("goodObj", goodObj);
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("applyTime", applyTime);
        ctx.put("personObj", personObj);
        List<Person> personList = personDAO.QueryAllPersonInfo();
        ctx.put("personList", personList);
        ctx.put("handlePerson", handlePerson);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryGoodApplyOutputToExcel() { 
        if(applyTime == null) applyTime = "";
        if(handlePerson == null) handlePerson = "";
        List<GoodApply> goodApplyList = goodApplyDAO.QueryGoodApplyInfo(goodObj,applyTime,personObj,handlePerson);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GoodApply信息记录"; 
        String[] headers = { "申请id","审请的用品","申请数量","申请时间","申请人","经办人","备注"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<goodApplyList.size();i++) {
        	GoodApply goodApply = goodApplyList.get(i); 
        	dataset.add(new String[]{goodApply.getApplyId() + "",goodApply.getGoodObj().getGoodName(),
goodApply.getApplyCount() + "",goodApply.getApplyTime(),goodApply.getPersonObj().getName(),
goodApply.getHandlePerson(),goodApply.getApplyMemo()});
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
			response.setHeader("Content-disposition","attachment; filename="+"GoodApply.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询GoodApply信息*/
    public String FrontQueryGoodApply() {
        if(currentPage == 0) currentPage = 1;
        if(applyTime == null) applyTime = "";
        if(handlePerson == null) handlePerson = "";
        List<GoodApply> goodApplyList = goodApplyDAO.QueryGoodApplyInfo(goodObj, applyTime, personObj, handlePerson, currentPage);
        /*计算总的页数和总的记录数*/
        goodApplyDAO.CalculateTotalPageAndRecordNumber(goodObj, applyTime, personObj, handlePerson);
        /*获取到总的页码数目*/
        totalPage = goodApplyDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = goodApplyDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodApplyList",  goodApplyList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("goodObj", goodObj);
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("applyTime", applyTime);
        ctx.put("personObj", personObj);
        List<Person> personList = personDAO.QueryAllPersonInfo();
        ctx.put("personList", personList);
        ctx.put("handlePerson", handlePerson);
        return "front_query_view";
    }

    /*查询要修改的GoodApply信息*/
    public String ModifyGoodApplyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键applyId获取GoodApply对象*/
        GoodApply goodApply = goodApplyDAO.GetGoodApplyByApplyId(applyId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        List<Person> personList = personDAO.QueryAllPersonInfo();
        ctx.put("personList", personList);
        ctx.put("goodApply",  goodApply);
        return "modify_view";
    }

    /*查询要修改的GoodApply信息*/
    public String FrontShowGoodApplyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键applyId获取GoodApply对象*/
        GoodApply goodApply = goodApplyDAO.GetGoodApplyByApplyId(applyId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        List<Person> personList = personDAO.QueryAllPersonInfo();
        ctx.put("personList", personList);
        ctx.put("goodApply",  goodApply);
        return "front_show_view";
    }

    /*更新修改GoodApply信息*/
    public String ModifyGoodApply() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(goodApply.getGoodObj().getGoodNo());
            goodApply.setGoodObj(goodObj);
            Person personObj = personDAO.GetPersonByUser_name(goodApply.getPersonObj().getUser_name());
            goodApply.setPersonObj(personObj);
            goodApplyDAO.UpdateGoodApply(goodApply);
            ctx.put("message",  java.net.URLEncoder.encode("GoodApply信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodApply信息更新失败!"));
            return "error";
       }
   }

    /*删除GoodApply信息*/
    public String DeleteGoodApply() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            goodApplyDAO.DeleteGoodApply(applyId);
            ctx.put("message",  java.net.URLEncoder.encode("GoodApply删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodApply删除失败!"));
            return "error";
        }
    }

}
