<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Department department = (Department)request.getAttribute("department");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改部门</TITLE>
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
    var departmentName = document.getElementById("department.departmentName").value;
    if(departmentName=="") {
        alert('请输入部门名称!');
        return false;
    }
    var departmentType = document.getElementById("department.departmentType").value;
    if(departmentType=="") {
        alert('请输入部门类别!');
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
    <TD align="left" vAlign=top ><s:form action="Department/Department_ModifyDepartment.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>部门编号:</td>
    <td width=70%><input id="department.departmentId" name="department.departmentId" type="text" value="<%=department.getDepartmentId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>部门名称:</td>
    <td width=70%><input id="department.departmentName" name="department.departmentName" type="text" size="20" value='<%=department.getDepartmentName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>部门类别:</td>
    <td width=70%><input id="department.departmentType" name="department.departmentType" type="text" size="20" value='<%=department.getDepartmentType() %>'/></td>
  </tr>

  <tr>
    <td width=30%>备注:</td>
    <td width=70%><textarea id="department.departmentMemo" name="department.departmentMemo" rows=5 cols=50><%=department.getDepartmentMemo() %></textarea></td>
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
