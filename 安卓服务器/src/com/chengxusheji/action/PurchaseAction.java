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

    /*界面层需要查询的属性: 购置物品*/
    private Goods goodObj;
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }
    public Goods getGoodObj() {
        return this.goodObj;
    }

    /*界面层需要查询的属性: 经办人*/
    private String operatorMan;
    public void setOperatorMan(String operatorMan) {
        this.operatorMan = operatorMan;
    }
    public String getOperatorMan() {
        return this.operatorMan;
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

    private int purchaseId;
    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
    public int getPurchaseId() {
        return purchaseId;
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
    @Resource PurchaseDAO purchaseDAO;

    /*待操作的Purchase对象*/
    private Purchase purchase;
    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
    public Purchase getPurchase() {
        return this.purchase;
    }

    /*跳转到添加Purchase视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Goods信息*/
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        return "add_view";
    }

    /*添加Purchase信息*/
    @SuppressWarnings("deprecation")
    public String AddPurchase() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(purchase.getGoodObj().getGoodNo());
            purchase.setGoodObj(goodObj);
            purchaseDAO.AddPurchase(purchase);
            ctx.put("message",  java.net.URLEncoder.encode("Purchase添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Purchase添加失败!"));
            return "error";
        }
    }

    /*查询Purchase信息*/
    public String QueryPurchase() {
        if(currentPage == 0) currentPage = 1;
        if(operatorMan == null) operatorMan = "";
        List<Purchase> purchaseList = purchaseDAO.QueryPurchaseInfo(goodObj, operatorMan, currentPage);
        /*计算总的页数和总的记录数*/
        purchaseDAO.CalculateTotalPageAndRecordNumber(goodObj, operatorMan);
        /*获取到总的页码数目*/
        totalPage = purchaseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryPurchaseOutputToExcel() { 
        if(operatorMan == null) operatorMan = "";
        List<Purchase> purchaseList = purchaseDAO.QueryPurchaseInfo(goodObj,operatorMan);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Purchase信息记录"; 
        String[] headers = { "购置id","购置物品","购置价格","购置数量","购置金额","入库时间","经办人","保管人","仓库"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Purchase.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Purchase信息*/
    public String FrontQueryPurchase() {
        if(currentPage == 0) currentPage = 1;
        if(operatorMan == null) operatorMan = "";
        List<Purchase> purchaseList = purchaseDAO.QueryPurchaseInfo(goodObj, operatorMan, currentPage);
        /*计算总的页数和总的记录数*/
        purchaseDAO.CalculateTotalPageAndRecordNumber(goodObj, operatorMan);
        /*获取到总的页码数目*/
        totalPage = purchaseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Purchase信息*/
    public String ModifyPurchaseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键purchaseId获取Purchase对象*/
        Purchase purchase = purchaseDAO.GetPurchaseByPurchaseId(purchaseId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("purchase",  purchase);
        return "modify_view";
    }

    /*查询要修改的Purchase信息*/
    public String FrontShowPurchaseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键purchaseId获取Purchase对象*/
        Purchase purchase = purchaseDAO.GetPurchaseByPurchaseId(purchaseId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("purchase",  purchase);
        return "front_show_view";
    }

    /*更新修改Purchase信息*/
    public String ModifyPurchase() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(purchase.getGoodObj().getGoodNo());
            purchase.setGoodObj(goodObj);
            purchaseDAO.UpdatePurchase(purchase);
            ctx.put("message",  java.net.URLEncoder.encode("Purchase信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Purchase信息更新失败!"));
            return "error";
       }
   }

    /*删除Purchase信息*/
    public String DeletePurchase() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            purchaseDAO.DeletePurchase(purchaseId);
            ctx.put("message",  java.net.URLEncoder.encode("Purchase删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Purchase删除失败!"));
            return "error";
        }
    }

}
