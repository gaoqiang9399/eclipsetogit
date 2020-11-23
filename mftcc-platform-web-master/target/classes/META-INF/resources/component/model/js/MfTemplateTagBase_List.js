;
var MfTemplateTagBase_List=function(window,$){
	var _init=function(){
		$(function(){
		    myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/mfTemplateTagBase/findByPageAjax",//列表数据查询的url
		    	tableId:"tabletagbase0001",//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	pageSize:30,//加载默认行数(不填为系统默认行数)
		    	callback:function(){
		    		$("table").tableRcswitcher({
		    		name:"useFlag",onText:"启用",offText:"禁用"});
		    	}//方法执行完回调函数（取完数据做处理的时候）
		    });
		 });
	};
	//新增标签
	var _addTemplateTagBase=function(){
		var url = webPath+"/mfTemplateTagBase/input";
		 top.openBigForm(url,'新增标签',function(){
			 
		 });
	};
	//标签编辑
	var _getTemplateTagBaseDetail=function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
	 top.openBigForm(url,'编辑标签',function(){
		 
	 });
	};
	//表头配置
	var _tableHeadConfig=function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		 top.openBigForm(url,'表头配置',function(){
			 
		 });
	};
	//表格列配置
	var _tableColumnConfig=function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		 top.openBigForm(url,'表格列配置',function(){
			 
		 });
	};
	return{
		init:_init,
		addTemplateTagBase:_addTemplateTagBase,
		getTemplateTagBaseDetail:_getTemplateTagBaseDetail,
		tableHeadConfig:_tableHeadConfig,
		tableColumnConfig:_tableColumnConfig
	};
}(window,jQuery);