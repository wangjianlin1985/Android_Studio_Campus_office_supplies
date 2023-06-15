<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Department��Ϣ
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�����Ա</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var user_name = document.getElementById("person.user_name").value;
    if(user_name=="") {
        alert('��������Ա���!');
        return false;
    }
    var password = document.getElementById("person.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var name = document.getElementById("person.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var sex = document.getElementById("person.sex").value;
    if(sex=="") {
        alert('�������Ա�!');
        return false;
    }
    var telephone = document.getElementById("person.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="Person/Person_AddPerson.action" method="post" id="personAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��Ա���:</td>
    <td width=70%><input id="person.user_name" name="person.user_name" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="person.password" name="person.password" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>���ڲ���:</td>
    <td width=70%>
      <select name="person.departmentObj.departmentId">
      <%
        for(Department department:departmentList) {
      %>
          <option value='<%=department.getDepartmentId() %>'><%=department.getDepartmentName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="person.name" name="person.name" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="person.sex" name="person.sex" type="text" size="4" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input type="text" readonly id="person.bornDate"  name="person.bornDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="person.telephone" name="person.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><input id="person.address" name="person.address" type="text" size="80" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
