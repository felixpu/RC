<%@page import="java.net.InetAddress"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
	String contextPath = request.getContextPath();
	String ipAddr = request.getRemoteAddr();
	//String ipAddr = InetAddress.getLocalHost().getHostAddress();
%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/jquerylib/plugins/jquery-easyui-1.2.3/themes/metro/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/jquerylib/plugins/jquery-easyui-1.2.3/themes/demo.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/jquerylib/plugins/jquery-easyui-1.2.3/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/pure-min.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/elegant/om-elegant.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/basic_layout.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/common_style.css"/>


<script src="<%=contextPath%>/js/jquery.js"></script>
<script src="<%=contextPath%>/js/jquery.form.js"></script>
<script src="<%=contextPath%>/js/operamasks-ui.js"></script>
<script src="<%=contextPath%>/jquerylib/jquery.easyui.min.js" type="text/javascript"></script>
<script src="<%=contextPath%>/jquerylib/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=contextPath%>/js/ajaxfileupload.js" type="text/javascript"></script>
<script src="<%=contextPath%>/js/util.js" type="text/javascript"></script>

<script>
var contextPath = "<%=contextPath%>";
var addr = '<%=ipAddr%>';
</script>
<script src="<%=contextPath%>/js/base.js" type="text/javascript"></script>
