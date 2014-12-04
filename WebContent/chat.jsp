<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>chat</title>
<%@include file="basePage/base.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/matrix-style.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/css/bootstrap.min.css" />
<script type="text/javascript" src="js/matrix.chat.js"></script>
<script src="<%=contextPath%>/js/bootstrap.min.js" type="text/javascript"></script>


</head>
<body>
	<div id="container">
	  <div class="widget-content nopadding">
	     <!-- <div class="chat-users panel-right2">
	           <ul class="contact-list">
	           <li id="user-Alex" class="online"><a href="#"><img alt="" src="img/demo/av1.jpg" /> <span>Alex</span></a></li>
	           <li id="user-Linda"><a href="#"><img alt="" src="img/demo/av2.jpg" /> <span>Linda</span></a></li>
	           <li id="user-John" class="online new"><a href="#"><img alt="" src="img/demo/av3.jpg" /> <span>John</span></a><span class="msg-count badge badge-info">3</span></li>
	           <li id="user-Mark" class="online"><a href="#"><img alt="" src="img/demo/av4.jpg" /> <span>Mark</span></a></li>
	           <li id="user-Maxi" class="online"><a href="#"><img alt="" src="img/demo/av5.jpg" /> <span>Maxi</span></a></li>
	         </ul>
	     </div> -->
	     <div class="chat-content panel-left2">
	       <div class="chat-messages" id="chat-messages">
	         <div id="chat-messages-inner"></div>
	       </div>
	       <div class="chat-message well">
	         <button class="btn btn-success">Send</button>
	         <span class="input-box">
	         <input type="text" name="msg-box" id="msg-box" />
	         </span> </div>
	     </div>
	   </div>
	</div>
</body>
</html>
