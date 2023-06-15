package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.GoodClass;
import com.chengxusheji.domain.Goods;

@Service @Transactional
public class GoodsDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddGoods(Goods goods) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(goods);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Goods> QueryGoodsInfo(String goodNo,GoodClass goodClassObj,String goodName,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Goods goods where 1=1";
    	if(!goodNo.equals("")) hql = hql + " and goods.goodNo like '%" + goodNo + "%'";
    	if(null != goodClassObj && goodClassObj.getGoodClassId()!=0) hql += " and goods.goodClassObj.goodClassId=" + goodClassObj.getGoodClassId();
    	if(!goodName.equals("")) hql = hql + " and goods.goodName like '%" + goodName + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List goodsList = q.list();
    	return (ArrayList<Goods>) goodsList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Goods> QueryGoodsInfo(String goodNo,GoodClass goodClassObj,String goodName) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Goods goods where 1=1";
    	if(!goodNo.equals("")) hql = hql + " and goods.goodNo like '%" + goodNo + "%'";
    	if(null != goodClassObj && goodClassObj.getGoodClassId()!=0) hql += " and goods.goodClassObj.goodClassId=" + goodClassObj.getGoodClassId();
    	if(!goodName.equals("")) hql = hql + " and goods.goodName like '%" + goodName + "%'";
    	Query q = s.createQuery(hql);
    	List goodsList = q.list();
    	return (ArrayList<Goods>) goodsList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Goods> QueryAllGoodsInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Goods";
        Query q = s.createQuery(hql);
        List goodsList = q.list();
        return (ArrayList<Goods>) goodsList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String goodNo,GoodClass goodClassObj,String goodName) {
        Session s = factory.getCurrentSession();
        String hql = "From Goods goods where 1=1";
        if(!goodNo.equals("")) hql = hql + " and goods.goodNo like '%" + goodNo + "%'";
        if(null != goodClassObj && goodClassObj.getGoodClassId()!=0) hql += " and goods.goodClassObj.goodClassId=" + goodClassObj.getGoodClassId();
        if(!goodName.equals("")) hql = hql + " and goods.goodName like '%" + goodName + "%'";
        Query q = s.createQuery(hql);
        List goodsList = q.list();
        recordNumber = goodsList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Goods GetGoodsByGoodNo(String goodNo) {
        Session s = factory.getCurrentSession();
        Goods goods = (Goods)s.get(Goods.class, goodNo);
        return goods;
    }

    /*更新Goods信息*/
    public void UpdateGoods(Goods goods) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(goods);
    }

    /*删除Goods信息*/
    public void DeleteGoods (String goodNo) throws Exception {
        Session s = factory.getCurrentSession();
        Object goods = s.load(Goods.class, goodNo);
        s.delete(goods);
    }

}
