<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.GoodUse" %>
<%@ page import="com.chengxusheji.domain.Goods" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Goods��Ϣ
    List<Goods> goodsList = (List<Goods>)request.getAttribute("goodsList");
    GoodUse goodUse = (GoodUse)request.getAttribute("goodUse");

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
    var userMan = document.getElementById("goodUse.userMan").value;
    if(userMan=="") {
        alert('������������!');
        return false;
    }
    var operatorMan = document.getElementById("goodUse.operatorMan").value;
    if(operatorMan=="") {
        alert('�����뾭����!');
        return false;
    }
    var storeHouse = document.getElementById("goodUse.storeHouse").value;
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
    <TD align="left" vAlign=top ><s:form action="GoodUse/GoodUse_ModifyGoodUse.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><input id="goodUse.useId" name="goodUse.useId" type="text" value="<%=goodUse.getUseId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>������Ʒ:</td>
    <td width=70%>
      <select name="goodUse.goodObj.goodNo">
      <%
        for(Goods goods:goodsList) {
          String selected = "";
          if(goods.getGoodNo().equals(goodUse.getGoodObj().getGoodNo()))
            selected = "selected";
      %>
          <option value='<%=goods.getGoodNo() %>' <%=selected %>><%=goods.getGoodName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><input id="goodUse.useCount" name="goodUse.useCount" type="text" size="8" value='<%=goodUse.getUseCount() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="goodUse.price" name="goodUse.price" type="text" size="8" value='<%=goodUse.getPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���:</td>
    <td width=70%><input id="goodUse.totalMoney" name="goodUse.totalMoney" type="text" size="8" value='<%=goodUse.getTotalMoney() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <% DateFormat useTimeSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="goodUse.useTime"  name="goodUse.useTime" onclick="setDay(this);" value='<%=useTimeSDF.format(goodUse.getUseTime()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="goodUse.userMan" name="goodUse.userMan" type="text" size="20" value='<%=goodUse.getUserMan() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="goodUse.operatorMan" name="goodUse.operatorMan" type="text" size="20" value='<%=goodUse.getOperatorMan() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ֿ�:</td>
    <td width=70%><input id="goodUse.storeHouse" name="goodUse.storeHouse" type="text" size="20" value='<%=goodUse.getStoreHouse() %>'/></td>
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
