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
import com.chengxusheji.domain.Person;
import com.chengxusheji.domain.GoodApply;

@Service @Transactional
public class GoodApplyDAO {

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
    public void AddGoodApply(GoodApply goodApply) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(goodApply);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GoodApply> QueryGoodApplyInfo(Goods goodObj,String applyTime,Person personObj,String handlePerson,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From GoodApply goodApply where 1=1";
    	if(null != goodObj && !goodObj.getGoodNo().equals("")) hql += " and goodApply.goodObj.goodNo='" + goodObj.getGoodNo() + "'";
    	if(!applyTime.equals("")) hql = hql + " and goodApply.applyTime like '%" + applyTime + "%'";
    	if(null != personObj && !personObj.getUser_name().equals("")) hql += " and goodApply.personObj.user_name='" + personObj.getUser_name() + "'";
    	if(!handlePerson.equals("")) hql = hql + " and goodApply.handlePerson like '%" + handlePerson + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List goodApplyList = q.list();
    	return (ArrayList<GoodApply>) goodApplyList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GoodApply> QueryGoodApplyInfo(Goods goodObj,String applyTime,Person personObj,String handlePerson) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From GoodApply goodApply where 1=1";
    	if(null != goodObj && !goodObj.getGoodNo().equals("")) hql += " and goodApply.goodObj.goodNo='" + goodObj.getGoodNo() + "'";
    	if(!applyTime.equals("")) hql = hql + " and goodApply.applyTime like '%" + applyTime + "%'";
    	if(null != personObj && !personObj.getUser_name().equals("")) hql += " and goodApply.personObj.user_name='" + personObj.getUser_name() + "'";
    	if(!handlePerson.equals("")) hql = hql + " and goodApply.handlePerson like '%" + handlePerson + "%'";
    	Query q = s.createQuery(hql);
    	List goodApplyList = q.list();
    	return (ArrayList<GoodApply>) goodApplyList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GoodApply> QueryAllGoodApplyInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From GoodApply";
        Query q = s.createQuery(hql);
        List goodApplyList = q.list();
        return (ArrayList<GoodApply>) goodApplyList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Goods goodObj,String applyTime,Person personObj,String handlePerson) {
        Session s = factory.getCurrentSession();
        String hql = "From GoodApply goodApply where 1=1";
        if(null != goodObj && !goodObj.getGoodNo().equals("")) hql += " and goodApply.goodObj.goodNo='" + goodObj.getGoodNo() + "'";
        if(!applyTime.equals("")) hql = hql + " and goodApply.applyTime like '%" + applyTime + "%'";
        if(null != personObj && !personObj.getUser_name().equals("")) hql += " and goodApply.personObj.user_name='" + personObj.getUser_name() + "'";
        if(!handlePerson.equals("")) hql = hql + " and goodApply.handlePerson like '%" + handlePerson + "%'";
        Query q = s.createQuery(hql);
        List goodApplyList = q.list();
        recordNumber = goodApplyList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public GoodApply GetGoodApplyByApplyId(int applyId) {
        Session s = factory.getCurrentSession();
        GoodApply goodApply = (GoodApply)s.get(GoodApply.class, applyId);
        return goodApply;
    }

    /*更新GoodApply信息*/
    public void UpdateGoodApply(GoodApply goodApply) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(goodApply);
    }

    /*删除GoodApply信息*/
    public void DeleteGoodApply (int applyId) throws Exception {
        Session s = factory.getCurrentSession();
        Object goodApply = s.load(GoodApply.class, applyId);
        s.delete(goodApply);
    }

}
