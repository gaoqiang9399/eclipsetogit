;
var MfMsgPledgeList = function(window, $) {
	//初始化
	var _init = function(){
		$(function(){
			myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath + "/mfMsgPledge/findByPageAjax", //列表数据查询的url
				tableId : "tablemfmsgpledge0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30, //加载默认行数(不填为系统默认行数)
				callback : function() {
					//$("#tablist td[mytitle]").initMytitle();
					$("table").tableRcswitcher({
						name : "useFlag",
						onText : "启用",
						offText : "停用"
					});
				}// 方法执行完回调函数（取完数据做处理的时候）
			});
		});

	};
	//新增押品预警配置信息
	var _addMfMsgPledge = function(classId,classSecondName){
		var url = webPath + "/mfMsgPledge/input";
		top.window.openBigForm(url,"押品预警配置",function(){
			window.updateTableData();
		});
	};
	//查看押品预警配置信息详情
	var _getMfMsgPledge = function(obj,urlArgs){
		var url = webPath + urlArgs;
		top.window.openBigForm(url,"押品预警配置",function(){
			window.updateTableData();
		});
	};
	return {
		init : _init,
		addMfMsgPledge:_addMfMsgPledge,
		getMfMsgPledge:_getMfMsgPledge
	};
}(window, jQuery);