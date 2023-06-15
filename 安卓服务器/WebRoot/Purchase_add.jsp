<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Goods" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Goods信息
    List<Goods> goodsList = (List<Goods>)request.getAttribute("goodsList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加物品购置</TITLE> 
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
/*验证表单*/
function checkForm() {
    var operatorMan = document.getElementById("purchase.operatorMan").value;
    if(operatorMan=="") {
        alert('请输入经办人!');
        return false;
    }
    var keepMan = document.getElementById("purchase.keepMan").value;
    if(keepMan=="") {
        alert('请输入保管人!');
        return false;
    }
    var storeHouse = document.getElementById("purchase.storeHouse").value;
    if(storeHouse=="") {
        alert('请输入仓库!');
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
    <s:form action="Purchase/Purchase_AddPurchase.action" method="post" id="purchaseAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>购置物品:</td>
    <td width=70%>
      <select name="purchase.goodObj.goodNo">
      <%
        for(Goods goods:goodsList) {
      %>
          <option value='<%=goods.getGoodNo() %>'><%=goods.getGoodName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>购置价格:</td>
    <td width=70%><input id="purchase.price" name="purchase.price" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>购置数量:</td>
    <td width=70%><input id="purchase.buyCount" name="purchase.buyCount" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>购置金额:</td>
    <td width=70%><input id="purchase.totalMoney" name="purchase.totalMoney" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>入库时间:</td>
    <td width=70%><input type="text" readonly id="purchase.inDate"  name="purchase.inDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>经办人:</td>
    <td width=70%><input id="purchase.operatorMan" name="purchase.operatorMan" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>保管人:</td>
    <td width=70%><input id="purchase.keepMan" name="purchase.keepMan" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>仓库:</td>
    <td width=70%><input id="purchase.storeHouse" name="purchase.storeHouse" type="text" size="20" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
