/**
 * datagrid 分页
 * @param data
 * @returns
 */
function pagerFilter(data){  
	if (data == null) return {rows : [], originalRows: [], total:0};//防止data为空, 抛出错误
     if (typeof data.length == 'number' && typeof data.splice == 'function'){    // is array  
        data = {  
             total: data.length,  
             rows: data  
         };  
     }  
     var dg = $(this);  
     var opts = dg.datagrid('options');  
     var pager = dg.datagrid('getPager');  
     pager.pagination({  
         onSelectPage:function(pageNum, pageSize){  
             opts.pageNumber = pageNum;  
             opts.pageSize = pageSize;  
             pager.pagination('refresh',{  
                 pageNumber:pageNum,  
                 pageSize:pageSize  
             });  
             dg.datagrid('loadData',data);  
         }  
     });  
     if (!data.originalRows){  
         data.originalRows = (data.rows);  
     }  
     var start = (opts.pageNumber-1)*parseInt(opts.pageSize);  
     var end = start + parseInt(opts.pageSize); 
     //防止rows为空做出判断
     if(data.originalRows == null){
    	 data.rows = new Array;
    	 data.originalRows = new Array;
     }else{
    	 data.rows = (data.originalRows.slice(start, end));  
     }
     
     return data;  
 }  

//各个input下面的验证方式,需要验证的请在input中添加required属性
function validateUnderInputByTd(myForm){
	var tds = myForm.find("td:has(input,textarea), th:has(input,textarea)");
	var flag = true;
	
	//验证非空
	$.each(tds, function(i, td){
		var inputs = $(td).find(":enabled[name].aids-required");
		var tdFlag = true;
		$.each(inputs, function(j,input){
			var inputValue = null;
			if("radio" == input.type){
				inputValue = $.trim($(":radio:checked[name=" + input.name + "]").val());
				if(inputValue == null || inputValue == ""){
					flag = false;
					tdFlag = false;	
				}
			}else if("text" == input.type){
				inputValue = $.trim(input.value);
				if(inputValue == null || inputValue == ""){
					flag = false;
					tdFlag = false;	
				}
			}else if("textarea" == input.type){
				inputValue = $.trim(input.value);
				if(inputValue == null || inputValue == ""){
					flag = false;
					tdFlag = false;	
				}
			}
		});
		if(tdFlag == true){
			$(td).find("span.required").remove();
		}else{
			var hasWrapperLength = $(td).has(".wrap").length;
			var text = $(td).prev().html();
			if(hasWrapperLength == 0){
				$(td).wrapInner(function(){
					return "<div class='wrap'/>";
				});
				$(td).append("<span class='required' style='color:red'>请填写: " +text + "</span>");
			}else{
				var spanRequiredLength = $(td).has("span.required").length;
				if(spanRequiredLength ==0 ){
					$(td).append("<span class='required' style='color:red'>请填写: " +text + "</span>");
				}
			}
		}
	});
	
	//验证数字
	$.each(tds, function(i, td){
		var inputs = $(td).find(":enabled.aids-number");
		var tdFlag = true;
		$.each(inputs, function(j,input){
			var inputValue = null;
			if("text" == input.type){
				inputValue = $.trim(input.value);
				if(isNaN(inputValue)){
					tdFlag = false;
					flag = false;
				}			
			}
		});
		if(tdFlag == true){	
			$(td).find("span.number").remove();
		}else{
			var hasWrapperLength = $(td).has(".wrap").length;
			var text = $(td).prev().html();
			if(hasWrapperLength == 0){
				$(td).wrapInner(function(){
					return "<div class='wrap'/>";
				});
				$(td).append("<span class='number' style='color:red'>填写数字:" +text + "</span>");
			}else{
				var spanNumberLength = $(td).has("span.number").length;
				if(spanNumberLength == 0){
					$(td).append("<span class='number' style='color:red'>填写数字:" +text + "</span>");
				}
			}
		}
	});
	$.each(tds, function(i, td){
		var isLegal = $(td).find("span.required,span.number");
		if(isLegal.length == 0){
			var hasWrapperLength = $(td).has(".wrap").length;
			if(hasWrapperLength > 0){
				$(td).children(":first-child").children().unwrap();
			}
		}
	});
	if(flag == false){
		msgShow("验证失败","一些关键字段未填写, 无法提交");
	}
	return flag;
}
/***
 * 引入一个js到当前js
 * (function(oWin) {loadJS({src:'test.js',charset:'utf-8'});})(window);
 */
