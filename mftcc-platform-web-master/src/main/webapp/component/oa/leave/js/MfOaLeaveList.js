var OaLeaveList = function(window, $) {
	var _init = function () {
		  myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfOaLeave/findByPageAjax?opNo=" + opNo, //列表数据查询的url
				tableId : "tableoaleave0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
		    });
	};
	/**
	 * 新增请假单
	 */
	var _leaveInsert = function () {
		top.window.openBigForm(webPath+'/mfOaLeave/input','新增请假单',function() {
			updateTableData();
		});
	}; 
	// 催办
   var _leavecuiban = function(obj,url){
		jQuery.ajax({
			url:webPath+url,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					window.top.alert(data.msg,1);
					updateTableData();
				}else if(data.flag == "error"){
					$.myAlert.Alert(data.msg);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});		   
   };
   // 重新提交
   var _leavetijiao = function(obj,url){
		   alert(top.getMessage("CONFIRM_OPERATION","重新申请"),2,function(){
	   			jQuery.ajax({
	   				url:webPath+url,
	   				type:"POST",
	   				dataType:"json",
	   				beforeSend:function(){  
	   				},success:function(data){
	   					if(data.flag == "success"){
	   						window.top.alert(data.msg,3);
	   						updateTableData();
	   					}else if(data.flag == "error"){
	   						$.myAlert.Alert(data.msg);
	   						updateTableData();
	   					}
	   				},error:function(data){
	   					alert(top.getMessage("FAILED_OPERATION"," "),0);
	   					updateTableData();
	   				}
	   			});
		   });
   };
   // 撤回
   var _leavedelete = function(obj,url){
		   alert(top.getMessage("CONFIRM_OPERATION","撤销申请"),2,function(){
	   			jQuery.ajax({
	   				url:webPath+url,
	   				type:"POST",
	   				dataType:"json",
	   				beforeSend:function(){  
	   				},success:function(data){
	   					if(data.flag == "success"){
	   						window.top.alert(data.msg,1);
	   						updateTableData();
	   					}else if(data.flag == "error"){
	   						$.myAlert.Alert(data.msg);
	   					}
	   				},error:function(data){
	   					alert(top.getMessage("FAILED_OPERATION"," "),0);
	   				}
	   			});
		   });
   };
   // 详情
  var _ajaxGetById = function (obj,url){
		   top.openBigForm(webPath+url+"&entryFlag=1","请假详情", function() {
			});
   };  
   // 打印审批表
   var _printApproveTable = function(obj,url){
	   var oaAppId = url.split("?")[1].split("=")[1];
		var poCntJson = {
				filePath : "",
				fileName : fileName,
				oaAppId : oaAppId,
				saveBtn : "0",
				fileType : 0
			};
		mfPageOffice.openPageOffice(poCntJson);
	};
return {
	init : _init,
	printApproveTable:_printApproveTable,
	leaveInsert:_leaveInsert,
	leavetijiao:_leavetijiao,
	leavedelete:_leavedelete,
	leavecuiban:_leavecuiban,
	ajaxGetById:_ajaxGetById
	
};
}(window, jQuery);


