var MfFundPlan_List = function(window, $) {
	var _init = function(){
		// 加载列表数据
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath + "/mfFundPlan/findByPageAjax",//列表数据查询的url
			tableId : "tablefundplan0001",//列表数据查询的table编号
			tableType : "tableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			callback:function(){
				$(".opBtn a").each(function(i,item){
					if($(item).parent().prev().find("input[name='opNo']").val() == regNo){
						$(item).text("编辑");
					}else{
						$(item).text("详情");
					}
				});
			}
		});
	};
	/**
	 * 新增记录
	 * @param obj
	 */
	var _applyInsert = function(obj){
		top.openBigForm(webPath+"/mfFundPlan/input","新增", function(){
			updateTableData();
		});
	};
	/**
	 *更新记录
	 * @param obj
	 */
	var _applyUpdate = function(obj,url){
		var opNo = url.split("?")[1].split("&")[0].split("=")[1];
		var title = "详情";
		if(regNo == opNo){
			title = "编辑";
		}else{
			title = "详情";
		}
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url,title, function(){
			updateTableData();
		});
	};
	/**
	 *创造资金计划表
	 * @param obj
	 */
	var _createFundPlanTable = function(obj){
		window.location.href=webPath+"/mfFundPlan/createFundPlanTable";
	};
	
	var _getFundDetail = function(obj,url){
		top.openBigForm(url, "详情", function(){
			//刷新预收列表
			_init();
		});
	};
	return {
		init:_init,
		applyInsert : _applyInsert,
		applyUpdate : _applyUpdate,
		createFundPlanTable : _createFundPlanTable,
		getFundDetail:_getFundDetail
	};
}(window, jQuery);