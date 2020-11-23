//双击修改公共方法
$(function(){
	//json 更新方法
	var ajaxUpdateOne = function($obj,jsonData){
		var formId = $obj.parents(".changeval").attr("formId");
		var flag = false;
		/*var msg = "";
		$.each(jsonData, function(key,val) {
			msg = key+","+val;
		});*/
		if(isExitsFunction(submitBtnOne)){
			flag = submitBtnOne(formId,jsonData);
		}else{
			alert("页面吧存在相应的提交方法（submitBtnOne）");
		}
		return flag;
	}
	//追加显示框
	var addChangevalDiv = function($event,name,fieldVal){
		var $obj = $event.find(".fieldReal").children();
		var formId = $event.parents("form").attr("id");
		if($obj.html()!==undefined){
			var inputType = $obj[0].type;
			var inputHtml = "";
			if(inputType=="text"||inputType=="textarea"){//文本 文本域
				inputHtml+='<input class="inputText" type="text" maxlength="20" value="'+fieldVal+'"/>'
			}else if(inputType=="checkbox"){//复选
				$obj.each(function() {
					var value = $(this).val();
					var textVal = $(this).attr("dataValue");
					inputHtml+='<input type="checkbox" name="'+name+'" value="'+value+'" datavalue="'+textVal+'"/><span>'+textVal+'</span>&nbsp;';
				});
			}else if(inputType=="radio"){//单选
				$obj.each(function() {
					var value = $(this).val();
					var textVal = $(this).attr("dataValue");
					inputHtml+='<input type="radio" name="'+name+'" value="'+value+'" datavalue="'+textVal+'"/><span>'+textVal+'</span>&nbsp;';
				});
			}else if(inputType=="select-one"){
				inputHtml+='<select name="'+name+'">';
				inputHtml+= $obj.html();
				inputHtml+='</select >';
			}
			var changevalDivHtml = '<div class="changeval" dataname="'+name+'" formId="'+formId+'"  style="display: none;">';
				changevalDivHtml += '<span class="inputcontent">';
				changevalDivHtml += inputHtml;
				changevalDivHtml += '</span>';
				changevalDivHtml += '<i class="icon-ok ok"></i>';
				changevalDivHtml += '<i class="icon-remove no"></i>';
				changevalDivHtml += '</div>';
		    $("body").append(changevalDivHtml);
		}else{
			//alert("数据字典项有误，请检查!");
		}
	}
	//事件绑定
	$(".changed").bind("dblclick",function(){
	//function changedDbClick(event){
		var name = $(this).find(".fieldReal").children().eq(0).attr("name");
		var fieldVal = $(this).find(".fieldShow").text().trim(); 
		if(name===undefined){
			//name = $(this).find(".fieldReal").children().children().eq(0).attr("name");
		}
		if($('.changeval[dataname="'+name+'"]').html()===undefined){
			 addChangevalDiv($(this),name,fieldVal);
		}
		if(name!==undefined){
			var $div = $('.changeval[dataname="'+name+'"]');
			$div.show();
			var offset = $(this).offset();
			$div.css({
				position: "absolute",
				top:offset.top-5,
				left:offset.left-2,
				//width:$(this).width()+57,
				height:$(this).height()+3
			});
		 	$div.unbind();
		 	$div.find("input").bind("keydown",function(){
		
			 });
		 	$div.find(".ok").bind("click",function(){
				var dataname = $(this).parents(".changeval").attr("dataname");
				var dataVal = "";
				var htmlTestVal = "";
				var $contentObj =$(this).parent().find(".inputcontent");
				var inputType = $contentObj.children()[0].type;
                var $radioObj;
				if(inputType=="text"||inputType=="textarea"){//文本 文本域
					dataVal = $contentObj.children().eq(0).val().trim();
					htmlTestVal = dataVal;
				}else if(inputType=="checkbox"){//复选
					$radioObj = $contentObj.find("input[type='checkbox']");
					var flag = 0;
					$radioObj.each(function(i,e){
						if($(e).is(':checked')){
							if(flag++!=0){
								dataVal+=",";
								htmlTestVal+=",";
							}
							dataVal += $(e).val().trim();
							htmlTestVal += $(e).attr("datavalue").trim();
						}
					});
				}else if(inputType=="radio"){//单选
					$radioObj = $contentObj.find("input[type='radio']");
					$radioObj.each(function(i,e){
						if($(e).is(':checked')){
							dataVal += $(e).val().trim();
							htmlTestVal += $(e).attr("datavalue").trim();
						}
					});
				}else if(inputType=="select-one"){
					htmlTestVal = $contentObj.children().eq(0).find("option:selected").text();
					dataVal = $contentObj.children().eq(0).val();
				}
				var jsonData = {};
				jsonData[dataname] = dataVal;
				if(ajaxUpdateOne($(this),jsonData)){
					$('[name="'+dataname+'"]').parents(".font-smallup").find(".fieldShow").text(htmlTestVal);
					$(this).parents(".changeval").remove();
				}
		 	});
		 	$div.find(".no").bind("click",function(){
				console.log($(this).parents(".changeval").attr("dataname"));
				$(this).parents(".changeval").remove();
		 	});
		}else{
			alert("文档结构错误，请检查!");
		}
	});
	//是否存在指定函数 
	function isExitsFunction(funcName) {
	    try {
	        if (typeof(eval(funcName)) == "function") {
	            return true;
	        }
	    } catch(e) {}
	    return false;
	}
	//是否存在指定变量 
	function isExitsVariable(variableName) {
	    try {
	        if (typeof(variableName) == "undefined") {
	            //alert("value is undefined"); 
	            return false;
	        } else {
	            //alert("value is true"); 
	            return true;
	        }
	    } catch(e) {}
	    return false;
	}
});