<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%
	String contextPath = request.getContextPath();

%>
<link rel="icon" href="img/tb.ico" type="image/x-icon" />
<link rel="shortcut icon" href="tb/my.ico" type="image/x-icon" />
<link rel="bookmark" href="img/tb.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Resume_Conversion V1.0</title>
<script>
var contextPath = "<%=contextPath%>";
</script>
<script src="<%=contextPath%>/js/jquery.js"></script>
<script src="<%=contextPath%>/js/ajaxfileupload.js"></script>
<script src="<%=contextPath%>/js/newResumeChange.js"></script>

</head>
<body>
  <div id="container">
    <div id="main">
      <div class='header'>
        <div id="logo"><h1>Resume_<font>Conversion</font></h1></div>
      </div>
      <div id="site_content">
		  <div id="content">
			<%-- <form id="rc" action="<%=request.getContextPath()%>/pages/system/resumeChange.do" method="POST" enctype="multipart/form-data"> --%> 
				<div id="first">
				<div class="struct">
					<div>Name:</div>
					<input id="name" type='text' name="name">
				</div>
				<div class="struct">
					<div>Mobile:</div>
					<input id="mobile" type='text'  name="mobile">
				</div>
				<div class="struct">
					<div>E_Mail:</div>
					<input id="email" type='text'  name="email">
				</div>
				<div class="struct">
					<div>Position:</div>
					<input id="position" type='text'  name="position">
				</div>
				</div>
				<div class="struct">
					<div>File:</div>
					<div align='right' style='width:80%;'>
						<input type='text'  class='filepath' id='filepath' readonly="true">
						<a id="afile" href='#' align='center'>			
							<font color=''>Choose File</font>
							<input id='htmfile' name='mfile' type="file" accept="text/html" onchange="document.getElementById('filepath').value=this.value">
						</a>
					</div>
				</div>
				<div class="struct">
					<input class='ok' id="ok" type="button" onclick='ajaxFileUpload()' value='CHANGE...'>
				</div>
			<!-- </form> -->
			</div>
			<div id="sidebar_container">
				<div>Write some bug info to here </div>
				<div id="txtArea">
					<div id='realtxt'>
					</div>
				</div>
				<table width="100%">
					<tr>
						<td ><input id="inputbugtxt" type="text" value='write here...'>	</td>
						<td width="30%" ><input class='ok' id="bugtxt" type="button" value='OK'></td>
					</tr>
				</table>
			</div>
      </div>
      <div class='footer'>
        <p><font size="2;">Copyright &copy;2014 http://<font color="red">192.168.1.111(112)</font>:8080/ResumeChange </font></p>
		<p><font size="1;">VISIONCAREER ENTERPRISE MANAGEMENT CO.，LTD</font><font size="1"></font></p>
      </div>
    </div>
  </div>
<div class="zzsc">
	<a href="javascript:" class="close">关闭</a>
	<div id='msg' style="color:white">
	</div>
</div>
</body>
</html>
