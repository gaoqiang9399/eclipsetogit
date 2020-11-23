var getFormValue = function(obj,formId,actionUrl,returnUrl){
	var pare = $(obj).parents(".weui_panel[data-property='panel']");
	var nodes = pare.find(".moveNode");
	var jsonObject = {formId:formId};
	var formVal = {};
	var msgArr = [];
	$.each(nodes,function(i,node){
		switch($(node).data("property"))
		{
		case "btn":
			break;
		case "bd":
			break;
		case "panel":
			break;
		case "text":
			Checkform.text($(node),formVal,msgArr);
			break;
		case "textarea":
			Checkform.textarea($(node),formVal,msgArr);
			break;
		case "select":
			Checkform.select($(node),formVal,msgArr);
			break;
		case "radio":
			Checkform.radio($(node),formVal,msgArr);
			break;
		case "checkbox":
			Checkform.checkbox($(node),formVal,msgArr);
			break;
		case "picker":
			Checkform.picker($(node),formVal,msgArr);
			break;
		case "datepicker":
			Checkform.datepicker($(node),formVal,msgArr);
			break;
		case "timepicker":
			Checkform.timepicker($(node),formVal,msgArr);
			break;
		case "hidden":
			Checkform.hidden($(node),formVal,msgArr);
			break;
		default:
			break;
		}
	});
	if(msgArr.length>0){
		var msgs = msgArr.join("<br>");
		$.alert(msgs);
	}else{
		jsonObject.formVal = formVal; 
		console.log(jsonObject);
		$.ajax({
			url:actionUrl,
			data:{ajaxData:JSON.stringify(jsonObject)},
			type:"POST",
			dataType:"json",
			beforeSend:function(){
				$.showLoading("操作中...");
			},
			success:function(data){
				$.alert(data.msg,function(){
				//	window.top.location.href = data.url;
				});
			},
			error:function(data){
				$.alert("操作失败！");
			},
			complete:function(){
				$.hideLoading();
			}
		});
	}
};
var Checkform = {
		text : function(node,json,msgArr){
			var elem = node.find("input.weui_input");
			var mustinput = elem.attr("mustinput");
			var label = node.find("label.weui_label");
			if(mustinput=="1"&&isValEmpty(elem.val())){
				msgHandle(label.text(),msgArr,"不能为空!");
			}
			wxForm_valStringType(elem.get(0),elem.attr("datatype"),label.text(),msgArr);
			json[elem.attr("name")] = elem.val();
		},
		textarea:function(node,json,msgArr){
			var elem = node.find("textarea.weui_textarea");
			var mustinput = elem.attr("mustinput");
			var label = node.find(".weui_cells_title");
			if(mustinput=="1"&&isValEmpty(elem.val())){
				msgHandle(label.text(),msgArr,"不能为空!");
			}
			wxForm_valStringType(elem.get(0),elem.attr("datatype"),label.text(),msgArr);
			json[elem.attr("name")] = elem.val();
		},
		select:function(node,json,msgArr){
			var elem = node.find("select.weui_select");
			var mustinput = elem.attr("mustinput");
			var label = node.find("label.weui_label");
			if(mustinput=="1"&&isValEmpty(elem.val())){
				msgHandle(label.text(),msgArr,"不能为空!");
			}
			wxForm_valStringType(elem.get(0),elem.attr("datatype"),label.text(),msgArr);
			json[elem.attr("name")] = elem.val();
		},
		radio:function(node,json,msgArr){
			var elem = node.find("input.weui_input");
			var mustinput = elem.attr("mustinput");
			var label = node.find("label.weui_label");
			if(mustinput=="1"&&isValEmpty(elem.val())){
				msgHandle(label.text(),msgArr,"不能为空!");
			}
			wxForm_valStringType(elem.get(0),elem.attr("datatype"),label.text(),msgArr);
			json[elem.attr("name")] = elem.data("values");
		},
		checkbox:function(node,json,msgArr){
			var elem = node.find("input.weui_input");
			var mustinput = elem.attr("mustinput");
			var label = node.find("label.weui_label");
			if(mustinput=="1"&&isValEmpty(elem.val())){
				msgHandle(label.text(),msgArr,"不能为空!");
			}
			wxForm_valStringType(elem.get(0),elem.attr("datatype"),label.text(),msgArr);
			json[elem.attr("name")] = elem.data("values");
		},
		picker:function(node,json,msgArr){
			var elem = node.find("input.weui_input");
			var mustinput = elem.attr("mustinput");
			var label = node.find("label.weui_label");
			if(mustinput=="1"&&isValEmpty(elem.val())){
				msgHandle(label.text(),msgArr,"不能为空!");
			}
			wxForm_valStringType(elem.get(0),elem.attr("datatype"),label.text(),msgArr);
			json[elem.attr("name")] = elem.data("values");
		},
		datepicker:function(node,json,msgArr){
			var elem = node.find("input.weui_input");
			var mustinput = elem.attr("mustinput");
			var label = node.find("label.weui_label");
			if(mustinput=="1"&&isValEmpty(elem.val())){
				msgHandle(label.text(),msgArr,"不能为空!");
			}
			wxForm_valStringType(elem.get(0),elem.attr("datatype"),label.text(),msgArr);
			json[elem.attr("name")] = elem.val();
		},
		timepicker:function(node,json,msgArr){
			var elem = node.find("input.weui_input");
			var mustinput = elem.attr("mustinput");
			var label = node.find("label.weui_label");
			if(mustinput=="1"&&isValEmpty(elem.val())){
				msgHandle(label.text(),msgArr,"不能为空!");
			}
			wxForm_valStringType(elem.get(0),elem.attr("datatype"),label.text(),msgArr);
			json[elem.attr("name")] = elem.val();
		},
		hidden:function(node,json,msgArr){
			var elem = node;
			json[elem.attr("name")] = elem.val();
		}
};

