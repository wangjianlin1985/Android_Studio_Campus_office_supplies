<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Purchase" %>
<%@ page import="com.chengxusheji.domain.Goods" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Goods��Ϣ
    List<Goods> goodsList = (List<Goods>)request.getAttribute("goodsList");
    Purchase purchase = (Purchase)request.getAttribute("purchase");

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
    <td width=70%><%=purchase.getPurchaseId() %></td>
  </tr>

  <tr>
    <td width=30%>������Ʒ:</td>
    <td width=70%>
      <%=purchase.getGoodObj().getGoodName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ü۸�:</td>
    <td width=70%><%=purchase.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><%=purchase.getBuyCount() %></td>
  </tr>

  <tr>
    <td width=30%>���ý��:</td>
    <td width=70%><%=purchase.getTotalMoney() %></td>
  </tr>

  <tr>
    <td width=30%>���ʱ��:</td>
        <% java.text.DateFormat inDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=inDateSDF.format(purchase.getInDate()) %></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><%=purchase.getOperatorMan() %></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><%=purchase.getKeepMan() %></td>
  </tr>

  <tr>
    <td width=30%>�ֿ�:</td>
    <td width=70%><%=purchase.getStoreHouse() %></td>
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
