var formHeight,bodyHeight;
$(function(){
	bodyHeight = $("body").height();
	formHeight = $(".content_form").height();
	$(".bigform_content").height(bodyHeight-20);
	$(".table_show").height(bodyHeight-110);
	$(".bigform_content,.table_show").mCustomScrollbar({
		advanced:{ 
			updateOnContentResize:true
		}
	});
});
window.ajaxInputFour = function(obj,url){
	var contentForm = $(obj).parents(".content_table").find(".content_form");
	var params = url.split("?");
	var ajaxUrl = params[0];
	var param = new Array();
	if(params[1]!==undefined){
		param = params[1].split("&");
	}
	contentForm.find("form").attr("action",ajaxUrl);
	var data = contentForm.find("form").serializeJSON(); 
	 $.each(data,function(name,value) {
	   	setFormEleValue(name, "",contentForm.find("form"));//调用公共js文件的方法表单赋值
  	});
  	if(param!=null&&param!==undefined){
  		$.each(param,function(index,value) {
  			var col = value.split("=");
  			setFormEleValue(col[0], col[1],contentForm.find("form"));
	 	});
  	}
	var height = $(".table_show").height() - formHeight-40;
	height = height>260?height:260;
	$(".table_show").animate({height:height+"px"});
	contentForm.slideDown();
}
window.colseBtnFour = function(obj){
	var height;
	if($(".table_show").height()=="260"){
		height = bodyHeight-110;
	}else{
		height = $(".table_show").height() + formHeight+40;
	}
	$(".table_show").animate({height:height+"px"});
	$(obj).parents(".content_form").slideUp();
}
window.ajaxSaveFour = function(obj,url){
	var contentForm = $(obj).parents(".content_table");
	var tableId = contentForm.find(".ls_list").attr("title");
	var flag = submitJsMethod(obj, '');
	if(flag){
		var ajaxUrl = $(obj).attr("action");
		if(ajaxUrl===undefined||ajaxUrl==null||ajaxUrl==""){
			ajaxUrl = url;
		}
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		jQuery.ajax({
			url:webPath+ajaxUrl,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					if(window.parent.window.$.myAlert){
						 window.parent.window.$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
						 window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
					}
					 if(data.tableData!=undefined&&data.tableData!=null){
						var tableHtml = $(data.tableData).find("tbody").html();
					 	contentForm.find(".ls_list tbody").html(tableHtml);
					 }
					 $(obj).removeAttr("action");
					 colseBtnFour(obj)
				}else if(data.flag=="error"){
					 //$.myAlert.Alert(data.msg);
					 alert(data.msg,0);
				}
			},error:function(data){
				 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
}
window.ajaxDeleteFour = function(obj,url){
	var dataParam = JSON.stringify($(obj).serializeArray());
	var contentForm = $(obj).parents(".content_table");
	var tableId = contentForm.find(".ls_list").attr("title");
	alert("是否确认删除!",2,function(){
		jQuery.ajax({
			url:webPath+url,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					  //$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					  alert(top.getMessage("SUCCEED_OPERATION"),1);
					  $.each(data,function(name,value) {
						   setFormEleValue(name, "");//调用公共js文件的方法表单赋值
					  });
					  if(data.tableData!=undefined&&data.tableData!=null){
						var tableHtml = $(data.tableData).html();
						contentForm.find(".ls_list").html(tableHtml);
					 }
					 $(obj).removeAttr("action");
					 colseBtnFour(obj);
				}
			},error:function(data){
				 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	});
	/*$.myAlert.Confirm("是否确认删除!","",function(){
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					  $.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					  $.each(data,function(name,value) {
						   setFormEleValue(name, "");//调用公共js文件的方法表单赋值
					  });
					  if(data.tableData!=undefined&&data.tableData!=null){
						var tableHtml = $(data.tableData).html();
						contentForm.find(".ls_list").html(tableHtml);
					 }
					 $(obj).removeAttr("action");
					 colseBtnFour(obj);
				}
			},error:function(data){
				 $.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
			}
		});
	});*/
}
window.ajaxGetByIdFour = function(obj,ajaxUrl){
	if(ajaxUrl!==undefined&&ajaxUrl!=null&&ajaxUrl!=""){
		jQuery.ajax({
			url:webPath+ajaxUrl,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$(obj).parents(".content_table").find(".content_form").find("form").attr("action","");
					var $content_from =  $(".content_table").find(".content_form");
					$content_from.find("table").html($(data.formHtml).html());
				    var height = $(".table_show").height() - formHeight-40;
					height = height>260?height:260;
					$(".table_show").animate({height:height+"px"});
					$content_from.slideDown();
				}else{
					 //$.myAlert.Alert(top.getMessage("ERROR_SELECT"));
					 alert(top.getMessage("ERROR_SELECT"),0);
				}
			},error:function(data){
				 //$.myAlert.Alert(top.getMessage("ERROR_SELECT"));
				 alert(top.getMessage("ERROR_SELECT"),0);
			}
		});
	}else{
		//$.myAlert.Alert("请检查列表链接");
		alert("请检查列表链接",0);
	}
}