function isValEmpty(str){
	var flag = false;
	str= wxForm_trim(str);
	if(typeof(str)=="undefined" || str == null || str == ""){
		flag = true;
	}
	return flag;
}

//去除首尾空格
function wxForm_trim(str){
	var result='';
	if(str!=null){
	result=str.replace(/^\s|\s$/g,'');
	}
	return result;
}

function msgHandle(label,msgArr,msg){
	msgArr.push("["+label.replace(/\*/g,"")+"]"+msg);
}


function wxForm_valStringType(obj,type,label,msgArr){
	var str=obj.value;
	var msg="";
	if(type!='undefined'&&wxForm_trim(type)!=''){
        var reg;
		if(type==1){
		reg=/^-?\d+$/;
			if(!reg.test(str)){
			msg='中的内容不是整数!\n';
		obj.value="0";
			}	
		}else if(type==2){
			reg=/^-?[1-9]\d*|0$/;
			if(!reg.test(str)){
			msg='中的内容不是长整数!\n';
		obj.value="0";
			}	
		}else if(type==3|| type==8 ||type==9||type==10||type==11){
			reg=/^-?([1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$/;
			if(!reg.test(str)){
				msg='中的内容不是数字!\n';
		obj.value="0";
			}
		}else if(type==4){
			reg=/^[1-9]\d*|[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$/;
			if(!reg.test(str)){
				msg='中的内容不是数字(正数)!\n';
		obj.value="0";
			}
		}else if(type==5){
			if(str.length==10 || str.length==8){
   			 	var yy=0,mm=0,dd=0;
        		if(str.length==8){
        			yy = parseInt(str.substring(0,4), 10);
	        		mm = parseInt(str.substring(4,6), 10);
	        		dd = parseInt(str.substring(6,8), 10);
        		}else {
        			yy = parseInt(str.substring(0,4), 10);
	        		mm = parseInt(str.substring(5,7), 10);
	        		dd = parseInt(str.substring(8,10), 10);
        		}
        		if(mm > 12 || mm <= 0 || dd <= 0 || dd > 31) 
        			msg='中的内容不是日期!\n';
       		 	var rndd = ((yy%4==0)&&(yy%100!=0)||(yy%400 == 0))?29:28;
        		switch(mm){	
         			 case 4:
          			case 6:
          			case 9:
          			case 11:
            		if(dd > 30 || dd<=0)
              			msg='中的内容不是日期!\n';
            			break;
          			case 1:
          			case 3:
          			case 5:
          			case 7:
          			case 8:
          			case 10:
          			case 12:
            		if(dd>31 || dd<=0)
                		msg='中的内容不是日期!\n';
            		break;
          			case 2:
            			if(dd > rndd || dd <= 0)
                		msg='中的内容不是日期!\n';
            		break;
       				}
				}else{
   					msg='中的内容不是日期!\n';
   				}
		}else if(type==7){
//			金额类型 0.00;1.00;1,000.00;1,000;1
			reg=/^([+-]?)((\d{1,3}(,\d{3})*)|(\d+))(\.\d*)?$/;
			if(!reg.test(str)){
				if(str==""){
					obj.value="0.00";
				}else{
					msg='中的内容不是金额类型!\n';
					obj.value="0.00";
				}
			}
		}
	}	
	if(msg!=""){
		msgArr.push("["+label.replace(/\*/g,"")+"]"+msg);
	}
}