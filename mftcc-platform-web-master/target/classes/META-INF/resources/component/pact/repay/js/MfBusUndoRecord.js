
var MfBusUndoRecord = function(window,$){
	var _init = function(){
		//初始化滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
	};
	
	var _initDetail = function(){
		//初始化滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
	}
   
	//申请保存方法
	var _insertUndoRecordApply = function(obj){	
		//提交还款撤销申请
		 alert(top.getMessage("CONFIRM_OPERATION_SERIOUS","还款撤销"),2,function(){//确定
			 saveApplyInfo(obj);
		  },function(){//取消
			  return ;
		  });
	};
	//保存申请信息
	var saveApplyInfo = function(obj){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url : url,
			data : {ajaxData : dataParam},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
				LoadingAnimate.start();
			},
			success : function(data) {
				if (data.flag == "success") {
					top.flag = true;
					window.top.alert(data.msg, 3);
					myclose_click();
				} else if (data.flag == "error") {
					window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			},complete:function(){
				LoadingAnimate.stop();
			}
		});
	};
	
	
	return{ 
		init:_init,
		initDetail:_initDetail,
		insertUndoRecordApply:_insertUndoRecordApply,
	};
	 
}(window,jQuery);