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
import com.chengxusheji.dao.GoodUseDAO;
import com.chengxusheji.domain.GoodUse;
import com.chengxusheji.dao.GoodsDAO;
import com.chengxusheji.domain.Goods;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class GoodUseAction extends BaseAction {

    /*�������Ҫ��ѯ������: ������Ʒ*/
    private Goods goodObj;
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }
    public Goods getGoodObj() {
        return this.goodObj;
    }

    /*�������Ҫ��ѯ������: ����ʱ��*/
    private String useTime;
    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
    public String getUseTime() {
        return this.useTime;
    }

    /*�������Ҫ��ѯ������: ������*/
    private String operatorMan;
    public void setOperatorMan(String operatorMan) {
        this.operatorMan = operatorMan;
    }
    public String getOperatorMan() {
        return this.operatorMan;
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

    private int useId;
    public void setUseId(int useId) {
        this.useId = useId;
    }
    public int getUseId() {
        return useId;
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
    @Resource GoodUseDAO goodUseDAO;

    /*��������GoodUse����*/
    private GoodUse goodUse;
    public void setGoodUse(GoodUse goodUse) {
        this.goodUse = goodUse;
    }
    public GoodUse getGoodUse() {
        return this.goodUse;
    }

    /*��ת�����GoodUse��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Goods��Ϣ*/
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        return "add_view";
    }

    /*���GoodUse��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddGoodUse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(goodUse.getGoodObj().getGoodNo());
            goodUse.setGoodObj(goodObj);
            goodUseDAO.AddGoodUse(goodUse);
            ctx.put("message",  java.net.URLEncoder.encode("GoodUse��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodUse���ʧ��!"));
            return "error";
        }
    }

    /*��ѯGoodUse��Ϣ*/
    public String QueryGoodUse() {
        if(currentPage == 0) currentPage = 1;
        if(useTime == null) useTime = "";
        if(operatorMan == null) operatorMan = "";
        List<GoodUse> goodUseList = goodUseDAO.QueryGoodUseInfo(goodObj, useTime, operatorMan, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        goodUseDAO.CalculateTotalPageAndRecordNumber(goodObj, useTime, operatorMan);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = goodUseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = goodUseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodUseList",  goodUseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("goodObj", goodObj);
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("useTime", useTime);
        ctx.put("operatorMan", operatorMan);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryGoodUseOutputToExcel() { 
        if(useTime == null) useTime = "";
        if(operatorMan == null) operatorMan = "";
        List<GoodUse> goodUseList = goodUseDAO.QueryGoodUseInfo(goodObj,useTime,operatorMan);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GoodUse��Ϣ��¼"; 
        String[] headers = { "����id","������Ʒ","��������","����","���","����ʱ��","������","������","�ֿ�"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<goodUseList.size();i++) {
        	GoodUse goodUse = goodUseList.get(i); 
        	dataset.add(new String[]{goodUse.getUseId() + "",goodUse.getGoodObj().getGoodName(),
goodUse.getUseCount() + "",goodUse.getPrice() + "",goodUse.getTotalMoney() + "",new SimpleDateFormat("yyyy-MM-dd").format(goodUse.getUseTime()),goodUse.getUserMan(),goodUse.getOperatorMan(),goodUse.getStoreHouse()});
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
			response.setHeader("Content-disposition","attachment; filename="+"GoodUse.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯGoodUse��Ϣ*/
    public String FrontQueryGoodUse() {
        if(currentPage == 0) currentPage = 1;
        if(useTime == null) useTime = "";
        if(operatorMan == null) operatorMan = "";
        List<GoodUse> goodUseList = goodUseDAO.QueryGoodUseInfo(goodObj, useTime, operatorMan, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        goodUseDAO.CalculateTotalPageAndRecordNumber(goodObj, useTime, operatorMan);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = goodUseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = goodUseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodUseList",  goodUseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("goodObj", goodObj);
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("useTime", useTime);
        ctx.put("operatorMan", operatorMan);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�GoodUse��Ϣ*/
    public String ModifyGoodUseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������useId��ȡGoodUse����*/
        GoodUse goodUse = goodUseDAO.GetGoodUseByUseId(useId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("goodUse",  goodUse);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�GoodUse��Ϣ*/
    public String FrontShowGoodUseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������useId��ȡGoodUse����*/
        GoodUse goodUse = goodUseDAO.GetGoodUseByUseId(useId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("goodUse",  goodUse);
        return "front_show_view";
    }

    /*�����޸�GoodUse��Ϣ*/
    public String ModifyGoodUse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(goodUse.getGoodObj().getGoodNo());
            goodUse.setGoodObj(goodObj);
            goodUseDAO.UpdateGoodUse(goodUse);
            ctx.put("message",  java.net.URLEncoder.encode("GoodUse��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodUse��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��GoodUse��Ϣ*/
    public String DeleteGoodUse() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            goodUseDAO.DeleteGoodUse(useId);
            ctx.put("message",  java.net.URLEncoder.encode("GoodUseɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodUseɾ��ʧ��!"));
            return "error";
        }
    }

}
