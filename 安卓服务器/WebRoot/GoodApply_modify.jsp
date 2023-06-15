<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.GoodApply" %>
<%@ page import="com.chengxusheji.domain.Goods" %>
<%@ page import="com.chengxusheji.domain.Person" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Goods信息
    List<Goods> goodsList = (List<Goods>)request.getAttribute("goodsList");
    //获取所有的Person信息
    List<Person> personList = (List<Person>)request.getAttribute("personList");
    GoodApply goodApply = (GoodApply)request.getAttribute("goodApply");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改物品申请</TITLE>
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
    var handlePerson = document.getElementById("goodApply.handlePerson").value;
    if(handlePerson=="") {
        alert('请输入经办人!');
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
    <TD align="left" vAlign=top ><s:form action="GoodApply/GoodApply_ModifyGoodApply.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>申请id:</td>
    <td width=70%><input id="goodApply.applyId" name="goodApply.applyId" type="text" value="<%=goodApply.getApplyId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>审请的用品:</td>
    <td width=70%>
      <select name="goodApply.goodObj.goodNo">
      <%
        for(Goods goods:goodsList) {
          String selected = "";
          if(goods.getGoodNo().equals(goodApply.getGoodObj().getGoodNo()))
            selected = "selected";
      %>
          <option value='<%=goods.getGoodNo() %>' <%=selected %>><%=goods.getGoodName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>申请数量:</td>
    <td width=70%><input id="goodApply.applyCount" name="goodApply.applyCount" type="text" size="8" value='<%=goodApply.getApplyCount() %>'/></td>
  </tr>

  <tr>
    <td width=30%>申请时间:</td>
    <td width=70%><input id="goodApply.applyTime" name="goodApply.applyTime" type="text" size="20" value='<%=goodApply.getApplyTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>申请人:</td>
    <td width=70%>
      <select name="goodApply.personObj.user_name">
      <%
        for(Person person:personList) {
          String selected = "";
          if(person.getUser_name().equals(goodApply.getPersonObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=person.getUser_name() %>' <%=selected %>><%=person.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>经办人:</td>
    <td width=70%><input id="goodApply.handlePerson" name="goodApply.handlePerson" type="text" size="20" value='<%=goodApply.getHandlePerson() %>'/></td>
  </tr>

  <tr>
    <td width=30%>备注:</td>
    <td width=70%><textarea id="goodApply.applyMemo" name="goodApply.applyMemo" rows=5 cols=50><%=goodApply.getApplyMemo() %></textarea></td>
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
