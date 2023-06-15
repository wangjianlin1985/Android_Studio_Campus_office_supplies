<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.GoodUse" %>
<%@ page import="com.chengxusheji.domain.Goods" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Goods信息
    List<Goods> goodsList = (List<Goods>)request.getAttribute("goodsList");
    GoodUse goodUse = (GoodUse)request.getAttribute("goodUse");

%>
<HTML><HEAD><TITLE>查看物品领用</TITLE>
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
    <td width=30%>领用id:</td>
    <td width=70%><%=goodUse.getUseId() %></td>
  </tr>

  <tr>
    <td width=30%>领用物品:</td>
    <td width=70%>
      <%=goodUse.getGoodObj().getGoodName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>领用数量:</td>
    <td width=70%><%=goodUse.getUseCount() %></td>
  </tr>

  <tr>
    <td width=30%>单价:</td>
    <td width=70%><%=goodUse.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>金额:</td>
    <td width=70%><%=goodUse.getTotalMoney() %></td>
  </tr>

  <tr>
    <td width=30%>领用时间:</td>
        <% java.text.DateFormat useTimeSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=useTimeSDF.format(goodUse.getUseTime()) %></td>
  </tr>

  <tr>
    <td width=30%>领用人:</td>
    <td width=70%><%=goodUse.getUserMan() %></td>
  </tr>

  <tr>
    <td width=30%>经办人:</td>
    <td width=70%><%=goodUse.getOperatorMan() %></td>
  </tr>

  <tr>
    <td width=30%>仓库:</td>
    <td width=70%><%=goodUse.getStoreHouse() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