var loadJS = function (params) {    
	var head = document.getElementsByTagName("head")[0];
	var script = document.createElement('script');
	script.charset = params.charset || document.charset || document.characterSet || 'utf-8';
	script.src = params.src;
	try {
		head.appendChild(script);
	} catch (exp) {
	}
};
/**
 *给页面所有的文本框加校验（可自行扩展判断）
 * 
 */
function addValidate(){
	var tag = document.getElementsByTagName('input');
	for (var i = 0; i < tag.length; ++i) {
		if (tag[i].type == "text") {
			$(tag[i]).addClass('easyui-validatebox');
			$(tag[i]).validatebox({    
				required: true,
				missingMessage:'此项不能为空。'
			}); 
		}
	}
}
function clearForm(objE){//清空表单
	//objE为form表单    
    $(objE).find(':input').each(    
        function(){    
            switch(this.type){    
               case 'passsword':    
                case 'select-multiple':    
                case 'select-one':
                case 'text': 
                	$(this).val('');
                case 'textarea':    
                    $(this).val('');    
                   break;  
                case 'hidden':    
                    $(this).val('');    
                   break;    
                case 'checkbox':    
                case 'radio':    
                    this.checked = false;    
            }    
        }       
    );    
}
/**
 * 根据json的数据自动填充form表单
 * @param form
 * @param json
 */
function autoFillin(form, json){
	clearForm(form);
	var hiddens = form.find("input[type=hidden]");
	var texts = form.find(":text");
	var radios = form.find(":radio");
	var checkboxs = form.find(":checkbox");
	var textareas = form.find("textarea[name]");
	var selects = form.find("select[name]");
	//自动填充表格内容
	$.each(hiddens, function(i, n){
		$(n).val(json[n.name]) ;
	});

	$.each(texts, function(i, n){
		$(n).val(json[n.name]) ;
	});
	$.each(radios, function(i, n){
		var value = json[n.name];
		if (value == true || value == "true"){
			value = "1";
		}else if(value == false || value == "false"){
			value = "0";
		}
		if(n.value == value){
			$(n).prop("checked", true);
		}
	});
	$.each(textareas, function(i, n){
		$(n).val(json[n.name]) ;
	});
	$.each(checkboxs, function(i, n){
		var values = json[n.name];
		try{
			if(values.length > 0){
				var array = json[n.name].split(",");
				if($.inArray(n.value, array) > -1){
					$(n).prop("checked", "checked");
				}
			}
		}catch(e){
			//alert('错误 : ' + e.message + ' 发生在: ' + e.lineNumber);
		}
	});
	$.each(selects, function(i, n){
		var value = json[n.name];
		if(value == true){
			value = "true";
		}else if(value == false){
			value = "false";
		}
		$.each($(n).find("option"), function(i,option ){
			if(option.value == value){
				$(option).prop("selected", value);
			}
		});

	});
}


/**
 * 时间对象的格式化;
 */
Date.prototype.format = function(format,now) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */

    var d = now ? (new Date(Date.parse(now.replace(/-/g,   "/")))) : this;
    var o = {
        "M+" : d.getMonth() + 1, // month
        "d+" : d.getDate(), // day
        "h+" : d.getHours(), // hour
        "m+" : d.getMinutes(), // minute
        "s+" : d.getSeconds(), // second
        "q+" : Math.floor((d.getMonth() + 3) / 3), // quarter
         "N+" : ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"][d.getDay()],
        "S" : d.getMilliseconds()// millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (d.getFullYear() + "").substr(4
                        - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                            ? o[k]
                            : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};
