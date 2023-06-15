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
import com.chengxusheji.dao.GoodsDAO;
import com.chengxusheji.domain.Goods;
import com.chengxusheji.dao.GoodClassDAO;
import com.chengxusheji.domain.GoodClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class GoodsAction extends BaseAction {

	/*图片或文件字段goodPhoto参数接收*/
	private File goodPhotoFile;
	private String goodPhotoFileFileName;
	private String goodPhotoFileContentType;
	public File getGoodPhotoFile() {
		return goodPhotoFile;
	}
	public void setGoodPhotoFile(File goodPhotoFile) {
		this.goodPhotoFile = goodPhotoFile;
	}
	public String getGoodPhotoFileFileName() {
		return goodPhotoFileFileName;
	}
	public void setGoodPhotoFileFileName(String goodPhotoFileFileName) {
		this.goodPhotoFileFileName = goodPhotoFileFileName;
	}
	public String getGoodPhotoFileContentType() {
		return goodPhotoFileContentType;
	}
	public void setGoodPhotoFileContentType(String goodPhotoFileContentType) {
		this.goodPhotoFileContentType = goodPhotoFileContentType;
	}
    /*界面层需要查询的属性: 物品编号*/
    private String goodNo;
    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo;
    }
    public String getGoodNo() {
        return this.goodNo;
    }

    /*界面层需要查询的属性: 商品类别*/
    private GoodClass goodClassObj;
    public void setGoodClassObj(GoodClass goodClassObj) {
        this.goodClassObj = goodClassObj;
    }
    public GoodClass getGoodClassObj() {
        return this.goodClassObj;
    }

    /*界面层需要查询的属性: 物品名称*/
    private String goodName;
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
    public String getGoodName() {
        return this.goodName;
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
    @Resource GoodClassDAO goodClassDAO;
    @Resource GoodsDAO goodsDAO;

    /*待操作的Goods对象*/
    private Goods goods;
    public void setGoods(Goods goods) {
        this.goods = goods;
    }
    public Goods getGoods() {
        return this.goods;
    }

    /*跳转到添加Goods视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的GoodClass信息*/
        List<GoodClass> goodClassList = goodClassDAO.QueryAllGoodClassInfo();
        ctx.put("goodClassList", goodClassList);
        return "add_view";
    }

    /*添加Goods信息*/
    @SuppressWarnings("deprecation")
    public String AddGoods() {
        ActionContext ctx = ActionContext.getContext();
        /*验证物品编号是否已经存在*/
        String goodNo = goods.getGoodNo();
        Goods db_goods = goodsDAO.GetGoodsByGoodNo(goodNo);
        if(null != db_goods) {
            ctx.put("error",  java.net.URLEncoder.encode("该物品编号已经存在!"));
            return "error";
        }
        try {
            GoodClass goodClassObj = goodClassDAO.GetGoodClassByGoodClassId(goods.getGoodClassObj().getGoodClassId());
            goods.setGoodClassObj(goodClassObj);
            /*处理物品图片上传*/
            String goodPhotoPath = "upload/noimage.jpg"; 
       	 	if(goodPhotoFile != null)
       	 		goodPhotoPath = photoUpload(goodPhotoFile,goodPhotoFileContentType);
       	 	goods.setGoodPhoto(goodPhotoPath);
            goodsDAO.AddGoods(goods);
            ctx.put("message",  java.net.URLEncoder.encode("Goods添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Goods添加失败!"));
            return "error";
        }
    }

    /*查询Goods信息*/
    public String QueryGoods() {
        if(currentPage == 0) currentPage = 1;
        if(goodNo == null) goodNo = "";
        if(goodName == null) goodName = "";
        List<Goods> goodsList = goodsDAO.QueryGoodsInfo(goodNo, goodClassObj, goodName, currentPage);
        /*计算总的页数和总的记录数*/
        goodsDAO.CalculateTotalPageAndRecordNumber(goodNo, goodClassObj, goodName);
        /*获取到总的页码数目*/
        totalPage = goodsDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = goodsDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodsList",  goodsList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("goodNo", goodNo);
        ctx.put("goodClassObj", goodClassObj);
        List<GoodClass> goodClassList = goodClassDAO.QueryAllGoodClassInfo();
        ctx.put("goodClassList", goodClassList);
        ctx.put("goodName", goodName);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryGoodsOutputToExcel() { 
        if(goodNo == null) goodNo = "";
        if(goodName == null) goodName = "";
        List<Goods> goodsList = goodsDAO.QueryGoodsInfo(goodNo,goodClassObj,goodName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Goods信息记录"; 
        String[] headers = { "物品编号","商品类别","物品名称","物品图片","规格型号","计量单位","库存数量","单价","金额","仓库"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<goodsList.size();i++) {
        	Goods goods = goodsList.get(i); 
        	dataset.add(new String[]{goods.getGoodNo(),goods.getGoodClassObj().getGoodClassName(),
goods.getGoodName(),goods.getGoodPhoto(),goods.getSpecModel(),goods.getMeasureUnit(),goods.getStockCount() + "",goods.getPrice() + "",goods.getTotalMoney() + "",goods.getStoreHouse()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Goods.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Goods信息*/
    public String FrontQueryGoods() {
        if(currentPage == 0) currentPage = 1;
        if(goodNo == null) goodNo = "";
        if(goodName == null) goodName = "";
        List<Goods> goodsList = goodsDAO.QueryGoodsInfo(goodNo, goodClassObj, goodName, currentPage);
        /*计算总的页数和总的记录数*/
        goodsDAO.CalculateTotalPageAndRecordNumber(goodNo, goodClassObj, goodName);
        /*获取到总的页码数目*/
        totalPage = goodsDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = goodsDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("goodsList",  goodsList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("goodNo", goodNo);
        ctx.put("goodClassObj", goodClassObj);
        List<GoodClass> goodClassList = goodClassDAO.QueryAllGoodClassInfo();
        ctx.put("goodClassList", goodClassList);
        ctx.put("goodName", goodName);
        return "front_query_view";
    }

    /*查询要修改的Goods信息*/
    public String ModifyGoodsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键goodNo获取Goods对象*/
        Goods goods = goodsDAO.GetGoodsByGoodNo(goodNo);

        List<GoodClass> goodClassList = goodClassDAO.QueryAllGoodClassInfo();
        ctx.put("goodClassList", goodClassList);
        ctx.put("goods",  goods);
        return "modify_view";
    }

    /*查询要修改的Goods信息*/
    public String FrontShowGoodsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键goodNo获取Goods对象*/
        Goods goods = goodsDAO.GetGoodsByGoodNo(goodNo);

        List<GoodClass> goodClassList = goodClassDAO.QueryAllGoodClassInfo();
        ctx.put("goodClassList", goodClassList);
        ctx.put("goods",  goods);
        return "front_show_view";
    }

    /*更新修改Goods信息*/
    public String ModifyGoods() {
        ActionContext ctx = ActionContext.getContext();
        try {
            GoodClass goodClassObj = goodClassDAO.GetGoodClassByGoodClassId(goods.getGoodClassObj().getGoodClassId());
            goods.setGoodClassObj(goodClassObj);
            /*处理物品图片上传*/
            if(goodPhotoFile != null) {
            	String goodPhotoPath = photoUpload(goodPhotoFile,goodPhotoFileContentType);
            	goods.setGoodPhoto(goodPhotoPath);
            }
            goodsDAO.UpdateGoods(goods);
            ctx.put("message",  java.net.URLEncoder.encode("Goods信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Goods信息更新失败!"));
            return "error";
       }
   }

    /*删除Goods信息*/
    public String DeleteGoods() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            goodsDAO.DeleteGoods(goodNo);
            ctx.put("message",  java.net.URLEncoder.encode("Goods删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Goods删除失败!"));
            return "error";
        }
    }

}
