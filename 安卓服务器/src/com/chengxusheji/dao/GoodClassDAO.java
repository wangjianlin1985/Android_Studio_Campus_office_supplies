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

@Service @Transactional
public class GoodClassDAO {

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
    public void AddGoodClass(GoodClass goodClass) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(goodClass);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GoodClass> QueryGoodClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From GoodClass goodClass where 1=1";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List goodClassList = q.list();
    	return (ArrayList<GoodClass>) goodClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GoodClass> QueryGoodClassInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From GoodClass goodClass where 1=1";
    	Query q = s.createQuery(hql);
    	List goodClassList = q.list();
    	return (ArrayList<GoodClass>) goodClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<GoodClass> QueryAllGoodClassInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From GoodClass";
        Query q = s.createQuery(hql);
        List goodClassList = q.list();
        return (ArrayList<GoodClass>) goodClassList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From GoodClass goodClass where 1=1";
        Query q = s.createQuery(hql);
        List goodClassList = q.list();
        recordNumber = goodClassList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public GoodClass GetGoodClassByGoodClassId(int goodClassId) {
        Session s = factory.getCurrentSession();
        GoodClass goodClass = (GoodClass)s.get(GoodClass.class, goodClassId);
        return goodClass;
    }

    /*����GoodClass��Ϣ*/
    public void UpdateGoodClass(GoodClass goodClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(goodClass);
    }

    /*ɾ��GoodClass��Ϣ*/
    public void DeleteGoodClass (int goodClassId) throws Exception {
        Session s = factory.getCurrentSession();
        Object goodClass = s.load(GoodClass.class, goodClassId);
        s.delete(goodClass);
    }

}
