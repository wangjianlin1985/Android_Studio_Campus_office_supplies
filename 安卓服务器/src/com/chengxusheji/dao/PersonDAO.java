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
import com.chengxusheji.domain.Department;
import com.chengxusheji.domain.Person;

@Service @Transactional
public class PersonDAO {

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
    public void AddPerson(Person person) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(person);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Person> QueryPersonInfo(String user_name,Department departmentObj,String name,String bornDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Person person where 1=1";
    	if(!user_name.equals("")) hql = hql + " and person.user_name like '%" + user_name + "%'";
    	if(null != departmentObj && departmentObj.getDepartmentId()!=0) hql += " and person.departmentObj.departmentId=" + departmentObj.getDepartmentId();
    	if(!name.equals("")) hql = hql + " and person.name like '%" + name + "%'";
    	if(!bornDate.equals("")) hql = hql + " and person.bornDate like '%" + bornDate + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List personList = q.list();
    	return (ArrayList<Person>) personList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Person> QueryPersonInfo(String user_name,Department departmentObj,String name,String bornDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Person person where 1=1";
    	if(!user_name.equals("")) hql = hql + " and person.user_name like '%" + user_name + "%'";
    	if(null != departmentObj && departmentObj.getDepartmentId()!=0) hql += " and person.departmentObj.departmentId=" + departmentObj.getDepartmentId();
    	if(!name.equals("")) hql = hql + " and person.name like '%" + name + "%'";
    	if(!bornDate.equals("")) hql = hql + " and person.bornDate like '%" + bornDate + "%'";
    	Query q = s.createQuery(hql);
    	List personList = q.list();
    	return (ArrayList<Person>) personList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Person> QueryAllPersonInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Person";
        Query q = s.createQuery(hql);
        List personList = q.list();
        return (ArrayList<Person>) personList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String user_name,Department departmentObj,String name,String bornDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Person person where 1=1";
        if(!user_name.equals("")) hql = hql + " and person.user_name like '%" + user_name + "%'";
        if(null != departmentObj && departmentObj.getDepartmentId()!=0) hql += " and person.departmentObj.departmentId=" + departmentObj.getDepartmentId();
        if(!name.equals("")) hql = hql + " and person.name like '%" + name + "%'";
        if(!bornDate.equals("")) hql = hql + " and person.bornDate like '%" + bornDate + "%'";
        Query q = s.createQuery(hql);
        List personList = q.list();
        recordNumber = personList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Person GetPersonByUser_name(String user_name) {
        Session s = factory.getCurrentSession();
        Person person = (Person)s.get(Person.class, user_name);
        return person;
    }

    /*����Person��Ϣ*/
    public void UpdatePerson(Person person) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(person);
    }

    /*ɾ��Person��Ϣ*/
    public void DeletePerson (String user_name) throws Exception {
        Session s = factory.getCurrentSession();
        Object person = s.load(Person.class, user_name);
        s.delete(person);
    }

}
