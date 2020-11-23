;
var MfCusGroup_List = function(window, $) {
	var _init = function() {
		/*myCustomScrollbar({
			obj : "#content", // 页面内容绑定的id
			url : webPath+"/MfCusGroup/findByPageAjax", // 列表数据查询的url
			tableId : "tablehuman0001", // 列表数据查询的table编号
			tableType : "thirdTableTag", // table所需解析标签的种类
			pageSize : 30,
			ownHeight : true
		// 加载默认行数(不填为系统默认行数)
		});*/
	};
	//校验是否配置客户类型
	var _checkCusType = function(){
			var url = webPath+"/mfCusGroup/checkCusTypeAjax";
			jQuery.ajax({
				url:url,
				data:{},
				type:"POST",
				dataType:"json",
				success:function(data){
					if(data.flag == "success"){
						top.window.openBigForm(webPath+'/mfCusGroup/input', '集团客户新增',function(){
					 		window.updateTableData();
					 	});

					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		
	};
	//申请新增
	 var _finForminput = function(){
	 	top.window.openBigForm(webPath+'/mfCusGroup/input', '集团客户新增',function(){
	 		window.updateTableData();
	 	});
	 };
	 //详情页面
	 var _getDetailPage = function (obj,url){
			top.LoadingAnimate.start();
			window.location.href=url;			
		};
	return {
		init:_init,
		finForminput:_finForminput,
		getDetailPage:_getDetailPage,
		checkCusType:_checkCusType,
	};
}(window, jQuery);
