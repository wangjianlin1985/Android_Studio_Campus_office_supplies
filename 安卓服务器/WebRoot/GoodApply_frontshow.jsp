<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.GoodApply" %>
<%@ page import="com.chengxusheji.domain.Goods" %>
<%@ page import="com.chengxusheji.domain.Person" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Goods��Ϣ
    List<Goods> goodsList = (List<Goods>)request.getAttribute("goodsList");
    //��ȡ���е�Person��Ϣ
    List<Person> personList = (List<Person>)request.getAttribute("personList");
    GoodApply goodApply = (GoodApply)request.getAttribute("goodApply");

%>
<HTML><HEAD><TITLE>�鿴��Ʒ����</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><%=goodApply.getApplyId() %></td>
  </tr>

  <tr>
    <td width=30%>�������Ʒ:</td>
    <td width=70%>
      <%=goodApply.getGoodObj().getGoodName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=goodApply.getApplyCount() %></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><%=goodApply.getApplyTime() %></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%>
      <%=goodApply.getPersonObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><%=goodApply.getHandlePerson() %></td>
  </tr>

  <tr>
    <td width=30%>��ע:</td>
    <td width=70%><%=goodApply.getApplyMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
