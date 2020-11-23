;
var MfCusOaWhitename = function(window, $) {
	var _getById = function (obj ,url){
		var title = "白名单信息";
		top.window.openBigForm(url,title,function(){});
	};
	//查询紧急联系人的信息
	var _getMfDiscountManageList=function(){
		var url = webPath+"/mfCusContactlist/findByPageAjax?idCardNo="+idcardno;
		$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		success:function(data){ 
			var tableHtmlDiscountManage=data.tableHtmlDiscountManage;
			$("#mfDiscountManageList").html(tableHtmlDiscountManage);
		},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	  
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		getById : _getById,
		getMfDiscountManageList : _getMfDiscountManageList
	};
}(window, jQuery);