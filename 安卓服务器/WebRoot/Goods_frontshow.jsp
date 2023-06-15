<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Goods" %>
<%@ page import="com.chengxusheji.domain.GoodClass" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的GoodClass信息
    List<GoodClass> goodClassList = (List<GoodClass>)request.getAttribute("goodClassList");
    Goods goods = (Goods)request.getAttribute("goods");

%>
<HTML><HEAD><TITLE>查看办公用品</TITLE>
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
    <td width=30%>物品编号:</td>
    <td width=70%><%=goods.getGoodNo() %></td>
  </tr>

  <tr>
    <td width=30%>商品类别:</td>
    <td width=70%>
      <%=goods.getGoodClassObj().getGoodClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>物品名称:</td>
    <td width=70%><%=goods.getGoodName() %></td>
  </tr>

  <tr>
    <td width=30%>物品图片:</td>
    <td width=70%><img src="<%=basePath %><%=goods.getGoodPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>规格型号:</td>
    <td width=70%><%=goods.getSpecModel() %></td>
  </tr>

  <tr>
    <td width=30%>计量单位:</td>
    <td width=70%><%=goods.getMeasureUnit() %></td>
  </tr>

  <tr>
    <td width=30%>库存数量:</td>
    <td width=70%><%=goods.getStockCount() %></td>
  </tr>

  <tr>
    <td width=30%>单价:</td>
    <td width=70%><%=goods.getPrice() %></td>
  </tr>

  <tr>
    <td width=30%>金额:</td>
    <td width=70%><%=goods.getTotalMoney() %></td>
  </tr>

  <tr>
    <td width=30%>仓库:</td>
    <td width=70%><%=goods.getStoreHouse() %></td>
  </tr>

  <tr>
    <td width=30%>备注:</td>
    <td width=70%><%=goods.getGoodMemo() %></td>
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
