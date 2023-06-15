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
import com.chengxusheji.domain.GoodUse;

@Service @Transactional
public class GoodUseDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddGoodUse(GoodUse goodUse) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(goodUse);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GoodUse> QueryGoodUseInfo(Goods goodObj,String useTime,String operatorMan,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From GoodUse goodUse where 1=1";
    	if(null != goodObj && !goodObj.getGoodNo().equals("")) hql += " and goodUse.goodObj.goodNo='" + goodObj.getGoodNo() + "'";
    	if(!useTime.equals("")) hql = hql + " and goodUse.useTime like '%" + useTime + "%'";
    	if(!operatorMan.equals("")) hql = hql + " and goodUse.operatorMan like '%" + operatorMan + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List goodUseList = q.list();
    	return (ArrayList<GoodUse>) goodUseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GoodUse> QueryGoodUseInfo(Goods goodObj,String useTime,String operatorMan) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From GoodUse goodUse where 1=1";
    	if(null != goodObj && !goodObj.getGoodNo().equals("")) hql += " and goodUse.goodObj.goodNo='" + goodObj.getGoodNo() + "'";
    	if(!useTime.equals("")) hql = hql + " and goodUse.useTime like '%" + useTime + "%'";
    	if(!operatorMan.equals("")) hql = hql + " and goodUse.operatorMan like '%" + operatorMan + "%'";
    	Query q = s.createQuery(hql);
    	List goodUseList = q.list();
    	return (ArrayList<GoodUse>) goodUseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GoodUse> QueryAllGoodUseInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From GoodUse";
        Query q = s.createQuery(hql);
        List goodUseList = q.list();
        return (ArrayList<GoodUse>) goodUseList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Goods goodObj,String useTime,String operatorMan) {
        Session s = factory.getCurrentSession();
        String hql = "From GoodUse goodUse where 1=1";
        if(null != goodObj && !goodObj.getGoodNo().equals("")) hql += " and goodUse.goodObj.goodNo='" + goodObj.getGoodNo() + "'";
        if(!useTime.equals("")) hql = hql + " and goodUse.useTime like '%" + useTime + "%'";
        if(!operatorMan.equals("")) hql = hql + " and goodUse.operatorMan like '%" + operatorMan + "%'";
        Query q = s.createQuery(hql);
        List goodUseList = q.list();
        recordNumber = goodUseList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public GoodUse GetGoodUseByUseId(int useId) {
        Session s = factory.getCurrentSession();
        GoodUse goodUse = (GoodUse)s.get(GoodUse.class, useId);
        return goodUse;
    }

    /*����GoodUse��Ϣ*/
    public void UpdateGoodUse(GoodUse goodUse) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(goodUse);
    }

    /*ɾ��GoodUse��Ϣ*/
    public void DeleteGoodUse (int useId) throws Exception {
        Session s = factory.getCurrentSession();
        Object goodUse = s.load(GoodUse.class, useId);
        s.delete(goodUse);
    }

}
