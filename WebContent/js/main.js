var v = setInterval('getDate01()', 1000);
$(document).ready(function(){
	$('#TabPage2 li').click(function(){
		var index = $(this).index();
		$(this).css({background:'#fff'});
		$('#TabPage2 li').each(function(i, ele){
			if( i!=index ){
				$(ele).css({background:'#303030'});
				}
			});
	});

});

/**隐藏或者显示侧边栏**/
function switchSysBar(flag){
	var side = $('#side');
if( flag==true ){	// flag==true
	side.css({width:'60px'});
	$('#top_nav').css({width:'77%', left:'304px'});
	$('#main').css({left:'60px'});
}else{
		side.css({width:'60px'});
		$('#top_nav').css({width:'77%', left:'304px', 'padding-left':'0px'});
    	$('#main').css({left:'60px'});
    	$("#show_hide_btn").find('img').attr('src', 'images/common/nav_hide.png');
	}
}
/**退出系统**/
function logout(){
	if(confirm("您确定要退出本系统吗？")){
		window.location.href = "login.html";
	}
}

/**获得当前日期**/
function  getDate01(){
	var time = new Date();
	var myYear = time.getFullYear();
	var myMonth = time.getMonth()+1;
	var myDay = time.getDate();
	var h= time.getHours();
	var m = time.getMinutes();
	var s = time.getSeconds();
	if(myMonth < 10){
		myMonth = "0" + myMonth;
	}
	if(m < 10){
		m = "0" + m;
	}
	if(s < 10){
		s = "0" + s;
	}
	document.getElementById("tdate").innerHTML =  myYear + "/" + myMonth + "/" + myDay;
	document.getElementById("ttime").innerHTML =  h + ":" + m + ":" + s;
	
	document.getElementById("nowuser").innerHTML = nowuser;
}

/**加入收藏夹**/
function addfavorite(){
	var ua = navigator.userAgent.toLowerCase();
	 if (ua.indexOf("360se") > -1){
	  	art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'由于360浏览器功能限制，加入收藏夹功能失效', ok:true,});
	 }else if (ua.indexOf("msie 8") > -1){
	  	window.external.AddToFavoritesBar('${dynamicURL}/authority/loginInit.action','西宁市公共租赁住房信息管理系统管理');//IE8
	 }else if (document.all){
	  	window.external.addFavorite('${dynamicURL}/authority/loginInit.action','西宁市公共租赁住房信息管理系统管理');
	 }else{
	  	art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'添加失败，请用ctrl+D进行添加', ok:true,});
	 }
} 

function switchTab(page,title){
	$('#rightMain')[0].src=page;
	$('#here_area').html(title);
}

