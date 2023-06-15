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
import com.chengxusheji.dao.PurchaseDAO;
import com.chengxusheji.domain.Purchase;
import com.chengxusheji.dao.GoodsDAO;
import com.chengxusheji.domain.Goods;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class PurchaseAction extends BaseAction {

    /*�������Ҫ��ѯ������: ������Ʒ*/
    private Goods goodObj;
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }
    public Goods getGoodObj() {
        return this.goodObj;
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

    private int purchaseId;
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
    public int getPurchaseId() {
        return purchaseId;
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
    @Resource PurchaseDAO purchaseDAO;

    /*��������Purchase����*/
    private Purchase purchase;
    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
    public Purchase getPurchase() {
        return this.purchase;
    }

    /*��ת�����Purchase��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Goods��Ϣ*/
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        return "add_view";
    }

    /*���Purchase��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddPurchase() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(purchase.getGoodObj().getGoodNo());
            purchase.setGoodObj(goodObj);
            purchaseDAO.AddPurchase(purchase);
            ctx.put("message",  java.net.URLEncoder.encode("Purchase��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Purchase���ʧ��!"));
            return "error";
        }
    }

    /*��ѯPurchase��Ϣ*/
    public String QueryPurchase() {
        if(currentPage == 0) currentPage = 1;
        if(operatorMan == null) operatorMan = "";
        List<Purchase> purchaseList = purchaseDAO.QueryPurchaseInfo(goodObj, operatorMan, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        purchaseDAO.CalculateTotalPageAndRecordNumber(goodObj, operatorMan);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = purchaseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = purchaseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("purchaseList",  purchaseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("goodObj", goodObj);
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("operatorMan", operatorMan);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryPurchaseOutputToExcel() { 
        if(operatorMan == null) operatorMan = "";
        List<Purchase> purchaseList = purchaseDAO.QueryPurchaseInfo(goodObj,operatorMan);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Purchase��Ϣ��¼"; 
        String[] headers = { "����id","������Ʒ","���ü۸�","��������","���ý��","���ʱ��","������","������","�ֿ�"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<purchaseList.size();i++) {
        	Purchase purchase = purchaseList.get(i); 
        	dataset.add(new String[]{purchase.getPurchaseId() + "",purchase.getGoodObj().getGoodName(),
purchase.getPrice() + "",purchase.getBuyCount() + "",purchase.getTotalMoney() + "",new SimpleDateFormat("yyyy-MM-dd").format(purchase.getInDate()),purchase.getOperatorMan(),purchase.getKeepMan(),purchase.getStoreHouse()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Purchase.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯPurchase��Ϣ*/
    public String FrontQueryPurchase() {
        if(currentPage == 0) currentPage = 1;
        if(operatorMan == null) operatorMan = "";
        List<Purchase> purchaseList = purchaseDAO.QueryPurchaseInfo(goodObj, operatorMan, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        purchaseDAO.CalculateTotalPageAndRecordNumber(goodObj, operatorMan);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = purchaseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = purchaseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("purchaseList",  purchaseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("goodObj", goodObj);
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("operatorMan", operatorMan);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Purchase��Ϣ*/
    public String ModifyPurchaseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������purchaseId��ȡPurchase����*/
        Purchase purchase = purchaseDAO.GetPurchaseByPurchaseId(purchaseId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("purchase",  purchase);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Purchase��Ϣ*/
    public String FrontShowPurchaseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������purchaseId��ȡPurchase����*/
        Purchase purchase = purchaseDAO.GetPurchaseByPurchaseId(purchaseId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("purchase",  purchase);
        return "front_show_view";
    }

    /*�����޸�Purchase��Ϣ*/
    public String ModifyPurchase() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(purchase.getGoodObj().getGoodNo());
            purchase.setGoodObj(goodObj);
            purchaseDAO.UpdatePurchase(purchase);
            ctx.put("message",  java.net.URLEncoder.encode("Purchase��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Purchase��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Purchase��Ϣ*/
    public String DeletePurchase() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            purchaseDAO.DeletePurchase(purchaseId);
            ctx.put("message",  java.net.URLEncoder.encode("Purchaseɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Purchaseɾ��ʧ��!"));
            return "error";
        }
    }

}
