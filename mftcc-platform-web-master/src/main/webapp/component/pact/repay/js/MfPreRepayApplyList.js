
var MfPreRepayApplyList = function(window,$){
	//提前还款申请新增
	var _applyInsert = function(){
		top.window.openBigForm(webPath+"/mfPreRepayApply/input","提前还款申请",function(){
			updateTableData(true);
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
		top.preRepayAppId = resObj.preRepayAppId;
		top.window.openBigForm(url,"提前还款",function(){
			if(top.flag){
				//更新提前还款申请信息
				$.ajax({
					url:webPath+"/mfPreRepayApply/updateAppStsAjax",
					type:'post',
					data : {"preRepayAppId":top.preRepayAppId,"preRepayAppSts":"4"},
					dataType:'json',
					success:function(data){
						updateTableData(true);	
					},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}
		});
	};
	
	
	
	var _getDetialPage = function(obj,url){	
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.LoadingAnimate.start();		
		top.window.openBigForm(url,"贷后详情",function(){
			updateTableData(true);
		});		
	};
	return{ 
		applyInsert:_applyInsert,
		getDetailPage:_getDetialPage,
		getPreRepayDetialPage:_getPreRepayDetialPage,
	};
	 
}(window,jQuery);