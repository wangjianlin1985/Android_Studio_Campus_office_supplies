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

    /*�������Ҫ��ѯ������: �������Ʒ*/
    private Goods goodObj;
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }
    public Goods getGoodObj() {
        return this.goodObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String applyTime;
    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
    public String getApplyTime() {
        return this.applyTime;
    }

    /*�������Ҫ��ѯ������: ������*/
    private Person personObj;
    public void setPersonObj(Person personObj) {
        this.personObj = personObj;
    }
    public Person getPersonObj() {
        return this.personObj;
    }

    /*�������Ҫ��ѯ������: ������*/
    private String handlePerson;
    public void setHandlePerson(String handlePerson) {
        this.handlePerson = handlePerson;
    }
    public String getHandlePerson() {
        return this.handlePerson;
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

    private int applyId;
    public void setApplyId(int applyId) {
        this.applyId = applyId;
    }
    public int getApplyId() {
        return applyId;
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
    @Resource GoodsDAO goodsDAO;
    @Resource PersonDAO personDAO;
    @Resource GoodApplyDAO goodApplyDAO;

    /*��������GoodApply����*/
    private GoodApply goodApply;
    public void setGoodApply(GoodApply goodApply) {
        this.goodApply = goodApply;
    }
    public GoodApply getGoodApply() {
        return this.goodApply;
    }

    /*��ת�����GoodApply��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Goods��Ϣ*/
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        /*��ѯ���е�Person��Ϣ*/
        List<Person> personList = personDAO.QueryAllPersonInfo();
        ctx.put("personList", personList);
        return "add_view";
    }

    /*���GoodApply��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddGoodApply() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(goodApply.getGoodObj().getGoodNo());
            goodApply.setGoodObj(goodObj);
            Person personObj = personDAO.GetPersonByUser_name(goodApply.getPersonObj().getUser_name());
            goodApply.setPersonObj(personObj);
            goodApplyDAO.AddGoodApply(goodApply);
            ctx.put("message",  java.net.URLEncoder.encode("GoodApply��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodApply���ʧ��!"));
            return "error";
        }
    }

    /*��ѯGoodApply��Ϣ*/
    public String QueryGoodApply() {
        if(currentPage == 0) currentPage = 1;
        if(applyTime == null) applyTime = "";
        if(handlePerson == null) handlePerson = "";
        List<GoodApply> goodApplyList = goodApplyDAO.QueryGoodApplyInfo(goodObj, applyTime, personObj, handlePerson, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        goodApplyDAO.CalculateTotalPageAndRecordNumber(goodObj, applyTime, personObj, handlePerson);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = goodApplyDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryGoodApplyOutputToExcel() { 
        if(applyTime == null) applyTime = "";
        if(handlePerson == null) handlePerson = "";
        List<GoodApply> goodApplyList = goodApplyDAO.QueryGoodApplyInfo(goodObj,applyTime,personObj,handlePerson);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GoodApply��Ϣ��¼"; 
        String[] headers = { "����id","�������Ʒ","��������","����ʱ��","������","������","��ע"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"GoodApply.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯGoodApply��Ϣ*/
    public String FrontQueryGoodApply() {
        if(currentPage == 0) currentPage = 1;
        if(applyTime == null) applyTime = "";
        if(handlePerson == null) handlePerson = "";
        List<GoodApply> goodApplyList = goodApplyDAO.QueryGoodApplyInfo(goodObj, applyTime, personObj, handlePerson, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        goodApplyDAO.CalculateTotalPageAndRecordNumber(goodObj, applyTime, personObj, handlePerson);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = goodApplyDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�GoodApply��Ϣ*/
    public String ModifyGoodApplyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������applyId��ȡGoodApply����*/
        GoodApply goodApply = goodApplyDAO.GetGoodApplyByApplyId(applyId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        List<Person> personList = personDAO.QueryAllPersonInfo();
        ctx.put("personList", personList);
        ctx.put("goodApply",  goodApply);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�GoodApply��Ϣ*/
    public String FrontShowGoodApplyQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������applyId��ȡGoodApply����*/
        GoodApply goodApply = goodApplyDAO.GetGoodApplyByApplyId(applyId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        List<Person> personList = personDAO.QueryAllPersonInfo();
        ctx.put("personList", personList);
        ctx.put("goodApply",  goodApply);
        return "front_show_view";
    }

    /*�����޸�GoodApply��Ϣ*/
    public String ModifyGoodApply() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(goodApply.getGoodObj().getGoodNo());
            goodApply.setGoodObj(goodObj);
            Person personObj = personDAO.GetPersonByUser_name(goodApply.getPersonObj().getUser_name());
            goodApply.setPersonObj(personObj);
            goodApplyDAO.UpdateGoodApply(goodApply);
            ctx.put("message",  java.net.URLEncoder.encode("GoodApply��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodApply��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��GoodApply��Ϣ*/
    public String DeleteGoodApply() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            goodApplyDAO.DeleteGoodApply(applyId);
            ctx.put("message",  java.net.URLEncoder.encode("GoodApplyɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodApplyɾ��ʧ��!"));
            return "error";
        }
    }

}