/**
 * 时间对象的格式化;
 */
Date.prototype.Format = function() {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	var format = 'yyyy-MM-dd';
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"N+" : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ][this.getDay()],
		"S" : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
/**
 * 
 * @param title
 * @param msg
 * @param position
 */
function msgShow(title,msg){
	if(null == title){
		title='提示';
	}

	$.omMessageTip.show({
		title:title,
		content:msg,
		timeout:5000
	});
	return;
}

/**
 * 数据库端datagrid删除记录
 * @param o easy-ui datagrid 中的删除按钮元素
 * @param tableId easy-ui datagrid 中的tableId
 * @param 删除记录的请求url
 * 
 */
function deleteRow(button, tableId ,url){
	if(confirm("是否将此删除?")== true){
		var tr = button.parentElement.parentElement.parentElement;
		var id = tr.firstChild.firstChild.innerHTML;
		$.ajax({
			url: url,
			type:"post",
			async: true,
			cache: false,
			data: {"id": id},
			success: function(msg){
				$.omMessageTip.show({title : "message",content : msg,timeout : 3500,type : "alert"});
				$('#' + tableId).datagrid("reload");
			},
			error:function(error){
				alert(error);
			}
		});
		return true;
	}else {	
		return false;
	};
}

/**
 * 浏览器端datagrid删除记录
 * @param button easy-ui datagrid 中的删除按钮元素
 * @param tableId easy-ui datagrid 中的tableId
 * 
 */
function deleteRowClientSide(button, tableId){
	var c =confirm("是否将此项删除?");
	if(c){
		var tr = button.parentElement.parentElement.parentElement;
		var index = $(tr).index();
		$('#' + tableId).datagrid("deleteRow",index);
	}
}
/**
 * 普通药品处方下拉选择药品列表渲染
 * @param id 页面元素id
 * @param valueFieldFunction 定义omCombo的值得函数, 不传则为默认
 */
function generateMedicine(){
	var id = arguments[0];
	var valueFieldFunction = arguments[1];
	 $('#'+ id).omCombo({
	 	dataSource:contextPath+'/pages/system/getMedListJson.do',
        //让下拉框宽度与输入框宽度可以不一样
        listAutoWidth:true,
        //让下拉框不出现垂直滚动条，有多高就显示多高
        listMaxHeight:'300',
        width:'400',
        //选择记录后回填时把记录的name值回填到输入框里
        inputField : function(data,index){
     	   return data.medName+'(' + data.medCode+')';
    	},        
        //选择记录后回填时虽然输入框里显示的是name值，但是实际上getValue时得到的是shortName值
        valueField : valueFieldFunction == null? function(data,index){
        	   return data.id+'&'+ data.medCode+'&' + data.dose+'&'+data.doseUnit+'&'+data.smallUnit;
        } : valueFieldFunction,
        //所有记录按下面的方法的算法渲染到下拉框里面
        listProvider : function(container,records){        	
            var html='<table cellpadding="3" cellspacing="0" class="easyui-datagrid">'
                        +'<thead><tr><th width="100">拼音代码</th><th width="100">药品代码</th><th width="150">药品名称</th><th>单价</th><th>单位</th><th width="80">每次用量</th><th width="80">用量单位</th><th>规格</th></tr></thead>'
                        +'<tbody>';
            $(records).each(function(){
            	html += '<tr><td>' + this.PYCode + '</td><td>'
                + this.medCode + '</td><td>'
                + this.medName + '</td><td>'
                + this.retPrice + '</td><td>'
                + this.smallUnit + '</td><td>'
                + this.dose + '</td><td>'
                + this.doseUnit + '</td><td>'
                + this.standard + '</td></tr>';
            });
            html +=      '</tbody>'
                     +'</table>';
            $(html).appendTo(container);
            container.addClass("combZindex");
            //只有tbody里的tr可以选择和高亮，其它的都不可以
            return container.find('tbody>tr');
        },
        //输入框里输入任意字符时用下面的方法进行过滤
        filterStrategy:function(text1,record){
            //只要type或name或shortName中包含输入的字符串就可以。（比较时忽略大小写）
            var text=text1.toLowerCase();
            return record.PYCode.toLowerCase().indexOf(text)>-1 || record.medCode.toLowerCase().indexOf(text)>-1 || record.medName.toLowerCase().indexOf(text)>-1;
        },
        //选择任意记录后在输入框右边打印出选择的值（注意是valueField表示的那个shortName属性的值，而不是输入框里显示出来的inputField的值）
        onValueChange:function(target, newValue, oldValue,event){
        	var value = newValue.split("&");
        	$('#medId').val(value[0]);
            $('#medCode').val(value[1]);
            $('#dose').val(value[2]);
            $('#doseUnit').val(value[3]);
            $('#smallUnit').val(value[4]);
        }
    });
}

