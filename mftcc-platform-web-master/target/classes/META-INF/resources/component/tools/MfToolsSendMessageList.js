;
var SendMessageList = function(window, $) {
	
	var _init = function () {
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:${webPath}+"/mfToolsSendMessage/findByPageAjax",//列表数据查询的url
	    	tableId:"tabletools0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	        pageSize:30,//加载默认行数(不填为系统默认行数) 
	        ownHeight : true,
		    callback:function(){
			}//方法执行完回调函数（取完数据做处理的时候）
	    });	
	};
   
   var _ajaxGetById = function (obj ,url){
	    top.addFlag = false;
		top.openBigForm(url,"短信内容", function() {
			myclose();
			if (top.addFlag) {
				window.location.reload();
			}
		});
   };      
        
   var _ajaxTrDeleteMessageList = function (obj,url, func_success){
        	var alertFlag = window.parent.window.$.myAlert;
        	if(alertFlag){
        		window.top.alert("是否确认删除!",2,function(){
        			jQuery.ajax({
        				url:url,
        				type:"POST",
        				dataType:"json",
        				beforeSend:function(){  
        				},success:function(data){
        					if(data.flag == "success"){
        						window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
        						window.location.reload();
        					}else if(data.flag == "error"){
        						alertFlag.Alert(data.msg);
        					}
        				},error:function(data){
        					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
        				}
        			});
        			$(obj).parents(".content_form").hide();
        		});
        	}else{
        		alert("是否确认删除!",2,function(){
        			jQuery.ajax({
        				url:url,
        				type:"POST",
        				dataType:"json",
        				beforeSend:function(){  
        				},success:function(data){
        					if(data.flag == "success"){
        						alert(top.getMessage("SUCCEED_OPERATION"),1);
        						window.location.reload();
        					}else if(data.flag == "error"){
        						$.myAlert.Alert(data.msg);
        					}
        				},error:function(data){
        					alert(top.getMessage("FAILED_OPERATION"," "),0);
        				}
        			});
        			$(obj).parents(".content_form").hide();
        		});
        	}
        };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxGetById : _ajaxGetById,
		ajaxTrDeleteMessageList :_ajaxTrDeleteMessageList
	};
}(window, jQuery);