<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>176基于Android的校园办公用品管理系统-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>Department/Department_FrontQueryDepartment.action" target="OfficeMain">部门</a></li> 
			<li><a href="<%=basePath %>Person/Person_FrontQueryPerson.action" target="OfficeMain">人员</a></li> 
			<li><a href="<%=basePath %>GoodClass/GoodClass_FrontQueryGoodClass.action" target="OfficeMain">物品类别</a></li> 
			<li><a href="<%=basePath %>Goods/Goods_FrontQueryGoods.action" target="OfficeMain">办公用品</a></li> 
			<li><a href="<%=basePath %>GoodApply/GoodApply_FrontQueryGoodApply.action" target="OfficeMain">物品申请</a></li> 
			<li><a href="<%=basePath %>Purchase/Purchase_FrontQueryPurchase.action" target="OfficeMain">物品购置</a></li> 
			<li><a href="<%=basePath %>GoodUse/GoodUse_FrontQueryGoodUse.action" target="OfficeMain">物品领用</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>双鱼林设计 QQ:287307421或254540457 &copy;版权所有 <a href="http://www.shuangyulin.com" target="_blank">双鱼林设计网</a>&nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