/**
 * 增加tab
 * @param title tab的名字
 * @param href tab的url链接
 * @param icon 图片
 * @param id tab的Id 
 */
function addTab(title, href, icon, id) {
	var content;
	if(id == undefined ){
		id = "tabs" ;
	}
	var tt = $('#' + id);
	if (tt.tabs('exists', title)) {//如果tab已经存在,则选中并刷新该tab          
		tt.tabs('select', title);
		refreshTab({
			tabTitle : title,
			url : href,
			id : id
		});
	} else {
		if (href) {
			content = '<iframe scrolling="yes" frameborder="0" src="' + href + '" style="width:100%;height:100%;" ></iframe>';
		} else {
			content = '未实现';
		}
		tt.tabs('add', {
			title : title,
			closable : true,
			content : content,
			iconCls : icon || 'icon-default'
		});
		return tt.tabs("getTab", title);
	}
}
/**     
 * 刷新tab 
 * @cfg  
 *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
 *如果tabTitle为空，则默认刷新当前选中的tab 
 *如果url为空，则默认以原来的url进行reload 
 */
function refreshTab(cfg) {
	var refresh_tab = cfg.tabTitle ? $('#' + cfg.id).tabs('getTab', cfg.tabTitle) : $('#' + cfg.id).tabs('getSelected');
	if (refresh_tab && refresh_tab.find('iframe').length > 0) {
		var _refresh_ifram = refresh_tab.find('iframe')[0];
		var refresh_url = cfg.url ? cfg.url : _refresh_ifram.src; 
		_refresh_ifram.contentWindow.location.href = refresh_url;
	}
}
/**
 *采用jquery easyui 添加遮罩css效果
 */
function ajaxLoading(){
	var zindex = $("#mzcz").parent().css("z-index");
	var body = $("body");
	$("<div class='datagrid-mask'></div>").css({display:"block",width:"100%",height:body.height()}).css("z-index", parseInt(zindex)+1).appendTo(body);
	$("<div class='datagrid-mask-msg'></div>").html("正在处理，请稍候......").appendTo(body)
	.css("z-index", parseInt(zindex)+1)
	.css("top", " top: 150px;")
	.css("position","fixed")
	.css({display:"block",left:($(document.body).outerWidth(true) - 190)/2});
}
/**
 * 取消遮罩效果
 */
function ajaxLoadEnd(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}
//定义简单Map
function getMap() {//初始化map_,给map_对象增加方法，使map_像Map  
         var map_ = new Object();  
         map_.put = function(key, value) {  
             map_[key+'_'] = value;  
         };  
         map_.get = function(key) {  
             return map_[key+'_'];  
         };  
         map_.remove = function(key) {  
             delete map_[key+'_'];  
         };  
         map_.keyset = function() {  
             var ret = "";  
             for(var p in map_) {  
                 if(typeof p == 'string' && p.substring(p.length-1) == "_") {  
                     ret += ",";  
                     ret += p.substring(0,p.length-1);  
                 }  
             }  
             if(ret == "") {  
                 return ret.split(",");  
             } else {  
                 return ret.substring(1).split(",");  
             }  
         };  
         return map_;  
} 
