;
var OAExpenseList = function(window, $) {
	var _init = function () {	
		 $(function(){
//			$(".scroll-content").mCustomScrollbar({
//				advanced : {
//					theme : "minimal-dark",
//					updateOnContentResize : true
//				}
//			});
			 /*
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			*/
			 _openList();
			 _expenseInsert();
			 });
	};	
    
	var _expenseInsert = function () {
		$("#expenseInsert").bind("click", function(event){
			top.addFlag  = false;
			top.window.openBigForm(webPath+'/mfOaExpense/inputForApply','新增报销申请',function() {
				myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});
		});
	};
	var _getById = function (obj,url) {
		top.addFlag  = false;
		top.window.openBigForm(url,'报销申请',function() {
			myclose();
			if (top.addFlag) {
				window.location.reload();
			}
		});
	};
 
	 var _approvalHis=function(obj,url){
	    	window.parent.openBigForm(url,"审批历史",function(){});
	    };
	 var _openList=function(){
		myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/mfOaExpense/findByPageAjax",//列表数据查询的url
	    	tableId:"tableexpense0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	        pageSize:30,//加载默认行数(不填为系统默认行数) 
		    callback:function(){
		    	top.LoadingAnimate.stop();
			}//方法执行完回调函数（取完数据做处理的时候）
	    });
	};	

   var _ajaxTrDeleteOaList = function (obj,url, func_success){
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
   var _startProcess = function(obj,url){
	   window.top.alert(top.getMessage("CONFIRM_OPERATION","提交"),2,function(){
		   jQuery.ajax({
  				url:url,
  				type:"POST",
  				dataType:"json",
  				beforeSend:function(){  
  				},success:function(data){
  					if(data.flag == "success"){
  						window.top.alert(data.msg,3);
  						window.location.reload();
  					}else if(data.flag == "error"){
  						window.top.alert(data.msg);
  					}
  				},
  				error:function(data){
  					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
  				}
  			});  
	   });
   };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxTrDeleteOaList:_ajaxTrDeleteOaList,
		getById:_getById,
		startProcess:_startProcess,
		approvalHis:_approvalHis,
	};
}(window, jQuery);