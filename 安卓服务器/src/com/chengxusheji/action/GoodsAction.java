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

	/*ͼƬ���ļ��ֶ�goodPhoto��������*/
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
    /*�������Ҫ��ѯ������: ��Ʒ���*/
    private String goodNo;
    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo;
    }
    public String getGoodNo() {
        return this.goodNo;
    }

    /*�������Ҫ��ѯ������: ��Ʒ���*/
    private GoodClass goodClassObj;
    public void setGoodClassObj(GoodClass goodClassObj) {
        this.goodClassObj = goodClassObj;
    }
    public GoodClass getGoodClassObj() {
        return this.goodClassObj;
    }

    /*�������Ҫ��ѯ������: ��Ʒ����*/
    private String goodName;
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
    public String getGoodName() {
        return this.goodName;
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
    @Resource GoodClassDAO goodClassDAO;
    @Resource GoodsDAO goodsDAO;

    /*��������Goods����*/
    private Goods goods;
    public void setGoods(Goods goods) {
        this.goods = goods;
    }
    public Goods getGoods() {
        return this.goods;
    }

    /*��ת�����Goods��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�GoodClass��Ϣ*/
        List<GoodClass> goodClassList = goodClassDAO.QueryAllGoodClassInfo();
        ctx.put("goodClassList", goodClassList);
        return "add_view";
    }

    /*���Goods��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddGoods() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤��Ʒ����Ƿ��Ѿ�����*/
        String goodNo = goods.getGoodNo();
        Goods db_goods = goodsDAO.GetGoodsByGoodNo(goodNo);
        if(null != db_goods) {
            ctx.put("error",  java.net.URLEncoder.encode("����Ʒ����Ѿ�����!"));
            return "error";
        }
        try {
            GoodClass goodClassObj = goodClassDAO.GetGoodClassByGoodClassId(goods.getGoodClassObj().getGoodClassId());
            goods.setGoodClassObj(goodClassObj);
            /*������ƷͼƬ�ϴ�*/
            String goodPhotoPath = "upload/noimage.jpg"; 
       	 	if(goodPhotoFile != null)
       	 		goodPhotoPath = photoUpload(goodPhotoFile,goodPhotoFileContentType);
       	 	goods.setGoodPhoto(goodPhotoPath);
            goodsDAO.AddGoods(goods);
            ctx.put("message",  java.net.URLEncoder.encode("Goods��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Goods���ʧ��!"));
            return "error";
        }
    }

    /*��ѯGoods��Ϣ*/
    public String QueryGoods() {
        if(currentPage == 0) currentPage = 1;
        if(goodNo == null) goodNo = "";
        if(goodName == null) goodName = "";
        List<Goods> goodsList = goodsDAO.QueryGoodsInfo(goodNo, goodClassObj, goodName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        goodsDAO.CalculateTotalPageAndRecordNumber(goodNo, goodClassObj, goodName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = goodsDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryGoodsOutputToExcel() { 
        if(goodNo == null) goodNo = "";
        if(goodName == null) goodName = "";
        List<Goods> goodsList = goodsDAO.QueryGoodsInfo(goodNo,goodClassObj,goodName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Goods��Ϣ��¼"; 
        String[] headers = { "��Ʒ���","��Ʒ���","��Ʒ����","��ƷͼƬ","����ͺ�","������λ","�������","����","���","�ֿ�"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Goods.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯGoods��Ϣ*/
    public String FrontQueryGoods() {
        if(currentPage == 0) currentPage = 1;
        if(goodNo == null) goodNo = "";
        if(goodName == null) goodName = "";
        List<Goods> goodsList = goodsDAO.QueryGoodsInfo(goodNo, goodClassObj, goodName, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        goodsDAO.CalculateTotalPageAndRecordNumber(goodNo, goodClassObj, goodName);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = goodsDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Goods��Ϣ*/
    public String ModifyGoodsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������goodNo��ȡGoods����*/
        Goods goods = goodsDAO.GetGoodsByGoodNo(goodNo);

        List<GoodClass> goodClassList = goodClassDAO.QueryAllGoodClassInfo();
        ctx.put("goodClassList", goodClassList);
        ctx.put("goods",  goods);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Goods��Ϣ*/
    public String FrontShowGoodsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������goodNo��ȡGoods����*/
        Goods goods = goodsDAO.GetGoodsByGoodNo(goodNo);

        List<GoodClass> goodClassList = goodClassDAO.QueryAllGoodClassInfo();
        ctx.put("goodClassList", goodClassList);
        ctx.put("goods",  goods);
        return "front_show_view";
    }

    /*�����޸�Goods��Ϣ*/
    public String ModifyGoods() {
        ActionContext ctx = ActionContext.getContext();
        try {
            GoodClass goodClassObj = goodClassDAO.GetGoodClassByGoodClassId(goods.getGoodClassObj().getGoodClassId());
            goods.setGoodClassObj(goodClassObj);
            /*������ƷͼƬ�ϴ�*/
            if(goodPhotoFile != null) {
            	String goodPhotoPath = photoUpload(goodPhotoFile,goodPhotoFileContentType);
            	goods.setGoodPhoto(goodPhotoPath);
            }
            goodsDAO.UpdateGoods(goods);
            ctx.put("message",  java.net.URLEncoder.encode("Goods��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Goods��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Goods��Ϣ*/
    public String DeleteGoods() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            goodsDAO.DeleteGoods(goodNo);
            ctx.put("message",  java.net.URLEncoder.encode("Goodsɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Goodsɾ��ʧ��!"));
            return "error";
        }
    }

}
