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
import com.chengxusheji.domain.Goods;
import com.chengxusheji.domain.Purchase;

@Service @Transactional
public class PurchaseDAO {

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
    public void AddPurchase(Purchase purchase) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(purchase);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Purchase> QueryPurchaseInfo(Goods goodObj,String operatorMan,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Purchase purchase where 1=1";
    	if(null != goodObj && !goodObj.getGoodNo().equals("")) hql += " and purchase.goodObj.goodNo='" + goodObj.getGoodNo() + "'";
    	if(!operatorMan.equals("")) hql = hql + " and purchase.operatorMan like '%" + operatorMan + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List purchaseList = q.list();
    	return (ArrayList<Purchase>) purchaseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Purchase> QueryPurchaseInfo(Goods goodObj,String operatorMan) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Purchase purchase where 1=1";
    	if(null != goodObj && !goodObj.getGoodNo().equals("")) hql += " and purchase.goodObj.goodNo='" + goodObj.getGoodNo() + "'";
    	if(!operatorMan.equals("")) hql = hql + " and purchase.operatorMan like '%" + operatorMan + "%'";
    	Query q = s.createQuery(hql);
    	List purchaseList = q.list();
    	return (ArrayList<Purchase>) purchaseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Purchase> QueryAllPurchaseInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Purchase";
        Query q = s.createQuery(hql);
        List purchaseList = q.list();
        return (ArrayList<Purchase>) purchaseList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Goods goodObj,String operatorMan) {
        Session s = factory.getCurrentSession();
        String hql = "From Purchase purchase where 1=1";
        if(null != goodObj && !goodObj.getGoodNo().equals("")) hql += " and purchase.goodObj.goodNo='" + goodObj.getGoodNo() + "'";
        if(!operatorMan.equals("")) hql = hql + " and purchase.operatorMan like '%" + operatorMan + "%'";
        Query q = s.createQuery(hql);
        List purchaseList = q.list();
        recordNumber = purchaseList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Purchase GetPurchaseByPurchaseId(int purchaseId) {
        Session s = factory.getCurrentSession();
        Purchase purchase = (Purchase)s.get(Purchase.class, purchaseId);
        return purchase;
    }

    /*更新Purchase信息*/
    public void UpdatePurchase(Purchase purchase) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(purchase);
    }

    /*删除Purchase信息*/
    public void DeletePurchase (int purchaseId) throws Exception {
        Session s = factory.getCurrentSession();
        Object purchase = s.load(Purchase.class, purchaseId);
        s.delete(purchase);
    }

}
