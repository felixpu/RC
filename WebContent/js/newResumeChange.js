var v = setInterval('readTxt()', 2000);
var t = null;
$(document).ready(function(){
	var remobile = /^1\d{10}$/;
	var remail = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
	$('#mobile').blur(function(){
		var f = pd($('#mobile').val(),$('#email').val(),remobile,remail);
		isError(f,$('#mobile'));
	});
	
	$('#email').blur(function(){
		var f = pd($('#mobile').val(),$('#email').val(),remobile,remail);
		isError(f,$('#email'));
	});
	$('#bugtxt').click(function(){
			writeTxt();
	});
	
	$('#inputbugtxt').click(function(){
		var val = $('#inputbugtxt').val();
		if(val.indexOf('write here')>-1){
			$('#inputbugtxt').val('');
		};
	});
	
	$('#inputbugtxt').blur(function(){
		var val = $('#inputbugtxt').val();
		if(!$.trim(val)){
			$('#inputbugtxt').val('write here...');
		};
	});
	$('.zzsc .close').click(function(){
		msgclose();
	});
//	$('#rc').submit(function() {
//		$(this).omAjaxSubmit(options);
//		return false;// 返回false,阻止浏览器默认行为
//	});
	
//	$('#rc').bind('submit', function(){
//        ajaxSubmit(this);
//        return false;
//    });
	readTxt();
});
var options = {
		beforeSubmit : showRequest,
		success : showResponse
};
//表单请求前
function showRequest(formData, jqForm, options) {
}
//表单响应后
function showResponse(responseText, statusText, xhr, $form) {
	msgshow(responseText);
}

//组合两个对话框
function pd(val1,val2,re1,re2){
	if(vix(val1, re1)== false || vix(val2, re2)==false){
		$('#ok').attr('disabled','disabled');
		$('#ok').addClass('okdisable');
		return false;
	}else{
		$('#ok').removeAttr('disabled');
		$('#ok').removeClass('okdisable');
		return true;
	}
}
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
function writeTxt(){
	var txt = $.trim($('#inputbugtxt').val());
	if(!txt || txt.indexOf('write here')>-1){
		msgshow('请有效输入！');
		return;
	}
	
	$.ajax({
		url : contextPath + '/pages/system/saveTxt.do',
		type : 'POST',
		data : { 'txt' : $.trim(txt) },
		dataType : 'json',
		success : function(val) {
			if(val){
				readTxt();
				$('#inputbugtxt').val('write here...');
			}
		}
	});
}
function readTxt(){
	$.ajax({
		url : contextPath + '/pages/system/getTxt.do',
		type : 'POST',
		//dataType : 'json',
		success : function(val) {
			$('#txtArea').html(val);
		}
	});
	
}
function isError(f,obj){
	if(!f && obj.val()!=''){
		obj[0].style.color='red';
		obj[0].style.borderColor='red';
	}else{
		obj[0].style.color='#FFF';
		obj[0].style.borderColor='#737F75';
	}
	
}
function msgclose(){
	$('.zzsc').animate({right:'-216px'},"fast");
	$('#msg').text('');
}
function msgshow(val){
	$('.zzsc').animate({right:'10px'});
	$('#msg').text(val);
	//setTimeout(msgclose, 3000);
}

function ajaxFileUpload(){
	var name = encodeStr($.trim($('#name').val()));
	var mobile = $.trim($('#mobile').val());
	var email = $.trim($('#email').val());
	var position = encodeStr($.trim($('#position').val()));
	
	var filepath = $('#filepath').val();
	if(!filepath){
		msgshow('没有选择文件。');
		return;
	}
    //执行上传文件操作的函数
    $.ajaxFileUpload({

        //处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
    	url: contextPath+'/pages/system/resumeChange.do?name='+name+'&mobile='+mobile+'&email='+email+'&position='+position,
    	type:'POST',
    	secureuri:false,                       //是否启用安全提交,默认为false
        fileElementId:'htmfile',           //文件选择框的id属性
        //data:{'name' : name,'mobile':mobile ,'email':email ,'position':position },
     //   data:'name='+name+'&mobile='+mobile+'&email='+email+'&position='+position,
        dataType:'text',
        success:function(data, status){
        	msgshow(data);
        	$('#filepath').val('');
        },
        error:function(data, status, e){ //服务器响应失败时的处理函数
            $('#result').html('失败，请重试！！');
        }
    });
}
function uniencode(text)
{
    text = escape(text.toString()).replace(/\+/g, "%2B");
    var matches = text.match(/(%([0-9A-F]{2}))/gi);
    if (matches)
    {
        for (var matchid = 0; matchid < matches.length; matchid++)
        {
            var code = matches[matchid].substring(1,3);
            if (parseInt(code, 16) >= 128)
            {
                text = text.replace(matches[matchid], '%u00' + code);
            }
        }
    }
    text = text.replace('%25', '%u0025');
 
    return text;
}
function encodeStr(str){
	 return encodeURI(encodeURI(str));
}