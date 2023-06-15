<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Purchase" %>
<%@ page import="com.chengxusheji.domain.Goods" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Goods��Ϣ
    List<Goods> goodsList = (List<Goods>)request.getAttribute("goodsList");
    Purchase purchase = (Purchase)request.getAttribute("purchase");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸���Ʒ����</TITLE>
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
    var operatorMan = document.getElementById("purchase.operatorMan").value;
    if(operatorMan=="") {
        alert('�����뾭����!');
        return false;
    }
    var keepMan = document.getElementById("purchase.keepMan").value;
    if(keepMan=="") {
        alert('�����뱣����!');
        return false;
    }
    var storeHouse = document.getElementById("purchase.storeHouse").value;
    if(storeHouse=="") {
        alert('������ֿ�!');
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
    <TD align="left" vAlign=top ><s:form action="Purchase/Purchase_ModifyPurchase.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><input id="purchase.purchaseId" name="purchase.purchaseId" type="text" value="<%=purchase.getPurchaseId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>������Ʒ:</td>
    <td width=70%>
      <select name="purchase.goodObj.goodNo">
      <%
        for(Goods goods:goodsList) {
          String selected = "";
          if(goods.getGoodNo().equals(purchase.getGoodObj().getGoodNo()))
            selected = "selected";
      %>
          <option value='<%=goods.getGoodNo() %>' <%=selected %>><%=goods.getGoodName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ü۸�:</td>
    <td width=70%><input id="purchase.price" name="purchase.price" type="text" size="8" value='<%=purchase.getPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="purchase.buyCount" name="purchase.buyCount" type="text" size="8" value='<%=purchase.getBuyCount() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���ý��:</td>
    <td width=70%><input id="purchase.totalMoney" name="purchase.totalMoney" type="text" size="8" value='<%=purchase.getTotalMoney() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���ʱ��:</td>
    <% DateFormat inDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="purchase.inDate"  name="purchase.inDate" onclick="setDay(this);" value='<%=inDateSDF.format(purchase.getInDate()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="purchase.operatorMan" name="purchase.operatorMan" type="text" size="20" value='<%=purchase.getOperatorMan() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="purchase.keepMan" name="purchase.keepMan" type="text" size="20" value='<%=purchase.getKeepMan() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ֿ�:</td>
    <td width=70%><input id="purchase.storeHouse" name="purchase.storeHouse" type="text" size="20" value='<%=purchase.getStoreHouse() %>'/></td>
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
