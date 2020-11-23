var mfCusCreditModelList = function(window, $){
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/mfCusCreditModel/findByPageAjax",//列表数据查询的url
			tableId : "tablecreditmodel0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30
		});
		
	};
	//新增模型配置
	var _newCreditModel = function(obj){
		var url = webPath+"/mfCusCreditModel/input";
 		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
		$(top.window.document).find("#showDialog").remove();
	};
	
	//编辑操作
	var _updateCreditModel = function(obj,url){
 		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
		$(top.window.document).find("#showDialog").remove();
	}; 
	
	//删除操作
	var _deleteCreditModel = function(obj,url){
		var modelId = url.split("?")[1].split("=")[1];
		var actionUrl = url.split("?")[0];
		$.ajax({
			url:actionUrl,
			type:"post",
			data:{
				modelId:modelId
			},
			success:function(data){
				if(data.flag == "success"){
					window.top.alert(data.msg, 1);
					//window.location.href = path+"/MfCusCreditModelAction_getListPage.action";
					window.location.reload(true);  //强制刷新页面
				}else{
					window.top.alert(data.msg, 1);
					//ajaxTrDelete(obj,url);
				}
			},
			error:function(){
				alert("删除表单请求异常");
			}
		});
	};
	
	return{
		init:_init,
		newCreditModel:_newCreditModel,
		updateCreditModel:_updateCreditModel,
		deleteCreditModel:_deleteCreditModel
	};
}(window, jQuery);