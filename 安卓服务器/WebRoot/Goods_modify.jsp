<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Goods" %>
<%@ page import="com.chengxusheji.domain.GoodClass" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的GoodClass信息
    List<GoodClass> goodClassList = (List<GoodClass>)request.getAttribute("goodClassList");
    Goods goods = (Goods)request.getAttribute("goods");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改办公用品</TITLE>
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
    var goodNo = document.getElementById("goods.goodNo").value;
    if(goodNo=="") {
        alert('请输入物品编号!');
        return false;
    }
    var goodName = document.getElementById("goods.goodName").value;
    if(goodName=="") {
        alert('请输入物品名称!');
        return false;
    }
    var specModel = document.getElementById("goods.specModel").value;
    if(specModel=="") {
        alert('请输入规格型号!');
        return false;
    }
    var measureUnit = document.getElementById("goods.measureUnit").value;
    if(measureUnit=="") {
        alert('请输入计量单位!');
        return false;
    }
    var storeHouse = document.getElementById("goods.storeHouse").value;
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
    <TD align="left" vAlign=top ><s:form action="Goods/Goods_ModifyGoods.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>物品编号:</td>
    <td width=70%><input id="goods.goodNo" name="goods.goodNo" type="text" value="<%=goods.getGoodNo() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>商品类别:</td>
    <td width=70%>
      <select name="goods.goodClassObj.goodClassId">
      <%
        for(GoodClass goodClass:goodClassList) {
          String selected = "";
          if(goodClass.getGoodClassId() == goods.getGoodClassObj().getGoodClassId())
            selected = "selected";
      %>
          <option value='<%=goodClass.getGoodClassId() %>' <%=selected %>><%=goodClass.getGoodClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>物品名称:</td>
    <td width=70%><input id="goods.goodName" name="goods.goodName" type="text" size="20" value='<%=goods.getGoodName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>物品图片:</td>
    <td width=70%><img src="<%=basePath %><%=goods.getGoodPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="goods.goodPhoto" value="<%=goods.getGoodPhoto() %>" />
    <input id="goodPhotoFile" name="goodPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>规格型号:</td>
    <td width=70%><input id="goods.specModel" name="goods.specModel" type="text" size="20" value='<%=goods.getSpecModel() %>'/></td>
  </tr>

  <tr>
    <td width=30%>计量单位:</td>
    <td width=70%><input id="goods.measureUnit" name="goods.measureUnit" type="text" size="20" value='<%=goods.getMeasureUnit() %>'/></td>
  </tr>

  <tr>
    <td width=30%>库存数量:</td>
    <td width=70%><input id="goods.stockCount" name="goods.stockCount" type="text" size="8" value='<%=goods.getStockCount() %>'/></td>
  </tr>

  <tr>
    <td width=30%>单价:</td>
    <td width=70%><input id="goods.price" name="goods.price" type="text" size="8" value='<%=goods.getPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>金额:</td>
    <td width=70%><input id="goods.totalMoney" name="goods.totalMoney" type="text" size="8" value='<%=goods.getTotalMoney() %>'/></td>
  </tr>

  <tr>
    <td width=30%>仓库:</td>
    <td width=70%><input id="goods.storeHouse" name="goods.storeHouse" type="text" size="20" value='<%=goods.getStoreHouse() %>'/></td>
  </tr>

  <tr>
    <td width=30%>备注:</td>
    <td width=70%><textarea id="goods.goodMemo" name="goods.goodMemo" rows=5 cols=50><%=goods.getGoodMemo() %></textarea></td>
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
