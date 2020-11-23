;
var OaDebtRerurnHisDetail = function(window, $) {
	var _init = function () {	
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				theme:"minimal-dark",
//				updateOnContentResize:true
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
		_bindUpdateAjax("#OaDebtRerurnHisDetail");
	};
	
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	}; 
	 var _endTimeChange = function(){
	      totalTime();
	  }
	  
	  function totalTime(){
	       var startTime=$("input[name='startTime']").val();
	       var endTime=$("input[name='endTime']").val();
	       if(startTime!="" && endTime!=""){
	          var startStr = (startTime).replace(/-/g,"/");
	          var endStr = (endTime).replace(/-/g,"/");
	          var startDate = new Date(startStr);
	          var endDate = new Date(endStr);
	          var num = (endDate-startDate)/(1000*3600*24);
	          
	          $("input[name='timeSum']").val((num>0)?num:0+"天");
	       }
	  }
	var _bindUpdateAjax = function(obj){
		$(".updateAjax").bind("click", function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				alert("url");
				var dataParam = JSON.stringify($(obj).serializeArray());
				loadingAnimate();
				jQuery.ajax({
							url : url,
							data : {
								ajaxData : dataParam
							},
							type : "POST",
							dataType : "json",
							beforeSend : function() {
							},
							success : function(data) {
								loadingAnimateClose();
								if (data.flag == "success") {
									//					  alert(top.getMessage("SUCCEED_OPERATION"),1);
									top.addFlag = true;
									if (data.htmlStrFlag == "1") {
										top.htmlStrFlag = true;
										top.htmlString = data.htmlStr;
									}
									$(top.window.document).find(
											"#bigFormShow .close").click();

									if (callback
											&& typeof (callback) == "function") {
										callback.call(this, data);
									}
								} else if (data.flag == "error") {
									alert(data.msg, 0);
								}
							},
							error : function(data) {
								loadingAnimateClose();
								alert(top.getMessage("FAILED_OPERATION"," "), 0);
							}
						});
			}
		});
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
		endTimeChange:_endTimeChange
	};
}(window, jQuery);