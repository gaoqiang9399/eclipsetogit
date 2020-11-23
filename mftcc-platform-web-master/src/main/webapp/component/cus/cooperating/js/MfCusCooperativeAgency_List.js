;
var MfCusCooperativeAgency_List = function(window, $) {
	/** 初始化 */
	var _init = function() {
		 myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusCooperativeAgency/findByPageAjax",//列表数据查询的url
			    	tableId:"tablecuscoop00001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
                    topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
         });
	};

	/** 新增 */
	var _input = function(url) {// 新增弹框
		top.addFlag = false;
		top.createShowDialog(url, "新增受益人", '90', '90', function() {
			if (top.addFlag) {
				updateTableData();// 重新加载列表数据
			}
		});
	};

	/** 详情 */
	var _getDetailPage = function(obj, url) {
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
        top.addFlag = false;
        top.createShowDialog(url, "受益人信息", '90', '90', function() {
            if (top.addFlag) {
                updateTableData();// 重新加载列表数据
            }
        });
	};

	//删除方法
	var _ajaxDelete = function(obj,url){
			alert(top.getMessage("CONFIRM_DELETE"),2,function(){
		 	$.ajax({
		 		url:webPath+url,
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
		 	});
		});
		
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		input : _input,
		getDetailPage : _getDetailPage,
		ajaxDelete:_ajaxDelete
	};
}(window, jQuery);
