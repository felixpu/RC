<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Login</title>
<script type="text/javascript">
	function openWin() {
		/*
			var w = (screen.availWidth * 70) / 100;
			var h = (screen.availHeight * 70) / 100;
			var top = (screen.availHeight - h) / 2;
			var left = (screen.availWidth - w) / 2;
			function openwin() {
				window
						.open(
								"resumeChange.jsp",
								"",
								"resizable=no,fullscreen=no,status=no,toolbar=no,menubar=no,titlebar=yes,location=no,scrollbars=yes,left="
										+ left
										+ ",top="
										+ top
										+ ",width="
										+ w
										+ ",height=" + h, true);
				///window.opener = null;
				//window.open("", "_self");
				//window.close();
			}
		 */
	}
</script>
</head>
<body onload="openWin()">
	<jsp:forward page="resumeChange.jsp"></jsp:forward>
</body>
</html>

