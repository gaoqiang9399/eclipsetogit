;
var OaLeave = function(window, $) {
	var _init = function () {	
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_bindClose();
		_bindInsertAjax("#OaLeaveApply");
	};
	
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
		
			myclose();
		});
	}; 
    
	var _bindInsertAjax = function(obj){		
		$(".updateAjax").bind("click", function(event){			
			var leaveSts="0";
			var startTime=$("input[name='startTime']").val();
		    var endTime=$("input[name='endTime']").val();
		    var  startStr = (startTime).replace(/-/g,"/");
		    var endStr = (endTime).replace(/-/g,"/");
		    var startDate = new Date(startStr);
		    var endDate = new Date(endStr);
		    var num = (endDate-startDate)/(1000*3600*24);		    
		    if(num<0){
		    	window.top.alert("开始时间不能大于结束时间",1);
		    	return false;
		    }		    
			alert("确定“提交请假审批”操作？",2,function(){
				leaveSts="1";
				leaveSave(obj,leaveSts);				
			},function(){				
				leaveSave(obj,leaveSts);
			});
		});
	}
	
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
		
	};
}(window, jQuery);


