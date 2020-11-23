;
var MfOaMattersReported_List = function(window, $) {
	var _init = function() {
		 myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfOaMattersReported/findByPageAjax", //列表数据查询的url
				tableId : "tablemattersreported0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30,//加载默认行数(不填为系统默认行数)
				ownHeight : true
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
	};
	//申请新增
	 var _finForminput = function(){
	 	top.window.openBigForm(webPath+'/mfOaMattersReported/input', '事项呈报申请',function(){
	 		window.updateTableData();
	 	});
	 };
	//查看详情   详情弹框
	 var _getByIdThis =function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
	 	top.openBigForm(url+"&entryFlag=1","查看申请详情",function(){
	 		//回调处理
	 		window.updateTableData();
		});
	 };
	 var _printApproveTable = function(obj,url){
			var oaAppId = url.split("?")[1].split("=")[1];
			var poCntJson = {
					filePath : "",
					fileName : fileName,
					oaAppId : oaAppId,
					saveBtn : "0",
					fileType : 0
				};
			mfPageOffice.openPageOffice(poCntJson);
		};
	return {
		init:_init,
		finForminput:_finForminput,
		getByIdThis:_getByIdThis,
		printApproveTable:_printApproveTable
	};
}(window, jQuery);
