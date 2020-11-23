;
var MfBusAppFee_CriterionList = function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	var _getPreRepayDetialPage = function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.flag=false;
		var resObj = StringUtil.urlParamsToObj(url);
		top.appId = resObj.appId;
		top.window.openBigForm(url,"费用标准完善",function(){

		});
	};
	
	var _advanceCollectFee = function(obj,url){
		top.openBigForm(url, "预收", function(){
			//刷新预收列表
			$.ajax({
		 		type:"post",
		 		url:webPath+"/mfBusAppFee/getBusAppFeeListHtmlStrAjax",
		 		data:{appId:appId,tableId:"tablefeeCriterionList"},//
		 		async: false,
		 		beforeSend:function(){
		 			LoadingAnimate.start();
				},success:function(data){
					if(data.flag=="success"){
						$("#feeCriterionList").html(data.htmlStr);
					}
		 		},
		 		complete : function() {
		 			if (LoadingAnimate) {
		 				LoadingAnimate.stop();
					}
		 		}
		 	});
		});
	};
	return{
		init:_init,
		getPreRepayDetialPage:_getPreRepayDetialPage,
		advanceCollectFee:_advanceCollectFee
	}
}(window,jQuery);