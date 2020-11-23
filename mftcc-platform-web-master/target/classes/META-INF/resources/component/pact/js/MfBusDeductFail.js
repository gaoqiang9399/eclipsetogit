	function getAppDetailPage(obj,url){	
//		url = url.split("?")[1].split("&");	
//	    var appId = url[1].split("=")[1];
//    	var fincId = url[0].split("=")[1];
//    	var busEntrance = "3";
//    	url = "MfBusPact/getPactFincById?fincId="+ fincId +"&appId="+ appId +"&busEntrance="+ busEntrance +"&subStringNub=";
		top.openBigForm(url,"项目详情", function(){
	 	});				
	};
	function getDetailPage(obj,url){		
		top.openBigForm(url,"客户详情", function(){
	 	});			
	};
;
var MfBusDeductFail = function(window, $) {
	//划扣操作按钮
	var _ajaxDeduct = function(obj,url){
		alert("是否确认进行划扣？",2,function(){
			jQuery.ajax({
				url:url,
//				data:{ajaxData:dataParam,tableId:tableId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					 alert(data.msg,2);
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"),0);
				}
			});
		});
	};
	//确认已还按钮  假删除操作
	var _ajaxRepayment=function(obj,url,callback){
		var contentForm = $(obj).parents(".content_table");
		var tableId = contentForm.find(".ls_list").attr("title");
		alert("确认已经还款了吗？",2,function(){
			jQuery.ajax({
				url:url,
//				data:{ajaxData:dataParam,tableId:tableId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						 alert(top.getMessage("SUCCEED_OPERATION"),1);
						 $(obj).parents("tr").hide();
//						 contentForm.find(".content_form").hide();
						 if(typeof(callback)=="function"){
							 callback.call(this);
						 }
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"),0);
				}
			});
		});
	};
	return {
		ajaxDeduct:_ajaxDeduct,
		ajaxRepayment:_ajaxRepayment
	};
}(window, jQuery);