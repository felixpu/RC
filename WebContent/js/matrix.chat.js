var v = setInterval('readTxt()', 1000);
$(document).ready(function(){
	//var msg_template = '<p><span class="msg-block"><strong></strong><span class="time"></span><span class="msg"></span></span></p>';
	
	$('.chat-message button').click(function(){
		var input = $(this).siblings('span').children('input[type=text]');		
		if(input.val() != ''){
			add_message(nowuser,input.val(),true);
		}		
	});
	
	$('.chat-message input').keypress(function(e){
		if(e.which == 13) {	
			if($(this).val() != ''){
				add_message(nowuser,$(this).val(),true);
			}		
		}
	});
	
   	var i = 0;
	function add_message(name,msg,clear) {
		var img = 'img/newman.png';
		i = i + 1;
		var  inner = $('#chat-messages-inner');
		var time = new Date();
		var hours = time.getHours();
		var minutes = time.getMinutes();
		if(hours < 10) hours = '0' + hours;
		if(minutes < 10) minutes = '0' + minutes;
		var id = 'msg-'+i;
        var idname = name.replace(' ','-').toLowerCase();
        var p = '<p id="'+id+'" class="user-'+idname+'">'
		+'<span class="msg-block"><img src="'+img+'" alt="" /><strong>'+name+'</strong> <span class="time"> '+hours+':'+minutes+'</span>'
		+'<span class="msg">'+msg+'</span></span></p>';
		inner.append(p);
		$('#'+id).hide().fadeIn(800);
		if(clear) {
			$('.chat-message input').val('').focus();
		}
		$('#chat-messages').animate({ scrollTop: inner.height() },1000);
		writeTxt(p);
	}
    function remove_user(userid,name) {
        i = i + 1;
        $('.contact-list li#user-'+userid).addClass('offline').delay(1000).slideUp(800,function(){
            $(this).remove();
        });
        var  inner = $('#chat-messages-inner');
        var id = 'msg-'+i;
        inner.append('<p class="offline" id="'+id+'"><span>User '+name+' left the chat</span></p>');
        $('#'+id).hide().fadeIn(800);
    }
    readTxt();
    
    $(".chat-messages").mouseover(function(){
    	clearInterval(v);
    });//鼠标在上
    $(".chat-messages").mouseout(function(){
    	v = setInterval('readTxt()', 1000);
    });//鼠标移开
});

function writeTxt(p){
	up = encodeURI(encodeURI(p));
	$.ajax({
		url : contextPath + "/pages/system/saveTxt.do?txt="+up,
		type : "POST",
		dataType : "json",
		success : function(val) {
			if(val){
				readTxt();
			}
		}
	});
}
function readTxt(){
	$.ajax({
		url : contextPath + "/pages/system/getTxt.do",
		type : "POST",
		success : function(val) {
			$('#chat-messages-inner').html(val);
		}
	});
	scrollTop();
}
function scrollTop(){
	var  inner = $('#chat-messages');
	inner.scrollTop(inner[0].scrollHeight);
}