<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.GoodClass" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�GoodClass��Ϣ
    List<GoodClass> goodClassList = (List<GoodClass>)request.getAttribute("goodClassList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��Ӱ칫��Ʒ</TITLE> 
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
    var goodNo = document.getElementById("goods.goodNo").value;
    if(goodNo=="") {
        alert('��������Ʒ���!');
        return false;
    }
    var goodName = document.getElementById("goods.goodName").value;
    if(goodName=="") {
        alert('��������Ʒ����!');
        return false;
    }
    var specModel = document.getElementById("goods.specModel").value;
    if(specModel=="") {
        alert('���������ͺ�!');
        return false;
    }
    var measureUnit = document.getElementById("goods.measureUnit").value;
    if(measureUnit=="") {
        alert('�����������λ!');
        return false;
    }
    var storeHouse = document.getElementById("goods.storeHouse").value;
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
    <TD align="left" vAlign=top >
    <s:form action="Goods/Goods_AddGoods.action" method="post" id="goodsAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>��Ʒ���:</td>
    <td width=70%><input id="goods.goodNo" name="goods.goodNo" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��Ʒ���:</td>
    <td width=70%>
      <select name="goods.goodClassObj.goodClassId">
      <%
        for(GoodClass goodClass:goodClassList) {
      %>
          <option value='<%=goodClass.getGoodClassId() %>'><%=goodClass.getGoodClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��Ʒ����:</td>
    <td width=70%><input id="goods.goodName" name="goods.goodName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ƷͼƬ:</td>
    <td width=70%><input id="goodPhotoFile" name="goodPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>����ͺ�:</td>
    <td width=70%><input id="goods.specModel" name="goods.specModel" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>������λ:</td>
    <td width=70%><input id="goods.measureUnit" name="goods.measureUnit" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><input id="goods.stockCount" name="goods.stockCount" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="goods.price" name="goods.price" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>���:</td>
    <td width=70%><input id="goods.totalMoney" name="goods.totalMoney" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>�ֿ�:</td>
    <td width=70%><input id="goods.storeHouse" name="goods.storeHouse" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��ע:</td>
    <td width=70%><textarea id="goods.goodMemo" name="goods.goodMemo" rows="5" cols="50"></textarea></td>
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
