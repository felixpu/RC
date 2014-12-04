$(document).ready(function(){
	initInputFile();
	var remobile = /^1\d{10}$/;
	var remail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	var positon = /[\/:*?"<>|]/;
	$("#name").textbox({
		width:'80%',
		height:'40px'
	});
	
	$("#mobile").textbox({
		width:'80%',
		height:'40px'
	});
	
	$("#email").textbox({
		width:'80%',
		height:'40px'
	});
	
	$("#position").textbox({
		width:'80%',
		height:'40px'
	});
	
	$("#bugtxt").textbox({
		onClickButton:function(){
			writeTxt();
		}
	});
	$('#ok').bind('click',function(){
		var file = $('#htmfile').val();
		if(file==''){
			messageShow('没有选择文件！');
			return;
		}
		if(!vix($("#mobile").val(),remobile)){
			messageShow('请正确填写手机号码！');
			return;
		}
		if(!vix($("#email").val(),remail)){
			messageShow('请正确填写邮箱！');
			return;
		}
		if(!vixp($("#position").val(),positon)){
			messageShow('职位中有非法字符！');
			return;
		}
		
		$('#form1').omAjaxSubmit({
			url:contextPath + "/pages/system/resumeChange.do",
			success:function(data){
				messageShow(data);
			}
		});
		return false;
	});
	
	$('#htmfile').live('change', function(){
		$('#htmfile').wrap("<form id='ajaxupload' method='post' enctype='multipart/form-data'></form>");
		var form = document.getElementById("ajaxupload");
	    var oData = new FormData(form);
	    var oReq = new XMLHttpRequest();
	    var url=contextPath + "/pages/system/getInfo.do";
	    oReq.open("POST", url);
	    oReq.onload = function(oEvent) {
	    if (oReq.status == 200 && oReq.readyState==4) {
	    	var respones = oReq.responseText;
	    	if(respones.indexOf('非简历文件') > -1){
	    		messageShow(respones);
	    	}else{
	    		var infos =respones.split('&');
	    		$("#name").textbox('setValue',infos[0]);
	    		$("#mobile").textbox('setValue',infos[1]);
	    		$("#email").textbox('setValue',infos[2]);
	    	}
	    	$('#htmfile').unwrap();
	    } else {
	    	$('#htmfile').unwrap();
	    }
	  };

	  oReq.send(oData);
	});
});

//组合两个对话框
//判断一个对话框
function vix(val,recx){
	var flag = true;
	if(val){
		if(!recx.test(val)){
			flag = false;
		}
	}
	return flag;
}
function vixp(val,recx){
	var flag = true;
	if(val){
		if(recx.test(val)){
			flag = false;
		}
	}
	return flag;
}
function messageShow(str){
	$.messager.show({
		title:'提示',
		msg:str,
		timeout:5000,
		showType:'show'
	});
}
function initInputFile(){
	$(":input[name=mfile]").attr('id','htmfile');
	$(":input[name=mfile]").attr('accept','text/html');
}