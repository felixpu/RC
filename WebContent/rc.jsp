<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SCT-后台系统</title>
<%@include file='basePage/base.jsp' %>
<style type="text/css">
font {
	font-weight: bold;
	font-size:15px;
}
form {
	margin-left: 15%;
}
#name,#mobile,#eamil,#position{
-moz-border-radius: 5px;
    -webkit-border-radius: 5px;
}
.struct{
	margin-top:15px;
}
.ok{
	border:medium;
	width:80%;
	height:40px;
	margin-bottom:5%;
	background-color:#F3EFF7;
    font-weight: bold;
}
.ok:HOVER{
	background-color:#DDDCDF;
}
.ok:ACTIVE{
	background-color: #F3EFF7;
}
#bug{
	height:32px;
	background-color:#F8F8F8;
	border-radius:5px;
    font-weight: bold;
}
table{
width: 100%;
}
.abtn{
	border:medium;
	width:100%;
	height:40px;
	margin-bottom:5%;
	background-color:#F3EFF7;
    font-weight: bold;
}
.abtn:HOVER{
	background-color:#DDDCDF;
}
.abtn:ACTIVE{
	background-color: #F3EFF7;
}
</style>
<script src="<%=contextPath%>/js/resumeChange.js"></script>
</head>
<body>
	<div id="container">
	<form id="form1" enctype="multipart/form-data" method="post"> 
				<div class="struct">
					<div><font>姓名:</font></div>
					<input id="name" name="name"  data-options="prompt:'输入姓名...',iconCls:'icon-newman'">
				</div>
				<div class="struct">
					<div><font>手机:</font></div>
					<input id="mobile" name="mobile"  data-options="prompt:'输入手机...',validType:'mobile',iconCls:'icon-newphone'" >
				</div>
				<div class="struct">
					<div><font>邮箱:</font></div>
					<input id="email" name="email" data-options="prompt:'输入邮箱...',validType:'email',iconCls:'icon-newmail'">
				</div>
				<div class="struct">
					<div><font>职位:</font></div>
					<input id="position" name="position" data-options="prompt:'输入职位...',validType:'position'">
				</div>
				<div class="struct" >
					<div><font>文件:</font></div>
					<input name='mfile' data-options="prompt:'选择一个文件...',buttonIcon:'icon-newadd',buttonText:'浏览...'" class="easyui-filebox"  style="width:80%;height:40px;border:0;">
				</div> <!-- ,id:'htmfile',accept:'text/html' -->
		 		<div class="struct"><input class='ok' id="ok" type="button" value='转换'></div> 
			</form>
	</div>
	<div style="color:rgb(49, 11, 11);" align="center">
		<h3>工具固定链接：http://vision-server:8080/ResumeChange</h3>
	</div>
</body>
</html>
