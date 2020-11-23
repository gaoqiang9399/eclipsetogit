;
var WkfApprovalUser = function(window, $) {
	var _init = function () {
		var tableId = "tablewkf0016";
		if(multipleFlag=="1"){
			tableId = "tablewkf0008";
			$("body").addClass("multi-select-list");
			$(".formRowCenter").removeClass("hide");
		}
		if("163" == pasSubType || "164" == pasSubType){
            tableId = "tablewkf0016_GCDB";
		}
		 myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/wkfApprovalUser/getUserForTaskByPageAjax",//列表数据查询的url
	    	data:{"taskId":taskId,"processId":processId,"nodeName":nodeName,'committeeFlag':committeeFlag,'ifFilterFlag':ifFilterFlag,'committeeMember':committeeMember,'creditAppId':creditAppId,pasSubType:pasSubType},
	    	tableId:tableId,//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	pageSize:30//加载默认行数(不填为系统默认行数)
		 });
	};
	
	//单选
	var _singleSelect = function(obj,url){
		var resObj = StringUtil.urlParamsToObj(url);
		if(resObj.wkfUserName == "") {
			alert("请选择审批人！");
			return;
		}
		parent.dialog.get('nextUserDialog').close(resObj).remove();
	};
	//多选
	var _multiSelect = function(){
		var opNo = "";
		var opName="";
		$("#content").find("tbody tr").each(function(index){
			$thisTr = $(this);
			if($thisTr.find("input[type=checkbox]").is(':checked')) {
				opNo = opNo+$thisTr.find("input[name=displayname]").val()+",";
				opName = opName + $thisTr.find("input[name=wkfUserName]").val()+",";
				
			}
		});
		var resObj = new Object();
		resObj.displayName = opNo.substring(0,opNo.length-1);
		resObj.wkfUserName = opName.substring(0,opName.length-1);
		parent.dialog.get('nextUserDialog').close(resObj).remove();
	};
	
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		singleSelect : _singleSelect,
		multiSelect : _multiSelect,
	};
}(window, jQuery);