;
var CreditResultHis=function(window, $){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		MfCreditQueryRecordInfo.showCreditHisByPage();
	};
	
	//征信结果页面返回到客户业务汇总信息
	var _back=function(){
		//刷新查询历史列表数据
		$("#closeButton").show();
		$("#cusAppLyInfo").show();
		$("#backButton").hide();
		$("#creditContent").hide();
		$(".footer_loader").show();
		$(".table-float-head").show();
		MfCreditQueryRecordInfo.showCreditHisByPage();
	};
	//刷新征信查询历史列表
	var _refreshCreditQueryHis=function(){
		$.ajax({
	 		url:webPath+"/mfCreditQueryRecordInfo/getCreditQueryHisHtmlAjax",
	 		dataType:'json',
	 		type:'post',
	 		data:{cusNo:cusNo},
	 		success : function(data){
	 			if (data.flag == "success") {
	 				var htmlStr=data.htmlStr;
	 				$("#creditQueryHisContentList").html(htmlStr);
				} else {
					
				}
	 		}
	 	});
	};
	return{
		init:_init,
		back:_back,
		refreshCreditQueryHis:_refreshCreditQueryHis
	}
}(window, jQuery);