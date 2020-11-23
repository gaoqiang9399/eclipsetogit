;
var MfBusGpsReg_InsertList = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content"
		});
	};
	//新增gps信息
	var _addGpsFormInfo = function(){
		top.flag = false;
		top.gpsListInfo = "";
		top.openBigForm(webPath + "/mfBusGpsReg/inputForList?appId="+appId, "GPS登记", addGpsFormInfoCall);
	};
	
	//gps登记保存后回调
	var addGpsFormInfoCall = function(){
		if(top.flag){
			$("#gpsInfoListDiv").html("").html(top.gpsListInfo);
		}
	};
	
	//gps流程提交
	var _submitAjax = function(){
		var url = webPath+"/mfBusGpsReg/validateGpsInfoAjax";
		$.ajax({
			url : url,
			data : {"appId" : appId},
			async : false,
			success : function(data) {
				if (data.flag == "success") {
					if(data.complete == "0"){
						$("#gpsInfoListDiv").html("").html(data.tipMsg);
						window.top.alert(data.msg, 3);
					}else{
						alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
							$.ajax({
								url : webPath+"/mfLoanApply/commitProcessAjax",
								data : {"appId" : appId},
								async : false,
								success : function(data) {
									if (data.flag == "success") {
										window.top.alert(data.msg, 3);
										_initGpsListInfo(appId);
										myclose_click();
									} 
								},error : function() {
									alert(top.getMessage("ERROR_SERVER"),0);
								}
							});
						});
					} 
				}
			},
			error : function() {
				alert(top.getMessage("ERROR_SERVER"),0);
			}
		});
	};
	
	//加载gps列表信息
	var _initGpsListInfo = function(appId){
		$.ajax({
			url : webPath + "/mfBusGpsReg/getGpsInfoListAjax",
			data : {"appId" : appId},
			async : false,
			success : function(data) {
				if(data.flag == "success"){
					//流程提交后gps列表回显
					top.flag=true;
					top.gpsListUpdateFlag=true;//表示是否是GPS节点
					top.gpsListInfo = data.tableHtml;
				}
			}
		})
	};
	return {
		init : _init,
		submitAjax : _submitAjax,
		addGpsFormInfo : _addGpsFormInfo
	};
}(window, jQuery);