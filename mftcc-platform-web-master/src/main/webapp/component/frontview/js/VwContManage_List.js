;
var VwContManageList = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		 myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/vwContManage/findByPageAjax",//列表数据查询的url
		    	tableId:"tablevwcont0001",//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	pageSize:30//加载默认行数(不填为系统默认行数)
		    });
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init
	};
}(window, jQuery);
function deleteThis(url){
	 window.top.alert("确定要删除此条信息吗？", 2,function(){
	 	$.ajax({
	 		url:url,
	 		dataType:'json',
	 		type:'post',
	 		success : function(data){
	 			if (data.flag == "success") {
					window.top.alert(data.msg, 1);
					updateTableData();//重新加载列表数据
				} else {
					window.top.alert(data.msg, 0);
				}
	 		}
	 	})
	 });
}
function finForm_input(url){//新增弹框
	top.addFlag = false;
	top.createShowDialog(url,"新增菜单内容",'90','90',function(){
		if(top.addFlag){
			 updateTableData();//重新加载列表数据
   		}
	});
}
function finForm_detail(url){//详情弹框
	top.updateFlag = false;
	top.createShowDialog(url,"菜单内容详情",'90','90',function(){
		if(top.updateFlag){
			 updateTableData();//重新加载列表数据
   		}
	});
}
//列表链接跳转
function goUrl(formStr){
	var start=formStr.indexOf("=");
	var end=formStr.indexOf("&");
	var url=formStr.substring(start+1,end);
	window.open(url);
}