<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="renderer" content="webkit"> 
	<title>维森凯睿工具</title>
	<%@include file="basePage/base.jsp"%>
	<link rel="shortcut icon" href="favicon.ico" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/main_css.css" />
	<script type="text/javascript" src="<%=contextPath%>/js/main.js"></script>
</head>
<body onload="getDate01()">
    <div id="top">
		<div id="top_logo">
			维森凯睿企业管理有限公司<font size="2px">-工具系统</font>
		</div>
		<div id="top_links">
			<div id="top_op">
				<ul>
					<li>
						<img alt="当前用户" src="images/common/user.jpg">：
						<span id='nowuser'></span>
					</li>
					<li>
						<img alt="事务月份" src="images/common/month.jpg">：
						<span id="tdate"></span>
					</li>
					<li>
						<img alt="今天是" src="images/common/date.jpg">：
						<span id="ttime"></span>
					</li>
				</ul> 
			</div>
			<div id="top_close">
				<a href="javascript:void(0);"  target="_parent">
					<img alt="退出系统" title="退出系统" src="images/common/close.jpg" style="position: relative; top: 10px; left: 25px;">
				</a>
			</div>
		</div>
	</div>
    <!-- side menu start -->
	<div id="side">
		<div id="left_menu">
		 	<ul id="TabPage2" style="height:200px; margin-top:50px;">
				<li id="left_tab1" class="selected" onClick="javascript:switchTab('rc.jsp','简历转换 2.0');" title="简历转换">
					<img alt="简历转换" title="简历转换" src="images/common/tools.png" width="33" height="31">
				</li>
				<li id="left_tab2" onClick="javascript:switchTab('chat.jsp','交流/留言');" title="交流/留言">
					<img alt="交流/留言" title="交流/留言" src="images/common/comment.png" width="33" height="31">
				</li>		
				<li id="left_tab3" onClick="javascript:switchTab('help.jsp','帮助');" title="帮助">
					<img alt="帮助" title="帮助" src="images/common/help.png" width="33" height="31">
				</li>
			</ul>
		 </div>
	</div>
    <!-- side menu start -->
    <div id="top_nav">
	 	<span id="here_area">简历转换2.0</span>
	</div>
    <div id="main">
      	<iframe name="right" id="rightMain" src="<%=contextPath%>/rc.jsp" frameborder="no" scrolling="auto" width="100%" height="100%" allowtransparency="true"/>
    </div>
</body>
</html>
   
 