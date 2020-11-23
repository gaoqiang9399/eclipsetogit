;
var MfTagColumnConfig_List=function(window,$){
	var _init=function(){
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath+"/mfTagColumnConfig/findByPageAjax", //列表数据查询的url
			tableId : "tabletagColumnConfig0001", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30, //加载默认行数(不填为系统默认行数)
			data:{
				keyNo:keyNo
			}
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		   });
	};
	//新增
	var _addTagColumnConfig=function(){
		var url=webPath+"/mfTagColumnConfig/input?keyNo="+keyNo;
		top.openBigForm(url,'新增表格列',function(){
			_init();
		});
	};
	//编辑
	var _editTagColumnConfig=function(obj,url){
		top.openBigForm(url,'编辑表格列',function(){
			_init();
		});
	};
	return{
		init:_init,
		addTagColumnConfig:_addTagColumnConfig,
		editTagColumnConfig:_editTagColumnConfig
	};
}(window,jQuery);