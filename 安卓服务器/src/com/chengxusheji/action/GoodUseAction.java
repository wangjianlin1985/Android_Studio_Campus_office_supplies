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

    /*界面层需要查询的属性: 领用物品*/
    private Goods goodObj;
    public void setGoodObj(Goods goodObj) {
        this.goodObj = goodObj;
    }
    public Goods getGoodObj() {
        return this.goodObj;
    }

    /*界面层需要查询的属性: 领用时间*/
    private String useTime;
    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }
    public String getUseTime() {
        return this.useTime;
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

    private int useId;
    public void setUseId(int useId) {
        this.useId = useId;
    }
    public int getUseId() {
        return useId;
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
    @Resource GoodUseDAO goodUseDAO;

    /*待操作的GoodUse对象*/
    private GoodUse goodUse;
    public void setGoodUse(GoodUse goodUse) {
        this.goodUse = goodUse;
    }
    public GoodUse getGoodUse() {
        return this.goodUse;
    }

    /*跳转到添加GoodUse视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Goods信息*/
        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        return "add_view";
    }

    /*添加GoodUse信息*/
    @SuppressWarnings("deprecation")
    public String AddGoodUse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(goodUse.getGoodObj().getGoodNo());
            goodUse.setGoodObj(goodObj);
            goodUseDAO.AddGoodUse(goodUse);
            ctx.put("message",  java.net.URLEncoder.encode("GoodUse添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodUse添加失败!"));
            return "error";
        }
    }

    /*查询GoodUse信息*/
    public String QueryGoodUse() {
        if(currentPage == 0) currentPage = 1;
        if(useTime == null) useTime = "";
        if(operatorMan == null) operatorMan = "";
        List<GoodUse> goodUseList = goodUseDAO.QueryGoodUseInfo(goodObj, useTime, operatorMan, currentPage);
        /*计算总的页数和总的记录数*/
        goodUseDAO.CalculateTotalPageAndRecordNumber(goodObj, useTime, operatorMan);
        /*获取到总的页码数目*/
        totalPage = goodUseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryGoodUseOutputToExcel() { 
        if(useTime == null) useTime = "";
        if(operatorMan == null) operatorMan = "";
        List<GoodUse> goodUseList = goodUseDAO.QueryGoodUseInfo(goodObj,useTime,operatorMan);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "GoodUse信息记录"; 
        String[] headers = { "领用id","领用物品","领用数量","单价","金额","领用时间","领用人","经办人","仓库"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"GoodUse.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询GoodUse信息*/
    public String FrontQueryGoodUse() {
        if(currentPage == 0) currentPage = 1;
        if(useTime == null) useTime = "";
        if(operatorMan == null) operatorMan = "";
        List<GoodUse> goodUseList = goodUseDAO.QueryGoodUseInfo(goodObj, useTime, operatorMan, currentPage);
        /*计算总的页数和总的记录数*/
        goodUseDAO.CalculateTotalPageAndRecordNumber(goodObj, useTime, operatorMan);
        /*获取到总的页码数目*/
        totalPage = goodUseDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的GoodUse信息*/
    public String ModifyGoodUseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键useId获取GoodUse对象*/
        GoodUse goodUse = goodUseDAO.GetGoodUseByUseId(useId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("goodUse",  goodUse);
        return "modify_view";
    }

    /*查询要修改的GoodUse信息*/
    public String FrontShowGoodUseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键useId获取GoodUse对象*/
        GoodUse goodUse = goodUseDAO.GetGoodUseByUseId(useId);

        List<Goods> goodsList = goodsDAO.QueryAllGoodsInfo();
        ctx.put("goodsList", goodsList);
        ctx.put("goodUse",  goodUse);
        return "front_show_view";
    }

    /*更新修改GoodUse信息*/
    public String ModifyGoodUse() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Goods goodObj = goodsDAO.GetGoodsByGoodNo(goodUse.getGoodObj().getGoodNo());
            goodUse.setGoodObj(goodObj);
            goodUseDAO.UpdateGoodUse(goodUse);
            ctx.put("message",  java.net.URLEncoder.encode("GoodUse信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodUse信息更新失败!"));
            return "error";
       }
   }

    /*删除GoodUse信息*/
    public String DeleteGoodUse() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            goodUseDAO.DeleteGoodUse(useId);
            ctx.put("message",  java.net.URLEncoder.encode("GoodUse删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("GoodUse删除失败!"));
            return "error";
        }
    }

}
