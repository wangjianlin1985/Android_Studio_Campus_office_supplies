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

    private int goodClassId;
    public void setGoodClassId(int goodClassId) {
        this.goodClassId = goodClassId;
    }
    public int getGoodClassId() {
        return goodClassId;
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
    @Resource GoodClassDAO goodClassDAO;

    /*��������GoodClass����*/
    private GoodClass goodClass;
    public void setGoodClass(GoodClass goodClass) {
        this.goodClass = goodClass;
    }
    public GoodClass getGoodClass() {
        return this.goodClass;
    }

    /*��ת�����GoodClass��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���GoodClass��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddGoodClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            goodClassDAO.AddGoodClass(goodClass);
            ctx.put("message",  java.net.URLEncoder.encode("GoodClass��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodClass���ʧ��!"));
            return "error";
        }
    }

    /*��ѯGoodClass��Ϣ*/
    public String QueryGoodClass() {
        if(currentPage == 0) currentPage = 1;
        List<GoodClass> goodClassList = goodClassDAO.QueryGoodClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        goodClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = goodClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = goodClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodClassList",  goodClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryGoodClassOutputToExcel() { 
        List<GoodClass> goodClassList = goodClassDAO.QueryGoodClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GoodClass��Ϣ��¼"; 
        String[] headers = { "��Ʒ���id","��Ʒ�������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"GoodClass.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯGoodClass��Ϣ*/
    public String FrontQueryGoodClass() {
        if(currentPage == 0) currentPage = 1;
        List<GoodClass> goodClassList = goodClassDAO.QueryGoodClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        goodClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = goodClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = goodClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodClassList",  goodClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�GoodClass��Ϣ*/
    public String ModifyGoodClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������goodClassId��ȡGoodClass����*/
        GoodClass goodClass = goodClassDAO.GetGoodClassByGoodClassId(goodClassId);

        ctx.put("goodClass",  goodClass);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�GoodClass��Ϣ*/
    public String FrontShowGoodClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������goodClassId��ȡGoodClass����*/
        GoodClass goodClass = goodClassDAO.GetGoodClassByGoodClassId(goodClassId);

        ctx.put("goodClass",  goodClass);
        return "front_show_view";
    }

    /*�����޸�GoodClass��Ϣ*/
    public String ModifyGoodClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            goodClassDAO.UpdateGoodClass(goodClass);
            ctx.put("message",  java.net.URLEncoder.encode("GoodClass��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodClass��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��GoodClass��Ϣ*/
    public String DeleteGoodClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            goodClassDAO.DeleteGoodClass(goodClassId);
            ctx.put("message",  java.net.URLEncoder.encode("GoodClassɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodClassɾ��ʧ��!"));
            return "error";
        }
    }

}
