var PssUnit_List = function(window, $) {

	var _refreshTableDataSin = function() {
		var dataParam = '[{"unitType":"1"}]'; 
		myCustomScrollbar({
			obj : "#contentsin",//页面内容绑定的id
			url : webPath+"/pssUnit/findByPageSingleAjax",//列表数据查询的url
			data:{ajaxData:dataParam},
			tableId : "tablepssunitsingle01",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			callback : function(obj) {
				$('.footer_loader').remove();
				$("#contentsin").tableRcswitcher({
		    		name:"invalidFlag",onText:"启用",offText:"停用"});
	    	}
		});
	};
	
	var _refreshTableDataMuti = function() {
		var dataParam = '[{"unitType":"2"}]'; 
		myCustomScrollbar({
			obj : "#contentmuti",//页面内容绑定的id
			url : webPath+"/pssUnit/findByPageMutiAjax",//列表数据查询的url
			data:{ajaxData:dataParam},
			tableId : "tablepssunitmuti01",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
			callback : function(obj) {
				$('.footer_loader').remove();
				$("#contentmuti").tableRcswitcher({
		    		name:"invalidFlag",onText:"启用",offText:"停用"});
	    	}
		});
	};
	
	var _init = function() {
		_refreshTableDataSin();
		_refreshTableDataMuti();
		$("table.table-float-head").remove();
//		$('.pss_detail_list_sp').css('height', '50%');
		
	};
	var _addSingleUnit=function(){
		top.createShowDialog(webPath+"/pssUnit/getInputPage?unitType=1","新增", '500px', '600px',function(){
			_init();
		});
	};
	var _addMutiUnit=function(){
		top.createShowDialog(webPath+"/pssUnit/getInputPage?unitType=2", '新增', '500px', '600px', function(){
			_init();
		});
	};
	var _updateUnit=function(url){
			top.createShowDialog(url,"详情", '500px', '600px',function(){
				_init();
			});
	};
	var _updateMutiUnit=function(url){
		top.createShowDialog(url,"详情", '500px', '600px',function(){
			_init();
		});
};
	return {
		refreshTableDataSin : _refreshTableDataSin,
		refreshTableDataMuti : _refreshTableDataMuti,
		addSingleUnit:_addSingleUnit,
		addMutiUnit:_addMutiUnit,
		updateUnit:_updateUnit,
		updateMutiUnit:_updateMutiUnit,
		init : _init
	};
}(window, jQuery);

window.onresize = function(){
	setTimeout(function(){
		$('.pss_detail_list_sp').css('height', '280');
		$('.pss_detail_list_sp #mCSB_1').css('height', 'auto');
	    $('.pss_detail_list_sp #mCSB_2').css('height', 'auto');
	    $('.table-float-head').remove(); 
	},50);
};